package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Base class for entry-variant subdivided account containers.
 * Maps to BaseSubdividedAccountTypeEntry in the XSD.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class SubdividedAccountEntry {

    @XmlAttribute(name = "primaryAccountId", required = true)
    private String primaryAccountId;

    @XmlAttribute(name = "name")
    private String name;

    public String getPrimaryAccountId() { return primaryAccountId; }
    public void setPrimaryAccountId(String primaryAccountId) { this.primaryAccountId = primaryAccountId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
