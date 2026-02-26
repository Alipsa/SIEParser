package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for general-purpose objects within a SIE 5 entry document.
 * Corresponds to {@code GeneralSubdividedAccountTypeEntry} in the SIE 5 XSD
 * (namespace {@code http://www.sie.se/sie5}), which extends
 * {@code BaseSubdividedAccountTypeEntry}.
 *
 * <p>Groups zero or more {@link SubdividedAccountObjectEntry} elements under a primary account.
 * Unlike {@link GeneralSubdividedAccount}, this entry variant has no secondary account
 * references.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class GeneralSubdividedAccountEntry extends SubdividedAccountEntry {

    @XmlElement(name = "GeneralObject")
    private List<SubdividedAccountObjectEntry> generalObjects = new ArrayList<>();

    /**
     * Returns the list of general object entries in this container.
     * @return the list of general object entries in this container
     */
    public List<SubdividedAccountObjectEntry> getGeneralObjects() { return generalObjects; }

    /**
     * Sets the list of general object entries to set.
     * @param generalObjects the list of general object entries to set
     */
    public void setGeneralObjects(List<SubdividedAccountObjectEntry> generalObjects) { this.generalObjects = generalObjects; }
}
