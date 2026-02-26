package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectReference {

    @XmlAttribute(name = "dimId", required = true)
    private String dimId;

    @XmlAttribute(name = "objectId", required = true)
    private String objectId;

    public String getDimId() { return dimId; }
    public void setDimId(String dimId) { this.dimId = dimId; }

    public String getObjectId() { return objectId; }
    public void setObjectId(String objectId) { this.objectId = objectId; }
}
