package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class CompanyEntry {

    @XmlAttribute(name = "organizationId", required = true)
    private String organizationId;

    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "clientId")
    private String clientId;

    @XmlAttribute(name = "multiple")
    private Integer multiple;

    public String getOrganizationId() { return organizationId; }
    public void setOrganizationId(String organizationId) { this.organizationId = organizationId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }

    public Integer getMultiple() { return multiple; }
    public void setMultiple(Integer multiple) { this.multiple = multiple; }
}
