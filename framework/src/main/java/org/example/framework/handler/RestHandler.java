package org.example.framework.handler;

import org.example.framework.http.Method;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RestHandler implements FrameworkHandler {
    private final Map<Method, PathWithParamsHandler> routes;

    private final FrameworkHandler methodNotAllowedHandler = (request, response) -> {
        byte[] responseBody = "Method not allowed".getBytes(StandardCharsets.UTF_8);

        OutputStream out = response.getOut();
        out.write("HTTP/1.1 405 Method Not Allowed\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    };

    public RestHandler(Map<Method, Map<String, FrameworkHandler>> routes) {
        this.routes = new HashMap<>(1 + (int)(routes.size() / 0.75f), 0.75f);
        for (Map.Entry<Method, Map<String, FrameworkHandler>> entry : routes.entrySet()) {
            Method key = entry.getKey();
            PathWithParamsHandler value = new PathWithParamsHandler(entry.getValue());
            this.routes.put(key, value);
        }
    }

    @Override
    public void handle(FrameworkRequest request, FrameworkResponse response) throws Exception {
        try {
            Method method = Method.valueOf(request.getMethod());
            PathWithParamsHandler handler = Optional.ofNullable(this.routes.get(method))
                    // just for simplicity -- use custom exception
                    .orElseThrow(() -> new IllegalArgumentException("Method not supported"));
            handler.handle(request, response);
        } catch (IllegalArgumentException e) {
            this.methodNotAllowedHandler.handle(request, response);
        }
    }
}
