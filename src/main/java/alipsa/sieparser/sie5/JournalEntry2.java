package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry-variant journal container (JournalTypeEntry in XSD).
 * Corresponds to {@code JournalTypeEntry} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>In entry files, the journal {@code id} is optional and there is no {@code name} attribute.
 * Named {@code JournalEntry2} to avoid collision with {@link JournalEntry}.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JournalEntry2 {

    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "JournalEntry")
    private List<JournalEntryEntry> journalEntries = new ArrayList<>();


    /** Creates a new instance. */
    public JournalEntry2() {}
    /**
     * Returns the optional journal identifier.
     * @return the optional journal identifier
     */
    public String getId() { return id; }

    /**
     * Sets the journal identifier.
     * @param id the journal identifier
     */
    public void setId(String id) { this.id = id; }

    /**
     * Returns the list of journal entries (entry-document variant) in this journal.
     * @return the list of journal entries (entry-document variant) in this journal
     */
    public List<JournalEntryEntry> getJournalEntries() { return journalEntries; }

    /**
     * Sets the list of journal entries to set.
     * @param journalEntries the list of journal entries to set
     */
    public void setJournalEntries(List<JournalEntryEntry> journalEntries) { this.journalEntries = journalEntries; }
}
