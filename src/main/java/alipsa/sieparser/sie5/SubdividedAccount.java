package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for subdivided account containers in full SIE 5 documents.
 * Corresponds to {@code BaseSubdividedAccountType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>A subdivided account groups objects (e.g. customer invoices, supplier invoices,
 * fixed assets) under a primary account and optionally references secondary accounts.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class SubdividedAccount {

    @XmlAttribute(name = "primaryAccountId", required = true)
    private String primaryAccountId;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "SecondaryAccountRef")
    private List<SecondaryAccountRef> secondaryAccountRefs = new ArrayList<>();

    /**
     * Returns the primary account identifier (required).
     * @return the primary account identifier (required)
     */
    public String getPrimaryAccountId() { return primaryAccountId; }

    /**
     * Sets the primary account identifier.
     * @param primaryAccountId the primary account identifier
     */
    public void setPrimaryAccountId(String primaryAccountId) { this.primaryAccountId = primaryAccountId; }

    /**
     * Returns the optional descriptive name of this subdivided account.
     * @return the optional descriptive name of this subdivided account
     */
    public String getName() { return name; }

    /**
     * Sets the descriptive name.
     * @param name the descriptive name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the list of secondary account references associated with this subdivided account.
     * @return the list of secondary account references associated with this subdivided account
     */
    public List<SecondaryAccountRef> getSecondaryAccountRefs() { return secondaryAccountRefs; }

    /**
     * Sets the list of secondary account references to set.
     * @param secondaryAccountRefs the list of secondary account references to set
     */
    public void setSecondaryAccountRefs(List<SecondaryAccountRef> secondaryAccountRefs) { this.secondaryAccountRefs = secondaryAccountRefs; }
}
