package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

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

    public SoftwareProduct getSoftwareProduct() { return softwareProduct; }
    public void setSoftwareProduct(SoftwareProduct softwareProduct) { this.softwareProduct = softwareProduct; }

    public FileCreation getFileCreation() { return fileCreation; }
    public void setFileCreation(FileCreation fileCreation) { this.fileCreation = fileCreation; }

    public CompanyEntry getCompany() { return company; }
    public void setCompany(CompanyEntry company) { this.company = company; }

    public AccountingCurrency getAccountingCurrency() { return accountingCurrency; }
    public void setAccountingCurrency(AccountingCurrency accountingCurrency) { this.accountingCurrency = accountingCurrency; }
}
