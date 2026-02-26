package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JAXB adapter for java.time.OffsetDateTime (xsd:dateTime).
 */
public class OffsetDateTimeAdapter extends XmlAdapter<String, OffsetDateTime> {


    /** Creates a new instance. */
    public OffsetDateTimeAdapter() {}
    /**
     * Converts an xsd:dateTime string (e.g. "2024-01-15T10:30:00+01:00") to an {@link OffsetDateTime}.
     *
     * @param v the xsd:dateTime string, may be {@code null} or empty
     * @return the parsed {@link OffsetDateTime}, or {@code null} if the input is {@code null} or empty
     */
    @Override
    public OffsetDateTime unmarshal(String v) {
        if (v == null || v.isEmpty()) return null;
        return OffsetDateTime.parse(v, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    /**
     * Converts an {@link OffsetDateTime} to its xsd:dateTime string representation (ISO-8601 with offset).
     *
     * @param v the {@link OffsetDateTime}, may be {@code null}
     * @return the formatted string (e.g. "2024-01-15T10:30:00+01:00"), or {@code null} if the input is {@code null}
     */
    @Override
    public String marshal(OffsetDateTime v) {
        if (v == null) return null;
        return v.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
