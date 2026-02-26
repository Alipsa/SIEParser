package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for opening and closing balances on subdivided account objects in
 * the SIE 5 format, corresponding to the XSD type {@code BalancesType}.
 * Holds an optional account identifier and a mixed list of
 * {@link AccountBalance.Opening} and {@link AccountBalance.Closing} entries.
 *
 * @see BaseBalance
 * @see AccountBalance
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Balances {

    /** Creates a new instance. */
    public Balances() {}

    @XmlAttribute(name = "accountId")
    private String accountId;

    @XmlElements({
        @XmlElement(name = "OpeningBalance", type = AccountBalance.Opening.class),
        @XmlElement(name = "ClosingBalance", type = AccountBalance.Closing.class)
    })
    private List<BaseBalance> balances = new ArrayList<>();

    /**
     * Returns the optional account identifier.
     * @return the optional account identifier, or {@code null}
     */
    public String getAccountId() { return accountId; }

    /**
     * Sets the account identifier to set.
     * @param accountId the account identifier to set, or {@code null} to omit
     */
    public void setAccountId(String accountId) { this.accountId = accountId; }

    /**
     * Returns the list of all balance entries (both opening and closing).
     * @return the list of all balance entries (both opening and closing)
     */
    public List<BaseBalance> getBalances() { return balances; }

    /**
     * Sets the list of balance entries to set.
     * @param balances the list of balance entries to set
     */
    public void setBalances(List<BaseBalance> balances) { this.balances = balances; }

    /**
     * Returns only the opening balance entries from the balances list.
     *
     * @return an unmodifiable list of opening balances
     */
    public List<BaseBalance> getOpeningBalances() {
        return balances.stream()
                .filter(b -> b instanceof AccountBalance.Opening)
                .toList();
    }

    /**
     * Returns only the closing balance entries from the balances list.
     *
     * @return an unmodifiable list of closing balances
     */
    public List<BaseBalance> getClosingBalances() {
        return balances.stream()
                .filter(b -> b instanceof AccountBalance.Closing)
                .toList();
    }
}
