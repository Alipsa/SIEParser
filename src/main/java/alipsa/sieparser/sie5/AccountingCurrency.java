package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

/**
 * Specifies the default accounting currency for a SIE 5 document.
 * Maps to the XSD complex type {@code AccountingCurrencyType}.
 *
 * <p>The {@code currency} attribute holds an ISO 4217 three-letter currency
 * code (e.g. "SEK", "EUR", "USD").</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountingCurrency {

    @XmlAttribute(name = "currency", required = true)
    private String currency;


    /** Creates a new instance. */
    public AccountingCurrency() {}
    /**
     * Returns the ISO 4217 currency code.
     *
     * @return the three-letter currency code (required)
     */
    public String getCurrency() { return currency; }

    /**
     * Sets the ISO 4217 currency code.
     *
     * @param currency the three-letter currency code (e.g. "SEK")
     */
    public void setCurrency(String currency) { this.currency = currency; }
}
