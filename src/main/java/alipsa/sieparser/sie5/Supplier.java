package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Represents supplier master data in SIE 5 documents.
 * Corresponds to {@code SupplierType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Contains identifying information (id, name, organization id, VAT number),
 * address fields, and payment details (Bankgiro, Plusgiro, BIC, IBAN) for a supplier.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Supplier {

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

    @XmlAttribute(name = "BgAccount")
    private String bgAccount;

    @XmlAttribute(name = "PgAccount")
    private String pgAccount;

    @XmlAttribute(name = "BIC")
    private String bic;

    @XmlAttribute(name = "IBAN")
    private String iban;

    /**
     * @return the unique supplier identifier (required)
     */
    public String getId() { return id; }

    /**
     * @param id the unique supplier identifier
     */
    public void setId(String id) { this.id = id; }

    /**
     * @return the supplier name (required)
     */
    public String getName() { return name; }

    /**
     * @param name the supplier name
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

    /**
     * @return the Bankgiro account number, or {@code null} if not set
     */
    public String getBgAccount() { return bgAccount; }

    /**
     * @param bgAccount the Bankgiro account number
     */
    public void setBgAccount(String bgAccount) { this.bgAccount = bgAccount; }

    /**
     * @return the Plusgiro account number, or {@code null} if not set
     */
    public String getPgAccount() { return pgAccount; }

    /**
     * @param pgAccount the Plusgiro account number
     */
    public void setPgAccount(String pgAccount) { this.pgAccount = pgAccount; }

    /**
     * @return the BIC (Bank Identifier Code), or {@code null} if not set
     */
    public String getBic() { return bic; }

    /**
     * @param bic the BIC (Bank Identifier Code)
     */
    public void setBic(String bic) { this.bic = bic; }

    /**
     * @return the IBAN (International Bank Account Number), or {@code null} if not set
     */
    public String getIban() { return iban; }

    /**
     * @param iban the IBAN (International Bank Account Number)
     */
    public void setIban(String iban) { this.iban = iban; }
}
