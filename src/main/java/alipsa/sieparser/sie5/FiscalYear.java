package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Declares a fiscal year within a SIE 5 document.
 * Maps to the XSD complex type {@code FiscalYearType}.
 *
 * <p>The {@code start} and {@code end} attributes (xsd:gYearMonth) define the
 * fiscal year period and are required. Optional boolean flags indicate the
 * characteristics of the fiscal year data, and {@code lastCoveredDate}
 * specifies the last date for which data is present.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FiscalYear {

    @XmlAttribute(name = "start", required = true)
    @XmlSchemaType(name = "gYearMonth")
    private YearMonth start;

    @XmlAttribute(name = "end", required = true)
    @XmlSchemaType(name = "gYearMonth")
    private YearMonth end;

    @XmlAttribute(name = "primary")
    private Boolean primary;

    @XmlAttribute(name = "closed")
    private Boolean closed;

    @XmlAttribute(name = "hasLedgerEntries")
    private Boolean hasLedgerEntries;

    @XmlAttribute(name = "hasSubordinateAccounts")
    private Boolean hasSubordinateAccounts;

    @XmlAttribute(name = "hasAttachedVoucherFiles")
    private Boolean hasAttachedVoucherFiles;

    @XmlAttribute(name = "lastCoveredDate")
    @XmlSchemaType(name = "date")
    private LocalDate lastCoveredDate;


    /** Creates a new instance. */
    public FiscalYear() {}
    /**
     * Returns the start month of the fiscal year.
     *
     * @return the start year-month (required, xsd:gYearMonth)
     */
    public YearMonth getStart() { return start; }

    /**
     * Sets the start month of the fiscal year.
     *
     * @param start the start year-month
     */
    public void setStart(YearMonth start) { this.start = start; }

    /**
     * Returns the end month of the fiscal year.
     *
     * @return the end year-month (required, xsd:gYearMonth)
     */
    public YearMonth getEnd() { return end; }

    /**
     * Sets the end month of the fiscal year.
     *
     * @param end the end year-month
     */
    public void setEnd(YearMonth end) { this.end = end; }

    /**
     * Returns whether this is the primary (current) fiscal year.
     *
     * @return {@code true} if primary, or {@code null} if not specified
     */
    public Boolean getPrimary() { return primary; }

    /**
     * Sets whether this is the primary (current) fiscal year.
     *
     * @param primary {@code true} if primary
     */
    public void setPrimary(Boolean primary) { this.primary = primary; }

    /**
     * Returns whether the fiscal year is closed.
     *
     * @return {@code true} if closed, or {@code null} if not specified
     */
    public Boolean getClosed() { return closed; }

    /**
     * Sets whether the fiscal year is closed.
     *
     * @param closed {@code true} if closed
     */
    public void setClosed(Boolean closed) { this.closed = closed; }

    /**
     * Returns whether the fiscal year contains ledger entries.
     *
     * @return {@code true} if ledger entries exist, or {@code null} if not specified
     */
    public Boolean getHasLedgerEntries() { return hasLedgerEntries; }

    /**
     * Sets whether the fiscal year contains ledger entries.
     *
     * @param hasLedgerEntries {@code true} if ledger entries exist
     */
    public void setHasLedgerEntries(Boolean hasLedgerEntries) { this.hasLedgerEntries = hasLedgerEntries; }

    /**
     * Returns whether the fiscal year uses subordinate (sub-divided) accounts.
     *
     * @return {@code true} if subordinate accounts exist, or {@code null} if not specified
     */
    public Boolean getHasSubordinateAccounts() { return hasSubordinateAccounts; }

    /**
     * Sets whether the fiscal year uses subordinate (sub-divided) accounts.
     *
     * @param hasSubordinateAccounts {@code true} if subordinate accounts exist
     */
    public void setHasSubordinateAccounts(Boolean hasSubordinateAccounts) { this.hasSubordinateAccounts = hasSubordinateAccounts; }

    /**
     * Returns whether the fiscal year has vouchers with attached files.
     *
     * @return {@code true} if attached voucher files exist, or {@code null} if not specified
     */
    public Boolean getHasAttachedVoucherFiles() { return hasAttachedVoucherFiles; }

    /**
     * Sets whether the fiscal year has vouchers with attached files.
     *
     * @param hasAttachedVoucherFiles {@code true} if attached voucher files exist
     */
    public void setHasAttachedVoucherFiles(Boolean hasAttachedVoucherFiles) { this.hasAttachedVoucherFiles = hasAttachedVoucherFiles; }

    /**
     * Returns the last date for which data is present in this fiscal year.
     *
     * @return the last covered date (xsd:date), or {@code null} if not specified
     */
    public LocalDate getLastCoveredDate() { return lastCoveredDate; }

    /**
     * Sets the last date for which data is present in this fiscal year.
     *
     * @param lastCoveredDate the last covered date
     */
    public void setLastCoveredDate(LocalDate lastCoveredDate) { this.lastCoveredDate = lastCoveredDate; }
}
