package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Represents customer master data in SIE 5 documents.
 * Corresponds to {@code CustomerType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Contains identifying information (id, name, organization id, VAT number)
 * and address fields for a customer.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "organizationId")
    private String organizationId;

    @XmlAttribute(name = "vatNr")
    private String vatNr;

    @XmlAttribute(name = "address1")
    private String address1;

    @XmlAttribute(name = "address2")
    private String address2;

    @XmlAttribute(name = "zipcode")
    private String zipcode;

    @XmlAttribute(name = "city")
    private String city;

    @XmlAttribute(name = "country")
    private String country;

    /**
     * @return the unique customer identifier (required)
     */
    public String getId() { return id; }

    /**
     * @param id the unique customer identifier
     */
    public void setId(String id) { this.id = id; }

    /**
     * @return the customer name (required)
     */
    public String getName() { return name; }

    /**
     * @param name the customer name
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return the organization identifier (e.g. Swedish organisationsnummer), or {@code null} if not set
     */
    public String getOrganizationId() { return organizationId; }

    /**
     * @param organizationId the organization identifier
     */
    public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

    /**
     * @return the VAT registration number, or {@code null} if not set
     */
    public String getVatNr() { return vatNr; }

    /**
     * @param vatNr the VAT registration number
     */
    public void setVatNr(String vatNr) { this.vatNr = vatNr; }

    /**
     * @return the first address line, or {@code null} if not set
     */
    public String getAddress1() { return address1; }

    /**
     * @param address1 the first address line
     */
    public void setAddress1(String address1) { this.address1 = address1; }

    /**
     * @return the second address line, or {@code null} if not set
     */
    public String getAddress2() { return address2; }

    /**
     * @param address2 the second address line
     */
    public void setAddress2(String address2) { this.address2 = address2; }

    /**
     * @return the postal/zip code, or {@code null} if not set
     */
    public String getZipcode() { return zipcode; }

    /**
     * @param zipcode the postal/zip code
     */
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }

    /**
     * @return the city name, or {@code null} if not set
     */
    public String getCity() { return city; }

    /**
     * @param city the city name
     */
    public void setCity(String city) { this.city = city; }

    /**
     * @return the country name or code, or {@code null} if not set
     */
    public String getCountry() { return country; }

    /**
     * @param country the country name or code
     */
    public void setCountry(String country) { this.country = country; }
}
