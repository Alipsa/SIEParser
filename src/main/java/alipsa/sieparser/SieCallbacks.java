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

public class SieCallbacks {
    public Action<String> Line;
    public Action<Exception> SieException;
    public Action<SiePeriodValue> IB;
    public Action<SiePeriodValue> UB;
    public Action<SiePeriodValue> OIB;
    public Action<SiePeriodValue> OUB;
    public Action<SiePeriodValue> PSALDO;
    public Action<SiePeriodValue> PBUDGET;
    public Action<SiePeriodValue> RES;
    public Action<SieVoucher> VER;

    public void callbackLine(String message) throws Exception {
        if (Line != null)
            Line.Invoke(message);

    }

    public void callbackException(Exception ex) throws Exception {
        if (SieException != null)
            SieException.Invoke(ex);

    }

    public void callbackIB(SiePeriodValue pv) throws Exception {
        if (IB != null)
            IB.Invoke(pv);

    }

    public void callbackUB(SiePeriodValue pv) throws Exception {
        if (UB != null)
            UB.Invoke(pv);

    }

    public void callbackOIB(SiePeriodValue pv) throws Exception {
        if (OIB != null)
            OIB.Invoke(pv);

    }

    public void callbackOUB(SiePeriodValue pv) throws Exception {
        if (OUB != null)
            OUB.Invoke(pv);

    }

    public void callbackPSALDO(SiePeriodValue pv) throws Exception {
        if (PSALDO != null)
            PSALDO.Invoke(pv);

    }

    public void callbackPBUDGET(SiePeriodValue pv) throws Exception {
        if (PBUDGET != null)
            PBUDGET.Invoke(pv);

    }

    public void callbackRES(SiePeriodValue pv) throws Exception {
        if (RES != null)
            RES.Invoke(pv);

    }

    public void callbackVER(SieVoucher v) throws Exception {
        if (VER != null)
            VER.Invoke(v);

    }

}


