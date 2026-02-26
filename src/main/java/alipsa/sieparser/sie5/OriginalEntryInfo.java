package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.LocalDate;

/**
 * Original entry registration information.
 * Corresponds to {@code OriginalEntryInfoType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Records how and when a record was originally entered into a pre-system
 * before being transferred to the current accounting system.
 * Both {@code date} and {@code by} are required attributes.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginalEntryInfo {

    @XmlAttribute(name = "date", required = true)
    @XmlSchemaType(name = "date")
    private LocalDate date;

    @XmlAttribute(name = "by", required = true)
    private String by;


    /** Creates a new instance. */
    public OriginalEntryInfo() {}
    /**
     * Returns the date when the record was originally entered (required).
     * @return the date when the record was originally entered (required)
     */
    public LocalDate getDate() { return date; }

    /**
     * Sets the original entry date.
     * @param date the original entry date
     */
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Returns the identifier of who originally entered the record (required).
     * @return the identifier of who originally entered the record (required)
     */
    public String getBy() { return by; }

    /**
     * Sets the identifier of who originally entered the record.
     * @param by the identifier of who originally entered the record
     */
    public void setBy(String by) { this.by = by; }
}
