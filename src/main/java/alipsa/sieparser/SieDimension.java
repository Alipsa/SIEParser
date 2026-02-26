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

    /**
     * Creates a new dimension with the given number, name, and default flag.
     * @param num the dimension number
     * @param aName the dimension name
     * @param defaultVal whether this is a predefined default dimension
     */
    public SieDimension(String num, String aName, boolean defaultVal) {
        number = num;
        name = aName;
        isDefault = defaultVal;
    }

    /**
     * Creates a new non-default dimension with the given number and name.
     * @param num the dimension number
     * @param aName the dimension name
     */
    public SieDimension(String num, String aName) {
        number = num;
        name = aName;
        isDefault = false;
    }

    /**
     * Creates a new non-default dimension with the given number and an empty name.
     * @param number the dimension number
     */
    public SieDimension(String number) {
        this(number, "");
    }

    /**
     * Returns whether this is a predefined default dimension.
     * @return {@code true} if this is a default dimension
     */
    public boolean isDefault() {
        return isDefault;
    }

    /**
     * Sets whether this is a predefined default dimension.
     * @param value the default flag
     */
    public void setDefault(boolean value) {
        isDefault = value;
    }

    /**
     * Returns the set of sub-dimensions under this dimension.
     * @return the sub-dimensions
     */
    public Set<SieDimension> getSubDim() {
        return subDim;
    }

    /**
     * Returns the objects belonging to this dimension, keyed by object number.
     * @return the objects map
     */
    public Map<String, SieObject> getObjects() {
        return objects;
    }

    /**
     * Returns the dimension number.
     * @return the dimension number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the dimension number.
     * @param value the dimension number
     */
    public void setNumber(String value) {
        number = value;
    }

    /**
     * Returns the dimension name.
     * @return the dimension name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the dimension name.
     * @param value the dimension name
     */
    public void setName(String value) {
        name = value;
    }

    /**
     * Returns the parent (super) dimension, or {@code null} if none.
     * @return the parent dimension
     */
    public SieDimension getSuperDim() {
        return parent;
    }

    /**
     * Sets the parent (super) dimension and registers this dimension as its sub-dimension.
     * @param value the parent dimension
     */
    public void setSuperDim(SieDimension value) {
        parent = value;
        parent.subDim.add(this);
    }
}
