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

    public SieVoucher() {
        setRows(new ArrayList<>());
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String value) {
        series = value;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String value) {
        number = value;
    }

    public LocalDate getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(LocalDate value) {
        voucherDate = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String value) {
        text = value;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate value) {
        createdDate = value;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String value) {
        createdBy = value;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String value) {
        token = value;
    }

    public List<SieVoucherRow> getRows() {
        return rows;
    }

    public void setRows(List<SieVoucherRow> value) {
        rows = value;
    }
}
