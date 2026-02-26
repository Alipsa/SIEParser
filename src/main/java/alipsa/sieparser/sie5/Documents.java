package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElements;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Documents {

    @XmlElements({
        @XmlElement(name = "EmbeddedFile", type = EmbeddedFile.class),
        @XmlElement(name = "FileReference", type = FileReference.class)
    })
    private List<Object> items = new ArrayList<>();

    public List<Object> getItems() { return items; }
    public void setItems(List<Object> items) { this.items = items; }

    public List<EmbeddedFile> getEmbeddedFiles() {
        return items.stream()
                .filter(i -> i instanceof EmbeddedFile)
                .map(i -> (EmbeddedFile) i)
                .toList();
    }

    public List<FileReference> getFileReferences() {
        return items.stream()
                .filter(i -> i instanceof FileReference)
                .map(i -> (FileReference) i)
                .toList();
    }
}
