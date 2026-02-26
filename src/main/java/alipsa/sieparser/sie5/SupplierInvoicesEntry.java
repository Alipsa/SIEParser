package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for supplier invoices within a SIE 5 entry document.
 * Corresponds to {@code SupplierInvoicesTypeEntry} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code BaseSubdividedAccountTypeEntry}.
 *
 * <p>Groups zero or more {@link SupplierInvoiceEntry} elements under a primary account.
 * Unlike {@link SupplierInvoices}, this entry variant has no secondary account references.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierInvoicesEntry extends SubdividedAccountEntry {

    @XmlElement(name = "SupplierInvoice")
    private List<SupplierInvoiceEntry> supplierInvoices = new ArrayList<>();

    /**
     * @return the list of supplier invoice entries in this container
     */
    public List<SupplierInvoiceEntry> getSupplierInvoices() { return supplierInvoices; }

    /**
     * @param supplierInvoices the list of supplier invoice entries to set
     */
    public void setSupplierInvoices(List<SupplierInvoiceEntry> supplierInvoices) { this.supplierInvoices = supplierInvoices; }
}
