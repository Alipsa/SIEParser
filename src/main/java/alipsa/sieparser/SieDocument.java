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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a complete parsed SIE document.
 * Contains all data from a SIE file including company info, accounts, dimensions,
 * balances, period values, vouchers, and metadata.
 */
public class SieDocument {

    private Map<String, SieDimension> dim;
    private Map<String, SieDimension> underDim;
    private Map<String, SieDimension> tempDim;
    private int flagga;
    private SieCompany fnamn;
    private String format;
    private LocalDate genDate;
    private String genNamn;
    private List<SiePeriodValue> ib;
    private Map<String, SieAccount> konto;
    private String kptyp;
    private long ksumma;
    private List<SiePeriodValue> oib;
    private LocalDate omfattn;
    private List<SiePeriodValue> oub;
    private List<SiePeriodValue> pbudget;
    private List<String> program;
    private String prosa;
    private List<SiePeriodValue> psaldo;
    private Map<Integer, SieBookingYear> rars;
    private List<SiePeriodValue> res;
    private int sieTyp;
    private int taxar;
    private List<SiePeriodValue> ub;
    private String valuta;
    private List<SieVoucher> ver;

    /** Creates a new empty SIE document with default dimensions initialized. */
    public SieDocument() {
        setFNAMN(new SieCompany());
        setKONTO(new HashMap<>());
        setDIM(new HashMap<>());
        setUNDERDIM(new HashMap<>());
        setTEMPDIM(new HashMap<>());
        setOIB(new ArrayList<>());
        setOUB(new ArrayList<>());
        setPSALDO(new ArrayList<>());
        setPBUDGET(new ArrayList<>());
        setPROGRAM(new ArrayList<>());
        setRars(new HashMap<>());
        setIB(new ArrayList<>());
        setUB(new ArrayList<>());
        setRES(new ArrayList<>());
        setVER(new ArrayList<>());

        initializeDimensions();
    }

    /**
     * Returns the dimensions map (#DIM), keyed by dimension number.
     * @return the dimensions map
     */
    public Map<String, SieDimension> getDIM() {
        return dim;
    }

    /**
     * Sets the dimensions map (#DIM).
     * @param value the dimensions map
     */
    public void setDIM(Map<String, SieDimension> value) {
        dim = value;
    }

    /**
     * Returns the sub-dimensions map (#UNDERDIM), keyed by dimension number.
     * @return the sub-dimensions map
     */
    public Map<String, SieDimension> getUNDERDIM() {
        return underDim;
    }

    /**
     * Sets the sub-dimensions map (#UNDERDIM).
     * @param value the sub-dimensions map
     */
    public void setUNDERDIM(Map<String, SieDimension> value) {
        underDim = value;
    }

    /**
     * Returns the temporary dimensions map, keyed by dimension number.
     * @return the temporary dimensions map
     */
    public Map<String, SieDimension> getTEMPDIM() {
        return tempDim;
    }

    /**
     * Sets the temporary dimensions map.
     * @param value the temporary dimensions map
     */
    public void setTEMPDIM(Map<String, SieDimension> value) {
        tempDim = value;
    }

    /**
     * Returns the FLAGGA value (0 = not imported, 1 = imported).
     * @return the flag value
     */
    public int getFLAGGA() {
        return flagga;
    }

    /**
     * Sets the FLAGGA value.
     * @param value the flag value
     */
    public void setFLAGGA(int value) {
        flagga = value;
    }

    /**
     * Returns the company information (#FNAMN).
     * @return the company object
     */
    public SieCompany getFNAMN() {
        return fnamn;
    }

    /**
     * Sets the company information (#FNAMN).
     * @param value the company object
     */
    public void setFNAMN(SieCompany value) {
        fnamn = value;
    }

    /**
     * Returns the file format string (#FORMAT).
     * @return the format string
     */
    public String getFORMAT() {
        return format;
    }

    /**
     * Sets the file format string (#FORMAT).
     * @param value the format string
     */
    public void setFORMAT(String value) {
        format = value;
    }

