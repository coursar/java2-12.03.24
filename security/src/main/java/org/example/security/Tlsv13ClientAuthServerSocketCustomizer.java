package org.example.security;

import org.example.server.ServerSocketCustomizer;

import javax.net.ssl.SSLServerSocket;
import java.net.ServerSocket;

public class Tlsv13ClientAuthServerSocketCustomizer implements ServerSocketCustomizer {
    @Override
    public void customize(ServerSocket serverSocket) {
        if (!(serverSocket instanceof SSLServerSocket)) {
            throw new IllegalArgumentException("ServerSocket is not SSLServerSocket");
        }
        SSLServerSocket sslServerSocket = (SSLServerSocket) serverSocket;
        sslServerSocket.setEnabledProtocols(new String[]{"TLSv1.3"});
        sslServerSocket.setWantClientAuth(true);
    }
}
