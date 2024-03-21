package org.example.example.simpleparams;

import org.example.example.simpleparams.api.CrudApi;
import org.example.framework.handler.FrameworkHandler;
import org.example.framework.handler.SimplePathHandler;
import org.example.server.Handler;
import org.example.server.Server;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // FIXME: move to builder
        CrudApi crudApi = new CrudApi();
        Map<String, FrameworkHandler> routes = Map.of(
                "/api/crud.getAll", crudApi::getAll, // (req, res) -> {crudApi.getAll(req, res);}
                "/api/crud.getById", crudApi::getById
        );

        SimplePathHandler router = new SimplePathHandler(routes);
        Server server = new Server("localhost", 8080, router);

        server.start();
    }
}
