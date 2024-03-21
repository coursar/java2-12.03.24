package org.example.example.rest;

import org.example.example.rest.api.CrudApi;
import org.example.framework.handler.FrameworkHandler;
import org.example.framework.handler.RestHandler;
import org.example.framework.http.Method;
import org.example.server.Server;

import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        // FIXME: move to builder
        CrudApi crudApi = new CrudApi();
        Map<Method, Map<String, FrameworkHandler>> routes = Map.of(
                Method.GET, Map.of(
                        "/api/items", crudApi::getAll,
                        "/api/items/{id}", crudApi::getById
                ),
                Method.POST, Map.of(
                        "/api/items", crudApi::save
                )
        );

        FrameworkHandler router = new RestHandler(routes);
        Server server = new Server("localhost", 8080, router);

        server.start();
    }
}
