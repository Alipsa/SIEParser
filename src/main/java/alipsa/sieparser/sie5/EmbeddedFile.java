package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import java.math.BigInteger;

/**
 * An embedded file with base64-encoded content.
 * Corresponds to {@code EmbeddedFileType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Represents a file embedded directly in the SIE 5 XML, identified by a required
 * {@code id} (xsd:positiveInteger) and {@code fileName}. The file content is stored
 * as a base64-encoded byte array.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class EmbeddedFile {

    @XmlAttribute(name = "id", required = true)
    private BigInteger id;

    @XmlAttribute(name = "fileName", required = true)
    private String fileName;

    @XmlValue
    private byte[] content;

    /**
     * Returns the file identifier (xsd:positiveInteger.
     * @return the file identifier (xsd:positiveInteger, required)
     */
    public BigInteger getId() { return id; }

    /**
     * Sets the file identifier (must be a positive integer).
     * @param id the file identifier (must be a positive integer)
     */
    public void setId(BigInteger id) { this.id = id; }

    /**
     * Returns the file name (required).
     * @return the file name (required)
     */
    public String getFileName() { return fileName; }

    /**
     * Sets the file name.
     * @param fileName the file name
     */
    public void setFileName(String fileName) { this.fileName = fileName; }

    /**
     * Returns the base64-decoded file content as a byte array.
     * @return the base64-decoded file content as a byte array
     */
    public byte[] getContent() { return content; }

    /**
     * Sets the file content as a byte array (will be base64-encoded in XML).
     * @param content the file content as a byte array (will be base64-encoded in XML)
     */
    public void setContent(byte[] content) { this.content = content; }
}
