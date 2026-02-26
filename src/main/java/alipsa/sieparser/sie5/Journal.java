package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Journal container for full SIE 5 documents.
 * Corresponds to {@code JournalType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>A journal groups related {@link JournalEntry} records and is identified
 * by a required {@code id} and {@code name}.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Journal {

    @XmlAttribute(name = "id", required = true)
    private String id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "JournalEntry")
    private List<JournalEntry> journalEntries = new ArrayList<>();

    /**
     * @return the journal identifier (required)
     */
    public String getId() { return id; }

    /**
     * @param id the journal identifier
     */
    public void setId(String id) { this.id = id; }

    /**
     * @return the journal name (required)
     */
    public String getName() { return name; }

    /**
     * @param name the journal name
     */
    public void setName(String name) { this.name = name; }

    /**
     * @return the list of journal entries in this journal
     */
    public List<JournalEntry> getJournalEntries() { return journalEntries; }

    /**
     * @param journalEntries the list of journal entries to set
     */
    public void setJournalEntries(List<JournalEntry> journalEntries) { this.journalEntries = journalEntries; }
}
