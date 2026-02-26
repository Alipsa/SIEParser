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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a SIE dimension (#DIM).
 * Dimensions are used for multi-dimensional accounting (e.g. cost centers, projects).
 * Standard dimensions 1-19 are predefined; custom dimensions can also be added.
 */
public class SieDimension {
    private boolean isDefault;
    private Set<SieDimension> subDim = new HashSet<>();
    private Map<String, SieObject> objects = new HashMap<>();
    private String number;
    private String name;
    private SieDimension parent = null;

    public SieDimension(String num, String aName, boolean defaultVal) {
        number = num;
        name = aName;
        isDefault = defaultVal;
    }

    public SieDimension(String num, String aName) {
        number = num;
        name = aName;
        isDefault = false;
    }

    public SieDimension(String number) {
        this(number, "");
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean value) {
        isDefault = value;
    }

    public Set<SieDimension> getSubDim() {
        return subDim;
    }

    public Map<String, SieObject> getObjects() {
        return objects;
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

    public SieDimension getSuperDim() {
        return parent;
    }

    public void setSuperDim(SieDimension value) {
        parent = value;
        parent.subDim.add(this);
    }
}
