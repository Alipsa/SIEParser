package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Represents an object within a dimension in the SIE 5 format, corresponding to
 * the XSD type {@code ObjectType}. Each object has a required string id and a
 * required name.
 *
 * @see Dimension
 * @see DimensionEntry
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DimensionObject {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;


    /** Creates a new instance. */
    public DimensionObject() {}
    /**
     * Returns the object identifier.
     * @return the object identifier
     */
    public String getId() { return id; }

    /**
     * Sets the object identifier (required).
     * @param id the object identifier (required)
     */
    public void setId(String id) { this.id = id; }

    /**
     * Returns the object name.
     * @return the object name
     */
    public String getName() { return name; }

    /**
     * Sets the object name (required).
     * @param name the object name (required)
     */
    public void setName(String name) { this.name = name; }
}
