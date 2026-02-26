package alipsa.sieparser.sie5;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.InputStream;

/**
 * Reader for SIE 5 XML documents.
 * Supports both full documents (Sie) and entry/import documents (SieEntry).
 */
public class Sie5DocumentReader {

    private static final JAXBContext CONTEXT;

    static {
        try {
            CONTEXT = JAXBContext.newInstance(Sie5Document.class, Sie5Entry.class);
        } catch (JAXBException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Read a full SIE 5 document from a file path.
     */
    public Sie5Document readDocument(String fileName) throws JAXBException {
        Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
        return (Sie5Document) unmarshaller.unmarshal(new File(fileName));
    }

    /**
     * Read a full SIE 5 document from an InputStream.
     */
    public Sie5Document readDocument(InputStream stream) throws JAXBException {
        Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
        return (Sie5Document) unmarshaller.unmarshal(stream);
    }

    /**
     * Read a SIE 5 entry (import) document from a file path.
     */
    public Sie5Entry readEntry(String fileName) throws JAXBException {
        Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
        return (Sie5Entry) unmarshaller.unmarshal(new File(fileName));
    }

    /**
     * Read a SIE 5 entry (import) document from an InputStream.
     */
    public Sie5Entry readEntry(InputStream stream) throws JAXBException {
        Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
        return unmarshaller.unmarshal(new StreamSource(stream), Sie5Entry.class).getValue();
    }
}
