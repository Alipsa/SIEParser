package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Represents a reference to a dimension object in the SIE 5 format, corresponding to
 * the XSD type {@code ObjectReferenceType}. Identifies a specific object within a
 * dimension by its dimension id and object id.
 *
 * @see Dimension
 * @see DimensionObject
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ObjectReference {

    @XmlAttribute(name = "dimId", required = true)
    private String dimId;

    @XmlAttribute(name = "objectId", required = true)
    private String objectId;


    /** Creates a new instance. */
    public ObjectReference() {}
    /**
     * Returns the dimension identifier.
     * @return the dimension identifier
     */
    public String getDimId() { return dimId; }

    /**
     * Sets the dimension identifier (required).
     * @param dimId the dimension identifier (required)
     */
    public void setDimId(String dimId) { this.dimId = dimId; }

    /**
     * Returns the object identifier within the dimension.
     * @return the object identifier within the dimension
     */
    public String getObjectId() { return objectId; }

    /**
     * Sets the object identifier within the dimension (required).
     * @param objectId the object identifier within the dimension (required)
     */
    public void setObjectId(String objectId) { this.objectId = objectId; }
}
