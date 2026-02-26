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
 * Maps to the {@code <SieEntry>} element in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>An entry document is a subset of a full SIE 5 document, intended for
 * importing data into an existing accounting system. It uses entry-specific
 * types such as {@link FileInfoEntry}, {@link AccountEntry}, and
 * {@link CompanyEntry} that have relaxed mandatory-field requirements
 * compared to their full-document counterparts.</p>
 *
 * @see Sie5Document
 * @see Sie5DocumentReader
 * @see Sie5DocumentWriter
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


    /** Creates a new instance. */
    public Sie5Entry() {}
    /**
     * Returns the file information metadata for this entry document.
     *
     * @return the entry file info, never {@code null} in a valid document
     */
    public FileInfoEntry getFileInfo() { return fileInfo; }

    /**
     * Sets the file information metadata for this entry document.
     *
     * @param fileInfo the entry file info (required)
     */
    public void setFileInfo(FileInfoEntry fileInfo) { this.fileInfo = fileInfo; }

    /**
     * Returns the list of accounts in this entry document.
     *
     * @return the accounts list (wrapped in an {@code <Accounts>} element)
     */
    public List<AccountEntry> getAccounts() { return accounts; }

    /**
     * Sets the list of accounts in this entry document.
     *
     * @param accounts the accounts list
     */
    public void setAccounts(List<AccountEntry> accounts) { this.accounts = accounts; }

    /**
     * Returns the list of dimensions defined in this entry document.
     *
     * @return the dimensions list (wrapped in a {@code <Dimensions>} element)
     */
    public List<DimensionEntry> getDimensions() { return dimensions; }

    /**
     * Sets the list of dimensions defined in this entry document.
     *
     * @param dimensions the dimensions list
     */
    public void setDimensions(List<DimensionEntry> dimensions) { this.dimensions = dimensions; }

    /**
     * Returns the customer invoices sections in this entry document.
     *
     * @return the customer invoices list
     */
    public List<CustomerInvoicesEntry> getCustomerInvoices() { return customerInvoices; }

    /**
     * Sets the customer invoices sections in this entry document.
     *
     * @param customerInvoices the customer invoices list
     */
    public void setCustomerInvoices(List<CustomerInvoicesEntry> customerInvoices) { this.customerInvoices = customerInvoices; }

    /**
     * Returns the supplier invoices sections in this entry document.
     *
     * @return the supplier invoices list
     */
    public List<SupplierInvoicesEntry> getSupplierInvoices() { return supplierInvoices; }

    /**
     * Sets the supplier invoices sections in this entry document.
     *
     * @param supplierInvoices the supplier invoices list
     */
    public void setSupplierInvoices(List<SupplierInvoicesEntry> supplierInvoices) { this.supplierInvoices = supplierInvoices; }

    /**
     * Returns the fixed assets sections in this entry document.
     *
     * @return the fixed assets list
     */
    public List<FixedAssetsEntry> getFixedAssets() { return fixedAssets; }

    /**
     * Sets the fixed assets sections in this entry document.
     *
     * @param fixedAssets the fixed assets list
     */
    public void setFixedAssets(List<FixedAssetsEntry> fixedAssets) { this.fixedAssets = fixedAssets; }

    /**
     * Returns the general subdivided accounts in this entry document.
     *
     * @return the general subdivided accounts list
     */
    public List<GeneralSubdividedAccountEntry> getGeneralSubdividedAccounts() { return generalSubdividedAccounts; }

    /**
     * Sets the general subdivided accounts in this entry document.
     *
     * @param generalSubdividedAccounts the general subdivided accounts list
     */
    public void setGeneralSubdividedAccounts(List<GeneralSubdividedAccountEntry> generalSubdividedAccounts) { this.generalSubdividedAccounts = generalSubdividedAccounts; }

    /**
     * Returns the list of customers in this entry document.
     *
     * @return the customers list (wrapped in a {@code <Customers>} element)
     */
    public List<Customer> getCustomers() { return customers; }

    /**
     * Sets the list of customers in this entry document.
     *
     * @param customers the customers list
     */
    public void setCustomers(List<Customer> customers) { this.customers = customers; }

    /**
     * Returns the list of suppliers in this entry document.
     *
     * @return the suppliers list (wrapped in a {@code <Suppliers>} element)
     */
    public List<Supplier> getSuppliers() { return suppliers; }

    /**
     * Sets the list of suppliers in this entry document.
     *
     * @param suppliers the suppliers list
     */
    public void setSuppliers(List<Supplier> suppliers) { this.suppliers = suppliers; }

    /**
     * Returns the journals in this entry document.
     *
     * @return the journals list
     */
    public List<JournalEntry2> getJournals() { return journals; }

    /**
     * Sets the journals in this entry document.
     *
     * @param journals the journals list
     */
    public void setJournals(List<JournalEntry2> journals) { this.journals = journals; }

    /**
     * Returns the embedded documents container, if present.
     *
     * @return the documents container, or {@code null} if none
     */
    public Documents getDocuments() { return documents; }

    /**
     * Sets the embedded documents container.
     *
     * @param documents the documents container
     */
    public void setDocuments(Documents documents) { this.documents = documents; }
}
