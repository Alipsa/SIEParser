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

    public BigInteger getId() { return id; }
    public void setId(BigInteger id) { this.id = id; }

    public LocalDate getJournalDate() { return journalDate; }
    public void setJournalDate(LocalDate journalDate) { this.journalDate = journalDate; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }

    public EntryInfo getEntryInfo() { return entryInfo; }
    public void setEntryInfo(EntryInfo entryInfo) { this.entryInfo = entryInfo; }

    public OriginalEntryInfo getOriginalEntryInfo() { return originalEntryInfo; }
    public void setOriginalEntryInfo(OriginalEntryInfo originalEntryInfo) { this.originalEntryInfo = originalEntryInfo; }

    public List<LedgerEntry> getLedgerEntries() { return ledgerEntries; }
    public void setLedgerEntries(List<LedgerEntry> ledgerEntries) { this.ledgerEntries = ledgerEntries; }

    public LockingInfo getLockingInfo() { return lockingInfo; }
    public void setLockingInfo(LockingInfo lockingInfo) { this.lockingInfo = lockingInfo; }

    public List<VoucherReference> getVoucherReferences() { return voucherReferences; }
    public void setVoucherReferences(List<VoucherReference> voucherReferences) { this.voucherReferences = voucherReferences; }

    public List<CorrectedBy> getCorrectedBy() { return correctedBy; }
    public void setCorrectedBy(List<CorrectedBy> correctedBy) { this.correctedBy = correctedBy; }
}
