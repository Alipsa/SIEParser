package alipsa.sieparser.sie5;

import alipsa.sieparser.SieException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import javax.xml.crypto.MarshalException;
import javax.xml.crypto.dsig.CanonicalizationMethod;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.SignatureMethod;
import javax.xml.crypto.dsig.SignedInfo;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureException;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.crypto.dsig.spec.TransformParameterSpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
    private static final String XML_DSIG_NS = "http://www.w3.org/2000/09/xmldsig#";

    private Sie5SigningCredentials signingCredentials;
    private boolean requireSignatureForFullDocuments = true;

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
     * Creates a writer configured with signing credentials for full documents.
     *
     * @param signingCredentials credentials used to produce XMLDSig signatures
     */
    public Sie5DocumentWriter(Sie5SigningCredentials signingCredentials) {
        this.signingCredentials = signingCredentials;
    }

    /**
     * Returns the signing credentials used for full-document signing.
     *
     * @return signing credentials, or {@code null} if not configured
     */
    public Sie5SigningCredentials getSigningCredentials() {
        return signingCredentials;
    }

    /**
     * Sets the signing credentials used for full-document signing.
     *
     * @param signingCredentials signing credentials, or {@code null} to disable automatic signing
     */
    public void setSigningCredentials(Sie5SigningCredentials signingCredentials) {
        this.signingCredentials = signingCredentials;
    }

    /**
     * Returns whether full documents must contain at least one XMLDSig signature.
     *
     * @return {@code true} if a signature is required for full documents
     */
    public boolean isRequireSignatureForFullDocuments() {
        return requireSignatureForFullDocuments;
    }

    /**
     * Sets whether full documents must contain at least one XMLDSig signature.
     *
     * @param requireSignatureForFullDocuments {@code true} to enforce signatures
     */
    public void setRequireSignatureForFullDocuments(boolean requireSignatureForFullDocuments) {
        this.requireSignatureForFullDocuments = requireSignatureForFullDocuments;
    }

    /**
     * Writes a full SIE 5 document to a file.
     *
     * @param doc      the document to write
     * @param fileName the path of the output file
     * @throws SieException if the document cannot be marshalled
     */
    public void write(Sie5Document doc, String fileName) {
        try (OutputStream out = new FileOutputStream(new File(fileName))) {
            write(doc, out);
        } catch (IOException e) {
            throw new SieException("Failed to write SIE 5 document: " + fileName, e);
        }
    }

    /**
     * Writes a full SIE 5 document to an {@link OutputStream}.
     *
     * @param doc    the document to write
     * @param stream the output stream to write to
     * @throws SieException if the document cannot be marshalled
     */
    public void write(Sie5Document doc, OutputStream stream) {
        try {
            Marshaller marshaller = createMarshaller();
            if (signingCredentials != null) {
                Document xmlDocument = marshalToDocument(doc, marshaller);
                removeExistingSignatures(xmlDocument);
                sign(xmlDocument, signingCredentials);
                writeDocument(xmlDocument, stream);
            } else {
                if (requireSignatureForFullDocuments && doc.getSignatures().isEmpty()) {
                    throw new SieException("Full SIE 5 documents require at least one XMLDSig signature. "
                        + "Configure signing credentials or provide a Signature element.");
                }
                marshaller.marshal(doc, stream);
            }
        } catch (JAXBException | GeneralSecurityException | ParserConfigurationException | TransformerException | XMLSignatureException | MarshalException e) {
            throw new SieException("Failed to write SIE 5 document to stream", e);
        }
    }

    /**
     * Writes a SIE 5 entry (import) document to a file.
     *
     * @param entry    the entry document to write
     * @param fileName the path of the output file
     * @throws SieException if the document cannot be marshalled
     */
    public void writeEntry(Sie5Entry entry, String fileName) {
        try {
            Marshaller marshaller = createMarshaller();
            marshaller.marshal(entry, new File(fileName));
        } catch (JAXBException e) {
            throw new SieException("Failed to write SIE 5 entry: " + fileName, e);
        }
    }

    /**
     * Writes a SIE 5 entry (import) document to an {@link OutputStream}.
     *
     * @param entry  the entry document to write
     * @param stream the output stream to write to
     * @throws SieException if the document cannot be marshalled
     */
    public void writeEntry(Sie5Entry entry, OutputStream stream) {
        try {
            Marshaller marshaller = createMarshaller();
            marshaller.marshal(entry, stream);
        } catch (JAXBException e) {
            throw new SieException("Failed to write SIE 5 entry to stream", e);
        }
    }

    private Marshaller createMarshaller() throws JAXBException {
        Marshaller marshaller = CONTEXT.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        return marshaller;
    }

    private Document marshalToDocument(Sie5Document doc, Marshaller marshaller)
        throws ParserConfigurationException, JAXBException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xmlDocument = db.newDocument();
        DOMResult result = new DOMResult(xmlDocument);
        marshaller.marshal(doc, result);
        return xmlDocument;
    }

    private void removeExistingSignatures(Document document) {
        NodeList signatures = document.getElementsByTagNameNS(XML_DSIG_NS, "Signature");
        List<Node> toRemove = new ArrayList<>();
        for (int i = 0; i < signatures.getLength(); i++) {
            toRemove.add(signatures.item(i));
        }
        for (Node node : toRemove) {
            Node parent = node.getParentNode();
            if (parent != null) {
                parent.removeChild(node);
            }
        }
    }

    private void sign(Document document, Sie5SigningCredentials credentials)
        throws GeneralSecurityException, XMLSignatureException, MarshalException {
        XMLSignatureFactory signatureFactory = XMLSignatureFactory.getInstance("DOM");
        Reference reference = signatureFactory.newReference(
            "",
            signatureFactory.newDigestMethod(DigestMethod.SHA256, null),
            List.of(
                signatureFactory.newTransform(Transform.ENVELOPED, (TransformParameterSpec) null),
                signatureFactory.newTransform(CanonicalizationMethod.INCLUSIVE, (TransformParameterSpec) null)
            ),
            null,
            null
        );

        PrivateKey privateKey = credentials.getPrivateKey();
        SignatureMethod signatureMethod = createSignatureMethod(signatureFactory, privateKey.getAlgorithm());
        SignedInfo signedInfo = signatureFactory.newSignedInfo(
            signatureFactory.newCanonicalizationMethod(CanonicalizationMethod.INCLUSIVE, (C14NMethodParameterSpec) null),
            signatureMethod,
            List.of(reference)
        );

        KeyInfoFactory keyInfoFactory = signatureFactory.getKeyInfoFactory();
        X509Data x509Data = keyInfoFactory.newX509Data(List.of(credentials.getCertificate()));
        KeyInfo keyInfo = keyInfoFactory.newKeyInfo(List.of(x509Data));

        DOMSignContext signContext = new DOMSignContext(privateKey, document.getDocumentElement());
        signContext.setDefaultNamespacePrefix("ds");
        XMLSignature signature = signatureFactory.newXMLSignature(signedInfo, keyInfo);
        signature.sign(signContext);
    }

    private SignatureMethod createSignatureMethod(XMLSignatureFactory signatureFactory, String keyAlgorithm)
        throws GeneralSecurityException {
        try {
            if ("EC".equalsIgnoreCase(keyAlgorithm) || "ECDSA".equalsIgnoreCase(keyAlgorithm)) {
                return signatureFactory.newSignatureMethod(SignatureMethod.ECDSA_SHA256, null);
            }
            return signatureFactory.newSignatureMethod(SignatureMethod.RSA_SHA256, null);
        } catch (Exception e) {
            throw new GeneralSecurityException("Unable to configure XML signature method for key algorithm: " + keyAlgorithm, e);
        }
    }

    private void writeDocument(Document document, OutputStream stream) throws TransformerException {
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.transform(new DOMSource(document), new StreamResult(stream));
    }
}