    /**
     * Returns the generation date (#GEN date).
     * @return the generation date
     */
    public LocalDate getGEN_DATE() {
        return genDate;
    }

    /**
     * Sets the generation date (#GEN date).
     * @param value the generation date
     */
    public void setGEN_DATE(LocalDate value) {
        genDate = value;
    }

    /**
     * Returns the name of the person or system that generated the file (#GEN name).
     * @return the generator name
     */
    public String getGEN_NAMN() {
        return genNamn;
    }

    /**
     * Sets the name of the person or system that generated the file (#GEN name).
     * @param value the generator name
     */
    public void setGEN_NAMN(String value) {
        genNamn = value;
    }

    /**
     * Returns the list of opening balances (#IB).
     * @return the opening balances
     */
    public List<SiePeriodValue> getIB() {
        return ib;
    }

    /**
     * Sets the list of opening balances (#IB).
     * @param value the opening balances
     */
    public void setIB(List<SiePeriodValue> value) {
        ib = value;
    }

    /**
     * Returns the accounts map (#KONTO), keyed by account number.
     * @return the accounts map
     */
    public Map<String, SieAccount> getKONTO() {
        return konto;
    }

    /**
     * Sets the accounts map (#KONTO).
     * @param value the accounts map
     */
    public void setKONTO(Map<String, SieAccount> value) {
        konto = value;
    }

    /**
     * Returns the chart of accounts type (#KPTYP).
     * @return the chart of accounts type
     */
    public String getKPTYP() {
        return kptyp;
    }

    /**
     * Sets the chart of accounts type (#KPTYP).
     * @param value the chart of accounts type
     */
    public void setKPTYP(String value) {
        kptyp = value;
    }

    /**
     * Returns the checksum value (#KSUMMA).
     * @return the CRC32 checksum
     */
    public long getKSUMMA() {
        return ksumma;
    }

    /**
     * Sets the checksum value (#KSUMMA).
     * @param value the CRC32 checksum
     */
    public void setKSUMMA(long value) {
        ksumma = value;
    }

    /**
     * Returns the list of object opening balances (#OIB).
     * @return the object opening balances
     */
    public List<SiePeriodValue> getOIB() {
        return oib;
    }

    /**
     * Sets the list of object opening balances (#OIB).
     * @param value the object opening balances
     */
    public void setOIB(List<SiePeriodValue> value) {
        oib = value;
    }

    /**
     * Returns the scope date (#OMFATTN).
     * @return the scope date
     */
    public LocalDate getOMFATTN() {
        return omfattn;
    }

    /**
     * Sets the scope date (#OMFATTN).
     * @param value the scope date
     */
    public void setOMFATTN(LocalDate value) {
        omfattn = value;
    }

    /**
     * Returns the list of object closing balances (#OUB).
     * @return the object closing balances
     */
    public List<SiePeriodValue> getOUB() {
        return oub;
    }

    /**
     * Sets the list of object closing balances (#OUB).
     * @param value the object closing balances
     */
    public void setOUB(List<SiePeriodValue> value) {
        oub = value;
    }

    /**
     * Returns the list of period budgets (#PBUDGET).
     * @return the period budgets
     */
    public List<SiePeriodValue> getPBUDGET() {
        return pbudget;
    }

    /**
     * Sets the list of period budgets (#PBUDGET).
     * @param value the period budgets
     */
    public void setPBUDGET(List<SiePeriodValue> value) {
        pbudget = value;
    }

    /**
     * Returns the program information (#PROGRAM).
     * @return the program info list
     */
    public List<String> getPROGRAM() {
        return program;
    }

    /**
     * Sets the program information (#PROGRAM).
     * @param value the program info list
     */
    public void setPROGRAM(List<String> value) {
        program = value;
    }

    /**
     * Returns the free-text comment (#PROSA).
     * @return the comment text
     */
    public String getPROSA() {
        return prosa;
    }

    /**
     * Sets the free-text comment (#PROSA).
     * @param value the comment text
     */
    public void setPROSA(String value) {
        prosa = value;
    }

    /**
     * Returns the list of period balances (#PSALDO).
     * @return the period balances
     */
    public List<SiePeriodValue> getPSALDO() {
        return psaldo;
    }

