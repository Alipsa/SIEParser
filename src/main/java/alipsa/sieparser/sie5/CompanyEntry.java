package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Company information in a SIE 5 entry (import) document.
 * Maps to the XSD complex type {@code CompanyTypeEntry}.
 *
 * <p>Unlike {@link Company}, the {@code name} attribute is optional in entry
 * documents. Only the {@code organizationId} is required.</p>
 *
 * @see Company
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyEntry {

    @XmlAttribute(name = "organizationId", required = true)
    private String organizationId;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "clientId")
    private String clientId;

    @XmlAttribute(name = "multiple")
    private Integer multiple;


    /** Creates a new instance. */
    public CompanyEntry() {}
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
     * Returns the company name, if specified.
     *
     * @return the company name, or {@code null} if not set (optional in entry documents)
     */
    public String getName() { return name; }

    /**
     * Sets the company name.
     *
     * @param name the company name (optional in entry documents)
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
