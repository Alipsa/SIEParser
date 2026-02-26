package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.math.BigInteger;
import java.time.YearMonth;

/**
 * A reference to a correcting journal entry.
 * Corresponds to {@code CorrectedByType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Identifies the journal entry that corrects this one, via the required
 * {@code journalId} and {@code journalEntryId}. The optional {@code fiscalYearId}
 * (xsd:gYearMonth) identifies the fiscal year of the correcting entry.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CorrectedBy {

    @XmlAttribute(name = "fiscalYearId")
    @XmlSchemaType(name = "gYearMonth")
    private YearMonth fiscalYearId;

    @XmlAttribute(name = "journalId", required = true)
    private String journalId;

    @XmlAttribute(name = "journalEntryId", required = true)
    private BigInteger journalEntryId;

    /**
     * @return the optional fiscal year of the correcting entry (xsd:gYearMonth)
     */
    public YearMonth getFiscalYearId() { return fiscalYearId; }

    /**
     * @param fiscalYearId the fiscal year of the correcting entry
     */
    public void setFiscalYearId(YearMonth fiscalYearId) { this.fiscalYearId = fiscalYearId; }

    /**
     * @return the journal id of the correcting entry (required)
     */
    public String getJournalId() { return journalId; }

    /**
     * @param journalId the journal id of the correcting entry
     */
    public void setJournalId(String journalId) { this.journalId = journalId; }

    /**
     * @return the journal entry id of the correcting entry (required)
     */
    public BigInteger getJournalEntryId() { return journalEntryId; }

    /**
     * @param journalEntryId the journal entry id of the correcting entry
     */
    public void setJournalEntryId(BigInteger journalEntryId) { this.journalEntryId = journalEntryId; }
}
