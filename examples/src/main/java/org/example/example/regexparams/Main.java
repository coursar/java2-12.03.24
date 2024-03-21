package org.example.example.regexparams;

import org.example.example.regexparams.api.CrudApi;
import org.example.framework.handler.FrameworkHandler;
import org.example.framework.handler.RegexPathWithParamsHandler;
import org.example.server.Server;

import java.util.Map;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws Exception {
        // FIXME: move to builder
        CrudApi crudApi = new CrudApi();
        Map<Pattern, FrameworkHandler> routes = Map.of(
                // /api/items/{id}
                Pattern.compile("^/api/items$"), crudApi::getAll, // (req, res) -> {crudApi.getAll(req, res);}
                Pattern.compile("^/api/items/(?<id>\\d+)$"), crudApi::getById
        );

        RegexPathWithParamsHandler router = new RegexPathWithParamsHandler(routes);
        Server server = new Server("localhost", 8080, router);

        server.start();
    }
}
