package alipsa.sieparser.sie5;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class Dimension {

    @XmlAttribute(name = "id", required = true)
    private int id;

    @XmlAttribute(name = "name", required = true)
    private String name;

    @XmlElement(name = "Object")
    private List<DimensionObject> objects = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<DimensionObject> getObjects() { return objects; }
    public void setObjects(List<DimensionObject> objects) { this.objects = objects; }
}
