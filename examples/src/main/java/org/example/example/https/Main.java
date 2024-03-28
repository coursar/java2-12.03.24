package org.example.example.https;

import com.fasterxml.jackson.databind.json.JsonMapper;
import org.example.example.https.api.CrudApi;
import org.example.example.https.model.Item;
import org.example.example.https.service.CrudService;
import org.example.framework.handler.FrameworkHandler;
import org.example.framework.handler.RestHandler;
import org.example.framework.http.Method;
import org.example.security.TlsServerSocketFactoryCreator;
import org.example.security.Tlsv13ClientAuthServerSocketCustomizer;
import org.example.security.X509AuthHandler;
import org.example.server.Server;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        System.setProperty("javax.net.ssl.keyStore", "certs/server.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "secret" /* <- bad thing */);
        System.setProperty("javax.net.ssl.trustStore", "certs/truststore.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "secret" /* <- bad thing */);

        JsonMapper jsonMapper = new JsonMapper();
        ModelMapper modelMapper = new ModelMapper();

        List<Item> items = new ArrayList<>();

        long nextId = 1;
        items.add(new Item(nextId++, "value", "CN=user,OU=Dev,O=Organization,L=Moscow,C=RU", Instant.now()));
        items.add(new Item(nextId++, "value", "CN=user,OU=Dev,O=Organization,L=Moscow,C=RU", Instant.now()));
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
                ),
                Method.DELETE, Map.of(
                        "/api/items/{id}", crudApi::deleteById
                )
        );

        FrameworkHandler router = new RestHandler(routes);
        X509AuthHandler handler = new X509AuthHandler(router);
        Server server = new Server(
                "0.0.0.0",
                8443,
                new TlsServerSocketFactoryCreator(),
                new Tlsv13ClientAuthServerSocketCustomizer(),
                handler
        );

        server.start();
    }
}
