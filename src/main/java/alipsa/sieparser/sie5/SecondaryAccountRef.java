package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Represents a reference to a secondary account within a subdivided account group
 * in the SIE 5 format. Contains the account identifier of the referenced
 * secondary account.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class SecondaryAccountRef {

    @XmlAttribute(name = "accountId")
    private String accountId;


    /** Creates a new instance. */
    public SecondaryAccountRef() {}
    /**
     * Returns the account identifier of the secondary account.
     * @return the account identifier of the secondary account
     */
    public String getAccountId() { return accountId; }

    /**
     * Sets the account identifier of the secondary account.
     * @param accountId the account identifier of the secondary account
     */
    public void setAccountId(String accountId) { this.accountId = accountId; }
}
