package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Abstract base class for subdivided account containers in SIE 5 entry documents.
 * Corresponds to {@code BaseSubdividedAccountTypeEntry} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Unlike {@link SubdividedAccount}, this entry variant does not include
 * {@code SecondaryAccountRef} children.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class SubdividedAccountEntry {

    @XmlAttribute(name = "primaryAccountId", required = true)
    private String primaryAccountId;

    @XmlAttribute(name = "name")
    private String name;

    /**
     * @return the primary account identifier (required)
     */
    public String getPrimaryAccountId() { return primaryAccountId; }

    /**
     * @param primaryAccountId the primary account identifier
     */
    public void setPrimaryAccountId(String primaryAccountId) { this.primaryAccountId = primaryAccountId; }

    /**
     * @return the optional descriptive name of this subdivided account
     */
    public String getName() { return name; }

    /**
     * @param name the descriptive name
     */
    public void setName(String name) { this.name = name; }
}
