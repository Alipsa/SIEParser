package alipsa.sieparser;

import java.util.EnumSet;

/**
 * Enumeration of SIE file format types.
 */
public enum SieType {
    TYPE_1(1),
    TYPE_2(2),
    TYPE_3(3),
    TYPE_4(4);

    private final int value;

    SieType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static SieType fromValue(int value) {
        for (SieType t : values()) {
            if (t.value == value) return t;
        }
        throw new IllegalArgumentException("Unknown SIE type: " + value);
    }

    public static EnumSet<SieType> all() {
        return EnumSet.allOf(SieType.class);
    }
}
