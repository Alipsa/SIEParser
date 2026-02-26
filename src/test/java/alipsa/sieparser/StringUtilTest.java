package alipsa.sieparser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for StringUtil utility methods.
 */
public class StringUtilTest {

    @Test
    public void equalsNullNull() {
        assertTrue(StringUtil.equals(null, null));
    }

    @Test
    public void equalsNullString() {
        assertFalse(StringUtil.equals(null, "abc"));
    }

    @Test
    public void equalsStringNull() {
        assertFalse(StringUtil.equals("abc", null));
    }

    @Test
    public void equalsSameString() {
        assertTrue(StringUtil.equals("abc", "abc"));
    }

    @Test
    public void equalsDifferentStrings() {
        assertFalse(StringUtil.equals("abc", "def"));
    }

    @Test
    public void isNullOrEmptyNull() {
        assertTrue(StringUtil.isNullOrEmpty(null));
    }

    @Test
    public void isNullOrEmptyEmpty() {
        assertTrue(StringUtil.isNullOrEmpty(""));
    }

    @Test
    public void isNullOrEmptyNonEmpty() {
        assertFalse(StringUtil.isNullOrEmpty("hello"));
    }

    @Test
    public void trimQuotes() {
        assertEquals("test", StringUtil.trim("\"test\"", new char[]{'"'}));
    }

    @Test
    public void trimNull() {
        assertNull(StringUtil.trim(null, new char[]{'"'}));
    }

    @Test
    public void trimStartOnly() {
        assertEquals("test\"", StringUtil.trim("\"test\"", new char[]{'"'}, true, false));
    }

    @Test
    public void trimEndOnly() {
        assertEquals("\"test", StringUtil.trim("\"test\"", new char[]{'"'}, false, true));
    }

    @Test
    public void trimMultipleChars() {
        assertEquals("test", StringUtil.trim("##test##", new char[]{'#'}));
    }
}
