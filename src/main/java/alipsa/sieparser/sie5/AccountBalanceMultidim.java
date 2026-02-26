package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Container for thin {@link BaseBalanceMultidim} subclasses that allow JAXB to
 * distinguish {@code <OpeningBalanceMultidim>} from {@code <ClosingBalanceMultidim>}
 * within the {@link Account} choice group.
 *
 * <p>This class is not instantiable. Use the nested {@link Opening} and
 * {@link Closing} classes, which map to the XSD types
 * {@code AccountOpeningBalanceMultidim} and {@code AccountClosingBalanceMultidim}
 * respectively. Multidimensional balances carry dimension object references
 * in addition to the balance amount.</p>
 *
 * @see AccountBalance
 * @see BaseBalanceMultidim
 */
public final class AccountBalanceMultidim {

    private AccountBalanceMultidim() {}

    /**
     * Multidimensional opening balance for an account.
     * Maps to the {@code <OpeningBalanceMultidim>} element within {@code AccountType}.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AccountOpeningBalanceMultidim")
    public static class Opening extends BaseBalanceMultidim {
        /** Creates a new instance. */
        public Opening() {}
    }

    /**
     * Multidimensional closing balance for an account.
     * Maps to the {@code <ClosingBalanceMultidim>} element within {@code AccountType}.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "AccountClosingBalanceMultidim")
    public static class Closing extends BaseBalanceMultidim {
        /** Creates a new instance. */
        public Closing() {}
    }
}
