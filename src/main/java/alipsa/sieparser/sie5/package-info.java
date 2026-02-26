@XmlSchema(namespace = "http://www.sie.se/sie5", elementFormDefault = XmlNsForm.QUALIFIED)
@XmlJavaTypeAdapters({
    @XmlJavaTypeAdapter(value = YearMonthAdapter.class, type = java.time.YearMonth.class),
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class, type = java.time.LocalDate.class),
    @XmlJavaTypeAdapter(value = OffsetDateTimeAdapter.class, type = java.time.OffsetDateTime.class)
})
package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlNsForm;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
