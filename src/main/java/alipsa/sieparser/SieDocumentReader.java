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

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;

/**
 * Reads and parses a SIE file into a {@link SieDocument}.
 * Supports SIE types 1 through 4 (including 4i).
 * Provides callback hooks via {@link SieCallbacks} for streaming processing.
 */
public class SieDocumentReader {

    public SieCallbacks callbacks = new SieCallbacks();
    public boolean ignoreBTRANS = false;
    public boolean ignoreMissingOMFATTNING = false;
    public boolean ignoreRTRANS = false;
    public boolean allowUnbalancedVoucher = false;
    public boolean ignoreKSUMMA = false;
    public boolean allowUnderDimensions = false;
    public boolean ignoreMissingDIM = false;
    private EnumSet<SieType> acceptSIETypes = null;
    private SieDocument sieDocument;
    private List<Exception> validationExceptions;
    public boolean streamValues = false;
    public SieCRC32 CRC = new SieCRC32();
    public boolean throwErrors = true;
    private String fileName;
    private int parsingLineNumber = 0;

    public static int getSieVersion(String fileName) throws IOException {
        int ret = -1;
        try (BufferedReader reader = IoUtil.getReader(fileName)) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (line.startsWith(SIE.SIETYP)) {
                    SieDataItem di = new SieDataItem(line, null, null);
                    ret = di.getInt(0);
                    break;
                }
            }
        }
        return ret;
    }

    public SieDocumentReader() {
        sieDocument = new SieDocument();
        setValidationExceptions(new ArrayList<>());
    }

    public SieDocumentReader(boolean ignoreBTRANS, boolean ignoreMissingOMFATTNING, boolean streamValues, boolean throwErrors) {
        this();
        this.ignoreBTRANS = ignoreBTRANS;
        this.ignoreMissingOMFATTNING = ignoreMissingOMFATTNING;
        this.streamValues = streamValues;
        this.throwErrors = throwErrors;
    }

    public int getParsingLineNumber() {
        return parsingLineNumber;
    }

    public EnumSet<SieType> getAcceptSIETypes() {
        return acceptSIETypes;
    }

    public void setAcceptSIETypes(EnumSet<SieType> acceptSIETypes) {
        this.acceptSIETypes = acceptSIETypes;
    }

    public SieDocument readDocument(String fileName) throws IOException {
        this.fileName = fileName;
        if (throwErrors) {
            Consumer<Exception> existing = callbacks.getSieException();
            callbacks.setSieException(ex -> {
                if (existing != null) existing.accept(ex);
                if (ex instanceof RuntimeException) throw (RuntimeException) ex;
                throw new SieParseException(ex.getMessage(), ex);
            });
        }

        SieVoucher curVoucher = null;
        boolean firstLine = true;
        parsingLineNumber = 0;

        try (BufferedReader reader = IoUtil.getReader(fileName)) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                parsingLineNumber++;
                callbacks.callbackLine(line);
                SieDataItem di = new SieDataItem(line, this, sieDocument);
                if (firstLine) {
                    firstLine = false;
                    if (!SIE.FLAGGA.equals(di.getItemType())) {
                        callbacks.callbackException(new SieInvalidFileException(this.fileName));
                        return null;
                    }
                }

                if (!ignoreKSUMMA && CRC.isStarted() && !SIE.KSUMMA.equals(di.getItemType()))
                    CRC.addData(di);

                SiePeriodValue pv;
                String itemType = di.getItemType();
                if (SIE.ADRESS.equals(itemType)) {
                    sieDocument.getFNAMN().setContact(di.getString(0));
                    sieDocument.getFNAMN().setStreet(di.getString(1));
                    sieDocument.getFNAMN().setZipCity(di.getString(2));
                    sieDocument.getFNAMN().setPhone(di.getString(3));
                } else if (SIE.BKOD.equals(itemType)) {
                    sieDocument.getFNAMN().setSni(di.getInt(0));
                } else if (SIE.BTRANS.equals(itemType)) {
                    if (!ignoreBTRANS) {
                        if (curVoucher == null) {
                            callbacks.callbackException(new SieParseException(
                                "#BTRANS outside #VER block at line " + parsingLineNumber));
                        } else {
                            parseTRANS(di, curVoucher);
                        }
                    }
                } else if (SIE.DIM.equals(itemType)) {
                    parseDimension(di);
                } else if (SIE.ENHET.equals(itemType)) {
                    parseENHET(di);
                } else if (SIE.FLAGGA.equals(itemType)) {
                    sieDocument.setFLAGGA(di.getInt(0));
                } else if (SIE.FNAMN.equals(itemType)) {
                    sieDocument.getFNAMN().setName(di.getString(0));
                } else if (SIE.FNR.equals(itemType)) {
                    sieDocument.getFNAMN().setCode(di.getString(0));
                } else if (SIE.FORMAT.equals(itemType)) {
                    sieDocument.setFORMAT(di.getString(0));
                } else if (SIE.FTYP.equals(itemType)) {
                    sieDocument.getFNAMN().setOrgType(di.getString(0));
                } else if (SIE.GEN.equals(itemType)) {
                    sieDocument.setGEN_DATE(di.getDate(0));
                    sieDocument.setGEN_NAMN(di.getString(1));
                } else if (SIE.IB.equals(itemType)) {
                    parseIB(di);
                } else if (SIE.KONTO.equals(itemType)) {
                    parseKONTO(di);
                } else if (SIE.KSUMMA.equals(itemType)) {
                    if (!ignoreKSUMMA) {
                        if (CRC.isStarted()) {
                            parseKSUMMA(di);
                        } else {
                            CRC.start();
                        }
                    }
                } else if (SIE.KTYP.equals(itemType)) {
                    parseKTYP(di);
                } else if (SIE.KPTYP.equals(itemType)) {
                    sieDocument.setKPTYP(di.getString(0));
                } else if (SIE.OBJEKT.equals(itemType)) {
                    parseOBJEKT(di);
                } else if (SIE.OIB.equals(itemType)) {
                    pv = parseOIB_OUB(di);
                    callbacks.callbackOIB(pv);
                    if (!streamValues) sieDocument.getOIB().add(pv);
                } else if (SIE.OUB.equals(itemType)) {
                    pv = parseOIB_OUB(di);
                    callbacks.callbackOUB(pv);
                    if (!streamValues) sieDocument.getOUB().add(pv);
                } else if (SIE.ORGNR.equals(itemType)) {
                    sieDocument.getFNAMN().setOrgIdentifier(di.getString(0));
                } else if (SIE.OMFATTN.equals(itemType)) {
                    sieDocument.setOMFATTN(di.getDate(0));
                } else if (SIE.PBUDGET.equals(itemType)) {
                    pv = parsePBUDGET_PSALDO(di);
                    if (pv != null) {
                        callbacks.callbackPBUDGET(pv);
                        if (!streamValues) sieDocument.getPBUDGET().add(pv);
                    }
                } else if (SIE.PROGRAM.equals(itemType)) {
                    sieDocument.setPROGRAM(di.getData());
                } else if (SIE.PROSA.equals(itemType)) {
                    sieDocument.setPROSA(di.getString(0));
                } else if (SIE.PSALDO.equals(itemType)) {
                    pv = parsePBUDGET_PSALDO(di);
                    if (pv != null) {
                        callbacks.callbackPSALDO(pv);
                        if (!streamValues) sieDocument.getPSALDO().add(pv);
                    }
                } else if (SIE.RAR.equals(itemType)) {
                    parseRAR(di);
                } else if (SIE.RTRANS.equals(itemType)) {
                    if (!ignoreRTRANS) {
                        if (curVoucher == null) {
                            callbacks.callbackException(new SieParseException(
                                "#RTRANS outside #VER block at line " + parsingLineNumber));
                        } else {
                            parseTRANS(di, curVoucher);
                        }
                    }
                } else if (SIE.SIETYP.equals(itemType)) {
                    sieDocument.setSIETYP(di.getInt(0));
                    if (acceptSIETypes != null) {
                        try {
                            SieType parsed = SieType.fromValue(di.getInt(0));
                            if (!acceptSIETypes.contains(parsed)) {
                                callbacks.callbackException(new SieInvalidFeatureException(
                                    "SIE type " + di.getInt(0) + " is not accepted"));
                                return null;
                            }
                        } catch (IllegalArgumentException e) {
                            callbacks.callbackException(new SieInvalidFeatureException(
                                "Unknown SIE type: " + di.getInt(0)));
                        }
                    }
                } else if (SIE.SRU.equals(itemType)) {
                    parseSRU(di);
                } else if (SIE.TAXAR.equals(itemType)) {
                    sieDocument.setTAXAR(di.getInt(0));
                } else if (SIE.UB.equals(itemType)) {
                    parseUB(di);
                } else if (SIE.TRANS.equals(itemType)) {
                    if (curVoucher == null) {
                        callbacks.callbackException(new SieParseException(
                            "#TRANS outside #VER block at line " + parsingLineNumber));
                    } else {
                        parseTRANS(di, curVoucher);
                    }
                } else if (SIE.RES.equals(itemType)) {
                    parseRES(di);
                } else if (SIE.UNDERDIM.equals(itemType)) {
                    parseUnderDimension(di);
                } else if (SIE.VALUTA.equals(itemType)) {
                    sieDocument.setVALUTA(di.getString(0));
                } else if (SIE.VER.equals(itemType)) {
                    curVoucher = parseVER(di);
                } else if ("".equals(itemType)) {
                } else if ("{".equals(itemType)) {
                } else if ("}".equals(itemType)) {
                    if (curVoucher != null) closeVoucher(curVoucher);
                    curVoucher = null;
                } else {
                    callbacks.callbackException(new UnsupportedOperationException(di.getItemType()));
                }
            }
        }

        if (!ignoreMissingDIM && !sieDocument.getTEMPDIM().isEmpty()) {
            callbacks.callbackException(new SieParseException(
                "Unresolved temporary dimensions: " + sieDocument.getTEMPDIM().keySet()));
        }

        validateDocument();
        return sieDocument;
    }

    private void parseRAR(SieDataItem di) {
        SieBookingYear rar = new SieBookingYear();
        rar.setId(di.getInt(0));
        rar.setStart(di.getDate(1));
        rar.setEnd(di.getDate(2));
        sieDocument.getRars().put(rar.getId(), rar);
    }

    private void parseDimension(SieDataItem di) {
        String number = di.getString(0);
        String name = di.getString(1);
        if (!sieDocument.getDIM().containsKey(number)) {
            SieDimension dim = new SieDimension(number, name);
            sieDocument.getDIM().put(number, dim);
        } else {
            sieDocument.getDIM().get(number).setName(name);
            sieDocument.getDIM().get(number).setDefault(false);
        }
    }

    private void parseUnderDimension(SieDataItem di) {
        String number = di.getString(0);
        String name = di.getString(1);
        String superDimNumber = di.getString(2);

        SieDimension dim;
        if (sieDocument.getUNDERDIM().containsKey(number)) {
            dim = sieDocument.getUNDERDIM().get(number);
            dim.setName(name);
        } else {
            dim = new SieDimension(number, name);
            sieDocument.getUNDERDIM().put(number, dim);
        }

        if (sieDocument.getDIM().containsKey(superDimNumber)) {
            dim.setSuperDim(sieDocument.getDIM().get(superDimNumber));
        }

        // Also register in DIM for object lookups
        if (!sieDocument.getDIM().containsKey(number)) {
            sieDocument.getDIM().put(number, dim);
        }

        // Remove from TEMPDIM if it was temporarily created
        sieDocument.getTEMPDIM().remove(number);
    }

    private void parseENHET(SieDataItem di) {
        if (!sieDocument.getKONTO().containsKey(di.getString(0))) {
            SieAccount account = new SieAccount();
            account.setNumber(di.getString(0));
            sieDocument.getKONTO().put(di.getString(0), account);
        }
        sieDocument.getKONTO().get(di.getString(0)).setUnit(di.getString(1));
    }

    private void parseIB(SieDataItem di) {
        if (!sieDocument.getKONTO().containsKey(di.getString(1))) {
            SieAccount account = new SieAccount();
            account.setNumber(di.getString(1));
            sieDocument.getKONTO().put(di.getString(1), account);
        }

        SiePeriodValue v = new SiePeriodValue();
        v.setYearNr(di.getInt(0));
        v.setAccount(sieDocument.getKONTO().get(di.getString(1)));
        v.setAmount(di.getDecimal(2));
        v.setQuantity(di.getDecimal(3));
        v.setToken(di.getItemType());
        callbacks.callbackIB(v);
        if (!streamValues) sieDocument.getIB().add(v);
    }

    private void parseKONTO(SieDataItem di) {
        String number = di.getString(0);
        String name = di.getString(1);
        if (sieDocument.getKONTO().containsKey(number)) {
            sieDocument.getKONTO().get(number).setName(name);
        } else {
            sieDocument.getKONTO().put(number, new SieAccount(number, name));
        }
    }

    private void parseKSUMMA(SieDataItem di) {
        sieDocument.setKSUMMA(di.getLong(0));
        long checksum = CRC.checksum();
        if (sieDocument.getKSUMMA() != checksum) {
            callbacks.callbackException(new SieInvalidChecksumException(fileName + "; expected: '" + sieDocument
                    .getKSUMMA() + "', found:'" + checksum + "'"));
        }
    }

    private void parseKTYP(SieDataItem di) {
        String number = di.getString(0);
        String type = di.getString(1);
        if (!sieDocument.getKONTO().containsKey(number)) {
            sieDocument.getKONTO().put(number, new SieAccount(number));
        }
        sieDocument.getKONTO().get(number).setType(type);
    }

    private void parseOBJEKT(SieDataItem di) {
        String dimNumber = di.getString(0);
        String number = di.getString(1);
        String name = di.getString(2);
        if (!sieDocument.getDIM().containsKey(dimNumber)) {
            sieDocument.getDIM().put(dimNumber, new SieDimension(number));
        }

        SieDimension dim = sieDocument.getDIM().get(dimNumber);
        SieObject obj = new SieObject(dim, number, name);
        dim.getObjects().put(number, obj);
    }

    private SiePeriodValue parseOIB_OUB(SieDataItem di) {
        String number = di.getString(1);
        if (!sieDocument.getKONTO().containsKey(number)) {
            sieDocument.getKONTO().put(number, new SieAccount(number));
        }

        if (sieDocument.getSIETYP() < 3) {
            callbacks.callbackException(new SieInvalidFeatureException("Neither OIB or OUB is part of SIE < 3"));
        }

        int objOffset = 0;
        if (di.getRawData().contains("{")) objOffset = 1;

        SiePeriodValue v = new SiePeriodValue();
        v.setYearNr(di.getInt(0));
        // OIB/OUB have no period field
        v.setAccount(sieDocument.getKONTO().get(number));
        v.setAmount(di.getDecimal(2 + objOffset));
        v.setQuantity(di.getDecimal(3 + objOffset));
        v.setObjects(di.getObjects());
        v.setToken(di.getItemType());
        return v;
    }

    private SiePeriodValue parsePBUDGET_PSALDO(SieDataItem di) {
        String accountNum = di.getString(2);
        if (!sieDocument.getKONTO().containsKey(accountNum)) {
            sieDocument.getKONTO().put(accountNum, new SieAccount(accountNum));
        }

        if (sieDocument.getSIETYP() == 1) {
            callbacks.callbackException(new SieInvalidFeatureException("Neither PSALDO or PBUDGET is part of SIE 1"));
        }

        if (sieDocument.getSIETYP() == 2 && di.getRawData().contains("{") && !di.getRawData().contains("{}")) {
            return null;
        }

        int objOffset = 0;
        if (di.getRawData().contains("{")) objOffset = 1;

        SiePeriodValue v = new SiePeriodValue();
        v.setYearNr(di.getInt(0));
        v.setPeriod(di.getInt(1));
        v.setAccount(sieDocument.getKONTO().get(accountNum));
        v.setAmount(di.getDecimal(3 + objOffset));
        v.setQuantity(di.getDecimal(4 + objOffset));
        v.setToken(di.getItemType());

        if (sieDocument.getSIETYP() != 2 && di.getRawData().contains("{")) v.setObjects(di.getObjects());

        return v;
    }

    private void parseRES(SieDataItem di) {
        String accountNum = di.getString(1);
        if (!sieDocument.getKONTO().containsKey(accountNum)) {
            sieDocument.getKONTO().put(accountNum, new SieAccount(accountNum));
        }

        int objOffset = 0;
        if (di.getRawData().contains("{")) objOffset = 1;

        SiePeriodValue v = new SiePeriodValue();
        v.setYearNr(di.getInt(0));
        v.setAccount(sieDocument.getKONTO().get(accountNum));
        v.setAmount(di.getDecimal(2 + objOffset));
        v.setQuantity(di.getDecimal(3 + objOffset));
        v.setToken(di.getItemType());

        callbacks.callbackRES(v);
        if (!streamValues) sieDocument.getRES().add(v);
    }

    private void parseSRU(SieDataItem di) {
        String number = di.getString(0);
        if (!sieDocument.getKONTO().containsKey(number)) {
            sieDocument.getKONTO().put(di.getString(0), new SieAccount(number));
        }
        sieDocument.getKONTO().get(number).getSRU().add(di.getString(1));
    }

    private void parseTRANS(SieDataItem di, SieVoucher v) {
        String number = di.getString(0);
        if (!sieDocument.getKONTO().containsKey(number)) {
            sieDocument.getKONTO().put(number, new SieAccount(number));
        }

        int objOffset = 0;
        if (di.getRawData().contains("{")) objOffset = 1;

        SieVoucherRow vr = new SieVoucherRow();
        vr.setAccount(sieDocument.getKONTO().get(number));
        vr.setObjects(di.getObjects());
        vr.setAmount(di.getDecimal(1 + objOffset));
        if (di.getDate(2 + objOffset) != null) vr.setRowDate(di.getDate(2 + objOffset));
        else vr.setRowDate(v.getVoucherDate());
        vr.setText(di.getString(3 + objOffset));
        vr.setQuantity(di.getDecimalNull(4 + objOffset));
        vr.setCreatedBy(di.getString(5 + objOffset));
        vr.setToken(di.getItemType());

        v.getRows().add(vr);
    }

    private void parseUB(SieDataItem di) {
        String number = di.getString(1);
        if (!sieDocument.getKONTO().containsKey(number)) {
            sieDocument.getKONTO().put(number, new SieAccount(number));
        }

        SiePeriodValue v = new SiePeriodValue();
        v.setYearNr(di.getInt(0));
        v.setAccount(sieDocument.getKONTO().get(number));
        v.setAmount(di.getDecimal(2));
        v.setQuantity(di.getDecimal(3));
        v.setToken(di.getItemType());
        callbacks.callbackUB(v);
        if (!streamValues) sieDocument.getUB().add(v);
    }

    private SieVoucher parseVER(SieDataItem di) {
        if (di.getDate(2) == null)
            callbacks.callbackException(new MissingFieldException("Voucher date"));

        SieVoucher v = new SieVoucher();
        v.setSeries(di.getString(0));
        v.setNumber(di.getString(1));
        v.setVoucherDate(di.getDate(2) != null ? di.getDate(2) : LocalDate.now());
        v.setText(di.getString(3));
        v.setCreatedDate(di.getDate(4));
        v.setCreatedBy(di.getString(5));
        v.setToken(di.getItemType());
        return v;
    }

    private void addValidationException(boolean isException, Exception ex) {
        if (isException) {
            getValidationExceptions().add(ex);
            callbacks.callbackException(ex);
        }
    }

    public List<Exception> getValidationExceptions() {
        return validationExceptions;
    }

    public void setValidationExceptions(List<Exception> value) {
        validationExceptions = value;
    }

    private void closeVoucher(SieVoucher v) {
        if (!allowUnbalancedVoucher) {
            BigDecimal check = BigDecimal.ZERO;
            for (SieVoucherRow r : v.getRows()) {
                // Skip ignored row types when computing checksum
                if (ignoreBTRANS && SIE.BTRANS.equals(r.getToken())) continue;
                if (ignoreRTRANS && SIE.RTRANS.equals(r.getToken())) continue;
                check = check.add(r.getAmount());
            }
            if (check.compareTo(BigDecimal.ZERO) != 0)
                callbacks.callbackException(new SieVoucherMissmatchException(
                    v.getSeries() + "." + v.getNumber() + " Sum is not zero."));
        }

        callbacks.callbackVER(v);
        if (!streamValues) sieDocument.getVER().add(v);
    }

    private void validateDocument() {
        addValidationException(sieDocument.getGEN_DATE() == null,
                new SieMissingMandatoryDateException("#GEN Date is missing in " + fileName));
        if (!ignoreMissingOMFATTNING && (sieDocument.getSIETYP() == 2 || sieDocument.getSIETYP() == 3) && sieDocument
                .getOMFATTN() == null) {
            addValidationException(sieDocument.getRES().size() > 0 || sieDocument.getUB().size() > 0 || sieDocument.getOUB().size() > 0,
                    new SieMissingMandatoryDateException("#OMFATTN is missing in " + fileName + ", SIE type=" +
                            sieDocument.getSIETYP()));
        }

        if (!ignoreKSUMMA) {
            addValidationException((CRC.isStarted()) && (sieDocument.getKSUMMA() == 0),
                new SieInvalidChecksumException(fileName));
        }
    }
}
