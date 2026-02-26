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
 * A journal entry in an SIE 5 entry document.
 * Corresponds to {@code JournalEntryTypeEntry} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>This is a simpler variant of {@link JournalEntry} used in entry documents:
 * the {@code id} is optional, {@link OriginalEntryInfo} is required,
 * and there is no {@link EntryInfo} or {@link LockingInfo}.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JournalEntryEntry {

    @XmlAttribute(name = "id")
    private BigInteger id;

    @XmlAttribute(name = "journalDate", required = true)
    @XmlSchemaType(name = "date")
    private LocalDate journalDate;

    @XmlAttribute(name = "text")
    private String text;

    @XmlAttribute(name = "referenceId")
    private String referenceId;

    @XmlElement(name = "OriginalEntryInfo", required = true)
    private OriginalEntryInfo originalEntryInfo;

    @XmlElement(name = "LedgerEntry")
    private List<LedgerEntryEntry> ledgerEntries = new ArrayList<>();

    @XmlElement(name = "VoucherReference")
    private List<VoucherReference> voucherReferences = new ArrayList<>();


    /** Creates a new instance. */
    public JournalEntryEntry() {}
    /**
     * Returns the optional journal entry id (xsd:nonNegativeInteger).
     * @return the optional journal entry id (xsd:nonNegativeInteger)
     */
    public BigInteger getId() { return id; }

    /**
     * Sets the journal entry id.
     * @param id the journal entry id
     */
    public void setId(BigInteger id) { this.id = id; }

    /**
     * Returns the journal date (required).
     * @return the journal date (required)
     */
    public LocalDate getJournalDate() { return journalDate; }

    /**
     * Sets the journal date.
     * @param journalDate the journal date
     */
    public void setJournalDate(LocalDate journalDate) { this.journalDate = journalDate; }

    /**
     * Returns optional descriptive text for this entry.
     * @return optional descriptive text for this entry
     */
    public String getText() { return text; }

    /**
     * Sets descriptive text for this entry.
     * @param text descriptive text for this entry
     */
    public void setText(String text) { this.text = text; }

    /**
     * Returns optional external reference identifier.
     * @return optional external reference identifier
     */
    public String getReferenceId() { return referenceId; }

    /**
     * Sets external reference identifier.
     * @param referenceId external reference identifier
     */
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }

    /**
     * Returns the original entry registration info (required).
     * @return the original entry registration info (required)
     */
    public OriginalEntryInfo getOriginalEntryInfo() { return originalEntryInfo; }

    /**
     * Sets original entry registration info.
     * @param originalEntryInfo original entry registration info
     */
    public void setOriginalEntryInfo(OriginalEntryInfo originalEntryInfo) { this.originalEntryInfo = originalEntryInfo; }

    /**
     * Returns the list of ledger entry rows belonging to this journal entry.
     * @return the list of ledger entry rows belonging to this journal entry
     */
    public List<LedgerEntryEntry> getLedgerEntries() { return ledgerEntries; }

    /**
     * Sets the list of ledger entry rows to set.
     * @param ledgerEntries the list of ledger entry rows to set
     */
    public void setLedgerEntries(List<LedgerEntryEntry> ledgerEntries) { this.ledgerEntries = ledgerEntries; }

    /**
     * Returns the list of voucher references attached to this entry.
     * @return the list of voucher references attached to this entry
     */
    public List<VoucherReference> getVoucherReferences() { return voucherReferences; }

    /**
     * Sets the list of voucher references to set.
     * @param voucherReferences the list of voucher references to set
     */
    public void setVoucherReferences(List<VoucherReference> voucherReferences) { this.voucherReferences = voucherReferences; }
}
