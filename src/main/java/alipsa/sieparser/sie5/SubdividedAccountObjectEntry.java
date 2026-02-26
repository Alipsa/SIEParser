package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Base class for objects within a subdivided account in SIE 5 entry documents.
 * Corresponds to {@code SubdividedAccountObjectTypeEntry} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Unlike {@link SubdividedAccountObject}, this entry variant carries only
 * an identifier and an optional name -- no {@code Balances} or {@code OriginalAmount}.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SubdividedAccountObjectEntry {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    /**
     * @return the unique object identifier (required)
     */
    public String getId() { return id; }

    /**
     * @param id the unique object identifier
     */
    public void setId(String id) { this.id = id; }

    /**
     * @return the optional descriptive name of this object
     */
    public String getName() { return name; }

    /**
     * @param name the descriptive name
     */
    public void setName(String name) { this.name = name; }
}
