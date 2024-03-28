package org.example.server;

import java.net.ServerSocket;

@FunctionalInterface
public interface ServerSocketCustomizer {
    void customize(ServerSocket serverSocket);

    static ServerSocketCustomizer noOps() {
        return serverSocket -> {};
    }
}
