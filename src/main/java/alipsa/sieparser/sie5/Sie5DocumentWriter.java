package alipsa.sieparser.sie5;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.OutputStream;

/**
 * Writer for SIE 5 XML documents.
 * Supports both full documents (Sie) and entry/import documents (SieEntry).
 */
public class Sie5DocumentWriter {

    private static final JAXBContext CONTEXT;

    static {
        try {
            CONTEXT = JAXBContext.newInstance(Sie5Document.class, Sie5Entry.class);
        } catch (JAXBException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Write a full SIE 5 document to a file.
     */
    public void write(Sie5Document doc, String fileName) throws JAXBException {
        Marshaller marshaller = createMarshaller();
        marshaller.marshal(doc, new File(fileName));
    }

    /**
     * Write a full SIE 5 document to an OutputStream.
     */
    public void write(Sie5Document doc, OutputStream stream) throws JAXBException {
        Marshaller marshaller = createMarshaller();
        marshaller.marshal(doc, stream);
    }

    /**
     * Write a SIE 5 entry (import) document to a file.
     */
    public void writeEntry(Sie5Entry entry, String fileName) throws JAXBException {
        Marshaller marshaller = createMarshaller();
        marshaller.marshal(entry, new File(fileName));
    }

    /**
     * Write a SIE 5 entry (import) document to an OutputStream.
     */
    public void writeEntry(Sie5Entry entry, OutputStream stream) throws JAXBException {
        Marshaller marshaller = createMarshaller();
        marshaller.marshal(entry, stream);
    }

    private Marshaller createMarshaller() throws JAXBException {
        Marshaller marshaller = CONTEXT.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        return marshaller;
    }
}
