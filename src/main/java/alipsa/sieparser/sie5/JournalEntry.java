package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * A journal entry in a full SIE 5 document.
 * Corresponds to {@code JournalEntryType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Contains a required non-negative integer {@code id}, a required {@code journalDate},
 * optional descriptive text and reference id, a required {@link EntryInfo},
 * optional {@link OriginalEntryInfo}, a list of {@link LedgerEntry} rows,
 * optional {@link LockingInfo}, and lists of {@link VoucherReference} and
 * {@link CorrectedBy} references.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JournalEntry {

    @XmlAttribute(name = "id", required = true)
    private BigInteger id;

    @XmlAttribute(name = "journalDate", required = true)
    @XmlSchemaType(name = "date")
    private LocalDate journalDate;

    @XmlAttribute(name = "text")
    private String text;

    @XmlAttribute(name = "referenceId")
    private String referenceId;

    @XmlElement(name = "EntryInfo", required = true)
    private EntryInfo entryInfo;

    @XmlElement(name = "OriginalEntryInfo")
    private OriginalEntryInfo originalEntryInfo;

    @XmlElement(name = "LedgerEntry")
    private List<LedgerEntry> ledgerEntries = new ArrayList<>();

    @XmlElement(name = "LockingInfo")
    private LockingInfo lockingInfo;

    @XmlElement(name = "VoucherReference")
    private List<VoucherReference> voucherReferences = new ArrayList<>();

    @XmlElement(name = "CorrectedBy")
    private List<CorrectedBy> correctedBy = new ArrayList<>();

    /**
     * @return the journal entry id (xsd:nonNegativeInteger, required)
     */
    public BigInteger getId() { return id; }

    /**
     * @param id the journal entry id (must be a non-negative integer)
     */
    public void setId(BigInteger id) { this.id = id; }

    /**
     * @return the journal date (required)
     */
    public LocalDate getJournalDate() { return journalDate; }

    /**
     * @param journalDate the journal date
     */
    public void setJournalDate(LocalDate journalDate) { this.journalDate = journalDate; }

    /**
     * @return optional descriptive text for this entry
     */
    public String getText() { return text; }

    /**
     * @param text descriptive text for this entry
     */
    public void setText(String text) { this.text = text; }

    /**
     * @return optional external reference identifier
     */
    public String getReferenceId() { return referenceId; }

    /**
     * @param referenceId external reference identifier
     */
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }

    /**
     * @return the entry registration info (required)
     */
    public EntryInfo getEntryInfo() { return entryInfo; }

    /**
     * @param entryInfo the entry registration info
     */
    public void setEntryInfo(EntryInfo entryInfo) { this.entryInfo = entryInfo; }

    /**
     * @return optional original entry registration info
     */
    public OriginalEntryInfo getOriginalEntryInfo() { return originalEntryInfo; }

    /**
     * @param originalEntryInfo original entry registration info
     */
    public void setOriginalEntryInfo(OriginalEntryInfo originalEntryInfo) { this.originalEntryInfo = originalEntryInfo; }

    /**
     * @return the list of ledger entry rows belonging to this journal entry
     */
    public List<LedgerEntry> getLedgerEntries() { return ledgerEntries; }

    /**
     * @param ledgerEntries the list of ledger entry rows to set
     */
    public void setLedgerEntries(List<LedgerEntry> ledgerEntries) { this.ledgerEntries = ledgerEntries; }

    /**
     * @return optional locking info indicating when the entry achieved "entered" status
     */
    public LockingInfo getLockingInfo() { return lockingInfo; }

    /**
     * @param lockingInfo locking info for this entry
     */
    public void setLockingInfo(LockingInfo lockingInfo) { this.lockingInfo = lockingInfo; }

    /**
     * @return the list of voucher references attached to this entry
     */
    public List<VoucherReference> getVoucherReferences() { return voucherReferences; }

    /**
     * @param voucherReferences the list of voucher references to set
     */
    public void setVoucherReferences(List<VoucherReference> voucherReferences) { this.voucherReferences = voucherReferences; }

    /**
     * @return the list of references to correcting journal entries
     */
    public List<CorrectedBy> getCorrectedBy() { return correctedBy; }

    /**
     * @param correctedBy the list of correcting journal entry references to set
     */
    public void setCorrectedBy(List<CorrectedBy> correctedBy) { this.correctedBy = correctedBy; }
}
