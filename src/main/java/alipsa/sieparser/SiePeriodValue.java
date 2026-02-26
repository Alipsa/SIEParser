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

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a period-based value in a SIE document.
 * Used for balance records (#IB, #UB, #OIB, #OUB, #PSALDO, #PBUDGET, #RES).
 */
public class SiePeriodValue {

    /** Creates a new empty SiePeriodValue. */
    public SiePeriodValue() {}

    private SieAccount account;
    private int yearNr;
    private int period;
    private BigDecimal amount;
    private BigDecimal quantity;
    private List<SieObject> objects;
    private String token;

    /**
     * Returns the account associated with this period value.
     *
     * @return the account
     */
    public SieAccount getAccount() {
        return account;
    }

    /**
     * Sets the account associated with this period value.
     *
     * @param account the account
     */
    public void setAccount(SieAccount account) {
        this.account = account;
    }

    /**
     * Returns the year number (0 = current year, -1 = previous year, etc.).
     *
     * @return the year number
     */
    public int getYearNr() {
        return yearNr;
    }

    /**
     * Sets the year number.
     *
     * @param yearNr the year number
     */
    public void setYearNr(int yearNr) {
        this.yearNr = yearNr;
    }

    /**
     * Returns the period (month) number, or 0 for yearly values.
     *
     * @return the period number
     */
    public int getPeriod() {
        return period;
    }

    /**
     * Sets the period (month) number.
     *
     * @param period the period number
     */
    public void setPeriod(int period) {
        this.period = period;
    }

    /**
     * Returns the monetary amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the monetary amount.
     *
     * @param amount the amount
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Returns the quantity value (for quantity accounting).
     *
     * @return the quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity value.
     *
     * @param quantity the quantity
     */
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the list of dimension objects associated with this value, or null.
     *
     * @return the objects list
     */
    public List<SieObject> getObjects() {
        return objects;
    }

    /**
     * Sets the list of dimension objects associated with this value.
     *
     * @param objects the objects list
     */
    public void setObjects(List<SieObject> objects) {
        this.objects = objects;
    }

    /**
     * Returns the SIE item type token (e.g. "#IB", "#UB") that produced this value.
     *
     * @return the token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the SIE item type token.
     *
     * @param token the token string
     */
    public void setToken(String token) {
        this.token = token;
    }
}
