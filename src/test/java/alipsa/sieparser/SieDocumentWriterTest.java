package alipsa.sieparser;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SieDocumentWriter, including stream writing, text escaping, and round-trip.
 */
public class SieDocumentWriterTest {

    @Test
    public void writeToStream() throws IOException {
        SieDocument doc = createMinimalDocument();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SieDocumentWriter writer = new SieDocumentWriter(doc);
        writer.write(baos);

        String output = baos.toString(Encoding.getCharset());
        assertTrue(output.contains("#FLAGGA 0"));
        assertTrue(output.contains("#SIETYP 4"));
        assertTrue(output.contains("#FNAMN \"Test Company\""));
    }

    @Test
    public void writeToFile() throws IOException {
        SieDocument doc = createMinimalDocument();

        File tmpFile = File.createTempFile("sie-writer-test", ".SE");
        try {
            SieDocumentWriter writer = new SieDocumentWriter(doc);
            writer.write(tmpFile.getAbsolutePath());

            assertTrue(tmpFile.exists());
            assertTrue(tmpFile.length() > 0);

            // Read it back
            SieDocumentReader reader = new SieDocumentReader();
            SieDocument readBack = reader.readDocument(tmpFile.getAbsolutePath());
            assertEquals("Test Company", readBack.getFNAMN().getName());
            assertEquals(4, readBack.getSIETYP());
        } finally {
            tmpFile.delete();
        }
    }

    @Test
    public void textEscapingQuotes() throws IOException {
        SieDocument doc = createMinimalDocument();

        // Add an account with quotes in name
        SieAccount acct = new SieAccount("1910", "Kassa \"kontant\"");
        doc.getKONTO().put("1910", acct);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SieDocumentWriter writer = new SieDocumentWriter(doc);
        writer.write(baos);

        String output = baos.toString(Encoding.getCharset());
        // Quotes should be escaped
        assertTrue(output.contains("Kassa \\\"kontant\\\""), "Quotes should be escaped in output: " + output);
    }

    @Test
    public void roundTripWithRtransCreatedBy() throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("samples/3_BL0001_typ4.SE");
        assertNotNull(url, "Sample file should exist");

        SieDocumentReader reader = new SieDocumentReader();
        SieDocument doc = reader.readDocument(new File(url.getFile()).getAbsolutePath());
        assertTrue(reader.getValidationExceptions().isEmpty(), "No validation errors expected");

        // Write to stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SieDocumentWriter writer = new SieDocumentWriter(doc);
        writer.write(baos);

        // Read back from stream
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        SieDocumentReader reader2 = new SieDocumentReader();
        SieDocument doc2 = reader2.readDocument(createTempFileFromBytes(baos.toByteArray()));

        List<String> diffs = SieDocumentComparer.compare(doc, doc2);
        assertTrue(diffs.isEmpty(), "Round-trip should produce identical documents, diffs: " + diffs);
    }

    @Test
    public void addVouchersToStream() throws IOException {
        SieVoucher v = new SieVoucher();
        v.setSeries("A");
        v.setNumber("1");
        v.setVoucherDate(LocalDate.of(2024, 1, 15));
        v.setText("Test voucher");
        v.setToken("#VER");

        SieVoucherRow row = new SieVoucherRow();
        row.setAccount(new SieAccount("1910", "Kassa"));
        row.setAmount(new BigDecimal("500"));
        row.setRowDate(LocalDate.of(2024, 1, 15));
        row.setText("Debit");
        row.setToken("#TRANS");
        v.getRows().add(row);

        SieDocument doc = createMinimalDocument();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SieDocumentWriter writer = new SieDocumentWriter(doc);
        writer.addVouchers(baos, List.of(v));

        String output = baos.toString(Encoding.getCharset());
        assertTrue(output.contains("#VER"));
        assertTrue(output.contains("#TRANS"));
        assertTrue(output.contains("1910"));
    }

    @Test
    public void writeOptionsDefault() {
        WriteOptions options = new WriteOptions();
        assertFalse(options.isWriteKSUMMA());
    }

    @Test
    public void writeOptionsWithKSUMMA() throws IOException {
        SieDocument doc = createMinimalDocument();
        WriteOptions options = new WriteOptions();
        options.setWriteKSUMMA(true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        SieDocumentWriter writer = new SieDocumentWriter(doc, options);
        writer.write(baos);

        String output = baos.toString(Encoding.getCharset());
        assertTrue(output.contains("#KSUMMA"), "Output should contain KSUMMA when enabled");
    }

    private SieDocument createMinimalDocument() {
        SieDocument doc = new SieDocument();
        doc.setFLAGGA(0);
        doc.setSIETYP(4);
        doc.setFORMAT("PC8");
        doc.setGEN_DATE(LocalDate.of(2024, 1, 1));
        doc.setGEN_NAMN("test");
        doc.getFNAMN().setName("Test Company");
        doc.getFNAMN().setOrgIdentifier("556677-8899");
        return doc;
    }

    private String createTempFileFromBytes(byte[] data) throws IOException {
        File tmp = File.createTempFile("sie-roundtrip", ".SE");
        tmp.deleteOnExit();
        java.nio.file.Files.write(tmp.toPath(), data);
        return tmp.getAbsolutePath();
    }
}
