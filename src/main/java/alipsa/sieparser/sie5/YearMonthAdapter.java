package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * JAXB adapter for java.time.YearMonth (xsd:gYearMonth).
 */
public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {


    /** Creates a new instance. */
    public YearMonthAdapter() {}
    /**
     * Converts an xsd:gYearMonth string (e.g. "2024-01") to a {@link YearMonth}.
     *
     * @param v the xsd:gYearMonth string, may be {@code null} or empty
     * @return the parsed {@link YearMonth}, or {@code null} if the input is {@code null} or empty
     */
    @Override
    public YearMonth unmarshal(String v) {
        if (v == null || v.isEmpty()) return null;
        return YearMonth.parse(v, DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    /**
     * Converts a {@link YearMonth} to its xsd:gYearMonth string representation.
     *
     * @param v the {@link YearMonth}, may be {@code null}
     * @return the formatted string (e.g. "2024-01"), or {@code null} if the input is {@code null}
     */
    @Override
    public String marshal(YearMonth v) {
        if (v == null) return null;
        return v.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }
}
