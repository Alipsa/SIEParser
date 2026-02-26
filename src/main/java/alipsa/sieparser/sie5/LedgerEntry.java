package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class LedgerEntry {

    @XmlAttribute(name = "accountId", required = true)
    private String accountId;

    @XmlAttribute(name = "amount", required = true)
    private BigDecimal amount;

    @XmlAttribute(name = "quantity")
    private BigDecimal quantity;

    @XmlAttribute(name = "text")
    private String text;

    @XmlAttribute(name = "ledgerDate")
    @XmlSchemaType(name = "date")
    private LocalDate ledgerDate;

    @XmlElement(name = "ForeignCurrencyAmount")
    private ForeignCurrencyAmount foreignCurrencyAmount;

    @XmlElement(name = "ObjectReference")
    private List<ObjectReference> objectReferences = new ArrayList<>();

    @XmlElement(name = "SubdividedAccountObjectReference")
    private SubdividedAccountObjectReference subdividedAccountObjectReference;

    @XmlElement(name = "EntryInfo")
    private EntryInfo entryInfo;

    @XmlElement(name = "Overstrike")
    private Overstrike overstrike;

    @XmlElement(name = "LockingInfo")
    private LockingInfo lockingInfo;

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public LocalDate getLedgerDate() { return ledgerDate; }
    public void setLedgerDate(LocalDate ledgerDate) { this.ledgerDate = ledgerDate; }

    public ForeignCurrencyAmount getForeignCurrencyAmount() { return foreignCurrencyAmount; }
    public void setForeignCurrencyAmount(ForeignCurrencyAmount foreignCurrencyAmount) { this.foreignCurrencyAmount = foreignCurrencyAmount; }

    public List<ObjectReference> getObjectReferences() { return objectReferences; }
    public void setObjectReferences(List<ObjectReference> objectReferences) { this.objectReferences = objectReferences; }

    public SubdividedAccountObjectReference getSubdividedAccountObjectReference() { return subdividedAccountObjectReference; }
    public void setSubdividedAccountObjectReference(SubdividedAccountObjectReference subdividedAccountObjectReference) { this.subdividedAccountObjectReference = subdividedAccountObjectReference; }

    public EntryInfo getEntryInfo() { return entryInfo; }
    public void setEntryInfo(EntryInfo entryInfo) { this.entryInfo = entryInfo; }

    public Overstrike getOverstrike() { return overstrike; }
    public void setOverstrike(Overstrike overstrike) { this.overstrike = overstrike; }

    public LockingInfo getLockingInfo() { return lockingInfo; }
    public void setLockingInfo(LockingInfo lockingInfo) { this.lockingInfo = lockingInfo; }
}
