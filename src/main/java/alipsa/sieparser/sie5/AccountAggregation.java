package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTaxonomy() { return taxonomy; }
    public void setTaxonomy(String taxonomy) { this.taxonomy = taxonomy; }

    public List<AccountAggregationTag> getTags() { return tags; }
    public void setTags(List<AccountAggregationTag> tags) { this.tags = tags; }
}
