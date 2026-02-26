package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.math.BigDecimal;
import java.time.YearMonth;

@XmlAccessorType(XmlAccessType.FIELD)
public class BaseBalance {

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
    private ObjectReference objectReference;

    public YearMonth getMonth() { return month; }
    public void setMonth(YearMonth month) { this.month = month; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public ForeignCurrencyAmount getForeignCurrencyAmount() { return foreignCurrencyAmount; }
    public void setForeignCurrencyAmount(ForeignCurrencyAmount foreignCurrencyAmount) { this.foreignCurrencyAmount = foreignCurrencyAmount; }

    public ObjectReference getObjectReference() { return objectReference; }
    public void setObjectReference(ObjectReference objectReference) { this.objectReference = objectReference; }
}
