package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

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

    public SoftwareProduct getSoftwareProduct() { return softwareProduct; }
    public void setSoftwareProduct(SoftwareProduct softwareProduct) { this.softwareProduct = softwareProduct; }

    public FileCreation getFileCreation() { return fileCreation; }
    public void setFileCreation(FileCreation fileCreation) { this.fileCreation = fileCreation; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public List<FiscalYear> getFiscalYears() { return fiscalYears; }
    public void setFiscalYears(List<FiscalYear> fiscalYears) { this.fiscalYears = fiscalYears; }

    public AccountingCurrency getAccountingCurrency() { return accountingCurrency; }
    public void setAccountingCurrency(AccountingCurrency accountingCurrency) { this.accountingCurrency = accountingCurrency; }
}
