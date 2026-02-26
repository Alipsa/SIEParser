package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dimension definition for SIE 5 entry documents, corresponding to the
 * XSD type {@code DimensionTypeEntry}. Unlike {@link Dimension}, the id is a string
 * and the name is optional. Contains zero or more {@link DimensionObject} children.
 *
 * @see Dimension
 * @see DimensionObject
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DimensionEntry {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "Object")
    private List<DimensionObject> objects = new ArrayList<>();

    /**
     * Returns the dimension id as a string.
     * @return the dimension id as a string
     */
    public String getId() { return id; }

    /**
     * Sets the dimension id (required).
     * @param id the dimension id (required)
     */
    public void setId(String id) { this.id = id; }

    /**
     * Returns the optional dimension name.
     * @return the optional dimension name, or {@code null}
     */
    public String getName() { return name; }

    /**
     * Sets the dimension name.
     * @param name the dimension name, or {@code null} to omit
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the list of objects belonging to this dimension.
     * @return the list of objects belonging to this dimension
     */
    public List<DimensionObject> getObjects() { return objects; }

    /**
     * Sets the list of objects to set for this dimension.
     * @param objects the list of objects to set for this dimension
     */
    public void setObjects(List<DimensionObject> objects) { this.objects = objects; }
}
