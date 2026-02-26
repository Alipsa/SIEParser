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


    /** Creates a new instance. */
    public LedgerEntry() {}
    /**
     * Returns the account identifier (required).
     * @return the account identifier (required)
     */
    public String getAccountId() { return accountId; }

    /**
     * Sets the account identifier.
     * @param accountId the account identifier
     */
    public void setAccountId(String accountId) { this.accountId = accountId; }

    /**
     * Returns the monetary amount (required).
     * @return the monetary amount (required)
     */
    public BigDecimal getAmount() { return amount; }

    /**
     * Sets the monetary amount.
     * @param amount the monetary amount
     */
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    /**
     * Returns the optional quantity.
     * @return the optional quantity
     */
    public BigDecimal getQuantity() { return quantity; }

    /**
     * Sets the quantity.
     * @param quantity the quantity
     */
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    /**
     * Returns optional descriptive text for this ledger entry.
     * @return optional descriptive text for this ledger entry
     */
    public String getText() { return text; }

    /**
     * Sets descriptive text for this ledger entry.
     * @param text descriptive text for this ledger entry
     */
    public void setText(String text) { this.text = text; }

    /**
     * Returns the optional ledger date (may differ from the journal date).
     * @return the optional ledger date (may differ from the journal date)
     */
    public LocalDate getLedgerDate() { return ledgerDate; }

    /**
     * Sets the ledger date.
     * @param ledgerDate the ledger date
     */
    public void setLedgerDate(LocalDate ledgerDate) { this.ledgerDate = ledgerDate; }

    /**
     * Returns the optional foreign currency amount.
     * @return the optional foreign currency amount
     */
    public ForeignCurrencyAmount getForeignCurrencyAmount() { return foreignCurrencyAmount; }

    /**
     * Sets the foreign currency amount.
     * @param foreignCurrencyAmount the foreign currency amount
     */
    public void setForeignCurrencyAmount(ForeignCurrencyAmount foreignCurrencyAmount) { this.foreignCurrencyAmount = foreignCurrencyAmount; }

    /**
     * Returns the list of object (dimension value) references for this ledger entry.
     * @return the list of object (dimension value) references for this ledger entry
     */
    public List<ObjectReference> getObjectReferences() { return objectReferences; }

    /**
     * Sets the list of object references to set.
     * @param objectReferences the list of object references to set
     */
    public void setObjectReferences(List<ObjectReference> objectReferences) { this.objectReferences = objectReferences; }

    /**
     * Returns the optional subdivided account object reference.
     * @return the optional subdivided account object reference
     */
    public SubdividedAccountObjectReference getSubdividedAccountObjectReference() { return subdividedAccountObjectReference; }

    /**
     * Sets the subdivided account object reference.
     * @param subdividedAccountObjectReference the subdivided account object reference
     */
    public void setSubdividedAccountObjectReference(SubdividedAccountObjectReference subdividedAccountObjectReference) { this.subdividedAccountObjectReference = subdividedAccountObjectReference; }

    /**
     * Returns optional entry registration info for this ledger entry.
     * @return optional entry registration info for this ledger entry
     */
    public EntryInfo getEntryInfo() { return entryInfo; }

    /**
     * Sets the entry registration info.
     * @param entryInfo the entry registration info
     */
    public void setEntryInfo(EntryInfo entryInfo) { this.entryInfo = entryInfo; }

    /**
     * Returns optional overstrike info if this ledger entry has been struck through.
     * @return optional overstrike info if this ledger entry has been struck through
     */
    public Overstrike getOverstrike() { return overstrike; }

    /**
     * Sets the overstrike info.
     * @param overstrike the overstrike info
     */
    public void setOverstrike(Overstrike overstrike) { this.overstrike = overstrike; }

    /**
     * Returns optional locking info indicating when the ledger entry achieved "entered" status.
     * @return optional locking info indicating when the ledger entry achieved "entered" status
     */
    public LockingInfo getLockingInfo() { return lockingInfo; }

    /**
     * Sets the locking info.
     * @param lockingInfo the locking info
     */
    public void setLockingInfo(LockingInfo lockingInfo) { this.lockingInfo = lockingInfo; }
}
