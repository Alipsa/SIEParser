package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.LocalDate;

/**
 * Represents a customer invoice within a subdivided account in full SIE 5 documents.
 * Corresponds to {@code CustomerInvoiceType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code SubdividedAccountObjectType}.
 *
 * <p>Adds customer-specific attributes such as the customer identifier,
 * invoice number, OCR number, and due date.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerInvoice extends SubdividedAccountObject {

    @XmlAttribute(name = "customerId", required = true)
    private String customerId;

    @XmlAttribute(name = "invoiceNumber")
    private String invoiceNumber;

    @XmlAttribute(name = "ocrNumber")
    private String ocrNumber;

    @XmlAttribute(name = "dueDate")
    @XmlSchemaType(name = "date")
    private LocalDate dueDate;

    /**
     * @return the customer identifier (required)
     */
    public String getCustomerId() { return customerId; }

    /**
     * @param customerId the customer identifier
     */
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    /**
     * @return the invoice number, or {@code null} if not set
     */
    public String getInvoiceNumber() { return invoiceNumber; }

    /**
     * @param invoiceNumber the invoice number
     */
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    /**
     * @return the OCR number (Swedish payment reference), or {@code null} if not set
     */
    public String getOcrNumber() { return ocrNumber; }

    /**
     * @param ocrNumber the OCR number
     */
    public void setOcrNumber(String ocrNumber) { this.ocrNumber = ocrNumber; }

    /**
     * @return the invoice due date, or {@code null} if not set
     */
    public LocalDate getDueDate() { return dueDate; }

    /**
     * @param dueDate the invoice due date
     */
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}
