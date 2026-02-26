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
 * Represents a multidimensional budget record in the SIE 5 format, corresponding to
 * the XSD type {@code BudgetMultidimType}. Similar to {@link Budget} but requires
 * two or more {@link ObjectReference} elements to identify the multidimensional
 * position. The month attribute is optional; when omitted the budget covers the
 * full fiscal year.
 *
 * @see Budget
 * @see ObjectReference
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BudgetMultidim {

    @XmlAttribute(name = "month")
    @XmlSchemaType(name = "gYearMonth")
    private YearMonth month;

    @XmlAttribute(name = "amount", required = true)
    private BigDecimal amount;

    @XmlAttribute(name = "quantity")
    private BigDecimal quantity;

    @XmlElement(name = "ObjectReference")
    private List<ObjectReference> objectReferences = new ArrayList<>();


    /** Creates a new instance. */
    public BudgetMultidim() {}
    /**
     * Returns the budget month.
     * @return the budget month, or {@code null} if the budget covers the full fiscal year
     */
    public YearMonth getMonth() { return month; }

    /**
     * Sets the budget month.
     * @param month the budget month, or {@code null} for the full fiscal year
     */
    public void setMonth(YearMonth month) { this.month = month; }

    /**
     * Returns the budget amount.
     * @return the budget amount
     */
    public BigDecimal getAmount() { return amount; }

    /**
     * Sets the budget amount (required).
     * @param amount the budget amount (required)
     */
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    /**
     * Returns the optional quantity.
     * @return the optional quantity, or {@code null}
     */
    public BigDecimal getQuantity() { return quantity; }

    /**
     * Sets the quantity to set.
     * @param quantity the quantity to set, or {@code null} to omit
     */
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    /**
     * Returns the list of object references (must contain 2 or more elements).
     * @return the list of object references (must contain 2 or more elements)
     */
    public List<ObjectReference> getObjectReferences() { return objectReferences; }

    /**
     * Sets the list of object references (must contain 2 or more elements).
     * @param objectReferences the list of object references (must contain 2 or more elements)
     */
    public void setObjectReferences(List<ObjectReference> objectReferences) { this.objectReferences = objectReferences; }
}
