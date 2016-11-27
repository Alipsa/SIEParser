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
import java.util.List;

public class ListSupport {
    // If the sequence of elements in stretch appears in l, then returns a new list, a copy of l without
    // the final such sequence. Otherwise returns l.
    public static <T> List<T> removeFinalStretch(List<T> l, List<T> stretch) {
        List<T> ret = l;
        boolean found = true;
        int endIdx = l.size() - 1;
        while (endIdx >= stretch.size() - 1) {
            // is this the start of a sequence of stretch?
            found = true;
            for (int j = 0; j < stretch.size(); j++) {
                if (l.get(endIdx - j) != stretch.get(stretch.size() - j - 1)) {
                    found = false;
                    break;
                }
            }
            if (found) {
                break;
            }
            endIdx--;
        }
        if (found) {
            // stretch starts at l(endIdx - stretch.size())
            // don't use subList, create a totally new list.
            ret = new ArrayList<T>(l.size() - stretch.size());
            for (int i = 0; i <= endIdx - stretch.size(); i++) {
                ret.add(l.get(i));
            }
            for (int i = endIdx + 1; i < l.size(); i++) {
                ret.add(l.get(i));
            }
        }

        return ret;
    }
}
