package alipsa.sieparser.sie5;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.File;
import java.io.OutputStream;

/**
 * JAXB-based writer for SIE 5 XML documents.
 * Supports writing both full documents ({@code <Sie>}) and
 * entry/import documents ({@code <SieEntry>}).
 *
 * <p>This writer uses a shared {@link JAXBContext} initialized once at class
 * loading time. Output is formatted (pretty-printed) with UTF-8 encoding.</p>
 *
 * @see Sie5Document
 * @see Sie5Entry
 * @see Sie5DocumentReader
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

    /** Creates a new instance. */
    public Sie5DocumentWriter() {}

    /**
     * Writes a full SIE 5 document to a file.
     *
     * @param doc      the document to write
     * @param fileName the path of the output file
     * @throws JAXBException if the document cannot be marshalled
     */
    public void write(Sie5Document doc, String fileName) throws JAXBException {
        Marshaller marshaller = createMarshaller();
        marshaller.marshal(doc, new File(fileName));
    }

    /**
     * Writes a full SIE 5 document to an {@link OutputStream}.
     *
     * @param doc    the document to write
     * @param stream the output stream to write to
     * @throws JAXBException if the document cannot be marshalled
     */
    public void write(Sie5Document doc, OutputStream stream) throws JAXBException {
        Marshaller marshaller = createMarshaller();
        marshaller.marshal(doc, stream);
    }

    /**
     * Writes a SIE 5 entry (import) document to a file.
     *
     * @param entry    the entry document to write
     * @param fileName the path of the output file
     * @throws JAXBException if the document cannot be marshalled
     */
    public void writeEntry(Sie5Entry entry, String fileName) throws JAXBException {
        Marshaller marshaller = createMarshaller();
        marshaller.marshal(entry, new File(fileName));
    }

    /**
     * Writes a SIE 5 entry (import) document to an {@link OutputStream}.
     *
     * @param entry  the entry document to write
     * @param stream the output stream to write to
     * @throws JAXBException if the document cannot be marshalled
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
