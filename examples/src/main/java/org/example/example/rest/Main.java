package org.example.example.rest;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.example.rest.api.CrudApi;
import org.example.example.rest.model.Item;
import org.example.example.rest.service.CrudService;
import org.example.framework.handler.FrameworkHandler;
import org.example.framework.handler.RestHandler;
import org.example.framework.http.Method;
import org.example.server.Server;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        JsonMapper jsonMapper = new JsonMapper();
        ModelMapper modelMapper = new ModelMapper();

        List<Item> items = new ArrayList<>();

        long nextId = 1;
        items.add(new Item(nextId++, "value", Instant.now()));
        CrudService crudService = new CrudService(items, nextId);

        CrudApi crudApi = new CrudApi(crudService, jsonMapper, modelMapper);
        Map<Method, Map<String, FrameworkHandler>> routes = Map.of(
                // .GET("/api/items/{id}, crudApi::getById)
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
