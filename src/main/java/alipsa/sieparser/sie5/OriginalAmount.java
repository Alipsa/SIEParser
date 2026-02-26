package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Represents an original amount with date in the SIE 5 format, corresponding to the
 * XSD type {@code OriginalAmountType}. Required on subdivided account objects to record
 * the original transaction amount and date. May optionally include a
 * {@link ForeignCurrencyAmount} child element.
 *
 * @see ForeignCurrencyAmount
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OriginalAmount {

    @XmlAttribute(name = "date", required = true)
    @XmlSchemaType(name = "date")
    private LocalDate date;

    @XmlAttribute(name = "amount", required = true)
    private BigDecimal amount;

    @XmlElement(name = "ForeignCurrencyAmount")
    private ForeignCurrencyAmount foreignCurrencyAmount;

    /**
     * Returns the date of the original amount.
     * @return the date of the original amount
     */
    public LocalDate getDate() { return date; }

    /**
     * Sets the date of the original amount (required).
     * @param date the date of the original amount (required)
     */
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Returns the original amount.
     * @return the original amount
     */
    public BigDecimal getAmount() { return amount; }

    /**
     * Sets the original amount (required).
     * @param amount the original amount (required)
     */
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    /**
     * Returns the optional foreign currency amount.
     * @return the optional foreign currency amount, or {@code null}
     */
    public ForeignCurrencyAmount getForeignCurrencyAmount() { return foreignCurrencyAmount; }

    /**
     * Sets the foreign currency amount to set.
     * @param foreignCurrencyAmount the foreign currency amount to set, or {@code null} to omit
     */
    public void setForeignCurrencyAmount(ForeignCurrencyAmount foreignCurrencyAmount) { this.foreignCurrencyAmount = foreignCurrencyAmount; }
}
