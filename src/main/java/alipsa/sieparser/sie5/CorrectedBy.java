package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.math.BigInteger;
import java.time.YearMonth;

@XmlAccessorType(XmlAccessType.FIELD)
public class CorrectedBy {

    @XmlAttribute(name = "fiscalYearId")
    @XmlSchemaType(name = "gYearMonth")
    private YearMonth fiscalYearId;

    @XmlAttribute(name = "journalId", required = true)
    private String journalId;

    @XmlAttribute(name = "journalEntryId", required = true)
    private BigInteger journalEntryId;

    public YearMonth getFiscalYearId() { return fiscalYearId; }
    public void setFiscalYearId(YearMonth fiscalYearId) { this.fiscalYearId = fiscalYearId; }

    public String getJournalId() { return journalId; }
    public void setJournalId(String journalId) { this.journalId = journalId; }

    public BigInteger getJournalEntryId() { return journalEntryId; }
    public void setJournalEntryId(BigInteger journalEntryId) { this.journalEntryId = journalEntryId; }
}
