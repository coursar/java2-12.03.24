package org.example.example.dump;

import org.example.example.dump.api.DumpApi;
import org.example.framework.handler.FrameworkHandler;
import org.example.framework.handler.SimplePathHandler;
import org.example.server.Server;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // FIXME: move to builder
        DumpApi dumpApi = new DumpApi();
        Map<String, FrameworkHandler> routes = Map.of(
                "/api/dump", dumpApi::dump
        );

        SimplePathHandler router = new SimplePathHandler(routes);
        Server server = new Server("localhost", 8080, router);

        server.start();
    }
}
