package org.example.example.template;

import org.example.example.template.api.OldSchoolApi;
import org.example.framework.handler.FrameworkHandler;
import org.example.framework.handler.SimplePathHandler;
import org.example.server.Server;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // FIXME: move to builder
        OldSchoolApi oldSchoolApi = new OldSchoolApi();
        Map<String, FrameworkHandler> routes = Map.of(
                "/api/index.html", oldSchoolApi::getIndexHtml
        );

        SimplePathHandler router = new SimplePathHandler(routes);
        Server server = new Server("localhost", 8080, router);

        server.start();
    }
}
