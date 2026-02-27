package alipsa.sieparser.sie5;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Objects;

/**
 * Signing credentials used to produce XMLDSig signatures for SIE 5 full documents.
 */
public class Sie5SigningCredentials {

    private final PrivateKey privateKey;
    private final X509Certificate certificate;

    /**
     * Creates signing credentials.
     *
     * @param privateKey private key used for signing
     * @param certificate X509 certificate embedded in the signature
     */
    public Sie5SigningCredentials(PrivateKey privateKey, X509Certificate certificate) {
        this.privateKey = Objects.requireNonNull(privateKey, "privateKey");
        this.certificate = Objects.requireNonNull(certificate, "certificate");
    }

    /**
     * Returns the private key.
     *
     * @return signing private key
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * Returns the certificate.
     *
     * @return embedded X509 certificate
     */
    public X509Certificate getCertificate() {
        return certificate;
    }
}
