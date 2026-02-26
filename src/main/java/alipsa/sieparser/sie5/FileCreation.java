package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlSchemaType;
import java.time.OffsetDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class FileCreation {

    @XmlAttribute(name = "time", required = true)
    @XmlSchemaType(name = "dateTime")
    private OffsetDateTime time;

    @XmlAttribute(name = "by", required = true)
    private String by;

    public OffsetDateTime getTime() { return time; }
    public void setTime(OffsetDateTime time) { this.time = time; }

    public String getBy() { return by; }
    public void setBy(String by) { this.by = by; }
}
