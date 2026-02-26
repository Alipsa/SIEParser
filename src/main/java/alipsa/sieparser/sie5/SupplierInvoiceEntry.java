package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class SupplierInvoiceEntry extends SubdividedAccountObjectEntry {

    @XmlAttribute(name = "supplierId", required = true)
    private String supplierId;

    @XmlAttribute(name = "invoiceNumber", required = true)
    private String invoiceNumber;

    @XmlAttribute(name = "ocrNumber")
    private String ocrNumber;

    @XmlAttribute(name = "dueDate")
    @XmlSchemaType(name = "date")
    private LocalDate dueDate;

    public String getSupplierId() { return supplierId; }
    public void setSupplierId(String supplierId) { this.supplierId = supplierId; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getOcrNumber() { return ocrNumber; }
    public void setOcrNumber(String ocrNumber) { this.ocrNumber = ocrNumber; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}
