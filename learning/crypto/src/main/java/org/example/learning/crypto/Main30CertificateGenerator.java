package org.example.learning.crypto;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509ExtensionUtils;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PKCS8Generator;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.openssl.jcajce.JcaPKCS8Generator;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.OutputEncryptor;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.jcajce.JcePKCSPBEOutputEncryptorBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.RSAKeyGenParameterSpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

public class Main30CertificateGenerator {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, OperatorCreationException, KeyStoreException, CertificateException {
        // certs -> .gitignore
        // *.crt -> public
        // *.key -> private
        Files.createDirectories(Path.of("certs"));

        Security.addProvider(new BouncyCastleProvider());

        final PrivateKey caPrivateKey;
        final X509Certificate caCertificate;
        final X509CertificateHolder caCertificateHolder;

        {
            final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
            generator.initialize(new RSAKeyGenParameterSpec(4096, RSAKeyGenParameterSpec.F4));

            final KeyPair keyPair = generator.generateKeyPair();
            final PrivateKey privateKey = keyPair.getPrivate();
            final PublicKey publicKey = keyPair.getPublic();

            final X500Name name = new X500NameBuilder(BCStyle.INSTANCE)
                    .addRDN(BCStyle.C, "RU")
                    .addRDN(BCStyle.L, "Moscow")
                    .addRDN(BCStyle.O, "Organization")
                    .addRDN(BCStyle.OU, "CA")
                    .addRDN(BCStyle.CN, "Local CA")
                    .build();

            final Instant start = Instant.now();
            final Instant end = start.plus(365L * 5, ChronoUnit.DAYS);

            final JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                    name,
                    BigInteger.valueOf(1L),
                    Date.from(start),
                    Date.from(end),
                    name,
                    publicKey
            );

            final JcaX509ExtensionUtils extensionUtils = new JcaX509ExtensionUtils();

            certificateBuilder
                    .addExtension(Extension.subjectKeyIdentifier, false, extensionUtils.createSubjectKeyIdentifier(publicKey))
                    .addExtension(Extension.authorityKeyIdentifier, false, extensionUtils.createAuthorityKeyIdentifier(publicKey))
                    .addExtension(Extension.basicConstraints, true, new BasicConstraints(true));

            final ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .build(privateKey);

            final X509CertificateHolder certificateHolder = certificateBuilder.build(signer);

            final X509Certificate certificate = new JcaX509CertificateConverter()
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .getCertificate(certificateHolder);

            try (
                    final JcaPEMWriter pemWriter = new JcaPEMWriter(Files.newBufferedWriter(Paths.get("certs", "ca.key")));
            ) {
                char[] secret = new char[]{'s', 'e', 'c', 'r', 'e', 't'};

                final OutputEncryptor encryptor = new JcePKCSPBEOutputEncryptorBuilder(PKCS8Generator.AES_256_CBC)
                        .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                        .build(secret);

                // !IMPORTANT!: immediately clear
                Arrays.fill(secret, '0');

                pemWriter.writeObject(new JcaPKCS8Generator(privateKey, encryptor));
            }
            try (
                    final JcaPEMWriter pemWriter = new JcaPEMWriter(Files.newBufferedWriter(Paths.get("certs", "ca.crt")));
            ) {
                pemWriter.writeObject(certificate);
            }

            caPrivateKey = privateKey;
            caCertificate = certificate;
            caCertificateHolder = certificateHolder;
        }

