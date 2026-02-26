package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

/**
 * Container for embedded files and external file references.
 * Corresponds to {@code DocumentsType} in the SIE 5 XSD (namespace {@code http://www.sie.se/sie5}).
 *
 * <p>Holds a mixed list of {@link EmbeddedFile} and {@link FileReference} items,
 * modeled as an XSD choice group. Convenience methods are provided to filter
 * items by type.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Documents {

    @XmlElements({
        @XmlElement(name = "EmbeddedFile", type = EmbeddedFile.class),
        @XmlElement(name = "FileReference", type = FileReference.class)
    })
    private List<Object> items = new ArrayList<>();

    /**
     * @return the raw list of document items ({@link EmbeddedFile} and/or {@link FileReference})
     */
    public List<Object> getItems() { return items; }

    /**
     * @param items the list of document items to set
     */
    public void setItems(List<Object> items) { this.items = items; }

    /**
     * Returns only the {@link EmbeddedFile} items from this container.
     *
     * @return an unmodifiable list of embedded files
     */
    public List<EmbeddedFile> getEmbeddedFiles() {
        return items.stream()
                .filter(i -> i instanceof EmbeddedFile)
                .map(i -> (EmbeddedFile) i)
                .toList();
    }

    /**
     * Returns only the {@link FileReference} items from this container.
     *
     * @return an unmodifiable list of file references
     */
    public List<FileReference> getFileReferences() {
        return items.stream()
                .filter(i -> i instanceof FileReference)
                .map(i -> (FileReference) i)
                .toList();
    }
}
