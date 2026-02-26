package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierInvoices extends SubdividedAccount {

    @XmlElement(name = "SupplierInvoice")
    private List<SupplierInvoice> supplierInvoices = new ArrayList<>();

    public List<SupplierInvoice> getSupplierInvoices() { return supplierInvoices; }
    public void setSupplierInvoices(List<SupplierInvoice> supplierInvoices) { this.supplierInvoices = supplierInvoices; }
}
