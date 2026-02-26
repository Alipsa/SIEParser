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

import java.util.function.Consumer;

/**
 * Container for callback consumers that are invoked during SIE document reading.
 */
public class SieCallbacks {

    public SieCallbacks() {}

    private Consumer<String> line;
    private Consumer<Exception> sieException;
    private Consumer<SiePeriodValue> ib;
    private Consumer<SiePeriodValue> ub;
    private Consumer<SiePeriodValue> oib;
    private Consumer<SiePeriodValue> oub;
    private Consumer<SiePeriodValue> psaldo;
    private Consumer<SiePeriodValue> pbudget;
    private Consumer<SiePeriodValue> res;
    private Consumer<SieVoucher> ver;

    public Consumer<String> getLine() { return line; }
    public void setLine(Consumer<String> value) { line = value; }

    public Consumer<Exception> getSieException() { return sieException; }
    public void setSieException(Consumer<Exception> value) { sieException = value; }

    public Consumer<SiePeriodValue> getIB() { return ib; }
    public void setIB(Consumer<SiePeriodValue> value) { ib = value; }

    public Consumer<SiePeriodValue> getUB() { return ub; }
    public void setUB(Consumer<SiePeriodValue> value) { ub = value; }

    public Consumer<SiePeriodValue> getOIB() { return oib; }
    public void setOIB(Consumer<SiePeriodValue> value) { oib = value; }

    public Consumer<SiePeriodValue> getOUB() { return oub; }
    public void setOUB(Consumer<SiePeriodValue> value) { oub = value; }

    public Consumer<SiePeriodValue> getPSALDO() { return psaldo; }
    public void setPSALDO(Consumer<SiePeriodValue> value) { psaldo = value; }

    public Consumer<SiePeriodValue> getPBUDGET() { return pbudget; }
    public void setPBUDGET(Consumer<SiePeriodValue> value) { pbudget = value; }

    public Consumer<SiePeriodValue> getRES() { return res; }
    public void setRES(Consumer<SiePeriodValue> value) { res = value; }

    public Consumer<SieVoucher> getVER() { return ver; }
    public void setVER(Consumer<SieVoucher> value) { ver = value; }

    public void callbackLine(String message) {
        if (line != null) line.accept(message);
    }

    public void callbackException(Exception ex) {
        if (sieException != null) sieException.accept(ex);
    }

    public void callbackIB(SiePeriodValue pv) {
        if (ib != null) ib.accept(pv);
    }

    public void callbackUB(SiePeriodValue pv) {
        if (ub != null) ub.accept(pv);
    }

    public void callbackOIB(SiePeriodValue pv) {
        if (oib != null) oib.accept(pv);
    }

    public void callbackOUB(SiePeriodValue pv) {
        if (oub != null) oub.accept(pv);
    }

    public void callbackPSALDO(SiePeriodValue pv) {
        if (psaldo != null) psaldo.accept(pv);
    }

    public void callbackPBUDGET(SiePeriodValue pv) {
        if (pbudget != null) pbudget.accept(pv);
    }

    public void callbackRES(SiePeriodValue pv) {
        if (res != null) res.accept(pv);
    }

    public void callbackVER(SieVoucher v) {
        if (ver != null) ver.accept(v);
    }
}
