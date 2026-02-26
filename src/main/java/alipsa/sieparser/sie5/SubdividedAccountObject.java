package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for objects within a subdivided account in full SIE 5 documents.
 * Corresponds to {@code SubdividedAccountObjectType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Concrete subclasses include {@link CustomerInvoice}, {@link SupplierInvoice},
 * {@link FixedAsset}, and {@link GeneralObject}. Each object carries an identifier,
 * an optional name, balance information, and an original amount.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SubdividedAccountObject {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "Balances")
    private List<Balances> balances = new ArrayList<>();

    @XmlElement(name = "OriginalAmount", required = true)
    private OriginalAmount originalAmount;

    /**
     * Returns the unique object identifier (required).
     * @return the unique object identifier (required)
     */
    public String getId() { return id; }

    /**
     * Sets the unique object identifier.
     * @param id the unique object identifier
     */
    public void setId(String id) { this.id = id; }

    /**
     * Returns the optional descriptive name of this object.
     * @return the optional descriptive name of this object
     */
    public String getName() { return name; }

    /**
     * Sets the descriptive name.
     * @param name the descriptive name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the list of balance records for this object.
     * @return the list of balance records for this object
     */
    public List<Balances> getBalances() { return balances; }

    /**
     * Sets the list of balance records to set.
     * @param balances the list of balance records to set
     */
    public void setBalances(List<Balances> balances) { this.balances = balances; }

    /**
     * Returns the original amount for this object (required).
     * @return the original amount for this object (required)
     */
    public OriginalAmount getOriginalAmount() { return originalAmount; }

    /**
     * Sets the original amount to set.
     * @param originalAmount the original amount to set
     */
    public void setOriginalAmount(OriginalAmount originalAmount) { this.originalAmount = originalAmount; }
}
