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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Reads and parses a SIE file into a {@link SieDocument}.
 * Supports SIE types 1 through 4 (including 4i).
 * Provides callback hooks via {@link SieCallbacks} for streaming processing.
 */
public class SieDocumentReader {

    private SieCallbacks callbacks = new SieCallbacks();
    private boolean ignoreBTRANS = false;
    private boolean ignoreMissingOMFATTNING = false;
    private boolean ignoreRTRANS = false;
    private boolean allowUnbalancedVoucher = false;
    private boolean ignoreKSUMMA = false;
    private boolean allowUnderDimensions = true;
    private boolean ignoreMissingDIM = false;
    private EnumSet<SieType> acceptSIETypes = null;
    private SieDocument sieDocument;
    private List<Exception> validationExceptions;
    private boolean streamValues = false;
    private SieCRC32 CRC = new SieCRC32();
    private boolean throwErrors = true;
    private String fileName;
    private int parsingLineNumber = 0;
    private SieVoucher curVoucher;
    private String pendingRTRANSMirrorData;
    private boolean abortParsing;
    private final Map<String, Consumer<SieDataItem>> handlers = new LinkedHashMap<>();
    private final Set<String> seenRecordTypes = new HashSet<>();
    private boolean sieTypSeen = false;
    private boolean formatSeen = false;
    private final Map<String, Integer> lastVoucherNumberBySeries = new HashMap<>();
    private List<Exception> validationWarnings = new ArrayList<>();

    /**
     * Returns the callbacks invoked during document reading.
     * @return the callbacks
     */
    public SieCallbacks getCallbacks() { return callbacks; }

    /**
     * Sets the callbacks invoked during document reading.
     * @param callbacks the callbacks to use
     */
    public void setCallbacks(SieCallbacks callbacks) { this.callbacks = callbacks; }

    /**
     * Returns whether #BTRANS rows are ignored during parsing.
     * @return true if #BTRANS rows are ignored
     */
    public boolean isIgnoreBTRANS() { return ignoreBTRANS; }

    /**
     * Sets whether #BTRANS rows are ignored during parsing.
     * @param ignoreBTRANS true to ignore #BTRANS rows
     */
    public void setIgnoreBTRANS(boolean ignoreBTRANS) { this.ignoreBTRANS = ignoreBTRANS; }

    /**
     * Returns whether a missing #OMFATTN is accepted.
     * @return true if a missing #OMFATTN is accepted
     */
    public boolean isIgnoreMissingOMFATTNING() { return ignoreMissingOMFATTNING; }

    /**
     * Sets whether a missing #OMFATTN is accepted.
     * @param ignoreMissingOMFATTNING true to accept a missing #OMFATTN
     */
    public void setIgnoreMissingOMFATTNING(boolean ignoreMissingOMFATTNING) { this.ignoreMissingOMFATTNING = ignoreMissingOMFATTNING; }

    /**
     * Returns whether #RTRANS rows are ignored during parsing.
     * @return true if #RTRANS rows are ignored
     */
    public boolean isIgnoreRTRANS() { return ignoreRTRANS; }

    /**
     * Sets whether #RTRANS rows are ignored during parsing.
     * @param ignoreRTRANS true to ignore #RTRANS rows
     */
    public void setIgnoreRTRANS(boolean ignoreRTRANS) { this.ignoreRTRANS = ignoreRTRANS; }

    /**
     * Returns whether unbalanced vouchers are accepted.
     * @return true if unbalanced vouchers are accepted
     */
    public boolean isAllowUnbalancedVoucher() { return allowUnbalancedVoucher; }

    /**
     * Sets whether unbalanced vouchers are accepted.
     * @param allowUnbalancedVoucher true to accept unbalanced vouchers
     */
    public void setAllowUnbalancedVoucher(boolean allowUnbalancedVoucher) { this.allowUnbalancedVoucher = allowUnbalancedVoucher; }

