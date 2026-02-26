package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for customer invoices within a full SIE 5 document.
 * Corresponds to {@code CustomerInvoicesType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code BaseSubdividedAccountType}.
 *
 * <p>Groups zero or more {@link CustomerInvoice} elements under a primary account.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerInvoices extends SubdividedAccount {

    @XmlElement(name = "CustomerInvoice")
    private List<CustomerInvoice> customerInvoices = new ArrayList<>();

    /**
     * @return the list of customer invoices in this container
     */
    public List<CustomerInvoice> getCustomerInvoices() { return customerInvoices; }

    /**
     * @param customerInvoices the list of customer invoices to set
     */
    public void setCustomerInvoices(List<CustomerInvoice> customerInvoices) { this.customerInvoices = customerInvoices; }
}
