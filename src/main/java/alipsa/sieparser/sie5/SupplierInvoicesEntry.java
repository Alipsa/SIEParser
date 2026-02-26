package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierInvoicesEntry extends SubdividedAccountEntry {

    @XmlElement(name = "SupplierInvoice")
    private List<SupplierInvoiceEntry> supplierInvoices = new ArrayList<>();

    public List<SupplierInvoiceEntry> getSupplierInvoices() { return supplierInvoices; }
    public void setSupplierInvoices(List<SupplierInvoiceEntry> supplierInvoices) { this.supplierInvoices = supplierInvoices; }
}
