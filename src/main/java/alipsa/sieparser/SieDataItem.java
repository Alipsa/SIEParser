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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a parsed line (data item) from a SIE file.
 * Each line consists of an item type (e.g. #KONTO, #VER) followed by data fields.
 * This class handles parsing the raw line and provides typed accessors for the fields.
 */
public class SieDataItem {
    private SieDocumentReader documentReader;
    private SieDocument document;
    private String itemType;
    private List<String> data;
    private String rawData;

    public SieDataItem(String line, SieDocumentReader documentReader, SieDocument doc) {
        setRawData(line);
        setDocumentReader(documentReader);
        document = doc;
        String l = line.trim();
        int p = firstWhiteSpace(l);
        if (p == -1) {
            setItemType(l);
            setData(new ArrayList<>());
        } else {
            setItemType(l.substring(0, p));
            setData(splitLine(l.substring(p + 1)));
        }
    }

    public SieDocumentReader getDocumentReader() {
        return documentReader;
    }

    public void setDocumentReader(SieDocumentReader value) {
        documentReader = value;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String value) {
        itemType = value;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> value) {
        data = value;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String value) {
        rawData = value;
    }

    private int firstWhiteSpace(String str) {
        int a = str.indexOf(" ");
        int b = str.indexOf("\t");
        if (a == -1 && b == -1) return -1;
        if (a == -1) return b;
        if (b == -1) return a;
        return Math.min(a, b);
    }

    List<String> splitLine(String untrimmedData) {
        String data = untrimmedData.trim();
        List<String> ret = new ArrayList<>();
        int isInField = 0;
        boolean isInObject = false;
        StringBuilder buffer = new StringBuilder();
        boolean skipNext = false;
        for (char c : data.toCharArray()) {
            // Fix: only skip if the next char after backslash is a quote
            if (skipNext && c == '"') {
                skipNext = false;
                buffer.append(c);
                continue;
            }
            skipNext = false;

            if (c == '\\') {
                skipNext = true;
                continue;
            }

            if (c == '"' && !isInObject) {
                isInField += 1;
                continue;
            }

            if (c == '{') isInObject = true;
            if (c == '}') isInObject = false;

            if ((c == ' ' || c == '\t') && (isInField != 1) && !isInObject) {
                String trimBuf = buffer.toString().trim();
                if (trimBuf.length() > 0 || isInField == 2) {
                    ret.add(trimBuf);
                    buffer = new StringBuilder();
                }
                isInField = 0;
            } else {
                buffer.append(c);
            }
        }
        if (buffer.length() > 0) {
            ret.add(buffer.toString().trim());
        }

        return ret;
    }

    public long getLong(int field) {
        if (getData().size() <= field) return 0;
        return Long.parseLong(getData().get(field));
    }

    public int getInt(int field) {
        Integer foo = getIntNull(field);
        return foo != null ? foo : 0;
    }

    public Integer getIntNull(int field) {
        if (getData().size() <= field) return null;
        try {
            return Integer.parseInt(getData().get(field));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public BigDecimal getDecimal(int field) {
        BigDecimal foo = getDecimalNull(field);
        return foo != null ? foo : BigDecimal.ZERO;
    }

    public BigDecimal getDecimalNull(int field) {
        if (getData().size() <= field) return null;
        try {
            return new BigDecimal(getData().get(field));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public String getString(int field) {
        if (getData().size() <= field) return "";
        String s = getData().get(field).trim();
        s = StringUtil.trim(s, new char[]{'"'});
        return s;
    }

    public LocalDate getDate(int field) {
        if (getData().size() <= field) return null;

        String foo = getData().get(field).trim();
        if (foo.isEmpty()) return null;

        if (foo.length() != 8) {
            getDocumentReader().callbacks.callbackException(new SieDateException(foo + " is not a valid date"));
            return null;
        }
        int y = Integer.parseInt(foo.substring(0, 4));
        int m = Integer.parseInt(foo.substring(4, 6));
        int d = Integer.parseInt(foo.substring(6, 8));
        return LocalDate.of(y, m, d);
    }

    public List<SieObject> getObjects() {
        String dimNumber;
        String objectNumber;
        List<SieObject> ret = new ArrayList<>();
        if (getRawData().contains("{}")) return null;

        String data = null;
        for (String i : getData()) {
            if (i.trim().startsWith("{")) {
                data = i.trim().replace("{", "").replace("}", "");
                break;
            }
        }
        if (data == null) {
            getDocumentReader().callbacks.callbackException(new SieMissingObjectException(getRawData()));
            return null;
        }

        List<String> dimData = splitLine(data);
        for (int i = 0; i < dimData.size(); i += 2) {
            dimNumber = dimData.get(i);
            // Look up UNDERDIM/TEMPDIM first, then fall back to DIM
            if (!document.getDIM().containsKey(dimNumber)) {
                if (document.getUNDERDIM().containsKey(dimNumber)) {
                    SieDimension underDim = document.getUNDERDIM().get(dimNumber);
                    document.getDIM().put(dimNumber, underDim);
                } else {
                    // Check TEMPDIM
                    if (document.getTEMPDIM().containsKey(dimNumber)) {
                        SieDimension tempDim = document.getTEMPDIM().get(dimNumber);
                        document.getDIM().put(dimNumber, tempDim);
                    } else {
                        document.getDIM().put(dimNumber, new SieDimension(dimNumber, "[TEMP]"));
                        document.getTEMPDIM().put(dimNumber, document.getDIM().get(dimNumber));
                    }
                }
            }

            SieDimension d = document.getDIM().get(dimNumber);
            objectNumber = dimData.get(i + 1);
            if (!d.getObjects().containsKey(objectNumber)) {
                d.getObjects().put(objectNumber, new SieObject(d, objectNumber, "[TEMP]"));
            }

            ret.add(d.getObjects().get(objectNumber));
        }
        return ret;
    }
}
