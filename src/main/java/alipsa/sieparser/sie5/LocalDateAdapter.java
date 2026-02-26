package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * JAXB adapter for java.time.LocalDate (xsd:date).
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    @Override
    public LocalDate unmarshal(String v) {
        if (v == null || v.isEmpty()) return null;
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) {
        if (v == null) return null;
        return v.toString();
    }
}
