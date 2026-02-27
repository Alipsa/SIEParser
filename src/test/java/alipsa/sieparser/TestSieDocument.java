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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestSieDocument {

    @BeforeEach
    public void setup() {
    }

    static Stream<File> sampleFiles() {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples");
        File sampleDir = new File(url.getFile());
        List<File> files = new ArrayList<>();
        for (File f : sampleDir.listFiles()) {
            if (f.isFile() && !f.getName().startsWith(".")) {
                files.add(f);
            }
        }
        files.sort((a, b) -> a.getName().compareTo(b.getName()));
        return files.stream();
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("sampleFiles")
    public void verifySampleFile(File sourceFile) throws IOException {
        switch (sourceFile.getName()) {
            case "37_Norstedts Bokslut SIE 1.se":
            case "38_Bokslut Norstedts SIE 4E.se":
            case "39_Norstedts Bokslut SIE 4I.si":
            case "40_Norstedts Revision SIE 1.SE":
            case "41_sie1.se":
            case "42_sie2.se":
            case "43_sie3.se":
            case "44_sie4.se":
                // skip: invalid checksum
                return;
            default:
                break;
        }

        boolean ignoreOMFATTN = false;
        switch (sourceFile.getName()) {
            case "2_BL0001_typ3.SE":
            case "1_BL0001_typ2.SE":
            case "52_periodsaldo_ovnbolag.se":
            case "53_objektsaldo_ovnbolag.se":
                ignoreOMFATTN = true;
                break;
        }

        File outFile = File.createTempFile("test-" + sourceFile.getName(), ".SE");
        try {
            verifyDocument(sourceFile, outFile, ignoreOMFATTN);
        } finally {
            outFile.delete();
        }
    }

    @Test
    public void verifyDocuments() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples");
        File sampleDir = new File(url.getFile());
        for (File sourceFile : sampleDir.listFiles()) {
            if (!sourceFile.isFile() || sourceFile.getName().startsWith(".")) continue;
            File outFile = File.createTempFile("test-" + sourceFile.getName(), ".SE");

            switch (sourceFile.getName()) {
                case "2_BL0001_typ3.SE":
                case "1_BL0001_typ2.SE":
                    verifyDocument(sourceFile, outFile, true);
                    break;
                case "37_Norstedts Bokslut SIE 1.se":
                case "38_Bokslut Norstedts SIE 4E.se":
                case "39_Norstedts Bokslut SIE 4I.si":
                case "40_Norstedts Revision SIE 1.SE":
                case "41_sie1.se":
                case "42_sie2.se":
                case "43_sie3.se":
                case "44_sie4.se":
                    // skip it, invalid checksum
                    break;
                case "52_periodsaldo_ovnbolag.se":
                case "53_objektsaldo_ovnbolag.se":
                    verifyDocument(sourceFile, outFile, true);
                    break;
                default:
                    verifyDocument(sourceFile, outFile, false);
            }
            outFile.deleteOnExit();
        }
    }

    private void verifyDocument(File sourceFile, File outFile, boolean ignoreOMFATTN) {
        System.out.println("Verifying " + sourceFile.getAbsolutePath() + " against " + outFile.getAbsolutePath());
        if (outFile.exists()) outFile.delete();
        SieDocument doc = readDocument(sourceFile.getAbsolutePath(), ignoreOMFATTN);
        writeDocument(doc, outFile.getAbsolutePath());
        assertTrue(outFile.exists(), "SIE file not found at " + outFile.getAbsolutePath());
        compareDocs(doc, readDocument(outFile.getAbsolutePath(), ignoreOMFATTN));
    }

    private void testSieVersion(File file, int sieVersion) {
        try {
            int ver = SieDocumentReader.getSieVersion(file.getAbsolutePath());
            assertEquals(sieVersion, ver, "SIE version differs");
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    private SieDocument readDocument(String fileName, boolean ignoreOMFATTN) {
        SieDocument doc = null;
        try {
            File file = new File(fileName);
            assertTrue(file.exists(), "SIE file not found at " + file.getAbsolutePath());
            SieDocumentReader reader = new SieDocumentReader();
            if (ignoreOMFATTN) {
                reader.setIgnoreMissingOMFATTNING(true);
            }
            doc = reader.readDocument(file.getAbsolutePath());
            if (reader.getValidationExceptions().size() > 0) {
                for (Exception ex : reader.getValidationExceptions()) {
                    System.out.println(ex.toString());
                }
                fail("Validation errors while reading document");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
        return doc;
    }

    private void writeDocument(SieDocument doc, String toFileName) {
        try {
            SieDocumentWriter writer = new SieDocumentWriter(doc);
            writer.write(toFileName);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    private void compareDocs(SieDocument aDoc, SieDocument bDoc) {
        List<String> result = SieDocumentComparer.compare(aDoc, bDoc);
        if (result.size() > 0) {
            StringBuilder buf = new StringBuilder();
            for (String error : result) {
                buf.append(error);
                buf.append(System.lineSeparator());
            }
            fail(buf.toString());
        }
    }
}
