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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SieDocumentWriter {
    private SieDocument sieDoc;
    private BufferedWriter writer;

    public SieDocumentWriter(SieDocument sie) throws Exception {
        sieDoc = sie;
    }

    public void write(String fileName) throws Exception {
        File file = new File(fileName);
        if (file.exists()) file.delete();

        writer = IoUtil.getWriter(fileName);
        writeLine(getFLAGGA());
        writeLine(getPROGRAM());
        writeLine(getFORMAT());
        writeLine(getGEN());
        writeLine(getSIETYP());
        if (!StringUtil.isNullOrEmpty(sieDoc.getPROSA())) {
            writeLine(SIE.PROSA + " \"" + sieDoc.getPROSA() + "\"");
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
        writer.close();
    }

    private void writeVALUTA() throws Exception {
        if (!StringUtil.isNullOrEmpty(sieDoc.getVALUTA())) {
            writeLine(SIE.VALUTA + " " + sieDoc.getVALUTA());
        }

    }

    private void writeVER() throws Exception {
        for (SieVoucher v : sieDoc.getVER()) {
            String createdBy = StringUtil.equals(v.getCreatedBy(), "") ? "" : " \"" + v.getCreatedBy() + "\"";
            String createdDate = v.getCreatedDate() == 0 ? "" : String.valueOf(v.getCreatedDate());
            writeLine(SIE.VER + " \"" + v.getSeries() + "\" \"" + v.getNumber() + "\" " + makeSieDate(v.getVoucherDate())
                    + " \"" + v.getText() + "\" " + createdDate + createdBy);
            writeLine("{");
            for (SieVoucherRow r : v.getRows()) {
                String obj = getObjeklista(r.getObjects());
                String quantity;
                if (r.quantity != null) {
                    quantity = sieAmount(r.quantity);
                } else {
                    quantity = "";
                }
                createdBy = StringUtil.equals(v.getCreatedBy(), "") ? "" : "\"" + v.getCreatedBy() + "\"";
                // TODO: improve null checking
                if (r.getAccount() != null) {
                    writeLine(SIE.TRANS + " " +
                            r.getAccount().getNumber() + " " +
                            obj + " " +
                            sieAmount(r.getAmount()) + " " +
                            makeSieDate(r.getRowDate()) + " \"" +
                            r.getText() + "\" " +
                            quantity + " " +
                            createdBy);
                }
            }
            writeLine("}");
        }
    }

    private String getObjeklista(List<SieObject> objects) throws Exception {
        if (sieDoc.getSIETYP() < 3)
            return "";

        String ret = "{";
        if (objects != null) {
            for (SieObject o : objects) {
                ret += o.getDimension().getNumber();
                ret += " \"" + o.getNumber() + "\" ";
            }
        }

        ret += "}";
        return ret;
    }

    private void writeDIM() throws Exception {
        for (SieDimension d : sieDoc.getDIM().values()) {
            writeLine(SIE.DIM + " " + d.getNumber() + " \"" + d.getName() + "\"");
            for (SieObject o : d.objects.values()) {
                writeLine(SIE.OBJEKT+" " + d.getNumber() + " " + o.getNumber() + " \"" + o.getName() + "\"");
            }
        }
    }

    private void writePeriodValue(String name, List<SiePeriodValue> list) throws Exception {
        for (SiePeriodValue v : list) {
            String objekt = getObjeklista(v.getObjects());
            if ((SIE.IB+SIE.UB+SIE.RES).contains(name)) {
                objekt = "";
            }
            // TODO: improve null checking
            if (v.getAccount() != null) {
                writeLine(name + " " +
                        String.valueOf(v.getYearNr()) + " " +
                        v.getAccount().getNumber() + " " +
                        objekt + " " +
                        sieAmount(v.getAmount()));
            }
        }
    }

    private void writePeriodSaldo(String name, List<SiePeriodValue> list) throws Exception {
        for (SiePeriodValue v : list) {
            String objekt = getObjeklista(v.getObjects());
            // TODO: improve null checking
            if (v.getAccount() != null) {
                writeLine(name + " " +
                        String.valueOf(v.getYearNr()) + " " +
                        String.valueOf(v.getPeriod()) + " " +
                        v.getAccount().getNumber() + " " +
                        objekt + " " +
                        sieAmount(v.getAmount()));
            }
        }
    }

    private void writeRAR() throws Exception {
        for (SieBookingYear r : sieDoc.getRars().values()) {
            writeLine(SIE.RAR+" " + String.valueOf(r.getId()) + " " + sieDate(r.getStart()) + " " + sieDate(r.getEnd()));
        }
    }

    private String sieDate(Date date) throws Exception {
        if (date != null) {
            SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
            return f.format(date);
        } else {
            return "";
        }
    }

    private String sieAmount(BigDecimal amount) throws Exception {
        return amount.toPlainString();
        //return String.valueOf(amount).replace(',', '.');
    }

    private String sieAmount(Integer amount) throws Exception {
        return String.valueOf(amount).replace(',', '.');
    }

    private void writeKONTO() throws Exception {
        for (SieAccount k : sieDoc.getKONTO().values()) {
            writeLine(SIE.KONTO+" " + k.getNumber() + " \"" + k.getName() + "\"");
            if ((k.getUnit() != null) && !k.getUnit().trim().isEmpty()) {
                writeLine(SIE.ENHET+" " + k.getNumber() + " \"" + k.getUnit() + "\"");
            }

            if (k.getType() != null && !k.getType().trim().isEmpty()) {
                writeLine(SIE.KTYP+" " + k.getNumber() + " " + k.getType());
            }

        }
        for (SieAccount k : sieDoc.getKONTO().values()) {
            for (String s : k.getSRU()) {
                writeLine(SIE.SRU+" " + k.getNumber() + " " + s);
            }
        }
    }

    private void writeOMFATTN() throws Exception {
        if (sieDoc.getOMFATTN() != null) {
            writeLine(SIE.OMFATTN+" " + sieDate(sieDoc.getOMFATTN()));
        }

    }

    private void writeTAXAR() throws Exception {
        if (sieDoc.getTAXAR() > 0) {
            writeLine(SIE.TAXAR+" " + String.valueOf(sieDoc.getTAXAR()));
        }

    }

    private void writeFTYP() throws Exception {
        String orgType = sieDoc.getFNAMN().getOrgType();
        if (orgType != null && !orgType.trim().isEmpty()) {
            writeLine(SIE.FTYP+" " + orgType);
        }

    }

    private void writeKPTYP() throws Exception {
        String kpTyp = sieDoc.getKPTYP();
        if (kpTyp != null && !kpTyp.trim().isEmpty()) {
            writeLine(SIE.KPTYP+" " + sieDoc.getKPTYP());
        }

    }

    private void writeADRESS() throws Exception {
        if (!(sieDoc.getFNAMN().getContact() == null && sieDoc.getFNAMN().getStreet() == null && sieDoc.getFNAMN().getZipCity() == null && sieDoc.getFNAMN().getPhone() == null)) {
            writeLine(SIE.ADRESS+" \"" + sieDoc.getFNAMN().getContact() + "\" \"" + sieDoc.getFNAMN().getStreet() +
                    "\" \"" + sieDoc.getFNAMN().getZipCity() + "\" \"" + sieDoc.getFNAMN().getPhone() + "\"");
        }

    }

    private String getFNAMN() throws Exception {
        return SIE.FNAMN+" \"" + sieDoc.getFNAMN().getName() + "\"";
    }

    private String getORGNR() throws Exception {
        return SIE.ORGNR+" " + sieDoc.getFNAMN().getOrgIdentifier();
    }

    private void writeFNR() throws Exception {
        String code = sieDoc.getFNAMN().getCode();
        if (code != null && !code.trim().isEmpty())
            writeLine(SIE.FNR+" \"" + sieDoc.getFNAMN().getCode() + "\"");

    }

    private String getSIETYP() throws Exception {
        return SIE.SIETYP+" " + String.valueOf(sieDoc.getSIETYP());
    }

    private String getGEN() throws Exception {
        String ret = SIE.GEN+" ";
        ret += makeSieDate(sieDoc.getGEN_DATE()) + " ";
        ret += makeField(sieDoc.getGEN_NAMN());
        return ret;
    }

    private String getFORMAT() throws Exception {
        return SIE.FORMAT+" PC8";
    }

    private String getPROGRAM() throws Exception {
        String program = SIE.PROGRAM+" ";
        for (String s : sieDoc.getPROGRAM()) {
            program += "\"SIEParser\" " + makeField(s) + " ";
        }
        return program;
    }

    private String getFLAGGA() throws Exception {
        return SIE.FLAGGA+" " + String.valueOf(sieDoc.getFLAGGA());
    }

    private void writeLine(String line) throws Exception {
        writer.write(line);
        writer.newLine();
    }

    private String makeField(String data) throws Exception {
        try {
            Integer.parseInt(data);
            return data;
        } catch (NumberFormatException e) {
            return "\"" + data + "\"";
        }
    }

    private String makeSieDate(Date date) throws Exception {
        if (date != null) {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            return df.format(date);
        } else {
            return "00000000";
        }
    }

}


