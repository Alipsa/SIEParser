package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Represents a reference to an account within an {@link AccountAggregationTag}
 * in the SIE 5 format. Contains a required account identifier of the
 * {@code AccountNumber} type.
 *
 * @see AccountAggregationTag
 * @see AccountAggregation
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountRef {

    @XmlAttribute(name = "accountId", required = true)
    private String accountId;

    /**
     * @return the referenced account identifier
     */
    public String getAccountId() { return accountId; }

    /**
     * @param accountId the account identifier to reference (required)
     */
    public void setAccountId(String accountId) { this.accountId = accountId; }
}
