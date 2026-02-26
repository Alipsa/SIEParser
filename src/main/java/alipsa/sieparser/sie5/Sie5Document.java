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
 * Maps to the {@code <Sie>} element in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>A full document contains complete accounting data including accounts,
 * dimensions, journals, customer/supplier invoices, fixed assets, and
 * embedded documents. This is in contrast to {@link Sie5Entry}, which
 * represents a partial import document.</p>
 *
 * @see Sie5Entry
 * @see Sie5DocumentReader
 * @see Sie5DocumentWriter
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

    /**
     * Returns the file information metadata for this document.
     *
     * @return the file info, never {@code null} in a valid document
     */
    public FileInfo getFileInfo() { return fileInfo; }

    /**
     * Sets the file information metadata for this document.
     *
     * @param fileInfo the file info (required)
     */
    public void setFileInfo(FileInfo fileInfo) { this.fileInfo = fileInfo; }

    /**
     * Returns the list of accounts in this document.
     *
     * @return the accounts list (wrapped in an {@code <Accounts>} element)
     */
    public List<Account> getAccounts() { return accounts; }

    /**
     * Sets the list of accounts in this document.
     *
     * @param accounts the accounts list
     */
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }

    /**
     * Returns the list of dimensions defined in this document.
     *
     * @return the dimensions list (wrapped in a {@code <Dimensions>} element)
     */
    public List<Dimension> getDimensions() { return dimensions; }

    /**
     * Sets the list of dimensions defined in this document.
     *
     * @param dimensions the dimensions list
     */
    public void setDimensions(List<Dimension> dimensions) { this.dimensions = dimensions; }

    /**
     * Returns the customer invoices sections in this document.
     *
     * @return the customer invoices list
     */
    public List<CustomerInvoices> getCustomerInvoices() { return customerInvoices; }

    /**
     * Sets the customer invoices sections in this document.
     *
     * @param customerInvoices the customer invoices list
     */
    public void setCustomerInvoices(List<CustomerInvoices> customerInvoices) { this.customerInvoices = customerInvoices; }

    /**
     * Returns the supplier invoices sections in this document.
     *
     * @return the supplier invoices list
     */
    public List<SupplierInvoices> getSupplierInvoices() { return supplierInvoices; }

    /**
     * Sets the supplier invoices sections in this document.
     *
     * @param supplierInvoices the supplier invoices list
     */
    public void setSupplierInvoices(List<SupplierInvoices> supplierInvoices) { this.supplierInvoices = supplierInvoices; }

    /**
     * Returns the fixed assets sections in this document.
     *
     * @return the fixed assets list
     */
    public List<FixedAssets> getFixedAssets() { return fixedAssets; }

    /**
     * Sets the fixed assets sections in this document.
     *
     * @param fixedAssets the fixed assets list
     */
    public void setFixedAssets(List<FixedAssets> fixedAssets) { this.fixedAssets = fixedAssets; }

    /**
     * Returns the general subdivided accounts in this document.
     *
     * @return the general subdivided accounts list
     */
    public List<GeneralSubdividedAccount> getGeneralSubdividedAccounts() { return generalSubdividedAccounts; }

    /**
     * Sets the general subdivided accounts in this document.
     *
     * @param generalSubdividedAccounts the general subdivided accounts list
     */
    public void setGeneralSubdividedAccounts(List<GeneralSubdividedAccount> generalSubdividedAccounts) { this.generalSubdividedAccounts = generalSubdividedAccounts; }

    /**
     * Returns the list of customers in this document.
     *
     * @return the customers list (wrapped in a {@code <Customers>} element)
     */
    public List<Customer> getCustomers() { return customers; }

    /**
     * Sets the list of customers in this document.
     *
     * @param customers the customers list
     */
    public void setCustomers(List<Customer> customers) { this.customers = customers; }

    /**
     * Returns the list of suppliers in this document.
     *
     * @return the suppliers list (wrapped in a {@code <Suppliers>} element)
     */
    public List<Supplier> getSuppliers() { return suppliers; }

    /**
     * Sets the list of suppliers in this document.
     *
     * @param suppliers the suppliers list
     */
    public void setSuppliers(List<Supplier> suppliers) { this.suppliers = suppliers; }

    /**
     * Returns the account aggregations defined in this document.
     *
     * @return the account aggregations list (wrapped in an {@code <AccountAggregations>} element)
     */
    public List<AccountAggregation> getAccountAggregations() { return accountAggregations; }

    /**
     * Sets the account aggregations defined in this document.
     *
     * @param accountAggregations the account aggregations list
     */
    public void setAccountAggregations(List<AccountAggregation> accountAggregations) { this.accountAggregations = accountAggregations; }

    /**
     * Returns the journals in this document.
     *
     * @return the journals list
     */
    public List<Journal> getJournals() { return journals; }

    /**
     * Sets the journals in this document.
     *
     * @param journals the journals list
     */
    public void setJournals(List<Journal> journals) { this.journals = journals; }

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
