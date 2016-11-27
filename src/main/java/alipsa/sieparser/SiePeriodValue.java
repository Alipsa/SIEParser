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

public class SiePeriodValue {
    private SieAccount account;
    private int yearNr;
    private int period;
    private BigDecimal amount;
    private BigDecimal quantity;
    private List<SieObject> objects;
    private String token;

    public SieAccount getAccount() {
        return account;
    }

    public void setAccount(SieAccount account) {
        this.account = account;
    }

    public int getYearNr() {
        return yearNr;
    }

    public void setYearNr(int yearNr) {
        this.yearNr = yearNr;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public List<SieObject> getObjects() {
        return objects;
    }

    public void setObjects(List<SieObject> objects) {
        this.objects = objects;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /*
    public SieVoucherRow toVoucherRow() throws Exception {
        SieVoucherRow vr = new SieVoucherRow();
        return vr;
    }

    public SieVoucherRow toInvertedVoucherRow() throws Exception {
        SieVoucherRow vr = toVoucherRow();
        vr.setAmount(vr.getAmount() * -1);
        return vr;
    }
    */
}


