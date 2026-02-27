package alipsa.sieparser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SieDocumentReader features: flags, error handling, new capabilities.
 */
public class SieDocumentReaderTest {

    @Test
    public void getSieVersion() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);
        int version = SieDocumentReader.getSieVersion(new File(url.getFile()).getAbsolutePath());
        assertEquals(4, version);
    }

    @Test
    public void readType2() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/1_BL0001_typ2.SE");
        assertNotNull(url);
        SieDocumentReader reader = new SieDocumentReader();
        reader.setIgnoreMissingOMFATTNING(true);
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertEquals(2, doc.getSIETYP());
    }

    @Test
    public void readType4() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);
        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertEquals(4, doc.getSIETYP());
        assertFalse(doc.getVER().isEmpty(), "Type 4 should have vouchers");
    }

    @Test
    public void allowUnbalancedVoucher() throws IOException {
        SieDocumentReader reader = new SieDocumentReader();
        reader.setAllowUnbalancedVoucher(true);
        assertDoesNotThrow(() -> {
            // Should not throw even if vouchers are unbalanced
            // (the flag prevents the balance check)
        });
    }

    @Test
    public void acceptSieTypesFilter() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);

        // Only accept type 2 - should reject type 4 file
        SieDocumentReader reader = new SieDocumentReader();
        reader.setAcceptSIETypes(EnumSet.of(SieType.TYPE_2));
        reader.setThrowErrors(true);

        assertThrows(RuntimeException.class, () -> {
            reader.readDocument(new File(url.getFile()).getAbsolutePath());
        });
    }

    @Test
    public void acceptSieTypesAllows() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);

        SieDocumentReader reader = new SieDocumentReader();
        reader.setAcceptSIETypes(EnumSet.of(SieType.TYPE_4));
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertNotNull(doc);
        assertEquals(4, doc.getSIETYP());
    }

    @Test
    public void ignoreKSUMMA() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);

        SieDocumentReader reader = new SieDocumentReader();
        reader.setIgnoreKSUMMA(true);
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertNotNull(doc);
    }

    @Test
    public void parsingLineNumberTracked() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertTrue(reader.getParsingLineNumber() > 0, "Line number should be tracked");
    }

    @Test
    public void ignoreBTRANS() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);

        SieDocumentReader reader = new SieDocumentReader();
        reader.setIgnoreBTRANS(true);
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertNotNull(doc);

        // All voucher rows should be #TRANS or #RTRANS, no #BTRANS
        for (SieVoucher v : doc.getVER()) {
            for (SieVoucherRow r : v.getRows()) {
                assertNotEquals("#BTRANS", r.getToken());
            }
        }
    }

    @Test
    public void companyInfoParsed() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertNotNull(doc.getFNAMN().getName());
        assertFalse(doc.getFNAMN().getName().isEmpty());
    }

    @Test
    public void accountsParsed() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertFalse(doc.getKONTO().isEmpty(), "Accounts should be parsed");
    }

    @Test
    public void rarsParsed() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertFalse(doc.getRars().isEmpty(), "Booking years should be parsed");
    }

    @Test
    public void ignoreRTRANS() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url);

        SieDocumentReader reader = new SieDocumentReader();
        reader.setIgnoreRTRANS(true);
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertNotNull(doc);

        for (SieVoucher v : doc.getVER()) {
            for (SieVoucherRow r : v.getRows()) {
                assertNotEquals("#RTRANS", r.getToken());
            }
        }
    }

    @Test
    public void streamValuesDoesNotStoreInDocument() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/1_BL0001_typ2.SE");
        assertNotNull(url);

        AtomicInteger ibCount = new AtomicInteger(0);
        AtomicInteger ubCount = new AtomicInteger(0);

        SieDocumentReader reader = new SieDocumentReader();
        reader.setStreamValues(true);
        reader.setIgnoreMissingOMFATTNING(true);
        reader.getCallbacks().setIB(pv -> ibCount.incrementAndGet());
        reader.getCallbacks().setUB(pv -> ubCount.incrementAndGet());
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());

        assertTrue(doc.getIB().isEmpty(), "IB should not be stored when streaming");
        assertTrue(doc.getUB().isEmpty(), "UB should not be stored when streaming");
        assertTrue(ibCount.get() > 0, "IB callback should have been called");
        assertTrue(ubCount.get() > 0, "UB callback should have been called");
    }

    @Test
    public void constructorWithParameters() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/1_BL0001_typ2.SE");
        assertNotNull(url);

        SieDocumentReader reader = new SieDocumentReader(false, true, false, true);
        assertTrue(reader.isIgnoreMissingOMFATTNING());
        assertTrue(reader.isThrowErrors());
        assertFalse(reader.isIgnoreBTRANS());
        assertFalse(reader.isStreamValues());

        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertNotNull(doc);
    }

    @Test
    public void invalidFileThrowsException(@TempDir Path tempDir) throws IOException {
        Path badFile = tempDir.resolve("bad.SE");
        Files.writeString(badFile, "This is not a SIE file\n");

        SieDocumentReader reader = new SieDocumentReader();
        reader.setThrowErrors(true);
        assertThrows(SieInvalidFileException.class, () -> {
            reader.readDocument(badFile.toString());
        });
    }

    @Test
    public void throwErrorsFalseCollectsExceptions(@TempDir Path tempDir) throws IOException {
        Path badFile = tempDir.resolve("bad.SE");
        Files.writeString(badFile, "This is not a SIE file\n");

        SieDocumentReader reader = new SieDocumentReader();
        reader.setThrowErrors(false);
        List<Exception> collected = new ArrayList<>();
        reader.getCallbacks().setSieException(collected::add);
        SieDocument doc = reader.readDocument(badFile.toString());

        assertNull(doc, "Should return null for invalid file");
        assertFalse(collected.isEmpty(), "Exceptions should be collected");
        assertInstanceOf(SieInvalidFileException.class, collected.get(0));
    }

    @Test
    public void invalidDateHandledGracefully(@TempDir Path tempDir) throws IOException {
        // Create a minimal SIE file with an invalid date (Feb 30)
        String content = "#FLAGGA 0\n#SIETYP 4\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20220230\n#FNAMN \"Test\"\n#KONTO 1000 \"Kassa\"\n#RAR 0 20220101 20221231\n";
        Path sieFile = tempDir.resolve("invalid_date.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        List<Exception> collected = new ArrayList<>();
        SieDocumentReader reader = new SieDocumentReader();
        reader.setThrowErrors(false);
        reader.getCallbacks().setSieException(collected::add);
        SieDocument doc = reader.readDocument(sieFile.toString());

        // GEN date should be null due to invalid date
        assertNull(doc.getGEN_DATE(), "Invalid date should result in null");
        assertTrue(collected.stream().anyMatch(e -> e instanceof SieDateException),
                "Should report a SieDateException for invalid date");
    }

    @Test
    public void rtransMirrorTransIsIgnoredWhenRtransHandled(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n"
                + "#PROGRAM \"Test\" 1.0\n"
                + "#FORMAT PC8\n"
                + "#GEN 20240101 Test\n"
                + "#SIETYP 4\n"
                + "#FNAMN \"Test\"\n"
                + "#KONTO 1910 \"Kassa\"\n"
                + "#RAR 0 20240101 20241231\n"
                + "#VER \"A\" \"1\" 20240101 \"Adj\"\n"
                + "{\n"
                + "#TRANS 1910 {} -100\n"
                + "#RTRANS 1910 {} 100\n"
                + "#TRANS 1910 {} 100\n"
                + "}\n";
        Path sieFile = tempDir.resolve("rtrans_mirror.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        reader.setAllowUnbalancedVoucher(true);
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertEquals(1, doc.getVER().size(), "Expected one voucher");
        assertEquals(2, doc.getVER().get(0).getRows().size(),
                "Expected mirror #TRANS after #RTRANS to be ignored");
        assertEquals("#TRANS", doc.getVER().get(0).getRows().get(0).getToken());
        assertEquals("#RTRANS", doc.getVER().get(0).getRows().get(1).getToken());
    }

    // === Phase 1 tests ===

    @Test
    public void unknownLabelSilentlyIgnored(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 4\n#FNAMN \"Test\"\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n"
                + "#CUSTOM_TAG some data\n";
        Path sieFile = tempDir.resolve("custom_tag.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        List<Exception> collected = new ArrayList<>();
        SieDocumentReader reader = new SieDocumentReader();
        reader.setThrowErrors(false);
        reader.getCallbacks().setSieException(collected::add);
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertNotNull(doc);
        assertTrue(collected.stream().noneMatch(e -> e instanceof UnsupportedOperationException),
                "Unknown labels should not cause UnsupportedOperationException");
    }

    @Test
    public void parseObjektDimensionId(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 4\n#FNAMN \"Test\"\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n"
                + "#OBJEKT 6 \"P1\" \"Project 1\"\n";
        Path sieFile = tempDir.resolve("objekt_dim.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(sieFile.toString());

        SieDimension dim = doc.getDIM().get("6");
        assertNotNull(dim, "Dimension 6 should exist");
        assertEquals("6", dim.getNumber(), "Dimension number should be '6', not the object number");
    }

    // === Phase 3 tests ===

    @Test
    public void mandatoryFieldValidation(@TempDir Path tempDir) throws IOException {
        // File missing #PROGRAM
        String content = "#FLAGGA 0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 4\n#FNAMN \"Test\"\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n";
        Path sieFile = tempDir.resolve("no_program.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertTrue(reader.getValidationWarnings().stream()
                .anyMatch(e -> e.getMessage().contains("#PROGRAM")),
                "Should warn about missing #PROGRAM");
    }

    @Test
    public void forbiddenDimInType1(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 1\n#FNAMN \"Test\"\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n"
                + "#DIM 20 \"Custom\"\n";
        Path sieFile = tempDir.resolve("dim_type1.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertTrue(reader.getValidationWarnings().stream()
                .anyMatch(e -> e instanceof SieInvalidFeatureException && e.getMessage().contains("#DIM")),
                "Should warn about #DIM in type 1");
    }

    @Test
    public void allowUnderDimensionsFalseRejectsUnderdim(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 4\n#FNAMN \"Test\"\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n"
                + "#DIM 1 \"Resultatenhet\"\n"
                + "#UNDERDIM 21 \"SubDim\" 1\n";
        Path sieFile = tempDir.resolve("underdim.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        List<Exception> collected = new ArrayList<>();
        SieDocumentReader reader = new SieDocumentReader();
        reader.setAllowUnderDimensions(false);
        reader.setThrowErrors(false);
        reader.getCallbacks().setSieException(collected::add);
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertTrue(collected.stream()
                .anyMatch(e -> e instanceof SieInvalidFeatureException
                        && e.getMessage().contains("UNDERDIM")),
                "Should report callback for #UNDERDIM when allowUnderDimensions=false");
    }

    @Test
    public void invalidPeriodFormat(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 2\n#FNAMN \"Test\"\n"
                + "#ORGNR 556677-8899\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n"
                + "#PSALDO 0 20241 1910 {} 100\n";
        Path sieFile = tempDir.resolve("bad_period.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        reader.setIgnoreMissingOMFATTNING(true);
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertTrue(reader.getValidationWarnings().stream()
                .anyMatch(e -> e.getMessage().contains("Invalid period")),
                "Should warn about invalid period format");
    }

    @Test
    public void amountExcessDecimalsWarning(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 4\n#FNAMN \"Test\"\n"
                + "#ORGNR 556677-8899\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n"
                + "#IB 0 1910 123.456\n";
        Path sieFile = tempDir.resolve("excess_decimals.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertTrue(reader.getValidationWarnings().stream()
                .anyMatch(e -> e.getMessage().contains("more than 2 decimal")),
                "Should warn about excess decimals");
    }

    @Test
    public void nonNumericAccountNumber(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 4\n#FNAMN \"Test\"\n"
                + "#ORGNR 556677-8899\n"
                + "#KONTO ABC \"Invalid\"\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n";
        Path sieFile = tempDir.resolve("alpha_account.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertTrue(reader.getValidationWarnings().stream()
                .anyMatch(e -> e.getMessage().contains("not numeric")),
                "Should warn about non-numeric account number");
    }

    @Test
    public void voucherOrderingWarning(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 4\n#FNAMN \"Test\"\n"
                + "#ORGNR 556677-8899\n"
                + "#KONTO 1910 \"Kassa\"\n#KONTO 2440 \"Lev\"\n"
                + "#RAR 0 20240101 20241231\n"
                + "#VER \"A\" \"2\" 20240101 \"Second\"\n{\n#TRANS 1910 {} 100\n#TRANS 2440 {} -100\n}\n"
                + "#VER \"A\" \"1\" 20240101 \"First\"\n{\n#TRANS 1910 {} 200\n#TRANS 2440 {} -200\n}\n";
        Path sieFile = tempDir.resolve("bad_order.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertTrue(reader.getValidationWarnings().stream()
                .anyMatch(e -> e.getMessage().contains("not in ascending order")),
                "Should warn about voucher ordering");
    }

    @Test
    public void orgnrFormatWarning(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 4\n#FNAMN \"Test\"\n"
                + "#ORGNR 5566778899\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n";
        Path sieFile = tempDir.resolve("orgnr_nohyphen.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertTrue(reader.getValidationWarnings().stream()
                .anyMatch(e -> e.getMessage().contains("ORGNR")),
                "Should warn about ORGNR format");
    }

    @Test
    public void orgnrWithHyphenNoWarning(@TempDir Path tempDir) throws IOException {
        String content = "#FLAGGA 0\n#PROGRAM \"Test\" 1.0\n#FORMAT PC8\n"
                + "#GEN 20240101 Test\n#SIETYP 4\n#FNAMN \"Test\"\n"
                + "#ORGNR 556677-8899\n"
                + "#KONTO 1910 \"Kassa\"\n#RAR 0 20240101 20241231\n";
        Path sieFile = tempDir.resolve("orgnr_ok.SE");
        Files.writeString(sieFile, content, Encoding.getCharset());

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(sieFile.toString());

        assertTrue(reader.getValidationWarnings().stream()
                .noneMatch(e -> e.getMessage().contains("ORGNR")),
                "Should not warn about valid ORGNR format");
    }
}
