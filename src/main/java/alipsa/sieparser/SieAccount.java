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

/**
 * #KONTO
 */
public class SieAccount {
    private String number;
    private String name;
    private String unit;
    private String type;
    private List<String> sru = new ArrayList<>();

    public SieAccount() {
        setSRU(new ArrayList<>());
        setName("");
    }

    public SieAccount(String number) {
        this(number, "");
    }

    public SieAccount(String number, String name) {
        this();
        setNumber(number);
        setName(name);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String value) {
        number = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String value) {
        unit = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        type = value;
    }

    public List<String> getSRU() {
        return sru;
    }

    public void setSRU(List<String> value) {
        sru = value;
    }

}


