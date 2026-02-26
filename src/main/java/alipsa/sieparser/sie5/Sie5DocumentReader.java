package alipsa.sieparser.sie5;

import alipsa.sieparser.SieException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import javax.xml.crypto.AlgorithmMethod;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.KeySelectorException;
import javax.xml.crypto.KeySelectorResult;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.XMLCryptoContext;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyValue;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import java.io.File;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
    private static final String XML_DSIG_NS = "http://www.w3.org/2000/09/xmldsig#";

    private boolean verifySignatures = true;
    private boolean allowUnsignedDocuments = false;
    private boolean allowInvalidSignatures = false;

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
     * Returns whether XMLDSig signatures are verified during reading.
     *
     * @return {@code true} if signatures are verified
     */
    public boolean isVerifySignatures() {
        return verifySignatures;
    }

    /**
     * Sets whether XMLDSig signatures are verified during reading.
     *
     * @param verifySignatures {@code true} to verify signatures
     */
    public void setVerifySignatures(boolean verifySignatures) {
        this.verifySignatures = verifySignatures;
    }

    /**
     * Returns whether unsigned full SIE 5 documents are accepted.
     *
     * @return {@code true} if missing signatures are allowed
     */
    public boolean isAllowUnsignedDocuments() {
        return allowUnsignedDocuments;
    }

    /**
     * Sets whether unsigned full SIE 5 documents are accepted.
     *
     * @param allowUnsignedDocuments {@code true} to allow full documents without signatures
     */
    public void setAllowUnsignedDocuments(boolean allowUnsignedDocuments) {
        this.allowUnsignedDocuments = allowUnsignedDocuments;
    }

    /**
     * Returns whether invalid signatures are accepted.
     *
     * @return {@code true} if invalid signatures are allowed
     */
    public boolean isAllowInvalidSignatures() {
        return allowInvalidSignatures;
    }

    /**
     * Sets whether invalid signatures are accepted.
     *
     * @param allowInvalidSignatures {@code true} to allow invalid signatures
     */
    public void setAllowInvalidSignatures(boolean allowInvalidSignatures) {
        this.allowInvalidSignatures = allowInvalidSignatures;
    }

    /**
     * Reads a full SIE 5 document from a file path.
     *
     * @param fileName the path to the SIE 5 XML file
     * @return the parsed {@link Sie5Document}
     * @throws SieException if the XML cannot be parsed or does not conform to the expected structure
     */
    public Sie5Document readDocument(String fileName) {
        try {
            Document document = parseDocument(new File(fileName));
            validateDocumentSignatures(document, true);
            Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
            return unmarshaller.unmarshal(document, Sie5Document.class).getValue();
        } catch (JAXBException | ParserConfigurationException | SAXException | java.io.IOException e) {
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
            Document document = parseDocument(stream);
            validateDocumentSignatures(document, true);
            Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
            return unmarshaller.unmarshal(document, Sie5Document.class).getValue();
        } catch (JAXBException | ParserConfigurationException | SAXException | java.io.IOException e) {
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
            Document document = parseDocument(new File(fileName));
            validateDocumentSignatures(document, false);
            Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
            return unmarshaller.unmarshal(document, Sie5Entry.class).getValue();
        } catch (JAXBException | ParserConfigurationException | SAXException | java.io.IOException e) {
            throw new SieException("Failed to read SIE 5 entry: " + fileName, e);
        }
    }

    /**
     * Reads a SIE 5 entry (import) document from an {@link InputStream}.
     *
     * <p>Uses DOM parsing to ensure the unmarshaller binds
     * to the correct {@link Sie5Entry} root type.</p>
     *
     * @param stream the input stream containing SIE 5 entry XML data
     * @return the parsed {@link Sie5Entry}
     * @throws SieException if the XML cannot be parsed or does not conform to the expected structure
     */
    public Sie5Entry readEntry(InputStream stream) {
        try {
            Document document = parseDocument(stream);
            validateDocumentSignatures(document, false);
            Unmarshaller unmarshaller = CONTEXT.createUnmarshaller();
            return unmarshaller.unmarshal(document, Sie5Entry.class).getValue();
        } catch (JAXBException | ParserConfigurationException | SAXException | java.io.IOException e) {
            throw new SieException("Failed to read SIE 5 entry from stream", e);
        }
    }

    private Document parseDocument(File file)
        throws ParserConfigurationException, SAXException, java.io.IOException {
        DocumentBuilderFactory dbf = createDocumentBuilderFactory();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(file);
    }

    private Document parseDocument(InputStream stream)
        throws ParserConfigurationException, SAXException, java.io.IOException {
        DocumentBuilderFactory dbf = createDocumentBuilderFactory();
        DocumentBuilder db = dbf.newDocumentBuilder();
        return db.parse(stream);
    }

    private DocumentBuilderFactory createDocumentBuilderFactory() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        dbf.setFeature(javax.xml.XMLConstants.FEATURE_SECURE_PROCESSING, true);
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
        dbf.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        dbf.setXIncludeAware(false);
        dbf.setExpandEntityReferences(false);
        return dbf;
    }

    private void validateDocumentSignatures(Document document, boolean requiredForDocument) {
        if (!verifySignatures) {
            return;
        }
        NodeList signatures = document.getElementsByTagNameNS(XML_DSIG_NS, "Signature");
        if (signatures.getLength() == 0) {
            if (requiredForDocument && !allowUnsignedDocuments) {
                throw new SieException("Missing required XMLDSig signature in SIE 5 document.");
            }
            return;
        }

        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
        KeySelector keySelector = new EmbeddedKeySelector();
        for (int i = 0; i < signatures.getLength(); i++) {
            Node signatureNode = signatures.item(i);
            try {
                DOMValidateContext validateContext = new DOMValidateContext(keySelector, signatureNode);
                validateContext.setProperty("org.jcp.xml.dsig.secureValidation", Boolean.FALSE);
                XMLSignature signature = signatureFactory.unmarshalXMLSignature(validateContext);
                boolean valid = signature.validate(validateContext);
                if (!valid && !allowInvalidSignatures) {
                    throw new SieException("Invalid XMLDSig signature in SIE 5 document.");
                }
            } catch (XMLSignatureException | MarshalException e) {
                if (!allowInvalidSignatures) {
                    throw new SieException("Failed to validate XMLDSig signature in SIE 5 document.", e);
                }
            }
        }
    }

    private static final class EmbeddedKeySelector extends KeySelector {
        @Override
        public KeySelectorResult select(KeyInfo keyInfo, Purpose purpose, AlgorithmMethod method, XMLCryptoContext context)
            throws KeySelectorException {
            if (keyInfo == null) {
                throw new KeySelectorException("No KeyInfo present in XMLDSig signature.");
            }
            for (Object o : keyInfo.getContent()) {
                XMLStructure info = (XMLStructure) o;
                if (info instanceof X509Data x509Data) {
                    for (Object data : x509Data.getContent()) {
                        if (data instanceof X509Certificate cert) {
                            return () -> cert.getPublicKey();
                        }
                    }
                } else if (info instanceof KeyValue keyValue) {
                    PublicKey publicKey;
                    try {
                        publicKey = keyValue.getPublicKey();
                    } catch (Exception e) {
                        throw new KeySelectorException("Unable to read XMLDSig KeyValue.", e);
                    }
                    return () -> publicKey;
                }
            }
            throw new KeySelectorException("No certificate or public key available for XMLDSig validation.");
        }
    }
}
