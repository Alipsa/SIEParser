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

import java.time.LocalDate;

/**
 * Represents a SIE booking year (#RAR).
 * Contains the year identifier, start date, and end date of the financial year.
 */
public class SieBookingYear {

    /** Creates a new empty booking year. */
    public SieBookingYear() {}

    private int id;
    private LocalDate start;
    private LocalDate end;

    /**
     * Returns the year identifier.
     * @return the year identifier (0 = current, -1 = previous, etc.)
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the year identifier.
     * @param value the year identifier
     */
    public void setId(int value) {
        id = value;
    }

    /**
     * Returns the start date of the financial year.
     * @return the start date
     */
    public LocalDate getStart() {
        return start;
    }

    /**
     * Sets the start date of the financial year.
     * @param value the start date
     */
    public void setStart(LocalDate value) {
        start = value;
    }

    /**
     * Returns the end date of the financial year.
     * @return the end date
     */
    public LocalDate getEnd() {
        return end;
    }

    /**
     * Sets the end date of the financial year.
     * @param value the end date
     */
    public void setEnd(LocalDate value) {
        end = value;
    }
}
