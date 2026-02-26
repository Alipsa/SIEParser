package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.math.BigInteger;

/**
 * A reference to an external file.
 * Corresponds to {@code FileReferenceType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Points to an external document via a required {@code id} (xsd:positiveInteger)
 * and a required URI string.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FileReference {

    @XmlAttribute(name = "id", required = true)
    private BigInteger id;

    @XmlAttribute(name = "URI", required = true)
    private String uri;

    /**
     * Returns the file reference identifier (xsd:positiveInteger.
     * @return the file reference identifier (xsd:positiveInteger, required)
     */
    public BigInteger getId() { return id; }

    /**
     * Sets the file reference identifier (must be a positive integer).
     * @param id the file reference identifier (must be a positive integer)
     */
    public void setId(BigInteger id) { this.id = id; }

    /**
     * Returns the URI pointing to the external file (required).
     * @return the URI pointing to the external file (required)
     */
    public String getUri() { return uri; }

    /**
     * Sets the URI pointing to the external file.
     * @param uri the URI pointing to the external file
     */
    public void setUri(String uri) { this.uri = uri; }
}
