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
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Centralized place for creating Readers and writers.
 */
public class IoUtil {

    public static BufferedReader getReader(String fileName) throws IOException {
        /*
        CharsetDecoder decoder = Encoding.getCharset().newDecoder();
        decoder.onMalformedInput(CodingErrorAction.REPORT);
        InputStreamReader isReader = new InputStreamReader(new FileInputStream(fileName), decoder);
        return new BufferedReader( isReader );
        */
        return Files.newBufferedReader(Paths.get(fileName), Encoding.getCharset());

    }

    public static BufferedWriter getWriter(String fileName) throws IOException {
        /*
        CharsetEncoder encoder = Encoding.getCharset().newEncoder();
        encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
        OutputStreamWriter osWriter = new OutputStreamWriter(new FileOutputStream(fileName), encoder);
        return new BufferedWriter( osWriter );
        */
        Path filePath = Paths.get(fileName);
        StandardOpenOption option;
        if (Files.exists(filePath)) {
            option = StandardOpenOption.WRITE;
        } else {
            option = StandardOpenOption.CREATE_NEW;
        }
        return Files.newBufferedWriter(Paths.get(fileName),Encoding.getCharset(), option);
    }
}
