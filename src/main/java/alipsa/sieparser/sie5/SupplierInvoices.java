package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for supplier invoices within a full SIE 5 document.
 * Corresponds to {@code SupplierInvoicesType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code BaseSubdividedAccountType}.
 *
 * <p>Groups zero or more {@link SupplierInvoice} elements under a primary account.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierInvoices extends SubdividedAccount {

    /** Creates a new instance. */
    public SupplierInvoices() {}

    @XmlElement(name = "SupplierInvoice")
    private List<SupplierInvoice> supplierInvoices = new ArrayList<>();

    /**
     * Returns the list of supplier invoices in this container.
     * @return the list of supplier invoices in this container
     */
    public List<SupplierInvoice> getSupplierInvoices() { return supplierInvoices; }

    /**
     * Sets the list of supplier invoices to set.
     * @param supplierInvoices the list of supplier invoices to set
     */
    public void setSupplierInvoices(List<SupplierInvoice> supplierInvoices) { this.supplierInvoices = supplierInvoices; }
}
