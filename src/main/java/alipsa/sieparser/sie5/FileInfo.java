package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * File metadata for a full SIE 5 document.
 * Maps to the XSD complex type {@code FileInfoType}.
 *
 * <p>Contains required information about the software that created the file,
 * creation timestamp, company identity, fiscal years, and the default
 * accounting currency. All fields are mandatory in a full document.</p>
 *
 * @see FileInfoEntry
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FileInfo {

    @XmlElement(name = "SoftwareProduct", required = true)
    private SoftwareProduct softwareProduct;

    @XmlElement(name = "FileCreation", required = true)
    private FileCreation fileCreation;

    @XmlElement(name = "Company", required = true)
    private Company company;

    @XmlElementWrapper(name = "FiscalYears", required = true)
    @XmlElement(name = "FiscalYear")
    private List<FiscalYear> fiscalYears = new ArrayList<>();

    @XmlElement(name = "AccountingCurrency", required = true)
    private AccountingCurrency accountingCurrency;


    /** Creates a new instance. */
    public FileInfo() {}
    /**
     * Returns the software product that created this file.
     *
     * @return the software product (required)
     */
    public SoftwareProduct getSoftwareProduct() { return softwareProduct; }

    /**
     * Sets the software product that created this file.
     *
     * @param softwareProduct the software product
     */
    public void setSoftwareProduct(SoftwareProduct softwareProduct) { this.softwareProduct = softwareProduct; }

    /**
     * Returns the file creation metadata (timestamp and author).
     *
     * @return the file creation info (required)
     */
    public FileCreation getFileCreation() { return fileCreation; }

    /**
     * Sets the file creation metadata.
     *
     * @param fileCreation the file creation info
     */
    public void setFileCreation(FileCreation fileCreation) { this.fileCreation = fileCreation; }

    /**
     * Returns the company information.
     *
     * @return the company (required)
     */
    public Company getCompany() { return company; }

    /**
     * Sets the company information.
     *
     * @param company the company
     */
    public void setCompany(Company company) { this.company = company; }

    /**
     * Returns the list of fiscal years declared in this document.
     *
     * @return the fiscal years list (wrapped in a {@code <FiscalYears>} element, required)
     */
    public List<FiscalYear> getFiscalYears() { return fiscalYears; }

    /**
     * Sets the list of fiscal years declared in this document.
     *
     * @param fiscalYears the fiscal years list
     */
    public void setFiscalYears(List<FiscalYear> fiscalYears) { this.fiscalYears = fiscalYears; }

    /**
     * Returns the default accounting currency for this document.
     *
     * @return the accounting currency (required)
     */
    public AccountingCurrency getAccountingCurrency() { return accountingCurrency; }

    /**
     * Sets the default accounting currency for this document.
     *
     * @param accountingCurrency the accounting currency
     */
    public void setAccountingCurrency(AccountingCurrency accountingCurrency) { this.accountingCurrency = accountingCurrency; }
}
