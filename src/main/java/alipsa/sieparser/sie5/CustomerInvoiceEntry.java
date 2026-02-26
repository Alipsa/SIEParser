package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.LocalDate;

/**
 * Represents a customer invoice within a subdivided account in SIE 5 entry documents.
 * Corresponds to the entry-variant of {@code CustomerInvoiceType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), extending
 * {@link SubdividedAccountObjectEntry}.
 *
 * <p>Carries the same customer-specific attributes as {@link CustomerInvoice}
 * but without balance or original-amount data.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerInvoiceEntry extends SubdividedAccountObjectEntry {

    @XmlAttribute(name = "customerId", required = true)
    private String customerId;

    @XmlAttribute(name = "invoiceNumber", required = true)
    private String invoiceNumber;

    @XmlAttribute(name = "ocrNumber")
    private String ocrNumber;

    @XmlAttribute(name = "dueDate")
    @XmlSchemaType(name = "date")
    private LocalDate dueDate;

    /**
     * Returns the customer identifier (required).
     * @return the customer identifier (required)
     */
    public String getCustomerId() { return customerId; }

    /**
     * Sets the customer identifier.
     * @param customerId the customer identifier
     */
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    /**
     * Returns the invoice number (required).
     * @return the invoice number (required)
     */
    public String getInvoiceNumber() { return invoiceNumber; }

    /**
     * Sets the invoice number.
     * @param invoiceNumber the invoice number
     */
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    /**
     * Returns the OCR number (Swedish payment reference).
     * @return the OCR number (Swedish payment reference), or {@code null} if not set
     */
    public String getOcrNumber() { return ocrNumber; }

    /**
     * Sets the OCR number.
     * @param ocrNumber the OCR number
     */
    public void setOcrNumber(String ocrNumber) { this.ocrNumber = ocrNumber; }

    /**
     * Returns the invoice due date.
     * @return the invoice due date, or {@code null} if not set
     */
    public LocalDate getDueDate() { return dueDate; }

    /**
     * Sets the invoice due date.
     * @param dueDate the invoice due date
     */
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}
