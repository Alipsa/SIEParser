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
 * Thrown when a required field is missing from a SIE data item.
 */
public class MissingFieldException extends SieException {

    /**
     * Creates a new MissingFieldException with the given message.
     *
     * @param s the detail message describing which field is missing
     */
    public MissingFieldException(String s) {
        super(s);
    }

    /**
     * Creates a new MissingFieldException with the given message and cause.
     *
     * @param s the detail message describing which field is missing
     * @param cause the underlying cause
     */
    public MissingFieldException(String s, Throwable cause) {
        super(s, cause);
    }
}
