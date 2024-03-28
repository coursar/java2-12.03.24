package org.example.security;

import org.example.server.ServerSocketFactoryCreator;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

public class TlsServerSocketFactoryCreator implements ServerSocketFactoryCreator {
    @Override
    public ServerSocketFactory getServerSocketFactory() {
        return SSLServerSocketFactory.getDefault();
    }
}
