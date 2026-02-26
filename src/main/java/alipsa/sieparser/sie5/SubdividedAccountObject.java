package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for subdivided account objects (CustomerInvoice, SupplierInvoice, FixedAsset, GeneralObject).
 * Maps to SubdividedAccountObjectType in the XSD.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SubdividedAccountObject {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "Balances")
    private List<Balances> balances = new ArrayList<>();

    @XmlElement(name = "OriginalAmount", required = true)
    private OriginalAmount originalAmount;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Balances> getBalances() { return balances; }
    public void setBalances(List<Balances> balances) { this.balances = balances; }

    public OriginalAmount getOriginalAmount() { return originalAmount; }
    public void setOriginalAmount(OriginalAmount originalAmount) { this.originalAmount = originalAmount; }
}
