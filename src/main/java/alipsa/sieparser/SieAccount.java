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
import java.util.List;

/**
 * Represents a SIE account (#KONTO).
 * Contains the account number, name, unit, type, and associated SRU codes.
 */
public class SieAccount {
    private String number;
    private String name;
    private String unit;
    private String type;
    private List<String> sru = new ArrayList<>();

    /**
     * Creates a new empty SieAccount with default values.
     */
    public SieAccount() {
        setSRU(new ArrayList<>());
        setName("");
    }

    /**
     * Creates a new SieAccount with the given account number.
     *
     * @param number the account number
     */
    public SieAccount(String number) {
        this(number, "");
    }

    /**
     * Creates a new SieAccount with the given account number and name.
     *
     * @param number the account number
     * @param name the account name
     */
    public SieAccount(String number, String name) {
        this();
        setNumber(number);
        setName(name);
    }

    /**
     * Returns the account number.
     *
     * @return the account number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the account number.
     *
     * @param value the account number
     */
    public void setNumber(String value) {
        number = value;
    }

    /**
     * Returns the account name.
     *
     * @return the account name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the account name.
     *
     * @param value the account name
     */
    public void setName(String value) {
        name = value;
    }

    /**
     * Returns the unit used for quantity accounting (#ENHET).
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Sets the unit used for quantity accounting (#ENHET).
     *
     * @param value the unit
     */
    public void setUnit(String value) {
        unit = value;
    }

    /**
     * Returns the account type (#KTYP).
     *
     * @return the account type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the account type (#KTYP).
     *
     * @param value the account type
     */
    public void setType(String value) {
        type = value;
    }

    /**
     * Returns the list of SRU codes associated with this account.
     *
     * @return the SRU codes
     */
    public List<String> getSRU() {
        return sru;
    }

    /**
     * Sets the list of SRU codes associated with this account.
     *
     * @param value the SRU codes
     */
    public void setSRU(List<String> value) {
        sru = value;
    }

}
