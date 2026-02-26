package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class AccountingCurrency {

    @XmlAttribute(name = "currency", required = true)
    private String currency;

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}
