package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Entry-variant journal container (JournalTypeEntry in XSD).
 * In entry files, the journal id is optional and there is no name attribute.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JournalEntry2 {

    @XmlAttribute(name = "id")
    private String id;

    @XmlElement(name = "JournalEntry")
    private List<JournalEntryEntry> journalEntries = new ArrayList<>();

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public List<JournalEntryEntry> getJournalEntries() { return journalEntries; }
    public void setJournalEntries(List<JournalEntryEntry> journalEntries) { this.journalEntries = journalEntries; }
}
