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
import java.util.List;

public class SieVoucher {
    private String series;
    private String number;
    private Date voucherDate;
    private String text;
    private int createdDate;
    private String createdBy;
    private String token;
    private List<SieVoucherRow> rows;

    public SieVoucher() throws Exception {
        setRows(new ArrayList<SieVoucherRow>());
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

    public Date getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(Date value) {
        voucherDate = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String value) {
        text = value;
    }

    public int getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(int value) {
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


