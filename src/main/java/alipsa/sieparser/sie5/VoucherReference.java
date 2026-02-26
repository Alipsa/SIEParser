package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.math.BigInteger;

/**
 * A reference to a voucher or supporting document.
 * Corresponds to {@code VoucherReferenceType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Links a journal entry to a document (embedded file or file reference)
 * via a required {@code documentId} (xsd:positiveInteger).</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class VoucherReference {

    @XmlAttribute(name = "documentId", required = true)
    private BigInteger documentId;


    /** Creates a new instance. */
    public VoucherReference() {}
    /**
     * Returns the document identifier (xsd:positiveInteger.
     * @return the document identifier (xsd:positiveInteger, required)
     */
    public BigInteger getDocumentId() { return documentId; }

    /**
     * Sets the document identifier (must be a positive integer).
     * @param documentId the document identifier (must be a positive integer)
     */
    public void setDocumentId(BigInteger documentId) { this.documentId = documentId; }
}
