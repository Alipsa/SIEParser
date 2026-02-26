/**
 * SIE 5 (XML-based) document model, reader, and writer.
 *
 * <p>This package provides JAXB-annotated model classes that map to the SIE 5 XSD schema
 * ({@code http://www.sie.se/sie5}), along with {@link alipsa.sieparser.sie5.Sie5DocumentReader}
 * and {@link alipsa.sieparser.sie5.Sie5DocumentWriter} for reading and writing SIE 5 XML files.</p>
 */
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
