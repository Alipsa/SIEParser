package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum AccountTypeValue {

    @XmlEnumValue("asset") ASSET,
    @XmlEnumValue("liability") LIABILITY,
    @XmlEnumValue("equity") EQUITY,
    @XmlEnumValue("cost") COST,
    @XmlEnumValue("income") INCOME,
    @XmlEnumValue("statistics") STATISTICS;
}
