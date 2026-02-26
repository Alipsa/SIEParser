package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralSubdividedAccount extends SubdividedAccount {

    @XmlElement(name = "GeneralObject")
    private List<GeneralObject> generalObjects = new ArrayList<>();

    public List<GeneralObject> getGeneralObjects() { return generalObjects; }
    public void setGeneralObjects(List<GeneralObject> generalObjects) { this.generalObjects = generalObjects; }
}
