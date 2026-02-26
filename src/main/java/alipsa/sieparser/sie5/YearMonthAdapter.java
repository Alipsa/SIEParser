package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * JAXB adapter for java.time.YearMonth (xsd:gYearMonth).
 */
public class YearMonthAdapter extends XmlAdapter<String, YearMonth> {

    @Override
    public YearMonth unmarshal(String v) {
        if (v == null || v.isEmpty()) return null;
        return YearMonth.parse(v, DateTimeFormatter.ofPattern("yyyy-MM"));
    }

    @Override
    public String marshal(YearMonth v) {
        if (v == null) return null;
        return v.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }
}
