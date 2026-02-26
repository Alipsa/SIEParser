package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * Represents a fixed asset within a subdivided account in SIE 5 entry documents.
 * Corresponds to the entry-variant of {@code FixedAssetType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), extending
 * {@link SubdividedAccountObjectEntry}.
 *
 * <p>This class adds no extra attributes beyond those inherited from
 * {@link SubdividedAccountObjectEntry} (id and name only).</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FixedAssetEntry extends SubdividedAccountObjectEntry {

    /** Creates a new instance. */
    public FixedAssetEntry() {}
}
