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
import java.time.LocalDate;
import java.util.List;

/**
 * Represents a single transaction row (#TRANS, #BTRANS, #RTRANS) within a voucher.
 * Each row records a debit or credit to a specific account.
 */
public class SieVoucherRow {

    /** Creates a new empty voucher row. */
    public SieVoucherRow() {}

    private BigDecimal quantity;
    private SieAccount account;
    private List<SieObject> objects;
    private BigDecimal amount;
    private LocalDate rowDate;
    private String text;
    private String createdBy;
    private String token;

    /**
     * Returns the account this row applies to.
     * @return the account
     */
    public SieAccount getAccount() {
        return account;
    }

    /**
     * Sets the account this row applies to.
     * @param value the account
     */
    public void setAccount(SieAccount value) {
        account = value;
    }

    /**
     * Returns the dimension objects associated with this row.
     * @return the list of objects, or {@code null} if none
     */
    public List<SieObject> getObjects() {
        return objects;
    }

    /**
     * Sets the dimension objects associated with this row.
     * @param value the list of objects
     */
    public void setObjects(List<SieObject> value) {
        objects = value;
    }

    /**
     * Returns the transaction amount (positive for debit, negative for credit).
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the transaction amount.
     * @param value the amount
     */
    public void setAmount(BigDecimal value) {
        amount = value;
    }

    /**
     * Returns the row date (may differ from the voucher date).
     * @return the row date
     */
    public LocalDate getRowDate() {
        return rowDate;
    }

    /**
     * Sets the row date.
     * @param value the row date
     */
    public void setRowDate(LocalDate value) {
        rowDate = value;
    }

    /**
     * Returns the descriptive text for this row.
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the descriptive text for this row.
     * @param value the text
     */
    public void setText(String value) {
        text = value;
    }

    /**
     * Returns the name of the person who created this row.
     * @return the creator name
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of the person who created this row.
     * @param value the creator name
     */
    public void setCreatedBy(String value) {
        createdBy = value;
    }

    /**
     * Returns the SIE token that produced this row (e.g. "#TRANS", "#BTRANS", "#RTRANS").
     * @return the token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the SIE token that produced this row.
     * @param value the token string
     */
    public void setToken(String value) {
        token = value;
    }

    /**
     * Returns the quantity for this row, or {@code null} if not set.
     * @return the quantity
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity for this row.
     * @param q the quantity
     */
    public void setQuantity(BigDecimal q) {
        quantity = q;
    }
}
