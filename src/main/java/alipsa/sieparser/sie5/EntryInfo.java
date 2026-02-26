package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.LocalDate;

/**
 * Entry registration information for a journal entry or ledger entry.
 * Corresponds to {@code EntryInfoType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Records when and by whom a record was registered in the accounting system.
 * Both {@code date} and {@code by} are required attributes.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EntryInfo {

    @XmlAttribute(name = "date", required = true)
    @XmlSchemaType(name = "date")
    private LocalDate date;

    @XmlAttribute(name = "by", required = true)
    private String by;

    /**
     * Returns the date when the record was registered (required).
     * @return the date when the record was registered (required)
     */
    public LocalDate getDate() { return date; }

    /**
     * Sets the registration date.
     * @param date the registration date
     */
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Returns the identifier of who registered the record (required).
     * @return the identifier of who registered the record (required)
     */
    public String getBy() { return by; }

    /**
     * Sets the identifier of who registered the record.
     * @param by the identifier of who registered the record
     */
    public void setBy(String by) { this.by = by; }
}
