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

/**
 * A ledger entry row in a full SIE 5 document.
 * Corresponds to {@code LedgerEntryType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Each ledger entry represents one accounting row with a required {@code accountId}
 * and {@code amount}. Optional fields include quantity, descriptive text, ledger date,
 * {@link ForeignCurrencyAmount}, {@link ObjectReference} list,
 * {@link SubdividedAccountObjectReference}, {@link EntryInfo}, {@link Overstrike},
 * and {@link LockingInfo}.</p>
 */
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

    /**
     * @return the account identifier (required)
     */
    public String getAccountId() { return accountId; }

    /**
     * @param accountId the account identifier
     */
    public void setAccountId(String accountId) { this.accountId = accountId; }

    /**
     * @return the monetary amount (required)
     */
    public BigDecimal getAmount() { return amount; }

    /**
     * @param amount the monetary amount
     */
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    /**
     * @return the optional quantity
     */
    public BigDecimal getQuantity() { return quantity; }

    /**
     * @param quantity the quantity
     */
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    /**
     * @return optional descriptive text for this ledger entry
     */
    public String getText() { return text; }

    /**
     * @param text descriptive text for this ledger entry
     */
    public void setText(String text) { this.text = text; }

    /**
     * @return the optional ledger date (may differ from the journal date)
     */
    public LocalDate getLedgerDate() { return ledgerDate; }

    /**
     * @param ledgerDate the ledger date
     */
    public void setLedgerDate(LocalDate ledgerDate) { this.ledgerDate = ledgerDate; }

    /**
     * @return the optional foreign currency amount
     */
    public ForeignCurrencyAmount getForeignCurrencyAmount() { return foreignCurrencyAmount; }

    /**
     * @param foreignCurrencyAmount the foreign currency amount
     */
    public void setForeignCurrencyAmount(ForeignCurrencyAmount foreignCurrencyAmount) { this.foreignCurrencyAmount = foreignCurrencyAmount; }

    /**
     * @return the list of object (dimension value) references for this ledger entry
     */
    public List<ObjectReference> getObjectReferences() { return objectReferences; }

    /**
     * @param objectReferences the list of object references to set
     */
    public void setObjectReferences(List<ObjectReference> objectReferences) { this.objectReferences = objectReferences; }

    /**
     * @return the optional subdivided account object reference
     */
    public SubdividedAccountObjectReference getSubdividedAccountObjectReference() { return subdividedAccountObjectReference; }

    /**
     * @param subdividedAccountObjectReference the subdivided account object reference
     */
    public void setSubdividedAccountObjectReference(SubdividedAccountObjectReference subdividedAccountObjectReference) { this.subdividedAccountObjectReference = subdividedAccountObjectReference; }

    /**
     * @return optional entry registration info for this ledger entry
     */
    public EntryInfo getEntryInfo() { return entryInfo; }

    /**
     * @param entryInfo the entry registration info
     */
    public void setEntryInfo(EntryInfo entryInfo) { this.entryInfo = entryInfo; }

    /**
     * @return optional overstrike info if this ledger entry has been struck through
     */
    public Overstrike getOverstrike() { return overstrike; }

    /**
     * @param overstrike the overstrike info
     */
    public void setOverstrike(Overstrike overstrike) { this.overstrike = overstrike; }

    /**
     * @return optional locking info indicating when the ledger entry achieved "entered" status
     */
    public LockingInfo getLockingInfo() { return lockingInfo; }

    /**
     * @param lockingInfo the locking info
     */
    public void setLockingInfo(LockingInfo lockingInfo) { this.lockingInfo = lockingInfo; }
}
