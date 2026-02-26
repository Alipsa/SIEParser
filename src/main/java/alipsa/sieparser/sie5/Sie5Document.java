package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Root element for a full SIE 5 document.
 * Maps to the &lt;Sie&gt; element in the XSD.
 */
@XmlRootElement(name = "Sie")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sie5Document {

    @XmlElement(name = "FileInfo", required = true)
    private FileInfo fileInfo;

    @XmlElementWrapper(name = "Accounts", required = true)
    @XmlElement(name = "Account")
    private List<Account> accounts = new ArrayList<>();

    @XmlElementWrapper(name = "Dimensions")
    @XmlElement(name = "Dimension")
    private List<Dimension> dimensions = new ArrayList<>();

    @XmlElement(name = "CustomerInvoices")
    private List<CustomerInvoices> customerInvoices = new ArrayList<>();

    @XmlElement(name = "SupplierInvoices")
    private List<SupplierInvoices> supplierInvoices = new ArrayList<>();

    @XmlElement(name = "FixedAssets")
    private List<FixedAssets> fixedAssets = new ArrayList<>();

    @XmlElement(name = "GeneralSubdividedAccount")
    private List<GeneralSubdividedAccount> generalSubdividedAccounts = new ArrayList<>();

    @XmlElementWrapper(name = "Customers")
    @XmlElement(name = "Customer")
    private List<Customer> customers = new ArrayList<>();

    @XmlElementWrapper(name = "Suppliers")
    @XmlElement(name = "Supplier")
    private List<Supplier> suppliers = new ArrayList<>();

    @XmlElementWrapper(name = "AccountAggregations")
    @XmlElement(name = "AccountAggregation")
    private List<AccountAggregation> accountAggregations = new ArrayList<>();

    @XmlElement(name = "Journal")
    private List<Journal> journals = new ArrayList<>();

    @XmlElement(name = "Documents")
    private Documents documents;

    public FileInfo getFileInfo() { return fileInfo; }
    public void setFileInfo(FileInfo fileInfo) { this.fileInfo = fileInfo; }

    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }

    public List<Dimension> getDimensions() { return dimensions; }
    public void setDimensions(List<Dimension> dimensions) { this.dimensions = dimensions; }

    public List<CustomerInvoices> getCustomerInvoices() { return customerInvoices; }
    public void setCustomerInvoices(List<CustomerInvoices> customerInvoices) { this.customerInvoices = customerInvoices; }

    public List<SupplierInvoices> getSupplierInvoices() { return supplierInvoices; }
    public void setSupplierInvoices(List<SupplierInvoices> supplierInvoices) { this.supplierInvoices = supplierInvoices; }

    public List<FixedAssets> getFixedAssets() { return fixedAssets; }
    public void setFixedAssets(List<FixedAssets> fixedAssets) { this.fixedAssets = fixedAssets; }

    public List<GeneralSubdividedAccount> getGeneralSubdividedAccounts() { return generalSubdividedAccounts; }
    public void setGeneralSubdividedAccounts(List<GeneralSubdividedAccount> generalSubdividedAccounts) { this.generalSubdividedAccounts = generalSubdividedAccounts; }

    public List<Customer> getCustomers() { return customers; }
    public void setCustomers(List<Customer> customers) { this.customers = customers; }

    public List<Supplier> getSuppliers() { return suppliers; }
    public void setSuppliers(List<Supplier> suppliers) { this.suppliers = suppliers; }

    public List<AccountAggregation> getAccountAggregations() { return accountAggregations; }
    public void setAccountAggregations(List<AccountAggregation> accountAggregations) { this.accountAggregations = accountAggregations; }

    public List<Journal> getJournals() { return journals; }
    public void setJournals(List<Journal> journals) { this.journals = journals; }

    public Documents getDocuments() { return documents; }
    public void setDocuments(Documents documents) { this.documents = documents; }
}