    /**
     * Sets the list of period balances (#PSALDO).
     * @param value the period balances
     */
    public void setPSALDO(List<SiePeriodValue> value) {
        psaldo = value;
    }

    /**
     * Returns the booking years map (#RAR), keyed by year identifier.
     * @return the booking years map
     */
    public Map<Integer, SieBookingYear> getRars() {
        return rars;
    }

    /**
     * Sets the booking years map (#RAR).
     * @param value the booking years map
     */
    public void setRars(Map<Integer, SieBookingYear> value) {
        rars = value;
    }

    /**
     * Returns the list of result values (#RES).
     * @return the result values
     */
    public List<SiePeriodValue> getRES() {
        return res;
    }

    /**
     * Sets the list of result values (#RES).
     * @param value the result values
     */
    public void setRES(List<SiePeriodValue> value) {
        res = value;
    }

    /**
     * Returns the SIE type (#SIETYP), e.g. 1, 2, 3, or 4.
     * @return the SIE type
     */
    public int getSIETYP() {
        return sieTyp;
    }

    /**
     * Sets the SIE type (#SIETYP).
     * @param value the SIE type
     */
    public void setSIETYP(int value) {
        sieTyp = value;
    }

    /**
     * Returns the tax year (#TAXAR).
     * @return the tax year
     */
    public int getTAXAR() {
        return taxar;
    }

    /**
     * Sets the tax year (#TAXAR).
     * @param value the tax year
     */
    public void setTAXAR(int value) {
        taxar = value;
    }

    /**
     * Returns the list of closing balances (#UB).
     * @return the closing balances
     */
    public List<SiePeriodValue> getUB() {
        return ub;
    }

    /**
     * Sets the list of closing balances (#UB).
     * @param value the closing balances
     */
    public void setUB(List<SiePeriodValue> value) {
        ub = value;
    }

    /**
     * Returns the currency code (#VALUTA), or {@code null} if not set.
     * Per the SIE specification, if this post is absent the reader should assume SEK.
     * @return the currency code, or {@code null} if not set
     */
    public String getVALUTA() {
        return valuta;
    }

    /**
     * Sets the currency code (#VALUTA).
     * @param value the currency code
     */
    public void setVALUTA(String value) {
        valuta = value;
    }

    /**
     * Returns the list of vouchers (#VER).
     * @return the vouchers
     */
    public List<SieVoucher> getVER() {
        return ver;
    }

    /**
     * Sets the list of vouchers (#VER).
     * @param value the vouchers
     */
    public void setVER(List<SieVoucher> value) {
        ver = value;
    }

    private void initializeDimensions() {
        getDIM().put("1", new SieDimension("1", "Resultatenhet", true));
        getDIM().put("2", new SieDimension("2", "Kostnadsbärare", true));
        getDIM().put("3", new SieDimension("3", "Reserverat", true));
        getDIM().put("4", new SieDimension("4", "Reserverat", true));
        getDIM().put("5", new SieDimension("5", "Reserverat", true));
        getDIM().put("6", new SieDimension("6", "Projekt", true));
        getDIM().put("7", new SieDimension("7", "Anställd", true));
        getDIM().put("8", new SieDimension("8", "Kund", true));
        getDIM().put("9", new SieDimension("9", "Leverantör", true));
        getDIM().put("10", new SieDimension("10", "Faktura", true));
        getDIM().put("11", new SieDimension("11", "Reserverat", true));
        getDIM().put("12", new SieDimension("12", "Reserverat", true));
        getDIM().put("13", new SieDimension("13", "Reserverat", true));
        getDIM().put("14", new SieDimension("14", "Reserverat", true));
        getDIM().put("15", new SieDimension("15", "Reserverat", true));
        getDIM().put("16", new SieDimension("16", "Reserverat", true));
        getDIM().put("17", new SieDimension("17", "Reserverat", true));
        getDIM().put("18", new SieDimension("18", "Reserverat", true));
        getDIM().put("19", new SieDimension("19", "Reserverat", true));
    }
}
