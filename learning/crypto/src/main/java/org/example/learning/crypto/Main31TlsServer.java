package org.example.learning.crypto;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

public class Main31TlsServer {
    // SSL/TLS: SSLx.x -> TLS1.2 (...) TLS1.3 (+)
    public static void main(String[] args) throws IOException {
        System.setProperty("javax.net.ssl.keyStore", "certs/server.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "secret" /* <- bad thing */);
        System.setProperty("javax.net.ssl.trustStore", "certs/truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "secret");

        ServerSocketFactory sslSocketFactory = SSLServerSocketFactory.getDefault();
        try (ServerSocket serverSocket = sslSocketFactory.createServerSocket();) {
            // TODO: setup ssl socket
            if (serverSocket instanceof SSLServerSocket) {
                SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocket;
                sslServerSocket.setEnabledProtocols(new String[]{"TLSv1.3"});
                sslServerSocket.setWantClientAuth(true);
            }
            serverSocket.bind(new InetSocketAddress("127.0.0.1", 8443));

            while (true) {
                try (
                        final Socket socket = serverSocket.accept();
                        final OutputStream out = socket.getOutputStream();
                ) {
                    if (socket instanceof SSLSocket) {
                        // FIXME: simplified version:
                        //  1. extract CN with regex
                        //  2. log credentials only on trace level, not sout!!!
                        try {
                            Principal principal = ((SSLSocket) socket).getSession().getPeerPrincipal();
                            System.out.println("principal = " + principal);
                        } catch (SSLPeerUnverifiedException e) {
                            System.out.println("anonymous principal");
                        }
                    }
                    out.write("ok!".getBytes(StandardCharsets.UTF_8));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
