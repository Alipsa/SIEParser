package alipsa.sieparser;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SieDataItem parsing, especially splitLine edge cases.
 */
public class SieDataItemTest {

    @Test
    public void splitLineSimple() {
        SieDataItem item = new SieDataItem("#KONTO 1910 \"Kassa\"", null, null);
        assertEquals("#KONTO", item.getItemType());
        assertEquals("1910", item.getData().get(0));
        assertEquals("Kassa", item.getData().get(1));
    }

    @Test
    public void splitLineWithObjects() {
        SieDataItem item = new SieDataItem("#TRANS 1910 {1 \"100\" 6 \"P1\"} 500 20200101 \"Text\"", null, null);
        assertEquals("#TRANS", item.getItemType());
        assertEquals("1910", item.getData().get(0));
        assertEquals("{1 \"100\" 6 \"P1\"}", item.getData().get(1));
        assertEquals("500", item.getData().get(2));
    }

    @Test
    public void splitLineEmptyObjects() {
        SieDataItem item = new SieDataItem("#TRANS 1910 {} 500 20200101 \"\"", null, null);
        assertEquals("#TRANS", item.getItemType());
        assertEquals("1910", item.getData().get(0));
        assertEquals("{}", item.getData().get(1));
        assertEquals("500", item.getData().get(2));
    }

    @Test
    public void splitLineBackslashEscapedQuote() {
        // Backslash followed by quote should produce a literal quote in the field
        SieDataItem item = new SieDataItem("#KONTO 1910 \"Name with \\\"quotes\\\"\"", null, null);
        assertEquals("Name with \"quotes\"", item.getData().get(1));
    }

    @Test
    public void splitLineBackslashNotFollowedByQuote() {
        // Backslash followed by non-quote should NOT skip the next character
        SieDataItem item = new SieDataItem("#KONTO 1910 \"Path\\ntest\"", null, null);
        // The backslash is consumed but the 'n' should still be present
        assertEquals("Pathntest", item.getData().get(1));
    }

    @Test
    public void splitLineEmptyQuotedField() {
        SieDataItem item = new SieDataItem("#VER A 1 20200101 \"\" 20200101 \"User\"", null, null);
        assertEquals("#VER", item.getItemType());
        assertEquals("A", item.getData().get(0));
        assertEquals("1", item.getData().get(1));
        assertEquals("20200101", item.getData().get(2));
        assertEquals("", item.getData().get(3));
    }

    @Test
    public void splitLineTabSeparated() {
        SieDataItem item = new SieDataItem("#KONTO\t1910\t\"Kassa\"", null, null);
        assertEquals("#KONTO", item.getItemType());
        assertEquals("1910", item.getData().get(0));
        assertEquals("Kassa", item.getData().get(1));
    }

    @Test
    public void splitLineNoData() {
        SieDataItem item = new SieDataItem("#KSUMMA", null, null);
        assertEquals("#KSUMMA", item.getItemType());
        assertTrue(item.getData().isEmpty());
    }

    @Test
    public void getStringBeyondBounds() {
        SieDataItem item = new SieDataItem("#KONTO 1910", null, null);
        assertEquals("", item.getString(5));
    }

    @Test
    public void getIntBeyondBounds() {
        SieDataItem item = new SieDataItem("#KONTO 1910", null, null);
        assertEquals(0, item.getInt(5));
    }

    @Test
    public void getDateBeyondBounds() {
        SieDataItem item = new SieDataItem("#VER A", null, null);
        assertNull(item.getDate(5));
    }

    @Test
    public void getDateValid() {
        SieDataItem item = new SieDataItem("#VER A 1 20200315", null, null);
        var date = item.getDate(2);
        assertNotNull(date);
        assertEquals(2020, date.getYear());
        assertEquals(3, date.getMonthValue());
        assertEquals(15, date.getDayOfMonth());
    }

    @Test
    public void getDecimalNullForNonNumeric() {
        SieDataItem item = new SieDataItem("#TRANS 1910 {} 500 20200101 \"text\" \"\" \"User\"", null, null);
        // The "" empty quoted field should return null for decimal
        assertNull(item.getDecimalNull(4));
    }

    @Test
    public void getDecimalValid() {
        SieDataItem item = new SieDataItem("#IB 0 1910 12345.67", null, null);
        assertEquals(0, item.getDecimal(2).compareTo(new java.math.BigDecimal("12345.67")));
    }
}
