import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

public class HumanXmlSerializer {

    public String toXml(Human human) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Human.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(human, sw);
        return sw.toString();
    }

    public String toXml(List<Human> humans) throws JAXBException {
        HumanList wrapper = new HumanList();
        wrapper.setHumans(humans);
        JAXBContext context = JAXBContext.newInstance(HumanList.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter sw = new StringWriter();
        marshaller.marshal(wrapper, sw);
        return sw.toString();
    }

    public Human fromXml(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Human.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Human) unmarshaller.unmarshal(new StringReader(xml));
    }

    public List<Human> listFromXml(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(HumanList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        HumanList wrapper = (HumanList) unmarshaller.unmarshal(new StringReader(xml));
        return wrapper.getHumans();
    }
}