package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Balances {

    @XmlAttribute(name = "accountId")
    private String accountId;

    @XmlElements({
        @XmlElement(name = "OpeningBalance", type = AccountBalance.Opening.class),
        @XmlElement(name = "ClosingBalance", type = AccountBalance.Closing.class)
    })
    private List<BaseBalance> balances = new ArrayList<>();

    public String getAccountId() { return accountId; }
    public void setAccountId(String accountId) { this.accountId = accountId; }

    public List<BaseBalance> getBalances() { return balances; }
    public void setBalances(List<BaseBalance> balances) { this.balances = balances; }

    public List<BaseBalance> getOpeningBalances() {
        return balances.stream()
                .filter(b -> b instanceof AccountBalance.Opening)
                .toList();
    }

    public List<BaseBalance> getClosingBalances() {
        return balances.stream()
                .filter(b -> b instanceof AccountBalance.Closing)
                .toList();
    }
}
