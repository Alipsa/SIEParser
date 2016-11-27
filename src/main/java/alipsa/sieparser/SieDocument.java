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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SieDocument {

    /**
     * #DIM
     */
    private HashMap<String, SieDimension> dim;
    /**
     * #FLAGGA
     */
    private int flagga;
    private SieCompany fnamn;
    /**
     * #FORMAT
     */
    private String format;
    /**
     * #GEN
     */
    private Date genDate;
    private String genNamn;
    /**
     * #IB
     */
    private List<SiePeriodValue> ib;
    /**
     * #KONTO
     */
    private HashMap<String, SieAccount> konto;
    /**
     * #KPTYP
     */
    private String kptyp;
    private long ksumma;
    /**
     * #OIB
     */
    private List<SiePeriodValue> oib;
    /**
     * #OMFATTN Obligatory when exporting period values
     */
    private Date omfattn;
    /**
     * #OUB
     */
    private List<SiePeriodValue> oub;
    /**
     * #PBUDGET
     */
    private List<SiePeriodValue> pbudget;
    /**
     * #PROGRAM
     */
    private List<String> program;
    /**
     * #PROSA
     */
    private String prosa;
    /**
     * #PSALDO
     */
    private List<SiePeriodValue> psaldo;
    /**
     * #RAR
     */
    private HashMap<Integer, SieBookingYear> rars;
    /**
     * #RES
     */
    private List<SiePeriodValue> res;
    /**
     * #SIETYP
     */
    private int sieTyp;
    /**
     * #TAXAR
     */
    private int taxar;
    /**
     * #UB
     */
    private List<SiePeriodValue> ub;

    /**
     * #VALUTA
     */
    private String valuta;
    /**
     * #VER
     */
    private List<SieVoucher> ver;

    public SieDocument() {
        setFNAMN(new SieCompany());
        setKONTO(new HashMap<>());
        setDIM(new HashMap<>());
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

    public HashMap<String, SieDimension> getDIM() {
        return dim;
    }

    public void setDIM(HashMap<String, SieDimension> value) {
        dim = value;
    }

    public int getFLAGGA() {
        return flagga;
    }

    public void setFLAGGA(int value) {
        flagga = value;
    }

    public SieCompany getFNAMN() {
        return fnamn;
    }

    public void setFNAMN(SieCompany value) {
        fnamn = value;
    }

    public String getFORMAT() {
        return format;
    }

    public void setFORMAT(String value) {
        format = value;
    }

    public Date getGEN_DATE() {
        return genDate;
    }

    public void setGEN_DATE(Date value) {
        genDate = value;
    }

    public String getGEN_NAMN() {
        return genNamn;
    }

    public void setGEN_NAMN(String value) {
        genNamn = value;
    }

    public List<SiePeriodValue> getIB() {
        return ib;
    }

    public void setIB(List<SiePeriodValue> value) {
        ib = value;
    }

    public HashMap<String, SieAccount> getKONTO() {
        return konto;
    }

    public void setKONTO(HashMap<String, SieAccount> value) {
        konto = value;
    }

    public String getKPTYP() {
        return kptyp;
    }

    public void setKPTYP(String value) {
        kptyp = value;
    }

    public long getKSUMMA() {
        return ksumma;
    }

    public void setKSUMMA(long value) {
        ksumma = value;
    }

    public List<SiePeriodValue> getOIB() {
        return oib;
    }

    public void setOIB(List<SiePeriodValue> value) {
        oib = value;
    }

    public Date getOMFATTN() {
        return omfattn;
    }

    public void setOMFATTN(Date value) {
        omfattn = value;
    }

    public List<SiePeriodValue> getOUB() {
        return oub;
    }

    public void setOUB(List<SiePeriodValue> value) {
        oub = value;
    }

    public List<SiePeriodValue> getPBUDGET() {
        return pbudget;
    }

    public void setPBUDGET(List<SiePeriodValue> value) {
        pbudget = value;
    }

    public List<String> getPROGRAM() {
        return program;
    }

    public void setPROGRAM(List<String> value) {
        program = value;
    }

    public String getPROSA() {
        return prosa;
    }

    public void setPROSA(String value) {
        prosa = value;
    }

    public List<SiePeriodValue> getPSALDO() {
        return psaldo;
    }

    public void setPSALDO(List<SiePeriodValue> value) {
        psaldo = value;
    }

    public HashMap<Integer, SieBookingYear> getRars() {
        return rars;
    }

    public void setRars(HashMap<Integer, SieBookingYear> value) {
        rars = value;
    }

    public List<SiePeriodValue> getRES() {
        return res;
    }

    public void setRES(List<SiePeriodValue> value) {
        res = value;
    }

    public int getSIETYP() {
        return sieTyp;
    }

    public void setSIETYP(int value) {
        sieTyp = value;
    }

    public int getTAXAR() {
        return taxar;
    }

    public void setTAXAR(int value) {
        taxar = value;
    }

    public List<SiePeriodValue> getUB() {
        return ub;
    }

    public void setUB(List<SiePeriodValue> value) {
        ub = value;
    }


    public String getVALUTA() {
        if (valuta == null) {
            return "";
        }
        return valuta;
    }

    public void setVALUTA(String value) {
        valuta = value;
    }

    public List<SieVoucher> getVER() {
        return ver;
    }

    public void setVER(List<SieVoucher> value) {
        ver = value;
    }


    private void initializeDimensions() {
        getDIM().put("1", new SieDimension("1", "Resultatenhet", true));
        getDIM().put("2", new SieDimension("2", "Kostnadsbärare", true));
        getDIM().put("3", new SieDimension("3", "Reserverat", true));
        getDIM().put("4", new SieDimension("4","Reserverat", true));
        getDIM().put("5", new SieDimension("5","Reserverat", true));
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


