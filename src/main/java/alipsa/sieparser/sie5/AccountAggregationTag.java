package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a named tag within an {@link AccountAggregation} in the SIE 5 format.
 * Each tag has a required name and contains zero or more {@link AccountRef} elements
 * that reference the accounts belonging to this tag.
 *
 * @see AccountAggregation
 * @see AccountRef
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountAggregationTag {

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "AccountRef")
    private List<AccountRef> accountRefs = new ArrayList<>();


    /** Creates a new instance. */
    public AccountAggregationTag() {}
    /**
     * Returns the tag name.
     * @return the tag name
     */
    public String getName() { return name; }

    /**
     * Sets the tag name (required).
     * @param name the tag name (required)
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the list of account references belonging to this tag.
     * @return the list of account references belonging to this tag
     */
    public List<AccountRef> getAccountRefs() { return accountRefs; }

    /**
     * Sets the list of account references to set.
     * @param accountRefs the list of account references to set
     */
    public void setAccountRefs(List<AccountRef> accountRefs) { this.accountRefs = accountRefs; }
}
