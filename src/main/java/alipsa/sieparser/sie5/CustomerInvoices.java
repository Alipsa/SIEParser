package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerInvoices extends SubdividedAccount {

    @XmlElement(name = "CustomerInvoice")
    private List<CustomerInvoice> customerInvoices = new ArrayList<>();

    public List<CustomerInvoice> getCustomerInvoices() { return customerInvoices; }
    public void setCustomerInvoices(List<CustomerInvoice> customerInvoices) { this.customerInvoices = customerInvoices; }
}
