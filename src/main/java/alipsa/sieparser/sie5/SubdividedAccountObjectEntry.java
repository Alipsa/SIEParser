package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Base for entry-variant subdivided account objects.
 * Maps to SubdividedAccountObjectTypeEntry in the XSD.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SubdividedAccountObjectEntry {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
