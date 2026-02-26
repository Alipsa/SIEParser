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
import java.util.List;

/**
 * Represents a SIE voucher (#VER).
 * A voucher contains a series, number, date, descriptive text,
 * and a list of transaction rows that must sum to zero.
 */
public class SieVoucher {
    private String series;
    private String number;
    private LocalDate voucherDate;
    private String text;
    private LocalDate createdDate;
    private String createdBy;
    private String token;
    private List<SieVoucherRow> rows;

    /** Creates a new empty voucher with an empty row list. */
    public SieVoucher() {
        setRows(new ArrayList<>());
    }

    /**
     * Returns the voucher series (e.g. "A", "B").
     * @return the series identifier
     */
    public String getSeries() {
        return series;
    }

    /**
     * Sets the voucher series.
     * @param value the series identifier
     */
    public void setSeries(String value) {
        series = value;
    }

    /**
     * Returns the voucher number within its series.
     * @return the voucher number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the voucher number.
     * @param value the voucher number
     */
    public void setNumber(String value) {
        number = value;
    }

    /**
     * Returns the voucher date.
     * @return the voucher date
     */
    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    /**
     * Sets the voucher date.
     * @param value the voucher date
     */
    public void setVoucherDate(LocalDate value) {
        voucherDate = value;
    }

    /**
     * Returns the descriptive text for this voucher.
     * @return the voucher text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the descriptive text for this voucher.
     * @param value the voucher text
     */
    public void setText(String value) {
        text = value;
    }

    /**
     * Returns the date when the voucher was created.
     * @return the creation date, or {@code null} if not set
     */
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets the date when the voucher was created.
     * @param value the creation date
     */
    public void setCreatedDate(LocalDate value) {
        createdDate = value;
    }

    /**
     * Returns the name of the person who created the voucher.
     * @return the creator name
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the name of the person who created the voucher.
     * @param value the creator name
     */
    public void setCreatedBy(String value) {
        createdBy = value;
    }

    /**
     * Returns the SIE token that produced this voucher (e.g. "#VER").
     * @return the token string
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the SIE token that produced this voucher.
     * @param value the token string
     */
    public void setToken(String value) {
        token = value;
    }

    /**
     * Returns the list of transaction rows in this voucher.
     * @return the voucher rows
     */
    public List<SieVoucherRow> getRows() {
        return rows;
    }

    /**
     * Sets the list of transaction rows for this voucher.
     * @param value the voucher rows
     */
    public void setRows(List<SieVoucherRow> value) {
        rows = value;
    }
}
