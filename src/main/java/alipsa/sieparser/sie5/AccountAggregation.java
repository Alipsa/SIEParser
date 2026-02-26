package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an account aggregation in the SIE 5 format, corresponding to the
 * XSD type {@code AccountAggregationType}. Groups accounts by named tags and has
 * a required id, a required name, and an optional taxonomy identifier.
 *
 * @see AccountAggregationTag
 * @see AccountRef
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountAggregation {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "taxonomy")
    private String taxonomy;

    @XmlElement(name = "Tag")
    private List<AccountAggregationTag> tags = new ArrayList<>();

    /**
     * @return the aggregation identifier
     */
    public String getId() { return id; }

    /**
     * @param id the aggregation identifier (required)
     */
    public void setId(String id) { this.id = id; }

    /**
     * @return the aggregation name
     */
    public String getName() { return name; }

    /**
     * @param name the aggregation name (required)
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return the optional taxonomy identifier, or {@code null}
     */
    public String getTaxonomy() { return taxonomy; }

    /**
     * @param taxonomy the taxonomy identifier, or {@code null} to omit
     */
    public void setTaxonomy(String taxonomy) { this.taxonomy = taxonomy; }

    /**
     * @return the list of tags grouping accounts within this aggregation
     */
    public List<AccountAggregationTag> getTags() { return tags; }

    /**
     * @param tags the list of tags to set
     */
    public void setTags(List<AccountAggregationTag> tags) { this.tags = tags; }
}
