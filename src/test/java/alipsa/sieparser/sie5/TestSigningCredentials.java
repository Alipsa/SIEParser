package alipsa.sieparser.sie5;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

final class TestSigningCredentials {

    private TestSigningCredentials() {
    }

    static Sie5SigningCredentials create() throws Exception {
        return new Sie5SigningCredentials(loadPrivateKey(), loadCertificate());
    }

    private static PrivateKey loadPrivateKey() throws Exception {
        byte[] keyBytes = decodePem(PRIVATE_KEY_PEM, "PRIVATE KEY");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    private static X509Certificate loadCertificate() throws Exception {
        byte[] certBytes = CERTIFICATE_PEM.getBytes(StandardCharsets.US_ASCII);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certBytes));
    }

    private static byte[] decodePem(String pem, String type) {
        String normalized = pem
            .replace("-----BEGIN " + type + "-----", "")
            .replace("-----END " + type + "-----", "")
            .replaceAll("\\s", "");
        return Base64.getDecoder().decode(normalized);
    }

    private static final String PRIVATE_KEY_PEM = """
        -----BEGIN PRIVATE KEY-----
        MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC5uvAGHx+ed3mY
        /1hLQGQX1XrR5ICxW6hoCBlbEpYKtWzzfyjooeE8BhRYl4knyygYA0jDu4QPLOS/
        EFgK//k5r+NOoSRfBvoHdUisTloE7meIlLohkLr6Z7tsIpH9cs1l7RykjFdTPBR2
        lE4xVmhMQVuEwr8IVFThoSJV9Fc0AY/8+rLWThGMUMpxo5iTrYqt4CHgNpzzzCLF
        TP2ywdOov/8UIhnZivBTsFnEfsCP4U9IOn8c+/4r5B94jUuN0QC7vtyXtlBeObcP
        o4K1tkYk2JC3gyBKXQygtl54kzd2jfosTm4WEWxGXA9AHZpsaW0GE5th+kkTcCnf
        xZ/s08lTAgMBAAECggEAA6D/7JayFvYNpawjjQDak86jgjNdQlngnfu+hxWDYf0u
        fkl3QqhbDsGtpxd64hCpnWJ/CvgAeg1uAL+wgLKEq5hgsBoc7FBmFTw46cj0IFGK
        K1SAmIRL6vWY52F7icCy+7FY1Gw7jpBHdBOsvXELQ6YpRBxMAD0plWkBEz3dcFIo
        8h5LUpweRoT4FPufqCO+8rkjev1Vl9mn46kFlpS3XUBvnc+YO/x4fVKon8Wo1Zfl
        29xK3TYtD7ruJfQpLXiGUQkGULrdXpo00sRZ73kL7lNkKeZuKGiutf/GabsxtYNJ
        EfCHZ8Af3Omc2Iu1qFvByUnDd+WAWgMXFc4M/cS0OQKBgQDsUvybTx68uE2kZC4i
        rWfdpJSXlNKK2Puh67mT1iJacALJuz68MQOrjB9Z9Ihfsi0FgqrmtZvsrJIqGioI
        SgTbLC3lKJ9Hd2zZGAY4ZaphwC0UZctIkYkZChM+5mLxejjbQkmnG9CqXdNtr7xi
        7lpjmEk63uPPsKo+MYUBphZACwKBgQDJMZdxnLA0Np/3lJCfOw76z+ZwYQT9xpj6
        I6onqsgRkYtarumN7Fxj6J+0QXYbcUzWkKa9ydQrGyky3i0Owk68/Vrq407+Nlqg
        ZYlbiob2nJVleEjGQs5Uag6y6YhzbTCEurejb4BPWH6u7VUUsUF8+Yam89mMYC+P
        VnWjaRaA2QKBgAlocFf6eV3H9IdT2aZVwunG8IdsTElsw++5Q6UIBEwXY3UGeEPj
        q6K7rE/XdUph/HrYrdcLac6tPBBjBENaNwFGq/kQee7NaU7nLvA10+eaT/Ec8E/O
        Q2f0x7lcUJoOZI8N/4Kgj9kIbS9TrKs/k+edG2U1lFojTVO2gvYC16XrAoGBAJME
        zBfXWeMtr4Npaq0QqQeaeFfSbaVMRGk1Ope18nD0HBLuEfkFqRXQ3TMJStcO2glI
        tq+lFodRV6+2LtLEJmlv8coGxKh664qd59uexLTdA0acuQE3vDJvNcKDaJSAS54S
        GzMwvWA92ITXJP7z8Fj0tfK16ljryJVDpr78gdcxAoGAd5cfetXSTJHv2UPa4Lp1
        H1MmhehJni5QBkz71pihuyocaArDi7F270Jbx8TgwL3M8oIVxC8f34IgzzdOlGkQ
        IhpPg9SbQCrDot7LfICdChJaEu3dFEQe/6s9TDGx92wsGsSOjzMClsNSqP8SR21x
        Y2DWAdYvjFyxE4+dZgsL+XQ=
        -----END PRIVATE KEY-----
        """;

    private static final String CERTIFICATE_PEM = """
        -----BEGIN CERTIFICATE-----
        MIIDVTCCAj2gAwIBAgIULJL4f/HfMdbtQ7Pn2QG52ynuH2wwDQYJKoZIhvcNAQEL
        BQAwOjEXMBUGA1UEAwwOU0lFNSBUZXN0IENlcnQxEjAQBgNVBAoMCVNJRVBhcnNl
        cjELMAkGA1UEBhMCU0UwHhcNMjYwMjI2MjIyNjM2WhcNMzYwMjI0MjIyNjM2WjA6
        MRcwFQYDVQQDDA5TSUU1IFRlc3QgQ2VydDESMBAGA1UECgwJU0lFUGFyc2VyMQsw
        CQYDVQQGEwJTRTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALm68AYf
        H553eZj/WEtAZBfVetHkgLFbqGgIGVsSlgq1bPN/KOih4TwGFFiXiSfLKBgDSMO7
        hA8s5L8QWAr/+Tmv406hJF8G+gd1SKxOWgTuZ4iUuiGQuvpnu2wikf1yzWXtHKSM
        V1M8FHaUTjFWaExBW4TCvwhUVOGhIlX0VzQBj/z6stZOEYxQynGjmJOtiq3gIeA2
        nPPMIsVM/bLB06i//xQiGdmK8FOwWcR+wI/hT0g6fxz7/ivkH3iNS43RALu+3Je2
        UF45tw+jgrW2RiTYkLeDIEpdDKC2XniTN3aN+ixObhYRbEZcD0AdmmxpbQYTm2H6
        SRNwKd/Fn+zTyVMCAwEAAaNTMFEwHQYDVR0OBBYEFE23yjH1Ehs7vokqytUglHf6
        LN6MMB8GA1UdIwQYMBaAFE23yjH1Ehs7vokqytUglHf6LN6MMA8GA1UdEwEB/wQF
        MAMBAf8wDQYJKoZIhvcNAQELBQADggEBAJKUFpwX0xC5ghV10f+sAGNwjS+QFrx5
        hLQ1GVvFQXb0HColch0ip5r4UyRTIydn8luGg7EoTlDZCxgALLxhXupPaRGGRbaE
        19dJWMGxERLu9le5zdI2nxVPfZOlDewc3Q4zW30BE89n/TZyKgUMH0UMJUxox5AX
        YsCaoVUFHrsaRzP0g82A80zh0mX5P3jLC9mQWOteMopbJFD3P+MhSXLC7v7s33hY
        7VgmUujjXjN/yycDvDpcVE50qlNbyi6iBeRz/liIxhPalK00fFysWEc6ElwVoAJZ
        hP5TxTqQjGIMEFLBqweF5oucOTiLstOpVsllCkVYUizhq6qrZTkr03A=
        -----END CERTIFICATE-----
        """;
}
