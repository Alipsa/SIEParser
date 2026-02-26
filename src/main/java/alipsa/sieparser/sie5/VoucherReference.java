package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
public class VoucherReference {

    @XmlAttribute(name = "documentId", required = true)
    private BigInteger documentId;

    public BigInteger getDocumentId() { return documentId; }
    public void setDocumentId(BigInteger documentId) { this.documentId = documentId; }
}
