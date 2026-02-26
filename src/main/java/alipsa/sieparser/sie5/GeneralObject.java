package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

/**
 * Represents a general-purpose object within a subdivided account in full SIE 5 documents.
 * Corresponds to {@code GeneralObjectType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code SubdividedAccountObjectType}.
 *
 * <p>This class adds no extra attributes beyond those inherited from
 * {@link SubdividedAccountObject} (id, name, balances, original amount).
 * It is used for subdivided account objects that are neither customer invoices,
 * supplier invoices, nor fixed assets.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralObject extends SubdividedAccountObject {
}
