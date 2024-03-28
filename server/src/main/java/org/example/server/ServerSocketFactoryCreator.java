package org.example.server;

import javax.net.ServerSocketFactory;

@FunctionalInterface
public interface ServerSocketFactoryCreator {
    ServerSocketFactory getServerSocketFactory();
    static ServerSocketFactory getDefault() {
        return ServerSocketFactory.getDefault();
    }
}
