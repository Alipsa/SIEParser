package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

/**
 * File metadata for a SIE 5 entry (import) document.
 * Maps to the XSD complex type {@code FileInfoTypeEntry}.
 *
 * <p>Similar to {@link FileInfo} but with relaxed requirements suitable for
 * import documents: fiscal years are omitted, and the accounting currency is
 * optional. The company element uses {@link CompanyEntry} where the name
 * attribute is optional.</p>
 *
 * @see FileInfo
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FileInfoEntry {

    @XmlElement(name = "SoftwareProduct", required = true)
    private SoftwareProduct softwareProduct;

    @XmlElement(name = "FileCreation", required = true)
    private FileCreation fileCreation;

    @XmlElement(name = "Company", required = true)
    private CompanyEntry company;

    @XmlElement(name = "AccountingCurrency")
    private AccountingCurrency accountingCurrency;

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
     * Returns the company information for this entry document.
     *
     * @return the company (required, but name attribute is optional)
     */
    public CompanyEntry getCompany() { return company; }

    /**
     * Sets the company information for this entry document.
     *
     * @param company the company
     */
    public void setCompany(CompanyEntry company) { this.company = company; }

    /**
     * Returns the default accounting currency, if specified.
     *
     * @return the accounting currency, or {@code null} if not set
     */
    public AccountingCurrency getAccountingCurrency() { return accountingCurrency; }

    /**
     * Sets the default accounting currency.
     *
     * @param accountingCurrency the accounting currency (optional for entry documents)
     */
    public void setAccountingCurrency(AccountingCurrency accountingCurrency) { this.accountingCurrency = accountingCurrency; }
}
