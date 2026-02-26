package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralSubdividedAccountEntry extends SubdividedAccountEntry {

    @XmlElement(name = "GeneralObject")
    private List<SubdividedAccountObjectEntry> generalObjects = new ArrayList<>();

    public List<SubdividedAccountObjectEntry> getGeneralObjects() { return generalObjects; }
    public void setGeneralObjects(List<SubdividedAccountObjectEntry> generalObjects) { this.generalObjects = generalObjects; }
}
