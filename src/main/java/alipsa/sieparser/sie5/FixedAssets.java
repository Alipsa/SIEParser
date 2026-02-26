package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class FixedAssets extends SubdividedAccount {

    @XmlElement(name = "FixedAsset")
    private List<FixedAsset> fixedAssets = new ArrayList<>();

    public List<FixedAsset> getFixedAssets() { return fixedAssets; }
    public void setFixedAssets(List<FixedAsset> fixedAssets) { this.fixedAssets = fixedAssets; }
}
