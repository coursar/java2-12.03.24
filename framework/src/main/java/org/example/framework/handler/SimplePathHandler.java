package org.example.framework.handler;

import lombok.RequiredArgsConstructor;
import org.example.server.Handler;
import org.example.server.Request;
import org.example.server.Response;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RequiredArgsConstructor
public class SimplePathHandler implements FrameworkHandler {
    private final Map<String, FrameworkHandler> routes;
    private final FrameworkHandler notFoundHandler = (request, response) -> {
        byte[] responseBody = "Not Found".getBytes(StandardCharsets.UTF_8);

        OutputStream out = response.getOut();
        out.write("HTTP/1.1 404 Not Found\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    };

    private final FrameworkHandler errorHandler = (request, response) -> {
        byte[] responseBody = "Internal Server Error".getBytes(StandardCharsets.UTF_8);

        // FIXME: pass Exception info to error handler
        OutputStream out = response.getOut();
        out.write("HTTP/1.1 500 Internal Server Error\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    };

    @Override
    public void handle(FrameworkRequest request, FrameworkResponse response) throws Exception {
        String path = request.getPath();
        FrameworkHandler handler = this.routes.getOrDefault(path, this.notFoundHandler);
        try {
            handler.handle(request, response);
        } catch (Exception e) {
            this.errorHandler.handle(request, response);
        }
    }
}
