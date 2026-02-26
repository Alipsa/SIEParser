package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.LocalDate;

/**
 * Overstrike information for a ledger entry.
 * Corresponds to {@code OverstrikeType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Records when and by whom a ledger entry was struck through (overstriken).
 * Both {@code date} and {@code by} are required attributes.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Overstrike {

    @XmlAttribute(name = "date", required = true)
    @XmlSchemaType(name = "date")
    private LocalDate date;

    @XmlAttribute(name = "by", required = true)
    private String by;

    /**
     * Returns the date when the ledger entry was struck through (required).
     * @return the date when the ledger entry was struck through (required)
     */
    public LocalDate getDate() { return date; }

    /**
     * Sets the overstrike date.
     * @param date the overstrike date
     */
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Returns the identifier of who struck through the ledger entry (required).
     * @return the identifier of who struck through the ledger entry (required)
     */
    public String getBy() { return by; }

    /**
     * Sets the identifier of who struck through the ledger entry.
     * @param by the identifier of who struck through the ledger entry
     */
    public void setBy(String by) { this.by = by; }
}
