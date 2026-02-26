package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

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

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public AccountTypeValue getType() { return type; }
    public void setType(AccountTypeValue type) { this.type = type; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public List<Object> getBalancesAndBudgets() { return balancesAndBudgets; }
    public void setBalancesAndBudgets(List<Object> balancesAndBudgets) { this.balancesAndBudgets = balancesAndBudgets; }

    public List<BaseBalance> getOpeningBalances() {
        return balancesAndBudgets.stream()
                .filter(o -> o instanceof AccountBalance.Opening)
                .map(o -> (BaseBalance) o)
                .toList();
    }

    public List<BaseBalance> getClosingBalances() {
        return balancesAndBudgets.stream()
                .filter(o -> o instanceof AccountBalance.Closing)
                .map(o -> (BaseBalance) o)
                .toList();
    }

    public List<Budget> getBudgets() {
        return balancesAndBudgets.stream()
                .filter(o -> o instanceof Budget)
                .map(o -> (Budget) o)
                .toList();
    }
}
