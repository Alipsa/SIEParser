package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.LocalDate;

/**
 * Locking information for a journal entry or ledger entry.
 * Corresponds to {@code LockingInfoType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Records when and by whom a record achieved "entered" (bokford) status
 * as defined by BFN AR 2013:2. Both {@code date} and {@code by} are required attributes.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class LockingInfo {

    @XmlAttribute(name = "date", required = true)
    @XmlSchemaType(name = "date")
    private LocalDate date;

    @XmlAttribute(name = "by", required = true)
    private String by;


    /** Creates a new instance. */
    public LockingInfo() {}
    /**
     * Returns the date when the record was locked/entered (required).
     * @return the date when the record was locked/entered (required)
     */
    public LocalDate getDate() { return date; }

    /**
     * Sets the locking date.
     * @param date the locking date
     */
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Returns the identifier of who locked/entered the record (required).
     * @return the identifier of who locked/entered the record (required)
     */
    public String getBy() { return by; }

    /**
     * Sets the identifier of who locked/entered the record.
     * @param by the identifier of who locked/entered the record
     */
    public void setBy(String by) { this.by = by; }
}
