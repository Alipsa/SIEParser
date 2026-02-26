package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Company information in a full SIE 5 document.
 * Maps to the XSD complex type {@code CompanyType}.
 *
 * <p>Both {@code organizationId} and {@code name} are required attributes.
 * The optional {@code clientId} identifies the company in the creating
 * software's client register, and {@code multiple} indicates a multi-company
 * identifier when applicable.</p>
 *
 * @see CompanyEntry
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Company {

    @XmlAttribute(name = "organizationId", required = true)
    private String organizationId;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "clientId")
    private String clientId;

    @XmlAttribute(name = "multiple")
    private Integer multiple;

    /**
     * Returns the organization ID (Swedish: organisationsnummer).
     *
     * @return the organization ID (required)
     */
    public String getOrganizationId() { return organizationId; }

    /**
     * Sets the organization ID (Swedish: organisationsnummer).
     *
     * @param organizationId the organization ID
     */
    public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

    /**
     * Returns the company name.
     *
     * @return the company name (required)
     */
    public String getName() { return name; }

    /**
     * Sets the company name.
     *
     * @param name the company name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the client ID in the creating software's client register.
     *
     * @return the client ID, or {@code null} if not set
     */
    public String getClientId() { return clientId; }

    /**
     * Sets the client ID in the creating software's client register.
     *
     * @param clientId the client ID
     */
    public void setClientId(String clientId) { this.clientId = clientId; }

    /**
     * Returns the multi-company identifier, if applicable.
     *
     * @return the multiple identifier, or {@code null} if not set
     */
    public Integer getMultiple() { return multiple; }

    /**
     * Sets the multi-company identifier.
     *
     * @param multiple the multiple identifier
     */
    public void setMultiple(Integer multiple) { this.multiple = multiple; }
}
