package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.math.BigInteger;

@XmlAccessorType(XmlAccessType.FIELD)
public class FileReference {

    @XmlAttribute(name = "id", required = true)
    private BigInteger id;

    @XmlAttribute(name = "URI", required = true)
    private String uri;

    public BigInteger getId() { return id; }
    public void setId(BigInteger id) { this.id = id; }

    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}
