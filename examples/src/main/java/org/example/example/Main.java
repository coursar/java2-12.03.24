package org.example.example;

import org.example.server.Server;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) throws Exception {
        // FIXME: move to builder
        Server server = new Server("localhost", 8080,
                (request, response) -> {
                    byte[] responseBody = "<h1>response</h1>".getBytes(StandardCharsets.UTF_8);

                    // TODO: if you open -> you close it
                    //  in your case we don't close out
                    OutputStream out = response.getOut();
                    out.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
                    out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
                    out.write("Content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
                    out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
                    out.write("\r\n".getBytes(StandardCharsets.UTF_8));
                    out.write(responseBody);
                }
        );

        server.start();
    }
}
