package org.example.learning.jetty;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        // 1. Server
        // 2. Request, Response -> Servlet (Java EE/Jakarta EE)
        // 3. Handler
        Server server = new Server(8080);

        server.setHandler(new AbstractHandler() {
            @Override
            public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
                // request.getParameter("key") // single first -> value1
                // request.getParameterValues("key") // value1, value2
                baseRequest.setHandled(true); // from Jetty
                response.getOutputStream().write("ok!\n".getBytes(StandardCharsets.UTF_8));
            }
        });

        server.start();
    }
}
