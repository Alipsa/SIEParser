/*
MIT License

Copyright (c) 2015 Johan Idstam
Modifications by Per Nyfelt Copyright (c) 2016 Alipsa HB

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package alipsa.sieparser;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Writes a {@link SieDocument} to a SIE file.
 * Produces output conforming to the SIE file format specification using IBM437 encoding.
 */
public class SieDocumentWriter {
    private static final DateTimeFormatter SIE_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

    private SieDocument sieDoc;
    private BufferedWriter writer;
    private WriteOptions options;

    /**
     * Creates a writer for the given SIE document with default options.
     * @param sie the document to write
     */
    public SieDocumentWriter(SieDocument sie) {
        sieDoc = sie;
        this.options = new WriteOptions();
    }

    /**
     * Creates a writer for the given SIE document with the specified options.
     * @param sie the document to write
     * @param options the write options, or {@code null} for defaults
     */
    public SieDocumentWriter(SieDocument sie, WriteOptions options) {
        sieDoc = sie;
        this.options = options != null ? options : new WriteOptions();
    }

    /**
     * Writes the SIE document to a file.
     * @param fileName the output file path
     * @throws IOException if an I/O error occurs
     */
    public void write(String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) file.delete();

        try (BufferedWriter bw = IoUtil.getWriter(fileName)) {
            writer = bw;
            writeContent();
        }
    }

    /**
     * Writes the SIE document to an output stream.
     * @param outputStream the output stream to write to
     * @throws IOException if an I/O error occurs
     */
    public void write(OutputStream outputStream) throws IOException {
        Charset charset = Encoding.getCharset();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, charset))) {
            writer = bw;
            writeContent();
        }
    }

    /**
     * Writes only the given vouchers to an output stream, without any header or metadata.
     * @param outputStream the output stream to write to
     * @param vouchers the vouchers to write
     * @throws IOException if an I/O error occurs
     */
    public void addVouchers(OutputStream outputStream, List<SieVoucher> vouchers) throws IOException {
        Charset charset = Encoding.getCharset();
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, charset))) {
            writer = bw;
            for (SieVoucher v : vouchers) {
                writeVoucherEntry(v);
            }
        }
    }

    private void writeContent() throws IOException {
        SieCRC32 crc = null;
        if (options.isWriteKSUMMA()) {
            crc = new SieCRC32();
            crc.start();
            writeLine(SIE.KSUMMA);
        }

        writeLine(getFLAGGA());
        writeLine(getPROGRAM());
        writeLine(getFORMAT());
        writeLine(getGEN());
        writeLine(getSIETYP());
        if (!StringUtil.isNullOrEmpty(sieDoc.getPROSA())) {
            writeLine(SIE.PROSA + " \"" + sieText(sieDoc.getPROSA()) + "\"");
        }
        writeFNR();
        writeLine(getORGNR());
        writeLine(getFNAMN());
        writeADRESS();
        writeFTYP();
        writeKPTYP();
        writeVALUTA();
        writeTAXAR();
        writeOMFATTN();
        writeRAR();
        writeDIM();
        writeUNDERDIM();
        writeKONTO();
        writePeriodValue(SIE.IB, sieDoc.getIB());
        writePeriodValue(SIE.UB, sieDoc.getUB());
        if (sieDoc.getSIETYP() >= 3) {
            writePeriodValue(SIE.OIB, sieDoc.getOIB());
            writePeriodValue(SIE.OUB, sieDoc.getOUB());
        }

        if (sieDoc.getSIETYP() > 1) {
            writePeriodSaldo(SIE.PBUDGET, sieDoc.getPBUDGET());
            writePeriodSaldo(SIE.PSALDO, sieDoc.getPSALDO());
        }

        writePeriodValue(SIE.RES, sieDoc.getRES());
        writeVER();

        if (options.isWriteKSUMMA() && crc != null) {
            writeLine(SIE.KSUMMA + " " + crc.checksum());
        }
    }

    private void writeVALUTA() throws IOException {
        if (!StringUtil.isNullOrEmpty(sieDoc.getVALUTA())) {
            writeLine(SIE.VALUTA + " " + sieDoc.getVALUTA());
        }
    }

    private void writeVER() throws IOException {
        for (SieVoucher v : sieDoc.getVER()) {
            writeVoucherEntry(v);
        }
    }

    private void writeVoucherEntry(SieVoucher v) throws IOException {
        String createdBy = StringUtil.equals(v.getCreatedBy(), "") ? "" : " \"" + v.getCreatedBy() + "\"";
        String createdDate = v.getCreatedDate() == null ? "" : makeSieDate(v.getCreatedDate());
        writeLine(SIE.VER + " \"" + v.getSeries() + "\" \"" + v.getNumber() + "\" " + makeSieDate(v.getVoucherDate())
                + " \"" + sieText(v.getText()) + "\" " + createdDate + createdBy);
        writeLine("{");
        for (SieVoucherRow r : v.getRows()) {
            String obj = getObjeklista(r.getObjects());
            String quantity;
            if (r.getQuantity() != null) {
                quantity = sieAmount(r.getQuantity());
            } else if (!StringUtil.isNullOrEmpty(r.getCreatedBy())) {
                // Write quoted empty string placeholder when createdBy follows
                quantity = "\"\"";
            } else {
                quantity = "";
            }
            String rowCreatedBy = StringUtil.equals(r.getCreatedBy(), "") ? "" : "\"" + r.getCreatedBy() + "\"";
            if (r.getAccount() != null) {
                // Fix: use r.getToken() instead of hardcoding SIE.TRANS
                writeLine(r.getToken() + " " +
                        r.getAccount().getNumber() + " " +
                        obj + " " +
                        sieAmount(r.getAmount()) + " " +
                        makeSieDate(r.getRowDate()) + " \"" +
                        sieText(r.getText()) + "\" " +
                        quantity + " " +
                        rowCreatedBy);
            }
        }
        writeLine("}");
    }

    private String getObjeklista(List<SieObject> objects) {
        if (sieDoc.getSIETYP() < 3) return "";

        StringBuilder ret = new StringBuilder("{");
        if (objects != null) {
            for (SieObject o : objects) {
                ret.append(o.getDimension().getNumber());
                ret.append(" \"").append(o.getNumber()).append("\" ");
            }
        }
        ret.append("}");
        return ret.toString();
    }

    private void writeDIM() throws IOException {
        for (SieDimension d : sieDoc.getDIM().values()) {
            writeLine(SIE.DIM + " " + d.getNumber() + " \"" + sieText(d.getName()) + "\"");
            for (SieObject o : d.getObjects().values()) {
                // Fix: quote object number
                writeLine(SIE.OBJEKT + " " + d.getNumber() + " \"" + o.getNumber() + "\" \"" + sieText(o.getName()) + "\"");
            }
        }
    }

    private void writeUNDERDIM() throws IOException {
        for (SieDimension d : sieDoc.getUNDERDIM().values()) {
            String superDim = d.getSuperDim() != null ? d.getSuperDim().getNumber() : "";
            writeLine(SIE.UNDERDIM + " " + d.getNumber() + " \"" + sieText(d.getName()) + "\" " + superDim);
        }
    }

    private void writePeriodValue(String name, List<SiePeriodValue> list) throws IOException {
        for (SiePeriodValue v : list) {
            String objekt = getObjeklista(v.getObjects());
            if ((SIE.IB + SIE.UB + SIE.RES).contains(name)) {
                objekt = "";
            }
            if (v.getAccount() != null) {
                writeLine(name + " " +
                        v.getYearNr() + " " +
                        v.getAccount().getNumber() + " " +
                        objekt + " " +
                        sieAmount(v.getAmount()));
            }
        }
    }

    private void writePeriodSaldo(String name, List<SiePeriodValue> list) throws IOException {
        for (SiePeriodValue v : list) {
            String objekt = getObjeklista(v.getObjects());
            if (v.getAccount() != null) {
                StringBuilder sb = new StringBuilder();
                sb.append(name).append(" ")
                  .append(v.getYearNr()).append(" ")
                  .append(v.getPeriod()).append(" ")
                  .append(v.getAccount().getNumber()).append(" ")
                  .append(objekt).append(" ")
                  .append(sieAmount(v.getAmount()));
                // Write quantity when present
                if (v.getQuantity() != null && v.getQuantity().compareTo(BigDecimal.ZERO) != 0) {
                    sb.append(" ").append(sieAmount(v.getQuantity()));
                }
                writeLine(sb.toString());
            }
        }
    }

    private void writeRAR() throws IOException {
        for (SieBookingYear r : sieDoc.getRars().values()) {
            writeLine(SIE.RAR + " " + r.getId() + " " + sieDate(r.getStart()) + " " + sieDate(r.getEnd()));
        }
    }

    private String sieDate(LocalDate date) {
        if (date != null) {
            return date.format(SIE_DATE_FORMAT);
        } else {
            return "";
        }
    }

    private String sieAmount(BigDecimal amount) {
        return amount.toPlainString();
    }

    private void writeKONTO() throws IOException {
        for (SieAccount k : sieDoc.getKONTO().values()) {
            writeLine(SIE.KONTO + " " + k.getNumber() + " \"" + sieText(k.getName()) + "\"");
            if ((k.getUnit() != null) && !k.getUnit().trim().isEmpty()) {
                writeLine(SIE.ENHET + " " + k.getNumber() + " \"" + k.getUnit() + "\"");
            }
            if (k.getType() != null && !k.getType().trim().isEmpty()) {
                writeLine(SIE.KTYP + " " + k.getNumber() + " " + k.getType());
            }
        }
        for (SieAccount k : sieDoc.getKONTO().values()) {
            for (String s : k.getSRU()) {
                writeLine(SIE.SRU + " " + k.getNumber() + " " + s);
            }
        }
    }

    private void writeOMFATTN() throws IOException {
        if (sieDoc.getOMFATTN() != null) {
            writeLine(SIE.OMFATTN + " " + sieDate(sieDoc.getOMFATTN()));
        }
    }

    private void writeTAXAR() throws IOException {
        if (sieDoc.getTAXAR() > 0) {
            writeLine(SIE.TAXAR + " " + sieDoc.getTAXAR());
        }
    }

    private void writeFTYP() throws IOException {
        String orgType = sieDoc.getFNAMN().getOrgType();
        if (orgType != null && !orgType.trim().isEmpty()) {
            writeLine(SIE.FTYP + " " + orgType);
        }
    }

    private void writeKPTYP() throws IOException {
        String kpTyp = sieDoc.getKPTYP();
        if (kpTyp != null && !kpTyp.trim().isEmpty()) {
            writeLine(SIE.KPTYP + " " + sieDoc.getKPTYP());
        }
    }

    private void writeADRESS() throws IOException {
        if (!(sieDoc.getFNAMN().getContact() == null && sieDoc.getFNAMN().getStreet() == null && sieDoc.getFNAMN().getZipCity() == null && sieDoc.getFNAMN().getPhone() == null)) {
            writeLine(SIE.ADRESS + " \"" + sieDoc.getFNAMN().getContact() + "\" \"" + sieDoc.getFNAMN().getStreet() +
                    "\" \"" + sieDoc.getFNAMN().getZipCity() + "\" \"" + sieDoc.getFNAMN().getPhone() + "\"");
        }
    }

    private String getFNAMN() {
        return SIE.FNAMN + " \"" + sieDoc.getFNAMN().getName() + "\"";
    }

    private String getORGNR() {
        return SIE.ORGNR + " " + sieDoc.getFNAMN().getOrgIdentifier();
    }

    private void writeFNR() throws IOException {
        String code = sieDoc.getFNAMN().getCode();
        if (code != null && !code.trim().isEmpty())
            writeLine(SIE.FNR + " \"" + sieDoc.getFNAMN().getCode() + "\"");
    }

    private String getSIETYP() {
        return SIE.SIETYP + " " + sieDoc.getSIETYP();
    }

    private String getGEN() {
        StringBuilder ret = new StringBuilder(SIE.GEN + " ");
        ret.append(makeSieDate(sieDoc.getGEN_DATE())).append(" ");
        ret.append(makeField(sieDoc.getGEN_NAMN()));
        return ret.toString();
    }

    private String getFORMAT() {
        return SIE.FORMAT + " PC8";
    }

    private String getPROGRAM() {
        StringBuilder program = new StringBuilder(SIE.PROGRAM + " ");
        if (sieDoc.getPROGRAM().isEmpty()) {
            program.append("\"SIEParser\" \"\"");
        } else {
            for (String s : sieDoc.getPROGRAM()) {
                program.append(makeField(s)).append(" ");
            }
        }
        return program.toString().trim();
    }

    private String getFLAGGA() {
        return SIE.FLAGGA + " " + sieDoc.getFLAGGA();
    }

    private void writeLine(String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }

    private String makeField(String data) {
        if (data == null) return "\"\"";
        try {
            Integer.parseInt(data);
            return data;
        } catch (NumberFormatException e) {
            return "\"" + data + "\"";
        }
    }

    private String makeSieDate(LocalDate date) {
        if (date != null) {
            return date.format(SIE_DATE_FORMAT);
        } else {
            return "00000000";
        }
    }

    /**
     * Escapes quotation marks in text fields for SIE output.
     */
    private String sieText(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"");
    }
}
