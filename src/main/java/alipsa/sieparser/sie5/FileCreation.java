package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.OffsetDateTime;

/**
 * Records when and by whom a SIE 5 file was created.
 * Maps to the XSD complex type {@code FileCreationType}.
 *
 * <p>Both the {@code time} (xsd:dateTime) and {@code by} attributes are required.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FileCreation {

    @XmlAttribute(name = "time", required = true)
    @XmlSchemaType(name = "dateTime")
    private OffsetDateTime time;

    @XmlAttribute(name = "by", required = true)
    private String by;


    /** Creates a new instance. */
    public FileCreation() {}
    /**
     * Returns the date and time when the file was created.
     *
     * @return the creation timestamp (required, xsd:dateTime)
     */
    public OffsetDateTime getTime() { return time; }

    /**
     * Sets the date and time when the file was created.
     *
     * @param time the creation timestamp
     */
    public void setTime(OffsetDateTime time) { this.time = time; }

    /**
     * Returns the name or identifier of the person or system that created the file.
     *
     * @return the creator identifier (required)
     */
    public String getBy() { return by; }

    /**
     * Sets the name or identifier of the person or system that created the file.
     *
     * @param by the creator identifier
     */
    public void setBy(String by) { this.by = by; }
}
