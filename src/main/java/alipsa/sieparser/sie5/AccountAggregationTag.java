package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountAggregationTag {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "AccountRef")
    private List<AccountRef> accountRefs = new ArrayList<>();

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<AccountRef> getAccountRefs() { return accountRefs; }
    public void setAccountRefs(List<AccountRef> accountRefs) { this.accountRefs = accountRefs; }
}
