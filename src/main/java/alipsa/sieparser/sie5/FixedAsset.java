package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * Represents a fixed asset within a subdivided account in full SIE 5 documents.
 * Corresponds to {@code FixedAssetType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code SubdividedAccountObjectType}.
 *
 * <p>This class adds no extra attributes beyond those inherited from
 * {@link SubdividedAccountObject} (id, name, balances, original amount).</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FixedAsset extends SubdividedAccountObject {
}
