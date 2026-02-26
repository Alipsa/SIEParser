package alipsa.sieparser;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Simple test that Encoding works as expected.
 * SIE standard requires IBM437:
 * 5.8 Teckenrepertoaren i filen ska vara IBM PC 8-bitars extended ASCII (Codepage 437)
 */
public class TestEncoding {

    @Test
    public void encodingTest() throws IOException {
        String text="Nu är det så att åäö behöver encodas rätt!";
        Path filePath = Paths.get("build", "test.txt");
        Files.createDirectories(filePath.getParent());
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        try {
            BufferedWriter writer = IoUtil.getWriter(filePath.toString());
            writer.write(text);
            writer.flush();
            writer.close();

            BufferedReader reader = IoUtil.getReader(filePath.toString());
            String content = reader.readLine();
            reader.close();
            System.out.println("Text is " + content);
            assertEquals(text, content, "Content should match");
        } finally {
            Files.deleteIfExists(filePath);
        }
    }

    @Test
    public void encodingsTest() throws IOException {
        String text="Nu är det så att åäö behöver encodas rätt!";
        Path filePath = Paths.get("build", "test2.txt");
        Files.createDirectories(filePath.getParent());
        try {
            compare(text, filePath, "UTF-8");
            compare(text, filePath, "ISO-8859-1");
            compare(text, filePath, "IBM437");
            compare(text, filePath, "Cp437");
        } finally {
            Files.deleteIfExists(filePath);
        }

    }

    private void compare(String text, Path filePath, String encoding) throws IOException {
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }
        write(text, filePath, encoding);
        String content = read(filePath, encoding);
        assertEquals(text, content, "Content should match");
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
