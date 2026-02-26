package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Container for thin {@link BaseBalance} subclasses that allow JAXB to
 * distinguish {@code <OpeningBalance>} from {@code <ClosingBalance>}
 * within the {@link Account} choice group.
 *
 * <p>This class is not instantiable. Use the nested {@link Opening} and
 * {@link Closing} classes, which map to the XSD types
 * {@code AccountOpeningBalance} and {@code AccountClosingBalance}
 * respectively.</p>
 *
 * @see AccountBalanceMultidim
 * @see BaseBalance
 */
public final class AccountBalance {

    private AccountBalance() {}

    /**
     * Opening balance for an account.
     * Maps to the {@code <OpeningBalance>} element within {@code AccountType}.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AccountOpeningBalance")
    public static class Opening extends BaseBalance {
        /** Creates a new instance. */
        public Opening() {}
    }

    /**
     * Closing balance for an account.
     * Maps to the {@code <ClosingBalance>} element within {@code AccountType}.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AccountClosingBalance")
    public static class Closing extends BaseBalance {
        /** Creates a new instance. */
        public Closing() {}
    }
}
