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

public class StringUtil {

    private static boolean isIn(char c, char[] cs) {
        for (char f : cs)
            if (f == c)
                return true;

        return false;
    }

    public static String trim(String in, char[] filters, boolean trimStart, boolean trimEnd) {
        if (in == null) {
            return null;
        }
        // Locate first non-trimmable index
        int firstIdx = 0;
        if (trimStart) {
            while (firstIdx < in.length()) {
                if (isIn(in.charAt(firstIdx), filters))
                    firstIdx++;
                else
                    break;
            }
        }

        int lastIdx = in.length();
        if (trimEnd) {
            while (lastIdx > firstIdx) {
                if (isIn(in.charAt(lastIdx - 1), filters))
                    lastIdx--;
                else
                    break;
            }
        }

        // firstIdx is start of new string, lastIdx is end of new string
        return in.substring(firstIdx, lastIdx);
    }

    public static String trim(String in, char[] filters) {
        return trim(in, filters, true, true);
    }

    public static boolean equals(String s1, String s2) {
        if (s1 == null)
            return s2 == null;
        else
            return s1.equals(s2);
    }

    public static boolean isNullOrEmpty(String str) {
        return (str == null || "".equals(str));
    }
}
