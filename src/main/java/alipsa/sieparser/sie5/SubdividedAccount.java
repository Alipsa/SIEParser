package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for subdivided account containers.
 * Maps to BaseSubdividedAccountType in the XSD.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class SubdividedAccount {

    @XmlAttribute(name = "primaryAccountId", required = true)
    private String primaryAccountId;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "SecondaryAccountRef")
    private List<SecondaryAccountRef> secondaryAccountRefs = new ArrayList<>();

    public String getPrimaryAccountId() { return primaryAccountId; }
    public void setPrimaryAccountId(String primaryAccountId) { this.primaryAccountId = primaryAccountId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<SecondaryAccountRef> getSecondaryAccountRefs() { return secondaryAccountRefs; }
    public void setSecondaryAccountRefs(List<SecondaryAccountRef> secondaryAccountRefs) { this.secondaryAccountRefs = secondaryAccountRefs; }
}
