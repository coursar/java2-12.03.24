package org.example.learning.crypto;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Main32TlsAdminClient {
    public static void main(String[] args) throws IOException {
        System.setProperty("javax.net.ssl.trustStore", "certs/truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "secret");
        System.setProperty("javax.net.ssl.keyStore", "certs/admin.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "secret");

        // PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
        final SocketFactory sslSocketFactory = SSLSocketFactory.getDefault();
        try (
                final Socket socket = sslSocketFactory.createSocket("127.0.0.1", 8443);
                final InputStream in = socket.getInputStream();
        ) {
            final byte[] bytes = in.readAllBytes();
            System.out.println(new String(bytes, StandardCharsets.UTF_8));
        }
    }
}
