package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class FixedAssetsEntry extends SubdividedAccountEntry {

    @XmlElement(name = "FixedAsset")
    private List<FixedAssetEntry> fixedAssets = new ArrayList<>();

    public List<FixedAssetEntry> getFixedAssets() { return fixedAssets; }
    public void setFixedAssets(List<FixedAssetEntry> fixedAssets) { this.fixedAssets = fixedAssets; }
}
