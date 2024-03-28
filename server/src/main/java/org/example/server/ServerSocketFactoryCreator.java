package org.example.server;

import javax.net.ServerSocketFactory;

@FunctionalInterface
public interface ServerSocketFactoryCreator {
    ServerSocketFactory getServerSocketFactory();
    static ServerSocketFactoryCreator getDefault() {
        return ServerSocketFactory::getDefault;
    }
}
