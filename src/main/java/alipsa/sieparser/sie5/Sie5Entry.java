package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Root element for a SIE 5 entry (import) document.
 * Maps to the &lt;SieEntry&gt; element in the XSD.
 */
@XmlRootElement(name = "SieEntry")
@XmlAccessorType(XmlAccessType.FIELD)
public class Sie5Entry {

    @XmlElement(name = "FileInfo", required = true)
    private FileInfoEntry fileInfo;

    @XmlElementWrapper(name = "Accounts")
    @XmlElement(name = "Account")
    private List<AccountEntry> accounts = new ArrayList<>();

    @XmlElementWrapper(name = "Dimensions")
    @XmlElement(name = "Dimension")
    private List<DimensionEntry> dimensions = new ArrayList<>();

    @XmlElement(name = "CustomerInvoices")
    private List<CustomerInvoicesEntry> customerInvoices = new ArrayList<>();

    @XmlElement(name = "SupplierInvoices")
    private List<SupplierInvoicesEntry> supplierInvoices = new ArrayList<>();

    @XmlElement(name = "FixedAssets")
    private List<FixedAssetsEntry> fixedAssets = new ArrayList<>();

    @XmlElement(name = "GeneralSubdividedAccount")
    private List<GeneralSubdividedAccountEntry> generalSubdividedAccounts = new ArrayList<>();

    @XmlElementWrapper(name = "Customers")
    @XmlElement(name = "Customer")
    private List<Customer> customers = new ArrayList<>();

    @XmlElementWrapper(name = "Suppliers")
    @XmlElement(name = "Supplier")
    private List<Supplier> suppliers = new ArrayList<>();

    @XmlElement(name = "Journal")
    private List<JournalEntry2> journals = new ArrayList<>();

    @XmlElement(name = "Documents")
    private Documents documents;

    public FileInfoEntry getFileInfo() { return fileInfo; }
    public void setFileInfo(FileInfoEntry fileInfo) { this.fileInfo = fileInfo; }

    public List<AccountEntry> getAccounts() { return accounts; }
    public void setAccounts(List<AccountEntry> accounts) { this.accounts = accounts; }

    public List<DimensionEntry> getDimensions() { return dimensions; }
    public void setDimensions(List<DimensionEntry> dimensions) { this.dimensions = dimensions; }

    public List<CustomerInvoicesEntry> getCustomerInvoices() { return customerInvoices; }
    public void setCustomerInvoices(List<CustomerInvoicesEntry> customerInvoices) { this.customerInvoices = customerInvoices; }

    public List<SupplierInvoicesEntry> getSupplierInvoices() { return supplierInvoices; }
    public void setSupplierInvoices(List<SupplierInvoicesEntry> supplierInvoices) { this.supplierInvoices = supplierInvoices; }

    public List<FixedAssetsEntry> getFixedAssets() { return fixedAssets; }
    public void setFixedAssets(List<FixedAssetsEntry> fixedAssets) { this.fixedAssets = fixedAssets; }

    public List<GeneralSubdividedAccountEntry> getGeneralSubdividedAccounts() { return generalSubdividedAccounts; }
    public void setGeneralSubdividedAccounts(List<GeneralSubdividedAccountEntry> generalSubdividedAccounts) { this.generalSubdividedAccounts = generalSubdividedAccounts; }

    public List<Customer> getCustomers() { return customers; }
    public void setCustomers(List<Customer> customers) { this.customers = customers; }

    public List<Supplier> getSuppliers() { return suppliers; }
    public void setSuppliers(List<Supplier> suppliers) { this.suppliers = suppliers; }

    public List<JournalEntry2> getJournals() { return journals; }
    public void setJournals(List<JournalEntry2> journals) { this.journals = journals; }

    public Documents getDocuments() { return documents; }
    public void setDocuments(Documents documents) { this.documents = documents; }
}
