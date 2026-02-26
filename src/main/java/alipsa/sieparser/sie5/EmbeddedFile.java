package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlValue;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
public class EmbeddedFile {

    @XmlAttribute(name = "id", required = true)
    private BigInteger id;

    @XmlAttribute(name = "fileName", required = true)
    private String fileName;

    @XmlValue
    private byte[] content;

    public BigInteger getId() { return id; }
    public void setId(BigInteger id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public byte[] getContent() { return content; }
    public void setContent(byte[] content) { this.content = content; }
}
