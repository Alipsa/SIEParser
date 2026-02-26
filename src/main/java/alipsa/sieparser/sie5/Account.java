package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

/**
 * An account in a full SIE 5 document.
 * Maps to the XSD complex type {@code AccountType}.
 *
 * <p>Each account has a required {@code id}, {@code name}, and {@code type}.
 * It may contain a mixed list of opening balances, closing balances, and
 * budgets in both single-dimension and multidimensional variants. Use the
 * convenience methods {@link #getOpeningBalances()}, {@link #getClosingBalances()},
 * and {@link #getBudgets()} to filter by type.</p>
 *
 * @see AccountEntry
 * @see AccountTypeValue
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Account {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlAttribute(name = "type", required = true)
    private AccountTypeValue type;

    @XmlAttribute(name = "unit")
    private String unit;

    @XmlElements({
        @XmlElement(name = "OpeningBalance", type = AccountBalance.Opening.class),
        @XmlElement(name = "ClosingBalance", type = AccountBalance.Closing.class),
        @XmlElement(name = "Budget", type = Budget.class),
        @XmlElement(name = "OpeningBalanceMultidim", type = AccountBalanceMultidim.Opening.class),
        @XmlElement(name = "ClosingBalanceMultidim", type = AccountBalanceMultidim.Closing.class),
        @XmlElement(name = "BudgetMultidim", type = BudgetMultidim.class)
    })
    private List<Object> balancesAndBudgets = new ArrayList<>();


    /** Creates a new instance. */
    public Account() {}
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
     * Returns the raw mixed list of balances and budgets as unmarshalled by JAXB.
     * Elements may be instances of {@link AccountBalance.Opening},
     * {@link AccountBalance.Closing}, {@link Budget},
     * {@link AccountBalanceMultidim.Opening}, {@link AccountBalanceMultidim.Closing},
     * or {@link BudgetMultidim}.
     *
     * @return the balances and budgets list
     */
    public List<Object> getBalancesAndBudgets() { return balancesAndBudgets; }

    /**
     * Sets the raw mixed list of balances and budgets.
     *
     * @param balancesAndBudgets the balances and budgets list
     */
    public void setBalancesAndBudgets(List<Object> balancesAndBudgets) { this.balancesAndBudgets = balancesAndBudgets; }

    /**
     * Returns only the opening balances from the mixed balances/budgets list.
     *
     * @return an unmodifiable list of opening balance entries
     */
    public List<BaseBalance> getOpeningBalances() {
        return balancesAndBudgets.stream()
                .filter(o -> o instanceof AccountBalance.Opening)
                .map(o -> (BaseBalance) o)
                .toList();
    }

    /**
     * Returns only the closing balances from the mixed balances/budgets list.
     *
     * @return an unmodifiable list of closing balance entries
     */
    public List<BaseBalance> getClosingBalances() {
        return balancesAndBudgets.stream()
                .filter(o -> o instanceof AccountBalance.Closing)
                .map(o -> (BaseBalance) o)
                .toList();
    }

    /**
     * Returns only the budget entries from the mixed balances/budgets list.
     *
     * @return an unmodifiable list of budget entries
     */
    public List<Budget> getBudgets() {
        return balancesAndBudgets.stream()
                .filter(o -> o instanceof Budget)
                .map(o -> (Budget) o)
                .toList();
    }
}
