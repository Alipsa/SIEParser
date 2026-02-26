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

    public BigInteger getId() { return id; }
    public void setId(BigInteger id) { this.id = id; }

    public LocalDate getJournalDate() { return journalDate; }
    public void setJournalDate(LocalDate journalDate) { this.journalDate = journalDate; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }

    public OriginalEntryInfo getOriginalEntryInfo() { return originalEntryInfo; }
    public void setOriginalEntryInfo(OriginalEntryInfo originalEntryInfo) { this.originalEntryInfo = originalEntryInfo; }

    public List<LedgerEntryEntry> getLedgerEntries() { return ledgerEntries; }
    public void setLedgerEntries(List<LedgerEntryEntry> ledgerEntries) { this.ledgerEntries = ledgerEntries; }

    public List<VoucherReference> getVoucherReferences() { return voucherReferences; }
    public void setVoucherReferences(List<VoucherReference> voucherReferences) { this.voucherReferences = voucherReferences; }
}
