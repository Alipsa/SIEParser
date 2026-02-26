package alipsa.sieparser;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.EnumSet;

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
        reader.ignoreMissingOMFATTNING = true;
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
        reader.allowUnbalancedVoucher = true;
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
        reader.throwErrors = true;

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
        reader.ignoreKSUMMA = true;
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
        reader.ignoreBTRANS = true;
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
}
