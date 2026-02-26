package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * An account in a SIE 5 entry (import) document.
 * Maps to the XSD complex type {@code AccountTypeEntry}.
 *
 * <p>Unlike {@link Account}, entry accounts only support budget data -- opening
 * and closing balances are not included in import documents.</p>
 *
 * @see Account
 * @see AccountTypeValue
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountEntry {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "type", required = true)
    private AccountTypeValue type;

    @XmlAttribute(name = "unit")
    private String unit;

    @XmlElement(name = "Budget")
    private List<BudgetEntry> budgets = new ArrayList<>();


    /** Creates a new instance. */
    public AccountEntry() {}
    /**
     * Returns the account ID (account number).
     *
     * @return the account ID (required)
     */
    public String getId() { return id; }

    /**
     * Sets the account ID (account number).
     *
     * @param id the account ID
     */
    public void setId(String id) { this.id = id; }

    /**
     * Returns the account name.
     *
     * @return the account name (required)
     */
    public String getName() { return name; }

    /**
     * Sets the account name.
     *
     * @param name the account name
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the account type (asset, liability, equity, cost, income, or statistics).
     *
     * @return the account type (required)
     */
    public AccountTypeValue getType() { return type; }

    /**
     * Sets the account type.
     *
     * @param type the account type
     */
    public void setType(AccountTypeValue type) { this.type = type; }

    /**
     * Returns the unit for statistics accounts (e.g. "hours", "pieces").
     *
     * @return the unit, or {@code null} if not set
     */
    public String getUnit() { return unit; }

    /**
     * Sets the unit for statistics accounts.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) { this.unit = unit; }

    /**
     * Returns the list of budget entries for this account.
     *
     * @return the budgets list
     */
    public List<BudgetEntry> getBudgets() { return budgets; }

    /**
     * Sets the list of budget entries for this account.
     *
     * @param budgets the budgets list
     */
    public void setBudgets(List<BudgetEntry> budgets) { this.budgets = budgets; }
}
