package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * JAXB adapter for java.time.OffsetDateTime (xsd:dateTime).
 */
public class OffsetDateTimeAdapter extends XmlAdapter<String, OffsetDateTime> {

    @Override
    public OffsetDateTime unmarshal(String v) {
        if (v == null || v.isEmpty()) return null;
        return OffsetDateTime.parse(v, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    @Override
    public String marshal(OffsetDateTime v) {
        if (v == null) return null;
        return v.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
