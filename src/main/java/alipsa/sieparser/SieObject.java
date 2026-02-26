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

/**
 * Represents a SIE object (#OBJEKT) within a dimension.
 * Objects are identified by a number within their dimension and have a descriptive name.
 */
public class SieObject {
    private SieDimension dimension;
    private String number;
    private String name;

    /**
     * Creates a new SieObject belonging to the given dimension.
     *
     * @param dimension the dimension this object belongs to
     * @param number the object number (unique within the dimension)
     * @param name the object name
     */
    public SieObject(SieDimension dimension, String number, String name) {
        setDimension(dimension);
        setNumber(number);
        setName(name);
    }

    /**
     * Returns the dimension this object belongs to.
     *
     * @return the parent dimension
     */
    public SieDimension getDimension() {
        return dimension;
    }

    /**
     * Sets the dimension this object belongs to.
     *
     * @param value the parent dimension
     */
    public void setDimension(SieDimension value) {
        dimension = value;
    }

    /**
     * Returns the object number.
     *
     * @return the object number
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the object number.
     *
     * @param value the object number
     */
    public void setNumber(String value) {
        number = value;
    }

    /**
     * Returns the object name.
     *
     * @return the object name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the object name.
     *
     * @param value the object name
     */
    public void setName(String value) {
        name = value;
    }

}
