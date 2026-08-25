import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;
import java.util.ArrayList;

@XmlRootElement(name = "humans")
@XmlAccessorType(XmlAccessType.FIELD)
public class HumanList {

    @XmlElement(name = "human")
    private List<Human> humans = new ArrayList<>();

    public List<Human> getHumans() { return humans; }
    public void setHumans(List<Human> humans) { this.humans = humans; }
}