    /**
     * Returns whether the #KSUMMA checksum is ignored.
     * @return true if the checksum is ignored
     */
    public boolean isIgnoreKSUMMA() { return ignoreKSUMMA; }

    /**
     * Sets whether the #KSUMMA checksum is ignored.
     * @param ignoreKSUMMA true to ignore the checksum
     */
    public void setIgnoreKSUMMA(boolean ignoreKSUMMA) { this.ignoreKSUMMA = ignoreKSUMMA; }

    /**
     * Returns whether #UNDERDIM definitions are allowed.
     * @return true if #UNDERDIM is allowed
     */
    public boolean isAllowUnderDimensions() { return allowUnderDimensions; }

    /**
     * Sets whether #UNDERDIM definitions are allowed.
     * @param allowUnderDimensions true to allow #UNDERDIM
     */
    public void setAllowUnderDimensions(boolean allowUnderDimensions) { this.allowUnderDimensions = allowUnderDimensions; }

    /**
     * Returns whether references to undefined dimensions are accepted.
     * @return true if undefined dimension references are accepted
     */
    public boolean isIgnoreMissingDIM() { return ignoreMissingDIM; }

    /**
     * Sets whether references to undefined dimensions are accepted.
     * @param ignoreMissingDIM true to accept undefined dimension references
     */
    public void setIgnoreMissingDIM(boolean ignoreMissingDIM) { this.ignoreMissingDIM = ignoreMissingDIM; }

    /**
     * Returns whether period values are only streamed via callbacks and not stored in the document.
     * @return true if values are streamed only
     */
    public boolean isStreamValues() { return streamValues; }

    /**
     * Sets whether period values are only streamed via callbacks and not stored in the document.
     * @param streamValues true to stream values only
     */
    public void setStreamValues(boolean streamValues) { this.streamValues = streamValues; }

    /**
     * Returns the CRC32 calculator used for checksum verification.
     * @return the CRC32 calculator
     */
    public SieCRC32 getCRC() { return CRC; }

    /**
     * Returns whether parsing errors are thrown as exceptions instead of being silently collected.
     * @return true if errors are thrown
     */
    public boolean isThrowErrors() { return throwErrors; }

    /**
     * Sets whether parsing errors are thrown as exceptions instead of being silently collected.
     * @param throwErrors true to throw errors as exceptions
     */
    public void setThrowErrors(boolean throwErrors) { this.throwErrors = throwErrors; }

    /**
     * Returns the SIE type version from a file without fully parsing it.
     * @param fileName the path to the SIE file
     * @return the SIE type (1-4), or -1 if no #SIETYP was found
     * @throws IOException if an I/O error occurs
     */
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

    /** Creates a new document reader with default settings. */
    public SieDocumentReader() {
        sieDocument = new SieDocument();
        setValidationExceptions(new ArrayList<>());
        validationWarnings = new ArrayList<>();
        seenRecordTypes.clear();
        sieTypSeen = false;
        formatSeen = false;
        lastVoucherNumberBySeries.clear();
        initHandlers();
    }

    /**
     * Creates a new document reader with the specified settings.
     * @param ignoreBTRANS whether to ignore #BTRANS rows
     * @param ignoreMissingOMFATTNING whether to ignore missing #OMFATTN
     * @param streamValues whether to stream values via callbacks only
     * @param throwErrors whether to throw parsing errors as exceptions
     */
    public SieDocumentReader(boolean ignoreBTRANS, boolean ignoreMissingOMFATTNING, boolean streamValues, boolean throwErrors) {
        this();
        this.ignoreBTRANS = ignoreBTRANS;
        this.ignoreMissingOMFATTNING = ignoreMissingOMFATTNING;
        this.streamValues = streamValues;
        this.throwErrors = throwErrors;
    }

    /**
     * Returns the current line number being parsed.
     * @return the line number (1-based)
     */
    public int getParsingLineNumber() {
        return parsingLineNumber;
    }

