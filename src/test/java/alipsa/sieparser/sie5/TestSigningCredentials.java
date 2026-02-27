package alipsa.sieparser.sie5;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.security.auth.x500.X500Principal;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

/**
 * Generates ephemeral RSA keypair and self-signed certificate for test use only.
 * No private key material is stored in the repository.
 */
final class TestSigningCredentials {

    private TestSigningCredentials() {
    }

    static Sie5SigningCredentials create() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair keyPair = kpg.generateKeyPair();

        X500Principal subject = new X500Principal("CN=SIE5 Test Cert, O=SIEParser, C=SE");
        long now = System.currentTimeMillis();
        Date notBefore = new Date(now);
        Date notAfter = new Date(now + 365L * 24 * 60 * 60 * 1000);

        ContentSigner signer = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());
        X509CertificateHolder certHolder = new JcaX509v3CertificateBuilder(
                subject, BigInteger.ONE, notBefore, notAfter, subject, keyPair.getPublic()
        ).build(signer);

        X509Certificate cert = new JcaX509CertificateConverter().getCertificate(certHolder);
        return new Sie5SigningCredentials(keyPair.getPrivate(), cert);
    }
}
