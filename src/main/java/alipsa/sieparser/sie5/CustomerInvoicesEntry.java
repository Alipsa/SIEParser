package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for customer invoices within a SIE 5 entry document.
 * Corresponds to {@code CustomerInvoicesTypeEntry} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code BaseSubdividedAccountTypeEntry}.
 *
 * <p>Groups zero or more {@link CustomerInvoiceEntry} elements under a primary account.
 * Unlike {@link CustomerInvoices}, this entry variant has no secondary account references.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerInvoicesEntry extends SubdividedAccountEntry {

    @XmlElement(name = "CustomerInvoice")
    private List<CustomerInvoiceEntry> customerInvoices = new ArrayList<>();


    /** Creates a new instance. */
    public CustomerInvoicesEntry() {}
    /**
     * Returns the list of customer invoice entries in this container.
     * @return the list of customer invoice entries in this container
     */
    public List<CustomerInvoiceEntry> getCustomerInvoices() { return customerInvoices; }

    /**
     * Sets the list of customer invoice entries to set.
     * @param customerInvoices the list of customer invoice entries to set
     */
    public void setCustomerInvoices(List<CustomerInvoiceEntry> customerInvoices) { this.customerInvoices = customerInvoices; }
}
