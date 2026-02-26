package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a dimension definition for full SIE 5 documents, corresponding to the
 * XSD type {@code DimensionType}. A dimension has a required positive integer id, a
 * required name, and contains zero or more {@link DimensionObject} children.
 *
 * @see DimensionObject
 * @see DimensionEntry
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Dimension {

    @XmlAttribute(name = "id", required = true)
    private int id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "Object")
    private List<DimensionObject> objects = new ArrayList<>();

    /**
     * Returns the dimension id (positive integer).
     * @return the dimension id (positive integer)
     */
    public int getId() { return id; }

    /**
     * Sets the dimension id (positive integer.
     * @param id the dimension id (positive integer, required)
     */
    public void setId(int id) { this.id = id; }

    /**
     * Returns the dimension name.
     * @return the dimension name
     */
    public String getName() { return name; }

    /**
     * Sets the dimension name (required).
     * @param name the dimension name (required)
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
