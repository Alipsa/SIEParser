package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Identifies the software product that created a SIE 5 file.
 * Maps to the XSD complex type {@code SoftwareProductType}.
 *
 * <p>Both the {@code name} and {@code version} attributes are required.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SoftwareProduct {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "version", required = true)
    private String version;

    /**
     * Returns the name of the software product.
     *
     * @return the product name (required)
     */
    public String getName() { return name; }

    /**
     * Sets the name of the software product.
     *
     * @param name the product name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the version of the software product.
     *
     * @return the product version (required)
     */
    public String getVersion() { return version; }

    /**
     * Sets the version of the software product.
     *
     * @param version the product version
     */
    public void setVersion(String version) { this.version = version; }
}
