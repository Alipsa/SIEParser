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

public class SIE {

    /** Address for the company */
    public static final String ADRESS = "#ADRESS";

    /** Company branch affiliation */
    public static final String BKOD = "#BKOD";

    /** Removed transaction entry */
    public static final String BTRANS = "#BTRANS";

    /** Dimension */
    public static final String DIM = "#DIM";

    /** Unit when doing quantity accounting (kvantitetsredovisning) */
    public static final String ENHET = "#ENHET";

    /** Flag indicating whether the file has been received by the recipient */
    public static final String FLAGGA = "#FLAGGA";

    /** Complete company name */
    public static final String FNAMN = "#FNAMN";

    /** Accounting systems internal code for company */
    public static final String FNR = "#FNR";

    /** Character set used */
    public static final String FORMAT = "#FORMAT";

    /** Company type */
    public static final String FTYP = "#FTYP";

    /** When and by whom the file was generated */
    public static final String GEN = "#GEN";

    /** Initial balance (Ingående balans) for an account */
    public static final String IB = "#IB";

    /** the account */
    public static final String KONTO = "#KONTO";

    /** Accounting plan type */
    public static final String KPTYP = "#KPTYP";

    /** Start of control summations / totals */
    public static final String KSUMMA = "#KSUMMA";

    /** Account type */
    public static final String KTYP = "#KTYP";

    /** Object. */
    public static final String OBJEKT = "#OBJEKT";

    /** Entering balance (Ingående balans) for an account */
    public static final String OIB = "#OIB";

    /** Outgoing balance (Utgående balans) for an account */
    public static final String OUB = "#OUB";

    /** Company registration umber (Organisationsnummer) */
    public static final String ORGNR = "#ORGNR";

    /** Date for periodsaldons omfattning */
    public static final String OMFATTN = "#OMFATTN";

    /** The budget for the period for the account */
    public static final String PBUDGET = "#PBUDGET";

    /** The program that generated the file */
    public static final String PROGRAM = "#PROGRAM";

    /** free text on the content of the file */
    public static final String PROSA = "#PROSA";

    /** Periodens saldo för ett visst konto. */
    public static final String PSALDO = "#PSALDO";

    /** Räkenskapsår från vilket exporterade data hämtats. */
    public static final String RAR = "#RAR";

    /** Saldopost för ett resultatkonto. */
    public static final String RES = "#RES";

    /** Tillagd transaktionspost. */
    public static final String RTRANS = "#RTRANS";

    /** The type of SIE format that file adheres to */
    public static final String SIETYP = "#SIETYP";

    /** RSV-kod för standardiserat räkenskapsutdrag. */
    public static final String SRU = "#SRU";

    /** Taxeringsår för deklarationsinformation (SRU-koder) */
    public static final String TAXAR = "#TAXAR";

    /** Transaktionspost. */
    public static final String TRANS = "#TRANS";

    /** Utgående balans för ett balanskonto. */
    public static final String UB = "#UB";

    /** Underdimension. */
    public static final String UNDERDIM = "#UNDERDIM";

    /** Redovisningsvaluta. */
    public static final String VALUTA = "#VALUTA";

    /** Verifikationspost. */
    public static final String VER = "#VER";
}
