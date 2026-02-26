package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * JAXB adapter for java.time.LocalDate (xsd:date).
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {


    /** Creates a new instance. */
    public LocalDateAdapter() {}
    /**
     * Converts an xsd:date string (e.g. "2024-01-15") to a {@link LocalDate}.
     *
     * @param v the xsd:date string, may be {@code null} or empty
     * @return the parsed {@link LocalDate}, or {@code null} if the input is {@code null} or empty
     */
    @Override
    public LocalDate unmarshal(String v) {
        if (v == null || v.isEmpty()) return null;
        return LocalDate.parse(v);
    }

    /**
     * Converts a {@link LocalDate} to its xsd:date string representation (ISO-8601).
     *
     * @param v the {@link LocalDate}, may be {@code null}
     * @return the formatted string (e.g. "2024-01-15"), or {@code null} if the input is {@code null}
     */
    @Override
    public String marshal(LocalDate v) {
        if (v == null) return null;
        return v.toString();
    }
}