        final PrivateKey serverPrivateKey;
        final X509Certificate serverCertificate;
        {
            final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
            generator.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4));

            final KeyPair keyPair = generator.generateKeyPair();
            final PrivateKey privateKey = keyPair.getPrivate();
            final PublicKey publicKey = keyPair.getPublic();

            final X500Name name = new X500NameBuilder(BCStyle.INSTANCE)
                    .addRDN(BCStyle.C, "RU")
                    .addRDN(BCStyle.L, "Moscow")
                    .addRDN(BCStyle.O, "Organization")
                    .addRDN(BCStyle.OU, "Dev")
                    .addRDN(BCStyle.CN, "server.local")
                    .build();

            final Instant start = Instant.now();
            final Instant end = start.plus(365L, ChronoUnit.DAYS);
            final JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                    caCertificateHolder.getSubject(),
                    BigInteger.valueOf(11L),
                    Date.from(start),
                    Date.from(end),
                    name,
                    publicKey
            );

            JcaX509ExtensionUtils extensionUtils = new JcaX509ExtensionUtils();

            certificateBuilder
                    .addExtension(Extension.subjectAlternativeName, false, new GeneralNames(new GeneralName[]{
                            new GeneralName(GeneralName.dNSName, "server.local"),
                            new GeneralName(GeneralName.iPAddress, "127.0.0.1"),
                    }))
                    .addExtension(Extension.subjectKeyIdentifier, false, extensionUtils.createSubjectKeyIdentifier(publicKey))
                    .addExtension(Extension.authorityKeyIdentifier, false, extensionUtils.createAuthorityKeyIdentifier(caCertificate.getPublicKey()))
            ;

            final ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .build(caPrivateKey);

            final X509CertificateHolder certificateHolder = certificateBuilder.build(signer);

            final X509Certificate certificate = new JcaX509CertificateConverter()
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .getCertificate(certificateHolder);

            try (
                    final JcaPEMWriter pemWriter = new JcaPEMWriter(Files.newBufferedWriter(Paths.get("certs", "server.key")));
            ) {
                pemWriter.writeObject(new JcaPKCS8Generator(privateKey, null));
            }
            try (
                    final JcaPEMWriter pemWriter = new JcaPEMWriter(Files.newBufferedWriter(Paths.get("certs", "server.crt")));
            ) {
                pemWriter.writeObject(certificate);
            }

            serverPrivateKey = privateKey;
            serverCertificate = certificate;
        }

        final PrivateKey adminPrivateKey;
        final X509Certificate adminCertificate;
        {
            final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
            generator.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4));

            final KeyPair keyPair = generator.generateKeyPair();
            final PrivateKey privateKey = keyPair.getPrivate();
            final PublicKey publicKey = keyPair.getPublic();

            final X500Name name = new X500NameBuilder(BCStyle.INSTANCE)
                    .addRDN(BCStyle.C, "RU")
                    .addRDN(BCStyle.L, "Moscow")
                    .addRDN(BCStyle.O, "Organization")
                    .addRDN(BCStyle.OU, "Dev")
                    .addRDN(BCStyle.CN, "admin")
                    .build();

            final Instant start = Instant.now();
            final Instant end = start.plus(365L, ChronoUnit.DAYS);
            final JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                    caCertificateHolder.getSubject(),
                    BigInteger.valueOf(21L),
                    Date.from(start),
                    Date.from(end),
                    name,
                    publicKey
            );

            JcaX509ExtensionUtils extensionUtils = new JcaX509ExtensionUtils();

            certificateBuilder
                    .addExtension(Extension.subjectKeyIdentifier, false, extensionUtils.createSubjectKeyIdentifier(publicKey))
                    .addExtension(Extension.authorityKeyIdentifier, false, extensionUtils.createAuthorityKeyIdentifier(caCertificate.getPublicKey()))
            ;

            final ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .build(caPrivateKey);

            final X509CertificateHolder certificateHolder = certificateBuilder.build(signer);

            final X509Certificate certificate = new JcaX509CertificateConverter()
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .getCertificate(certificateHolder);

            try (
                    final JcaPEMWriter pemWriter = new JcaPEMWriter(Files.newBufferedWriter(Paths.get("certs", "admin.key")));
            ) {
                pemWriter.writeObject(new JcaPKCS8Generator(privateKey, null));
            }
            try (
                    final JcaPEMWriter pemWriter = new JcaPEMWriter(Files.newBufferedWriter(Paths.get("certs", "admin.crt")));
            ) {
                pemWriter.writeObject(certificate);
            }

            adminPrivateKey = privateKey;
            adminCertificate = certificate;
        }

        final PrivateKey userPrivateKey;
        final X509Certificate userCertificate;
        {
            final KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
            generator.initialize(new RSAKeyGenParameterSpec(2048, RSAKeyGenParameterSpec.F4));

            final KeyPair keyPair = generator.generateKeyPair();
            final PrivateKey privateKey = keyPair.getPrivate();
            final PublicKey publicKey = keyPair.getPublic();

            final X500Name name = new X500NameBuilder(BCStyle.INSTANCE)
                    .addRDN(BCStyle.C, "RU")
                    .addRDN(BCStyle.L, "Moscow")
                    .addRDN(BCStyle.O, "Organization")
                    .addRDN(BCStyle.OU, "Dev")
                    .addRDN(BCStyle.CN, "user")
                    .build();

            final Instant start = Instant.now();
            final Instant end = start.plus(365L, ChronoUnit.DAYS);
            final JcaX509v3CertificateBuilder certificateBuilder = new JcaX509v3CertificateBuilder(
                    caCertificateHolder.getSubject(),
                    BigInteger.valueOf(22L),
                    Date.from(start),
                    Date.from(end),
                    name,
                    publicKey
            );

            JcaX509ExtensionUtils extensionUtils = new JcaX509ExtensionUtils();

            certificateBuilder
                    .addExtension(Extension.subjectKeyIdentifier, false, extensionUtils.createSubjectKeyIdentifier(publicKey))
                    .addExtension(Extension.authorityKeyIdentifier, false, extensionUtils.createAuthorityKeyIdentifier(caCertificate.getPublicKey()))
            ;

            final ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .build(caPrivateKey);

            final X509CertificateHolder certificateHolder = certificateBuilder.build(signer);

            final X509Certificate certificate = new JcaX509CertificateConverter()
                    .setProvider(BouncyCastleProvider.PROVIDER_NAME)
                    .getCertificate(certificateHolder);

            try (
                    final JcaPEMWriter pemWriter = new JcaPEMWriter(Files.newBufferedWriter(Paths.get("certs", "user.key")));
            ) {
                pemWriter.writeObject(new JcaPKCS8Generator(privateKey, null));
            }
            try (
                    final JcaPEMWriter pemWriter = new JcaPEMWriter(Files.newBufferedWriter(Paths.get("certs", "user.crt")));
            ) {
                pemWriter.writeObject(certificate);
            }

            userPrivateKey = privateKey;
            userCertificate = certificate;
        }

        // generate stores
        {
            final KeyStore store = KeyStore.getInstance("PKCS12", BouncyCastleProvider.PROVIDER_NAME);
            store.load(null, null);
            store.setCertificateEntry("ca", caCertificate);
            try (final FileOutputStream out = new FileOutputStream(Paths.get("certs", "truststore.jks").toFile());) {
                char[] secret = new char[]{'s', 'e', 'c', 'r', 'e', 't'};
                store.store(out, secret);
                // !IMPORTANT!: immediately clear
                Arrays.fill(secret, '0');
            }
        }

        {
            final KeyStore store = KeyStore.getInstance("PKCS12", BouncyCastleProvider.PROVIDER_NAME);
            store.load(null, null);
            char[] entrySecret = new char[]{'s', 'e', 'c', 'r', 'e', 't'};
            store.setKeyEntry("key", serverPrivateKey, entrySecret, new Certificate[]{serverCertificate});
            // !IMPORTANT!: immediately clear
            Arrays.fill(entrySecret, '0');
            try (final FileOutputStream out = new FileOutputStream(Paths.get("certs", "server.jks").toFile());) {
                char[] secret = new char[]{'s', 'e', 'c', 'r', 'e', 't'};
                store.store(out, secret);
                // !IMPORTANT!: immediately clear
                Arrays.fill(secret, '0');
            }
        }

        {
            final KeyStore store = KeyStore.getInstance("PKCS12", BouncyCastleProvider.PROVIDER_NAME);
            store.load(null, null);
            char[] entrySecret = new char[]{'s', 'e', 'c', 'r', 'e', 't'};
            store.setKeyEntry("key", adminPrivateKey, entrySecret, new Certificate[]{adminCertificate});
            Arrays.fill(entrySecret, '0');
            try (final FileOutputStream out = new FileOutputStream(Paths.get("certs", "admin.jks").toFile());) {
                char[] secret = new char[]{'s', 'e', 'c', 'r', 'e', 't'};
                store.store(out, secret);
                // !IMPORTANT!: immediately clear
                Arrays.fill(secret, '0');
            }
        }

        {
            final KeyStore store = KeyStore.getInstance("PKCS12", BouncyCastleProvider.PROVIDER_NAME);
            store.load(null, null);
            char[] entrySecret = new char[]{'s', 'e', 'c', 'r', 'e', 't'};
            store.setKeyEntry("key", userPrivateKey, entrySecret, new Certificate[]{userCertificate});
            // !IMPORTANT!: immediately clear
            Arrays.fill(entrySecret, '0');
            try (final FileOutputStream out = new FileOutputStream(Paths.get("certs", "user.jks").toFile());) {
                char[] secret = new char[]{'s', 'e', 'c', 'r', 'e', 't'};
                store.store(out, secret);
                // !IMPORTANT!: immediately clear
                Arrays.fill(secret, '0');
            }
        }
    }
}
