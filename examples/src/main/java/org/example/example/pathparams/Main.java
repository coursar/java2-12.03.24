package org.example.example.pathparams;

import org.example.example.pathparams.api.CrudApi;
import org.example.framework.handler.FrameworkHandler;
import org.example.framework.handler.PathWithParamsHandler;
import org.example.framework.handler.RegexPathWithParamsHandler;
import org.example.server.Server;

import java.util.Map;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        // FIXME: move to builder
        CrudApi crudApi = new CrudApi();
        Map<String, FrameworkHandler> routes = Map.of(
                "/api/items", crudApi::getAll,
                "/api/items/{id}", crudApi::getById
        );

        FrameworkHandler router = new PathWithParamsHandler(routes);
        Server server = new Server("localhost", 8080, router);

        server.start();
    }
}
