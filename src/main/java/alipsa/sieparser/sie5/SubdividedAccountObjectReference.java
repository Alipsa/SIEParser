package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Represents a reference to a subdivided account object in the SIE 5 format,
 * corresponding to the XSD type {@code SubdividedAccountObjectReferenceType}.
 * Unlike {@link ObjectReference}, this type carries only an object id without
 * a dimension id, as the dimension is implied by the subdivided account context.
 *
 * @see ObjectReference
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SubdividedAccountObjectReference {

    @XmlAttribute(name = "objectId", required = true)
    private String objectId;


    /** Creates a new instance. */
    public SubdividedAccountObjectReference() {}
    /**
     * Returns the object identifier within the subdivided account.
     * @return the object identifier within the subdivided account
     */
    public String getObjectId() { return objectId; }

    /**
     * Sets the object identifier (required).
     * @param objectId the object identifier (required)
     */
    public void setObjectId(String objectId) { this.objectId = objectId; }
}
