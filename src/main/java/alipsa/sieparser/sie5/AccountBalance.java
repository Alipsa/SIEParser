package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Thin subclasses of BaseBalance to allow JAXB to distinguish
 * OpeningBalance from ClosingBalance in the Account choice group.
 */
public final class AccountBalance {

    private AccountBalance() {}

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AccountOpeningBalance")
    public static class Opening extends BaseBalance {}

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AccountClosingBalance")
    public static class Closing extends BaseBalance {}
}
