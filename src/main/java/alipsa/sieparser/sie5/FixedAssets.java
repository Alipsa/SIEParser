package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for fixed assets within a full SIE 5 document.
 * Corresponds to {@code FixedAssetsType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code BaseSubdividedAccountType}.
 *
 * <p>Groups zero or more {@link FixedAsset} elements under a primary account.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FixedAssets extends SubdividedAccount {

    @XmlElement(name = "FixedAsset")
    private List<FixedAsset> fixedAssets = new ArrayList<>();

    /**
     * Returns the list of fixed assets in this container.
     * @return the list of fixed assets in this container
     */
    public List<FixedAsset> getFixedAssets() { return fixedAssets; }

    /**
     * Sets the list of fixed assets to set.
     * @param fixedAssets the list of fixed assets to set
     */
    public void setFixedAssets(List<FixedAsset> fixedAssets) { this.fixedAssets = fixedAssets; }
}
