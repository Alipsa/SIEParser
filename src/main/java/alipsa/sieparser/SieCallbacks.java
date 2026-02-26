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

    /** Creates a new empty callbacks container. */
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

    /**
     * Returns the line callback consumer.
     * @return the line consumer, or {@code null} if not set
     */
    public Consumer<String> getLine() { return line; }

    /**
     * Sets the line callback consumer.
     * @param value the line consumer
     */
    public void setLine(Consumer<String> value) { line = value; }

    /**
     * Returns the exception callback consumer.
     * @return the exception consumer, or {@code null} if not set
     */
    public Consumer<Exception> getSieException() { return sieException; }

    /**
     * Sets the exception callback consumer.
     * @param value the exception consumer
     */
    public void setSieException(Consumer<Exception> value) { sieException = value; }

    /**
     * Returns the IB (ingående balans / opening balance) callback consumer.
     * @return the IB consumer, or {@code null} if not set
     */
    public Consumer<SiePeriodValue> getIB() { return ib; }

    /**
     * Sets the IB (ingående balans / opening balance) callback consumer.
     * @param value the IB consumer
     */
    public void setIB(Consumer<SiePeriodValue> value) { ib = value; }

    /**
     * Returns the UB (utgående balans / closing balance) callback consumer.
     * @return the UB consumer, or {@code null} if not set
     */
    public Consumer<SiePeriodValue> getUB() { return ub; }

    /**
     * Sets the UB (utgående balans / closing balance) callback consumer.
     * @param value the UB consumer
     */
    public void setUB(Consumer<SiePeriodValue> value) { ub = value; }

    /**
     * Returns the OIB (opening balance for object) callback consumer.
     * @return the OIB consumer, or {@code null} if not set
     */
    public Consumer<SiePeriodValue> getOIB() { return oib; }

    /**
     * Sets the OIB (opening balance for object) callback consumer.
     * @param value the OIB consumer
     */
    public void setOIB(Consumer<SiePeriodValue> value) { oib = value; }

    /**
     * Returns the OUB (closing balance for object) callback consumer.
     * @return the OUB consumer, or {@code null} if not set
     */
    public Consumer<SiePeriodValue> getOUB() { return oub; }

    /**
     * Sets the OUB (closing balance for object) callback consumer.
     * @param value the OUB consumer
     */
    public void setOUB(Consumer<SiePeriodValue> value) { oub = value; }

    /**
     * Returns the PSALDO (period balance) callback consumer.
     * @return the PSALDO consumer, or {@code null} if not set
     */
    public Consumer<SiePeriodValue> getPSALDO() { return psaldo; }

    /**
     * Sets the PSALDO (period balance) callback consumer.
     * @param value the PSALDO consumer
     */
    public void setPSALDO(Consumer<SiePeriodValue> value) { psaldo = value; }

    /**
     * Returns the PBUDGET (period budget) callback consumer.
     * @return the PBUDGET consumer, or {@code null} if not set
     */
    public Consumer<SiePeriodValue> getPBUDGET() { return pbudget; }

    /**
     * Sets the PBUDGET (period budget) callback consumer.
     * @param value the PBUDGET consumer
     */
    public void setPBUDGET(Consumer<SiePeriodValue> value) { pbudget = value; }

    /**
     * Returns the RES (result) callback consumer.
     * @return the RES consumer, or {@code null} if not set
     */
    public Consumer<SiePeriodValue> getRES() { return res; }

    /**
     * Sets the RES (result) callback consumer.
     * @param value the RES consumer
     */
    public void setRES(Consumer<SiePeriodValue> value) { res = value; }

    /**
     * Returns the VER (voucher) callback consumer.
     * @return the VER consumer, or {@code null} if not set
     */
    public Consumer<SieVoucher> getVER() { return ver; }

    /**
     * Sets the VER (voucher) callback consumer.
     * @param value the VER consumer
     */
    public void setVER(Consumer<SieVoucher> value) { ver = value; }

    /**
     * Invokes the line callback with the given message, if set.
     * @param message the line message
     */
    public void callbackLine(String message) {
        if (line != null) line.accept(message);
    }

    /**
     * Invokes the exception callback with the given exception, if set.
     * @param ex the exception
     */
    public void callbackException(Exception ex) {
        if (sieException != null) sieException.accept(ex);
    }

    /**
     * Invokes the IB callback with the given period value, if set.
     * @param pv the period value
     */
    public void callbackIB(SiePeriodValue pv) {
        if (ib != null) ib.accept(pv);
    }

    /**
     * Invokes the UB callback with the given period value, if set.
     * @param pv the period value
     */
    public void callbackUB(SiePeriodValue pv) {
        if (ub != null) ub.accept(pv);
    }

    /**
     * Invokes the OIB callback with the given period value, if set.
     * @param pv the period value
     */
    public void callbackOIB(SiePeriodValue pv) {
        if (oib != null) oib.accept(pv);
    }

    /**
     * Invokes the OUB callback with the given period value, if set.
     * @param pv the period value
     */
    public void callbackOUB(SiePeriodValue pv) {
        if (oub != null) oub.accept(pv);
    }

    /**
     * Invokes the PSALDO callback with the given period value, if set.
     * @param pv the period value
     */
    public void callbackPSALDO(SiePeriodValue pv) {
        if (psaldo != null) psaldo.accept(pv);
    }

    /**
     * Invokes the PBUDGET callback with the given period value, if set.
     * @param pv the period value
     */
    public void callbackPBUDGET(SiePeriodValue pv) {
        if (pbudget != null) pbudget.accept(pv);
    }

    /**
     * Invokes the RES callback with the given period value, if set.
     * @param pv the period value
     */
    public void callbackRES(SiePeriodValue pv) {
        if (res != null) res.accept(pv);
    }

    /**
     * Invokes the VER callback with the given voucher, if set.
     * @param v the voucher
     */
    public void callbackVER(SieVoucher v) {
        if (ver != null) ver.accept(v);
    }
}
