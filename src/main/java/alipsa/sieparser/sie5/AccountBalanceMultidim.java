package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Thin subclasses of BaseBalanceMultidim to allow JAXB to distinguish
 * OpeningBalanceMultidim from ClosingBalanceMultidim in the Account choice group.
 */
public final class AccountBalanceMultidim {

    private AccountBalanceMultidim() {}

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AccountOpeningBalanceMultidim")
    public static class Opening extends BaseBalanceMultidim {}

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AccountClosingBalanceMultidim")
    public static class Closing extends BaseBalanceMultidim {}
}
