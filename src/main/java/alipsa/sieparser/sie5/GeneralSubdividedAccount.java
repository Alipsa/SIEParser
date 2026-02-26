package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for general-purpose objects within a full SIE 5 document.
 * Corresponds to {@code GeneralSubdividedAccountType} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code BaseSubdividedAccountType}.
 *
 * <p>Groups zero or more {@link GeneralObject} elements under a primary account.
 * Used for subdivided accounts whose objects are not customer invoices,
 * supplier invoices, or fixed assets.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralSubdividedAccount extends SubdividedAccount {

    @XmlElement(name = "GeneralObject")
    private List<GeneralObject> generalObjects = new ArrayList<>();

    /**
     * @return the list of general objects in this container
     */
    public List<GeneralObject> getGeneralObjects() { return generalObjects; }

    /**
     * @param generalObjects the list of general objects to set
     */
    public void setGeneralObjects(List<GeneralObject> generalObjects) { this.generalObjects = generalObjects; }
}
