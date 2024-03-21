package org.example.framework.handler;

import lombok.RequiredArgsConstructor;
import org.example.server.Handler;
import org.example.server.Request;
import org.example.server.Response;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class RegexPathWithParamsHandler implements FrameworkHandler {
    private final Map<Pattern, FrameworkHandler> routes;
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
        FrameworkHandler handler = this.notFoundHandler;
        for (Map.Entry<Pattern, FrameworkHandler> routeEntry : routes.entrySet()) {
            Pattern pattern = routeEntry.getKey();

            Matcher matcher = pattern.matcher(path);
            if (!matcher.matches()) {
                continue;
            }

            Map<String, String> pathParamMap = new HashMap<>();
            Map<String, Integer> names = matcher.namedGroups();
            for (Map.Entry<String, Integer> nameEntry : names.entrySet()) {
                String group = nameEntry.getKey();
                String value = matcher.group(nameEntry.getValue());
                pathParamMap.put(group, value);
            }

            request.addPathParams(pathParamMap);
            handler = routeEntry.getValue();
        }

        try {
            handler.handle(request, response);
        } catch (Exception e) {
            this.errorHandler.handle(request, response);
        }
    }
}
