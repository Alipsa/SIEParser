package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import java.math.BigDecimal;

/**
 * Represents a foreign currency amount in the SIE 5 format, corresponding to the
 * XSD type {@code ForeignCurrencyAmountType}. Contains a required amount expressed
 * in the foreign currency and a required 3-letter ISO 4217 currency code.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ForeignCurrencyAmount {

    @XmlAttribute(name = "amount", required = true)
    private BigDecimal amount;

    @XmlAttribute(name = "currency", required = true)
    private String currency;

    /**
     * Returns the amount in the foreign currency.
     * @return the amount in the foreign currency
     */
    public BigDecimal getAmount() { return amount; }

    /**
     * Sets the amount in the foreign currency (required).
     * @param amount the amount in the foreign currency (required)
     */
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    /**
     * Returns the 3-letter ISO 4217 currency code.
     * @return the 3-letter ISO 4217 currency code
     */
    public String getCurrency() { return currency; }

    /**
     * Sets the 3-letter ISO 4217 currency code (required).
     * @param currency the 3-letter ISO 4217 currency code (required)
     */
    public void setCurrency(String currency) { this.currency = currency; }
}
