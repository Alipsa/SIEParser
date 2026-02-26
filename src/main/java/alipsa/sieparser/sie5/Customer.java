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
     * Returns the unique customer identifier (required).
     * @return the unique customer identifier (required)
     */
    public String getId() { return id; }

    /**
     * Sets the unique customer identifier.
     * @param id the unique customer identifier
     */
    public void setId(String id) { this.id = id; }

    /**
     * Returns the customer name (required).
     * @return the customer name (required)
     */
    public String getName() { return name; }

    /**
     * Sets the customer name.
     * @param name the customer name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the organization identifier (e.g. Swedish organisationsnummer).
     * @return the organization identifier (e.g. Swedish organisationsnummer), or {@code null} if not set
     */
    public String getOrganizationId() { return organizationId; }

    /**
     * Sets the organization identifier.
     * @param organizationId the organization identifier
     */
    public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

    /**
     * Returns the VAT registration number.
     * @return the VAT registration number, or {@code null} if not set
     */
    public String getVatNr() { return vatNr; }

    /**
     * Sets the VAT registration number.
     * @param vatNr the VAT registration number
     */
    public void setVatNr(String vatNr) { this.vatNr = vatNr; }

    /**
     * Returns the first address line.
     * @return the first address line, or {@code null} if not set
     */
    public String getAddress1() { return address1; }

    /**
     * Sets the first address line.
     * @param address1 the first address line
     */
    public void setAddress1(String address1) { this.address1 = address1; }

    /**
     * Returns the second address line.
     * @return the second address line, or {@code null} if not set
     */
    public String getAddress2() { return address2; }

    /**
     * Sets the second address line.
     * @param address2 the second address line
     */
    public void setAddress2(String address2) { this.address2 = address2; }

    /**
     * Returns the postal/zip code.
     * @return the postal/zip code, or {@code null} if not set
     */
    public String getZipcode() { return zipcode; }

    /**
     * Sets the postal/zip code.
     * @param zipcode the postal/zip code
     */
    public void setZipcode(String zipcode) { this.zipcode = zipcode; }

    /**
     * Returns the city name.
     * @return the city name, or {@code null} if not set
     */
    public String getCity() { return city; }

    /**
     * Sets the city name.
     * @param city the city name
     */
    public void setCity(String city) { this.city = city; }

    /**
     * Returns the country name or code.
     * @return the country name or code, or {@code null} if not set
     */
    public String getCountry() { return country; }

    /**
     * Sets the country name or code.
     * @param country the country name or code
     */
    public void setCountry(String country) { this.country = country; }
}
