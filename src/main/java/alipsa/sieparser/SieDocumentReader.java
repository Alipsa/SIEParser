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
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SieDocumentReader {
    /**
     * This is where all the callbacks to client code happens.
     */
    public SieCallbacks callbacks = new SieCallbacks();
    public boolean ignoreBTRANS = false;
    public boolean ignoreMissingOMFATTNING = false;
    public boolean ignoreRTRANS = false;
    private SieDocument sieDocument;
    /**
     * Will contain all validation errors after doing a ValidateDocument
     */
    private List<Exception> validationExceptions;
    /**
     * If this is set to true in ReadFile no period values, balances or transactions will be saved in memory.
     * Use this in combination with callbacks to stream through a file.
     */
    public boolean streamValues = false;
    /**
     * Calculates KSUMMA
     */
    public SieCRC32 CRC = new SieCRC32();
    /**
     * If this is set to true in ReadFile each error will be thrown otherwise they will just be callbacked.
     */
    public boolean throwErrors = true;
    /**
     * This is the file currently being read.
     */
    private String fileName;

    /**
     * Does a fast scan of the file to get the Sie version it adheres to.
     *
     * @param fileName the file name to parse
     * @throws Exception if a parsing problem occurred
     * @return -1 if no SIE version was found in the file else SIETYPE is returned.
     */
    public static int getSieVersion(String fileName) throws Exception {
        int ret = -1;
        BufferedReader reader = IoUtil.getReader(fileName);

        for (String line = reader.readLine(); line != null; line = reader.readLine()) {

            if (line.startsWith(SIE.SIETYP)) {
                SieDataItem di = new SieDataItem(line, null, null);
                ret = di.getInt(0);
                break;
            }

        }
        reader.close();
        return ret;
    }

    public SieDocumentReader() {
        sieDocument = new SieDocument();
        setValidationExceptions(new ArrayList<>());
    }

    public SieDocumentReader(boolean ignoreBTRANS, boolean ignoreMissingOMFATTNING, boolean streamValues, boolean
            throwErrors) {
        this();
        this.ignoreBTRANS = ignoreBTRANS;
        this.ignoreMissingOMFATTNING = ignoreMissingOMFATTNING;
        this.streamValues = streamValues;
        this.throwErrors = throwErrors;
    }

    public SieDocument readDocument(String fileName) throws Exception {
        this.fileName = fileName;
        if (throwErrors)
            callbacks.SieException = MultiAction.Combine(callbacks.SieException, new Action<Exception>() {
                @Override
                public void Invoke(Exception obj) throws Exception {
                    throw obj;
                }

                @Override
                public List<Action<Exception>> getInvocationList() throws Exception {
                    List<Action<Exception>> ret = new ArrayList<>();
                    ret.add(this);
                    return ret;
                }
            });


        SieVoucher curVoucher = null;
        boolean firstLine = true;
        BufferedReader reader = IoUtil.getReader(fileName);

        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            callbacks.callbackLine(line);
            SieDataItem di = new SieDataItem(line, this, sieDocument);
            if (firstLine) {
                firstLine = false;
                if (!SIE.FLAGGA.equals(di.getItemType())) {
                    callbacks.callbackException(new SieInvalidFileException(this.fileName));
                    return null;
                }

            }

            if (CRC.Started && !SIE.KSUMMA.equals(di.getItemType()))
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
                if (!ignoreBTRANS)
                    parseTRANS(di, curVoucher);

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
                if (CRC.Started) {
                    parseKSUMMA(di);
                } else {
                    CRC.start();
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
                if (!streamValues)
                    sieDocument.getOIB().add(pv);

            } else if (SIE.OUB.equals(itemType)) {
                pv = parseOIB_OUB(di);
                callbacks.callbackOUB(pv);
                if (!streamValues)
                    sieDocument.getOUB().add(pv);

            } else if (SIE.ORGNR.equals(itemType)) {
                sieDocument.getFNAMN().setOrgIdentifier(di.getString(0));
            } else if (SIE.OMFATTN.equals(itemType)) {
                sieDocument.setOMFATTN(di.getDate(0));
            } else if (SIE.PBUDGET.equals(itemType)) {
                pv = parsePBUDGET_PSALDO(di);
                if (pv != null) {
                    callbacks.callbackPBUDGET(pv);
                    if (!streamValues)
                        sieDocument.getPBUDGET().add(pv);

                }

            } else if (SIE.PROGRAM.equals(itemType)) {
                sieDocument.setPROGRAM(di.getData());
            } else if (SIE.PROSA.equals(itemType)) {
                sieDocument.setPROSA(di.getString(0));
            } else if (SIE.PSALDO.equals(itemType)) {
                pv = parsePBUDGET_PSALDO(di);
                if (pv != null) {
                    callbacks.callbackPSALDO(pv);
                    if (!streamValues)
                        sieDocument.getPSALDO().add(pv);

                }

            } else if (SIE.RAR.equals(itemType)) {
                parseRAR(di);
            } else if (SIE.RTRANS.equals(itemType)) {
                if (!ignoreBTRANS) parseTRANS(di, curVoucher);
            } else if (SIE.SIETYP.equals(itemType)) {
                sieDocument.setSIETYP(di.getInt(0));
            } else if (SIE.SRU.equals(itemType)) {
                parseSRU(di);
            } else if (SIE.TAXAR.equals(itemType)) {
                sieDocument.setTAXAR(di.getInt(0));
            } else if (SIE.UB.equals(itemType)) {
                parseUB(di);
            } else if (SIE.TRANS.equals(itemType)) {
                parseTRANS(di, curVoucher);
            } else if (SIE.RES.equals(itemType)) {
                parseRES(di);
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
                callbacks.callbackException(new NotImplementedException(di.getItemType()));
            }
        }
        validateDocument();
        reader.close();
        return sieDocument;
    }

    private void parseRAR(SieDataItem di) throws Exception {
        SieBookingYear rar = new SieBookingYear();
        rar.setId(di.getInt(0));
        rar.setStart(di.getDate(1));
        rar.setEnd(di.getDate(2));
        sieDocument.getRars().put(rar.getId(), rar);
    }

    private void parseDimension(SieDataItem di) throws Exception {
        String number = di.getString(0);
        String name = di.getString(1);
        if (!sieDocument.getDIM().containsKey(number)) {
            SieDimension dim = new SieDimension(number, name);
            dim.setName(name);
            dim.setNumber(number);
            sieDocument.getDIM().put(number, dim);
        } else {
            sieDocument.getDIM().get(number).setName(name);
            sieDocument.getDIM().get(number).isDefault = false;
        }
    }

    private void parseENHET(SieDataItem di) throws Exception {
        if (!sieDocument.getKONTO().containsKey(di.getString(0))) {
            SieAccount account = new SieAccount();
            account.setNumber(di.getString(0));
            sieDocument.getKONTO().put(di.getString(0),account );
        }

        sieDocument.getKONTO().get(di.getString(0)).setUnit(di.getString(1));
    }

    private void parseIB(SieDataItem di) throws Exception {
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

    private void parseKONTO(SieDataItem di) throws Exception {
        String number = di.getString(0);
        String name = di.getString(1);
        if (sieDocument.getKONTO().containsKey(number)) {
            sieDocument.getKONTO().get(number).setName(name);
        } else {
            sieDocument.getKONTO().put(number, new SieAccount(number,name ));
        }
    }

    private void parseKSUMMA(SieDataItem di) throws Exception {
        sieDocument.setKSUMMA(di.getLong(0));
        long checksum = CRC.checksum();
        if (sieDocument.getKSUMMA() != checksum) {
            callbacks.callbackException(new SieInvalidChecksumException(fileName + "; expected: '" + sieDocument
                    .getKSUMMA() + "', found:'" + checksum + "'"));
        }

    }

    private void parseKTYP(SieDataItem di) throws Exception {
        //Create the account if it hasn't been loaded yet.
        String number = di.getString(0);
        String type = di.getString(1);
        if (!sieDocument.getKONTO().containsKey(number)) {
            sieDocument.getKONTO().put(number, new SieAccount(number));
        }

        sieDocument.getKONTO().get(number).setType(type);
    }

    private void parseOBJEKT(SieDataItem di) throws Exception {
        String dimNumber = di.getString(0);
        String number = di.getString(1);
        String name = di.getString(2);
        if (!sieDocument.getDIM().containsKey(dimNumber)) {
            sieDocument.getDIM().put(dimNumber, new SieDimension(number));
        }

        SieDimension dim = sieDocument.getDIM().get(dimNumber);
        SieObject obj = new SieObject(dim, number, name);
        // add a new entry or replace existing
        dim.objects.put(number, obj);
    }

    private SiePeriodValue parseOIB_OUB(SieDataItem di) throws Exception {
        String number = di.getString(1);
        //Create the account if it hasn't been loaded yet.
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
        v.setPeriod(di.getInt(1));
        v.setAccount(sieDocument.getKONTO().get(number));
        v.setAmount(di.getDecimal(3 + objOffset));
        v.setQuantity(di.getDecimal(4).add(new BigDecimal(objOffset)));
        v.setObjects(di.getObjects());
        v.setToken(di.getItemType());
        return v;
    }

    private SiePeriodValue parsePBUDGET_PSALDO(SieDataItem di) throws Exception {
        String accountNum = di.getString(2);
        //Create the account if it hasn't been loaded yet.
        if (!sieDocument.getKONTO().containsKey(accountNum)) {
            sieDocument.getKONTO().put(accountNum, new SieAccount(accountNum));
        }

        if (sieDocument.getSIETYP() == 1) {
            callbacks.callbackException(new SieInvalidFeatureException("Neither PSALDO or PBUDGET is part of SIE 1"));
        }

        //Applications reading SIE type 2 should ignore PSALDO containing non empty dimension.
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

    private void parseRES(SieDataItem di) throws Exception {
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

    private void parseSRU(SieDataItem di) throws Exception {
        String number = di.getString(0);
        if (!sieDocument.getKONTO().containsKey(number)) {
            sieDocument.getKONTO().put(di.getString(0), new SieAccount(number));
        }

        sieDocument.getKONTO().get(number).getSRU().add(di.getString(1));
    }

    private void parseTRANS(SieDataItem di, SieVoucher v) throws Exception {
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
        vr.setQuantity(di.getIntNull(4 + objOffset));
        vr.setCreatedBy(di.getString(5 + objOffset));
        vr.setToken(di.getItemType());

        v.getRows().add(vr);
    }

    private void parseUB(SieDataItem di) throws Exception {
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
        if (!streamValues)
            sieDocument.getUB().add(v);

    }

    private SieVoucher parseVER(SieDataItem di) throws Exception {
        if (di.getDate(2) == null)
            callbacks.callbackException(new MissingFieldException("Voucher date"));

        SieVoucher v = new SieVoucher();
        v.setSeries(di.getString(0));
        v.setNumber(di.getString(1));
        v.setVoucherDate(di.getDate(2) != null ? di.getDate(2) : new Date());
        v.setText(di.getString(3));
        v.setCreatedDate(di.getInt(4));
        v.setCreatedBy(di.getString(5));
        v.setToken(di.getItemType());
        return v;
    }

    private void addValidationException(boolean isException, Exception ex) throws Exception {
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

    private void closeVoucher(SieVoucher v) throws Exception {
        //Check sum of rows
        BigDecimal check = new BigDecimal(0);
        for (SieVoucherRow r : v.getRows()) {
            check = check.add(r.getAmount());
        }
        if (check.compareTo(new BigDecimal(0)) != 0)
            callbacks.callbackException(new SieVoucherMissmatchException(v.getSeries() + "." + v.getNumber() + " Sum is not zero."));

        callbacks.callbackVER(v);
        if (!streamValues) sieDocument.getVER().add(v);
    }


    private void validateDocument() throws Exception {
        addValidationException(sieDocument.getGEN_DATE() == null,
                new SieMissingMandatoryDateException("#GEN Date is missing in " + fileName));
        //If there are period values #OMFATTN has to tell the value date.
        if (!ignoreMissingOMFATTNING && (sieDocument.getSIETYP() == 2 || sieDocument.getSIETYP() == 3) && sieDocument
                .getOMFATTN() == null) {
                addValidationException(sieDocument.getRES().size() > 0 || sieDocument.getUB().size() > 0 || sieDocument.getOUB().size() > 0,
                        new SieMissingMandatoryDateException("#OMFATTN is missing in " + fileName + ", SIE type=" +
                                sieDocument.getSIETYP()));
        }

        addValidationException((CRC.Started) && (sieDocument.getKSUMMA() == 0), new SieInvalidChecksumException(fileName));
    }
}
