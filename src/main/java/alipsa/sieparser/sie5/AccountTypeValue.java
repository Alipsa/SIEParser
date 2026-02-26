package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

/**
 * Enumeration of account types in SIE 5.
 * Maps to the XSD simple type {@code AccountTypeValue}.
 *
 * <p>Possible values are:</p>
 * <ul>
 *   <li>{@link #ASSET} -- asset accounts (Swedish: tillgangar)</li>
 *   <li>{@link #LIABILITY} -- liability accounts (Swedish: skulder)</li>
 *   <li>{@link #EQUITY} -- equity accounts (Swedish: eget kapital)</li>
 *   <li>{@link #COST} -- cost/expense accounts (Swedish: kostnader)</li>
 *   <li>{@link #INCOME} -- income/revenue accounts (Swedish: intakter)</li>
 *   <li>{@link #STATISTICS} -- statistical accounts (non-monetary)</li>
 * </ul>
 */
@XmlEnum
public enum AccountTypeValue {

    /** Asset account. */
    @XmlEnumValue("asset") ASSET,

    /** Liability account. */
    @XmlEnumValue("liability") LIABILITY,

    /** Equity account. */
    @XmlEnumValue("equity") EQUITY,

    /** Cost/expense account. */
    @XmlEnumValue("cost") COST,

    /** Income/revenue account. */
    @XmlEnumValue("income") INCOME,

    /** Statistical (non-monetary) account. */
    @XmlEnumValue("statistics") STATISTICS;
}
