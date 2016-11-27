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
import java.util.LinkedList;
import java.util.List;

public class MultiAction<T> implements Action<T> {

    private List<Action<T>> _invocationList = new ArrayList<Action<T>>();

    public static <T> Action<T> Combine(Action<T> a, Action<T> b) throws Exception {
        if (a == null)
            return b;

        if (b == null)
            return a;

        MultiAction<T> ret = new MultiAction<T>();
        ret._invocationList = a.getInvocationList();
        ret._invocationList.addAll(b.getInvocationList());
        return ret;
    }

    public static <T> Action<T> remove(Action<T> a, Action<T> b) throws Exception {
        if (a == null || b == null)
            return a;

        List<Action<T>> aInvList = a.getInvocationList();
        List<Action<T>> newInvList = ListSupport.removeFinalStretch(aInvList, b.getInvocationList());
        if (aInvList == newInvList) {
            return a;
        } else {
            MultiAction<T> ret = new MultiAction<T>();
            ret._invocationList = newInvList;
            return ret;
        }
    }

    public void Invoke(T obj) throws Exception {
        List<Action<T>> copy, members = this.getInvocationList();
        synchronized (members) {
            copy = new LinkedList<>(members);
        }
        for (Action<T> d : copy) {
            d.Invoke(obj);
        }
    }

    public List<Action<T>> getInvocationList() throws Exception {
        return _invocationList;
    }


}
