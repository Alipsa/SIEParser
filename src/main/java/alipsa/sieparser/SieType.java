package alipsa.sieparser;

import java.util.EnumSet;

/**
 * Enumeration of SIE file format types.
 */
public enum SieType {
    /** SIE type 1: Year-end balances. */
    TYPE_1(1),
    /** SIE type 2: Period balances. */
    TYPE_2(2),
    /** SIE type 3: Object balances. */
    TYPE_3(3),
    /** SIE type 4: Transactions. */
    TYPE_4(4);

    private final int value;

    SieType(int value) {
        this.value = value;
    }

    /**
     * Returns the numeric value of this SIE type.
     * @return the SIE type number (1-4)
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the SieType corresponding to the given numeric value.
     * @param value the numeric SIE type (1-4)
     * @return the matching SieType
     * @throws IllegalArgumentException if no matching type exists
     */
    public static SieType fromValue(int value) {
        for (SieType t : values()) {
            if (t.value == value) return t;
        }
        throw new IllegalArgumentException("Unknown SIE type: " + value);
    }

    /**
     * Returns an EnumSet containing all SIE types.
     * @return all SIE types
     */
    public static EnumSet<SieType> all() {
        return EnumSet.allOf(SieType.class);
    }
}
