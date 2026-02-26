package alipsa.sieparser.sie5;

import alipsa.sieparser.SieException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.InputStream;

/**
 * JAXB-based reader for SIE 5 XML documents.
 * Supports reading both full documents ({@code <Sie>}) and
 * entry/import documents ({@code <SieEntry>}).
 *
 * <p>This reader uses a shared {@link JAXBContext} initialized once at class
 * loading time for both {@link Sie5Document} and {@link Sie5Entry} root types.
 * Each read operation creates a new {@link Unmarshaller} to ensure thread safety.</p>
 *
 * @see Sie5Document
 * @see Sie5Entry
 * @see Sie5DocumentWriter
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

    /** Creates a new instance. */
    public Sie5DocumentReader() {}

    /**
     * Reads a full SIE 5 document from a file path.
     *
     * @param fileName the path to the SIE 5 XML file
     * @return the parsed {@link Sie5Document}
     * @throws SieException if the XML cannot be parsed or does not conform to the expected structure
     */
    public Sie5Document readDocument(String fileName) {
        try {
            Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
            return (Sie5Document) unmarshaller.unmarshal(new File(fileName));
        } catch (JAXBException e) {
            throw new SieException("Failed to read SIE 5 document: " + fileName, e);
        }
    }

    /**
     * Reads a full SIE 5 document from an {@link InputStream}.
     *
     * @param stream the input stream containing SIE 5 XML data
     * @return the parsed {@link Sie5Document}
     * @throws SieException if the XML cannot be parsed or does not conform to the expected structure
     */
    public Sie5Document readDocument(InputStream stream) {
        try {
            Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
            return (Sie5Document) unmarshaller.unmarshal(stream);
        } catch (JAXBException e) {
            throw new SieException("Failed to read SIE 5 document from stream", e);
        }
    }

    /**
     * Reads a SIE 5 entry (import) document from a file path.
     *
     * @param fileName the path to the SIE 5 entry XML file
     * @return the parsed {@link Sie5Entry}
     * @throws SieException if the XML cannot be parsed or does not conform to the expected structure
     */
    public Sie5Entry readEntry(String fileName) {
        try {
            Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
            return (Sie5Entry) unmarshaller.unmarshal(new File(fileName));
        } catch (JAXBException e) {
            throw new SieException("Failed to read SIE 5 entry: " + fileName, e);
        }
    }

    /**
     * Reads a SIE 5 entry (import) document from an {@link InputStream}.
     *
     * <p>Uses {@link StreamSource} wrapping to ensure the unmarshaller binds
     * to the correct {@link Sie5Entry} root type.</p>
     *
     * @param stream the input stream containing SIE 5 entry XML data
     * @return the parsed {@link Sie5Entry}
     * @throws SieException if the XML cannot be parsed or does not conform to the expected structure
     */
    public Sie5Entry readEntry(InputStream stream) {
        try {
            Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
            return unmarshaller.unmarshal(new StreamSource(stream), Sie5Entry.class).getValue();
        } catch (JAXBException e) {
            throw new SieException("Failed to read SIE 5 entry from stream", e);
        }
    }
}
