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
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class SieDataItem {
    private SieDocumentReader documentReader;
    private SieDocument document;
    private String itemType;
    private List<String> data;
    private String rawData;

    public SieDataItem(String line, SieDocumentReader documentReader, SieDocument doc) throws Exception {
        setRawData(line);
        setDocumentReader(documentReader);
        document = doc;
        String l = line.trim();
        int p = firstWhiteSpace(l);
        if (p == -1) {
            setItemType(l);
            setData(new ArrayList<>());
        } else {
            setItemType(l.substring(0, (0) + (p)));
            setData(splitLine(l.substring(p + 1, (p + 1) + (l.length() - (p + 1)))));
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

    private int firstWhiteSpace(String str) throws Exception {
        int a = str.indexOf(" ");
        int b = str.indexOf("\t");
        if (a == -1 && b == -1)
            return -1;

        if (a == -1 && b != -1)
            return b;

        if (b == -1)
            return a;

        if (a <= b) {
            return a;
        } else {
            return b;
        }
    }

    private List<String> splitLine(String untrimmedData) throws Exception {
        String data = untrimmedData.trim();
        List<String> ret = new ArrayList<>();
        int isInField = 0;
        boolean isInObject = false;
        String buffer = "";
        boolean skipNext = false;
        for (char c : data.toCharArray()) {
            if (skipNext) {
                skipNext = false;
                continue;
            }

            if (c == '\\') {
                skipNext = true;
                continue;
            }

            if (c == '"' && !isInObject) {
                isInField += 1;
                continue;
            }

            if (c == '{')
                isInObject = true;

            if (c == '}')
                isInObject = false;

            if ((c == ' ' || c == '\t') && (isInField != 1) && !isInObject) {
                String trimBuf = buffer.trim();
                if (trimBuf.length() > 0 || isInField == 2) {
                    ret.add(trimBuf);
                    buffer = "";
                }

                isInField = 0;
            }

            buffer += c;
        }
        if (buffer.length() > 0) {
            ret.add(buffer.trim());
        }

        return ret;
    }

    public long getLong(int field) throws Exception {
        if (getData().size() <= field)
            return 0;

        long i = 0;
        i = Long.parseLong(getData().get(field));
        return i;
    }

    public int getInt(int field) throws Exception {
        Integer foo = getIntNull(field);
        return foo != null ? foo : 0;
    }

    public Integer getIntNull(int field) throws Exception {
        if (getData().size() <= field)
            return null;

        int i = 0;
        try {
            i = Integer.parseInt(getData().get(field));
        } catch (NumberFormatException e) {
            return null;
        }
        return i;
    }

    public BigDecimal getDecimal(int field) throws Exception {
        BigDecimal foo = getDecimalNull(field);
        return foo != null ? foo : new BigDecimal(0);
    }

    public BigDecimal getDecimalNull(int field) throws Exception {
        if (getData().size() <= field)
            return null;

        return new BigDecimal(getData().get(field));
    }

    public String getString(int field) throws Exception {
        if (getData().size() <= field)
            return "";

        String s = getData().get(field).trim();
        s = StringUtil.trim(s, new char[]{'"'});
        return s;
    }

    public Date getDate(int field) throws Exception {
        if (getData().size() <= field)
            return null;

        String foo = getData().get(field).trim();

        if (foo.length()==0)
            return null;

        if (foo.length() != 8) {
            getDocumentReader().callbacks.callbackException(new SieDateException(foo + " is not a valid date"));
            return null;
        }
        int y = Integer.parseInt(foo.substring(0,4));
        int m = Integer.parseInt(foo.substring(4,6));;
        int d = Integer.parseInt(foo.substring(6,8));;
        return new GregorianCalendar(y, m - 1, d).getTime();
    }

    public List<SieObject> getObjects() throws Exception {
        String dimNumber = null;
        String objectNumber = null;
        List<SieObject> ret = new ArrayList<SieObject>();
        if (getRawData().contains("{}"))
            return null;

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
            //TODO: not sure how this would be possible
            //Add temporary Dimension if the dimensions hasn't been loaded yet.
            if (!document.getDIM().containsKey(dimNumber)) {
                document.getDIM().put(dimNumber, new SieDimension(dimNumber, "[TEMP]"));
            }

            SieDimension d = document.getDIM().get(dimNumber);
            objectNumber = dimData.get(i + 1);
            //Add temporary object if the objects hasn't been loaded yet.
            if (!d.objects.containsKey(objectNumber)) {
                d.objects.put(objectNumber, new SieObject(d,objectNumber, "[TEMP]"));
            }

            ret.add(d.objects.get(objectNumber));
        }
        return ret;
    }

}


