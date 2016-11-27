package alipsa.sieparser;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.Assert.assertEquals;


/**
 * Simple test that Encoding works as expected.
 * SIE standard requires IBM437:
 * 5.8 Teckenrepertoaren i filen ska vara IBM PC 8-bitars extended ASCII (Codepage 437)
 */
public class TestEncoding {

    @Test
    public void encodingTest() throws IOException {
        String text="Nu är det så att åäö behöver encodas rätt!";
        String fileName = "test.txt";
        Path filePath = Paths.get(fileName);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        BufferedWriter writer = IoUtil.getWriter(fileName);
        writer.write(text);
        writer.flush();
        writer.close();

        BufferedReader reader = IoUtil.getReader(fileName);
        String content = reader.readLine();
        reader.close();
        System.out.println("Text is " + content);
        assertEquals("Content should match", text, content);
    }

    @Test
    public void encodingsTest() throws IOException {
        String text="Nu är det så att åäö behöver encodas rätt!";
        String fileName = "test2.txt";
        Path filePath = Paths.get(fileName);
        compare(text, filePath, "UTF-8");
        compare(text, filePath, "ISO-8859-1");
        compare(text, filePath, "IBM437");
        compare(text, filePath, "Cp437");

    }

    private void compare(String text, Path filePath, String encoding) throws IOException {
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        write(text, filePath, encoding);
        String content = read(filePath, encoding);
        //System.out.println("Text is " + content);
        assertEquals("Content should match", text, content);
    }

    private void write(String text, Path filePath, String encoding) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(
                filePath,
                Charset.forName(encoding),
                StandardOpenOption.CREATE_NEW);
        writer.write(text);
        writer.flush();
        writer.close();
    }

    private String read(Path filePath, String encoding) throws IOException {
        BufferedReader reader = Files.newBufferedReader(filePath, Charset.forName(encoding));
        String content = reader.readLine();
        reader.close();
        return content;
    }
}