    /**
     * Returns the set of accepted SIE types, or {@code null} if all types are accepted.
     * @return the accepted SIE types
     */
    public EnumSet<SieType> getAcceptSIETypes() {
        return acceptSIETypes;
    }

    /**
     * Sets the accepted SIE types. Only files matching these types will be parsed.
     * @param acceptSIETypes the accepted SIE types, or {@code null} to accept all
     */
    public void setAcceptSIETypes(EnumSet<SieType> acceptSIETypes) {
        this.acceptSIETypes = acceptSIETypes;
    }

    /**
     * Reads and parses a SIE file into a {@link SieDocument}.
     * @param fileName the path to the SIE file
     * @return the parsed document, or {@code null} if the file is invalid
     * @throws IOException if an I/O error occurs
     */
    public SieDocument readDocument(String fileName) throws IOException {
        this.fileName = fileName;
        curVoucher = null;
        pendingRTRANSMirrorData = null;
        abortParsing = false;

        if (throwErrors) {
            Consumer<Exception> existing = callbacks.getSieException();
            callbacks.setSieException(ex -> {
                if (existing != null) existing.accept(ex);
                if (ex instanceof RuntimeException) throw (RuntimeException) ex;
                throw new SieParseException(ex.getMessage(), ex);
            });
        }

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

                String itemType = di.getItemType();
                if ("".equals(itemType) || "{".equals(itemType)) {
                    // blank lines and opening braces are ignored
                } else if ("}".equals(itemType)) {
                    if (curVoucher != null) closeVoucher(curVoucher);
                    curVoucher = null;
                    pendingRTRANSMirrorData = null;
                } else {
                    // #RTRANS mirror rows only apply to the immediate next #TRANS row
                    if (pendingRTRANSMirrorData != null && !SIE.TRANS.equals(itemType)) {
                        pendingRTRANSMirrorData = null;
                    }
                    seenRecordTypes.add(itemType);
                    Consumer<SieDataItem> handler = handlers.get(itemType);
                    if (handler != null) {
                        handler.accept(di);
                        if (abortParsing) return null;
                    } else {
                        // Unknown labels are silently ignored per the SIE spec
                    }
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

    private void initHandlers() {
        handlers.put(SIE.ADRESS, this::handleADRESS);
        handlers.put(SIE.BKOD, di -> sieDocument.getFNAMN().setSni(di.getInt(0)));
        handlers.put(SIE.BTRANS, this::handleBTRANS);
        handlers.put(SIE.DIM, this::parseDimension);
        handlers.put(SIE.ENHET, this::parseENHET);
        handlers.put(SIE.FLAGGA, di -> sieDocument.setFLAGGA(di.getInt(0)));
        handlers.put(SIE.FNAMN, di -> sieDocument.getFNAMN().setName(di.getString(0)));
        handlers.put(SIE.FNR, di -> sieDocument.getFNAMN().setCode(di.getString(0)));
        handlers.put(SIE.FORMAT, di -> { formatSeen = true; sieDocument.setFORMAT(di.getString(0)); });
        handlers.put(SIE.FTYP, di -> sieDocument.getFNAMN().setOrgType(di.getString(0)));
        handlers.put(SIE.GEN, this::handleGEN);
        handlers.put(SIE.IB, this::parseIB);
        handlers.put(SIE.KONTO, this::parseKONTO);
        handlers.put(SIE.KSUMMA, this::handleKSUMMA);
        handlers.put(SIE.KTYP, this::parseKTYP);
        handlers.put(SIE.KPTYP, di -> sieDocument.setKPTYP(di.getString(0)));
        handlers.put(SIE.OBJEKT, this::parseOBJEKT);
        handlers.put(SIE.OIB, this::handleOIB);
        handlers.put(SIE.OUB, this::handleOUB);
        handlers.put(SIE.ORGNR, this::handleORGNR);
        handlers.put(SIE.OMFATTN, di -> sieDocument.setOMFATTN(di.getDate(0)));
        handlers.put(SIE.PBUDGET, this::handlePBUDGET);
        handlers.put(SIE.PROGRAM, di -> sieDocument.setPROGRAM(di.getData()));
        handlers.put(SIE.PROSA, di -> sieDocument.setPROSA(di.getString(0)));
        handlers.put(SIE.PSALDO, this::handlePSALDO);
        handlers.put(SIE.RAR, this::parseRAR);
        handlers.put(SIE.RTRANS, this::handleRTRANS);
        handlers.put(SIE.SIETYP, this::handleSIETYP);
        handlers.put(SIE.SRU, this::parseSRU);
        handlers.put(SIE.TAXAR, di -> sieDocument.setTAXAR(di.getInt(0)));
        handlers.put(SIE.UB, this::parseUB);
        handlers.put(SIE.TRANS, this::handleTRANS);
        handlers.put(SIE.RES, this::parseRES);
        handlers.put(SIE.UNDERDIM, this::parseUnderDimension);
        handlers.put(SIE.VALUTA, di -> sieDocument.setVALUTA(di.getString(0)));
        handlers.put(SIE.VER, di -> curVoucher = parseVER(di));
    }

    private void handleADRESS(SieDataItem di) {
        sieDocument.getFNAMN().setContact(di.getString(0));
        sieDocument.getFNAMN().setStreet(di.getString(1));
        sieDocument.getFNAMN().setZipCity(di.getString(2));
        sieDocument.getFNAMN().setPhone(di.getString(3));
    }

    private void handleGEN(SieDataItem di) {
        sieDocument.setGEN_DATE(di.getDate(0));
        sieDocument.setGEN_NAMN(di.getString(1));
    }

    private void handleORGNR(SieDataItem di) {
        String orgNr = di.getString(0);
        if (orgNr.isEmpty()) {
            sieDocument.getFNAMN().setOrgIdentifier(null);
        } else {
            sieDocument.getFNAMN().setOrgIdentifier(orgNr);
            if (!orgNr.matches("\\d+-\\d+")) {
                addSoftValidation(new SieParseException(
                    "ORGNR '" + orgNr + "' does not match expected format NNNNNN-NNNN"));
            }
        }
    }

    private void handleBTRANS(SieDataItem di) {
        if (!ignoreBTRANS) {
            if (curVoucher == null) {
                callbacks.callbackException(new SieParseException(
                    "#BTRANS outside #VER block at line " + parsingLineNumber));
            } else {
                parseTRANS(di, curVoucher);
            }
        }
    }

    private void handleRTRANS(SieDataItem di) {
        if (!ignoreRTRANS) {
            if (curVoucher == null) {
                callbacks.callbackException(new SieParseException(
                    "#RTRANS outside #VER block at line " + parsingLineNumber));
            } else {
                pendingRTRANSMirrorData = rowDataWithoutTag(di);
                parseTRANS(di, curVoucher);
            }
        }
    }

    private void handleTRANS(SieDataItem di) {
        if (curVoucher == null) {
            callbacks.callbackException(new SieParseException(
                "#TRANS outside #VER block at line " + parsingLineNumber));
        } else {
            if (pendingRTRANSMirrorData != null) {
                String transData = rowDataWithoutTag(di);
                if (pendingRTRANSMirrorData.equals(transData)) {
                    // SIE 4B: when #RTRANS is handled, the directly following mirror #TRANS is ignored.
                    pendingRTRANSMirrorData = null;
                    return;
                }
                pendingRTRANSMirrorData = null;
            }
            parseTRANS(di, curVoucher);
        }
    }

    private void handleKSUMMA(SieDataItem di) {
        if (!ignoreKSUMMA) {
            if (CRC.isStarted()) {
                parseKSUMMA(di);
            } else {
                CRC.start();
            }
        }
    }

    private void handleSIETYP(SieDataItem di) {
        sieTypSeen = true;
        sieDocument.setSIETYP(di.getInt(0));
        if (acceptSIETypes != null) {
            try {
                SieType parsed = SieType.fromValue(di.getInt(0));
                if (!acceptSIETypes.contains(parsed)) {
                    callbacks.callbackException(new SieInvalidFeatureException(
                        "SIE type " + di.getInt(0) + " is not accepted"));
                    abortParsing = true;
                }
            } catch (IllegalArgumentException e) {
                callbacks.callbackException(new SieInvalidFeatureException(
                    "Unknown SIE type: " + di.getInt(0)));
            }
        }
    }

    private void handleOIB(SieDataItem di) {
        SiePeriodValue pv = parseOIB_OUB(di);
        callbacks.callbackOIB(pv);
        if (!streamValues) sieDocument.getOIB().add(pv);
    }

    private void handleOUB(SieDataItem di) {
        SiePeriodValue pv = parseOIB_OUB(di);
        callbacks.callbackOUB(pv);
        if (!streamValues) sieDocument.getOUB().add(pv);
    }

    private void handlePBUDGET(SieDataItem di) {
        SiePeriodValue pv = parsePBUDGET_PSALDO(di);
        if (pv != null) {
            callbacks.callbackPBUDGET(pv);
            if (!streamValues) sieDocument.getPBUDGET().add(pv);
        }
    }

    private void handlePSALDO(SieDataItem di) {
        SiePeriodValue pv = parsePBUDGET_PSALDO(di);
        if (pv != null) {
            callbacks.callbackPSALDO(pv);
            if (!streamValues) sieDocument.getPSALDO().add(pv);
        }
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
        if (!allowUnderDimensions) {
            callbacks.callbackException(new SieInvalidFeatureException(
                "#UNDERDIM is not allowed (allowUnderDimensions=false)"));
            return;
        }
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
        warnIfExcessDecimals(v.getAmount(), "#IB");
        v.setQuantity(di.getDecimal(3));
        v.setToken(di.getItemType());
        callbacks.callbackIB(v);
        if (!streamValues) sieDocument.getIB().add(v);
    }

    private void parseKONTO(SieDataItem di) {
        String number = di.getString(0);
        String name = di.getString(1);
        if (!number.matches("\\d+")) {
            addSoftValidation(new SieParseException(
                "Account number '" + number + "' is not numeric at line " + parsingLineNumber));
        }
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
            sieDocument.getDIM().put(dimNumber, new SieDimension(dimNumber));
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

        // Validate period format: should be YYYYMM (6 digits, month 01-12)
        String periodStr = di.getString(1);
        if (!periodStr.isEmpty()) {
            if (!periodStr.matches("\\d{6}")) {
                addSoftValidation(new SieParseException(
                    "Invalid period format '" + periodStr + "', expected YYYYMM at line " + parsingLineNumber));
            } else {
                int month = Integer.parseInt(periodStr.substring(4));
                if (month < 1 || month > 12) {
                    addSoftValidation(new SieParseException(
                        "Invalid month in period '" + periodStr + "' at line " + parsingLineNumber));
                }
            }
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
        warnIfExcessDecimals(vr.getAmount(), di.getItemType());
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
        warnIfExcessDecimals(v.getAmount(), "#UB");
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
        v.setVoucherDate(di.getDate(2));
        v.setText(di.getString(3));
        v.setCreatedDate(di.getDate(4));
        v.setCreatedBy(di.getString(5));
        v.setToken(di.getItemType());
        return v;
    }

    private String rowDataWithoutTag(SieDataItem di) {
        String raw = di.getRawData() == null ? "" : di.getRawData().trim();
        int p = raw.indexOf(' ');
        if (p < 0 || p >= raw.length() - 1) return "";
        return raw.substring(p + 1).trim();
    }

    private void warnIfExcessDecimals(BigDecimal amount, String context) {
        if (amount != null && amount.stripTrailingZeros().scale() > 2) {
            addSoftValidation(new SieParseException(
                "Amount " + amount.toPlainString() + " has more than 2 decimal places in " + context
                + " at line " + parsingLineNumber));
        }
    }

    private void addSoftValidation(Exception ex) {
        validationWarnings.add(ex);
    }

    private void addSoftValidation(boolean condition, Exception ex) {
        if (condition) {
            validationWarnings.add(ex);
        }
    }

    private void addValidationException(boolean isException, Exception ex) {
        if (isException) {
            getValidationExceptions().add(ex);
            callbacks.callbackException(ex);
        }
    }

    /**
     * Returns the list of validation exceptions collected during reading.
     * @return the validation exceptions
     */
    public List<Exception> getValidationExceptions() {
        return validationExceptions;
    }

    /**
     * Sets the list of validation exceptions.
     * @param value the validation exceptions list
     */
    public void setValidationExceptions(List<Exception> value) {
        validationExceptions = value;
    }

    /**
     * Returns the list of soft validation warnings collected during reading.
     * These are spec compliance issues that do not prevent parsing
     * (e.g. missing mandatory fields, non-standard formats).
     * @return the validation warnings
     */
    public List<Exception> getValidationWarnings() {
        return validationWarnings;
    }

    private void closeVoucher(SieVoucher v) {
        // Check voucher number ordering per series
        String series = v.getSeries() != null ? v.getSeries() : "";
        String numStr = v.getNumber();
        if (numStr != null && !numStr.isEmpty()) {
            try {
                int num = Integer.parseInt(numStr);
                Integer lastNum = lastVoucherNumberBySeries.get(series);
                if (lastNum != null && num < lastNum) {
                    addSoftValidation(new SieParseException(
                        "Voucher number " + num + " in series '" + series
                        + "' is not in ascending order (previous was " + lastNum + ")"));
                }
                lastVoucherNumberBySeries.put(series, num);
            } catch (NumberFormatException e) {
                // Non-numeric voucher number, skip ordering check
            }
        }

        if (!allowUnbalancedVoucher) {
            BigDecimal check = BigDecimal.ZERO;
            for (SieVoucherRow r : v.getRows()) {
                // Skip ignored row types when computing checksum
                if (ignoreBTRANS && SIE.BTRANS.equals(r.getToken())) continue;
                if (ignoreRTRANS && SIE.RTRANS.equals(r.getToken())) continue;
                check = check.add(r.getAmount());
            }
            if (check.compareTo(BigDecimal.ZERO) != 0)
                callbacks.callbackException(new SieVoucherMismatchException(
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

        // Issue #8: Mandatory field validation (soft — added to list, not thrown)
        addSoftValidation(sieDocument.getPROGRAM().isEmpty(),
            new SieParseException("#PROGRAM is missing in " + fileName));
        addSoftValidation(!formatSeen,
            new SieParseException("#FORMAT is missing in " + fileName));
        addSoftValidation(sieDocument.getFNAMN().getName() == null || sieDocument.getFNAMN().getName().isEmpty(),
            new SieParseException("#FNAMN is missing or empty in " + fileName));
        addSoftValidation(!sieTypSeen,
            new SieParseException("#SIETYP is missing in " + fileName));
        addSoftValidation(sieDocument.getKONTO().isEmpty(),
            new SieParseException("#KONTO is missing in " + fileName));
        addSoftValidation(sieDocument.getRars().isEmpty(),
            new SieParseException("#RAR is missing in " + fileName));

        // Issue #9: Forbidden record enforcement (soft)
        int sieTyp = sieDocument.getSIETYP();
        if (sieTyp == 1 || sieTyp == 2) {
            addSoftValidation(seenRecordTypes.contains(SIE.DIM),
                new SieInvalidFeatureException("#DIM is not allowed in SIE type " + sieTyp));
            addSoftValidation(seenRecordTypes.contains(SIE.UNDERDIM),
                new SieInvalidFeatureException("#UNDERDIM is not allowed in SIE type " + sieTyp));
        }
        if (sieTyp == 1) {
            addSoftValidation(seenRecordTypes.contains(SIE.OMFATTN),
                new SieInvalidFeatureException("#OMFATTN is not allowed in SIE type 1"));
        }
    }
}
