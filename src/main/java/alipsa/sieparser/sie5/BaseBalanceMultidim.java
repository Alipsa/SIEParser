package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a multidimensional balance record in the SIE 5 format, corresponding to
 * the XSD type {@code BaseBalanceMultidimType}. Similar to {@link BaseBalance} but
 * requires two or more {@link ObjectReference} elements to identify the
 * multidimensional position.
 *
 * @see BaseBalance
 * @see ObjectReference
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseBalanceMultidim {

    @XmlAttribute(name = "month", required = true)
    @XmlSchemaType(name = "gYearMonth")
    private YearMonth month;

    @XmlAttribute(name = "amount", required = true)
    private BigDecimal amount;

    @XmlAttribute(name = "quantity")
    private BigDecimal quantity;

    @XmlElement(name = "ForeignCurrencyAmount")
    private ForeignCurrencyAmount foreignCurrencyAmount;

    @XmlElement(name = "ObjectReference")
    private List<ObjectReference> objectReferences = new ArrayList<>();

    /**
     * @return the balance month as a {@code gYearMonth} value
     */
    public YearMonth getMonth() { return month; }

    /**
     * @param month the balance month (required)
     */
    public void setMonth(YearMonth month) { this.month = month; }

    /**
     * @return the balance amount
     */
    public BigDecimal getAmount() { return amount; }

    /**
     * @param amount the balance amount (required)
     */
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    /**
     * @return the optional quantity associated with this balance, or {@code null}
     */
    public BigDecimal getQuantity() { return quantity; }

    /**
     * @param quantity the quantity to set, or {@code null} to omit
     */
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    /**
     * @return the optional foreign currency amount, or {@code null}
     */
    public ForeignCurrencyAmount getForeignCurrencyAmount() { return foreignCurrencyAmount; }

    /**
     * @param foreignCurrencyAmount the foreign currency amount to set, or {@code null} to omit
     */
    public void setForeignCurrencyAmount(ForeignCurrencyAmount foreignCurrencyAmount) { this.foreignCurrencyAmount = foreignCurrencyAmount; }

    /**
     * @return the list of object references (must contain 2 or more elements)
     */
    public List<ObjectReference> getObjectReferences() { return objectReferences; }

    /**
     * @param objectReferences the list of object references (must contain 2 or more elements)
     */
    public void setObjectReferences(List<ObjectReference> objectReferences) { this.objectReferences = objectReferences; }
}
