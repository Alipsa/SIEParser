package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for fixed assets within a SIE 5 entry document.
 * Corresponds to {@code FixedAssetsTypeEntry} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code BaseSubdividedAccountTypeEntry}.
 *
 * <p>Groups zero or more {@link FixedAssetEntry} elements under a primary account.
 * Unlike {@link FixedAssets}, this entry variant has no secondary account references.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FixedAssetsEntry extends SubdividedAccountEntry {

    @XmlElement(name = "FixedAsset")
    private List<FixedAssetEntry> fixedAssets = new ArrayList<>();


    /** Creates a new instance. */
    public FixedAssetsEntry() {}
    /**
     * Returns the list of fixed asset entries in this container.
     * @return the list of fixed asset entries in this container
     */
    public List<FixedAssetEntry> getFixedAssets() { return fixedAssets; }

    /**
     * Sets the list of fixed asset entries to set.
     * @param fixedAssets the list of fixed asset entries to set
     */
    public void setFixedAssets(List<FixedAssetEntry> fixedAssets) { this.fixedAssets = fixedAssets; }
}
