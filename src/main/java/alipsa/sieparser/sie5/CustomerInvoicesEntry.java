package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerInvoicesEntry extends SubdividedAccountEntry {

    @XmlElement(name = "CustomerInvoice")
    private List<CustomerInvoiceEntry> customerInvoices = new ArrayList<>();

    public List<CustomerInvoiceEntry> getCustomerInvoices() { return customerInvoices; }
    public void setCustomerInvoices(List<CustomerInvoiceEntry> customerInvoices) { this.customerInvoices = customerInvoices; }
}
