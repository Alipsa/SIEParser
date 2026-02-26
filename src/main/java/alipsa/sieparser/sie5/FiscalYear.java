package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.LocalDate;
import java.time.YearMonth;

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

    public YearMonth getStart() { return start; }
    public void setStart(YearMonth start) { this.start = start; }

    public YearMonth getEnd() { return end; }
    public void setEnd(YearMonth end) { this.end = end; }

    public Boolean getPrimary() { return primary; }
    public void setPrimary(Boolean primary) { this.primary = primary; }

    public Boolean getClosed() { return closed; }
    public void setClosed(Boolean closed) { this.closed = closed; }

    public Boolean getHasLedgerEntries() { return hasLedgerEntries; }
    public void setHasLedgerEntries(Boolean hasLedgerEntries) { this.hasLedgerEntries = hasLedgerEntries; }

    public Boolean getHasSubordinateAccounts() { return hasSubordinateAccounts; }
    public void setHasSubordinateAccounts(Boolean hasSubordinateAccounts) { this.hasSubordinateAccounts = hasSubordinateAccounts; }

    public Boolean getHasAttachedVoucherFiles() { return hasAttachedVoucherFiles; }
    public void setHasAttachedVoucherFiles(Boolean hasAttachedVoucherFiles) { this.hasAttachedVoucherFiles = hasAttachedVoucherFiles; }

    public LocalDate getLastCoveredDate() { return lastCoveredDate; }
    public void setLastCoveredDate(LocalDate lastCoveredDate) { this.lastCoveredDate = lastCoveredDate; }
}
