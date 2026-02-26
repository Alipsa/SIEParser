package alipsa.sieparser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SieCRC32 checksum calculation.
 */
public class SieCRC32Test {

    @Test
    public void notStartedByDefault() {
        SieCRC32 crc = new SieCRC32();
        assertFalse(crc.isStarted());
    }

    @Test
    public void startedAfterStart() {
        SieCRC32 crc = new SieCRC32();
        crc.start();
        assertTrue(crc.isStarted());
    }

    @Test
    public void checksumDeterministic() {
        SieCRC32 crc1 = new SieCRC32();
        crc1.start();
        SieDataItem item = new SieDataItem("#KONTO 1910 \"Kassa\"", null, null);
        crc1.addData(item);
        long checksum1 = crc1.checksum();

        SieCRC32 crc2 = new SieCRC32();
        crc2.start();
        crc2.addData(item);
        long checksum2 = crc2.checksum();

        assertEquals(checksum1, checksum2, "Same data should produce same checksum");
    }

    @Test
    public void differentDataDifferentChecksum() {
        SieCRC32 crc1 = new SieCRC32();
        crc1.start();
        crc1.addData(new SieDataItem("#KONTO 1910 \"Kassa\"", null, null));

        SieCRC32 crc2 = new SieCRC32();
        crc2.start();
        crc2.addData(new SieDataItem("#KONTO 2440 \"Leverantör\"", null, null));

        assertNotEquals(crc1.checksum(), crc2.checksum(), "Different data should produce different checksums");
    }

    @Test
    public void checksumNonZero() {
        SieCRC32 crc = new SieCRC32();
        crc.start();
        crc.addData(new SieDataItem("#FLAGGA 0", null, null));
        assertNotEquals(0, crc.checksum());
    }
}
