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
     * @return the file identifier (xsd:positiveInteger, required)
     */
    public BigInteger getId() { return id; }

    /**
     * @param id the file identifier (must be a positive integer)
     */
    public void setId(BigInteger id) { this.id = id; }

    /**
     * @return the file name (required)
     */
    public String getFileName() { return fileName; }

    /**
     * @param fileName the file name
     */
    public void setFileName(String fileName) { this.fileName = fileName; }

    /**
     * @return the base64-decoded file content as a byte array
     */
    public byte[] getContent() { return content; }

    /**
     * @param content the file content as a byte array (will be base64-encoded in XML)
     */
    public void setContent(byte[] content) { this.content = content; }
}
