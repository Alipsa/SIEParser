/*
MIT License

Copyright (c) 2016 Alipsa HB

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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Centralized place for creating Readers and Writers using the SIE charset (IBM437).
 */
public class IoUtil {

    private IoUtil() {}

    /**
     * Creates a {@link BufferedReader} for reading a SIE file using the IBM437 charset.
     *
     * @param fileName the path to the file to read
     * @return a buffered reader for the file
     * @throws IOException if the file cannot be opened
     */
    public static BufferedReader getReader(String fileName) throws IOException {
        return Files.newBufferedReader(Paths.get(fileName), Encoding.getCharset());
    }

    /**
     * Creates a {@link BufferedWriter} for writing a SIE file using the IBM437 charset.
     * If the file already exists it will be overwritten; otherwise a new file is created.
     *
     * @param fileName the path to the file to write
     * @return a buffered writer for the file
     * @throws IOException if the file cannot be opened or created
     */
    public static BufferedWriter getWriter(String fileName) throws IOException {
        return Files.newBufferedWriter(Paths.get(fileName), Encoding.getCharset(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
