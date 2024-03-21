package org.example.example.regexparams.api;

import org.example.framework.handler.FrameworkRequest;
import org.example.framework.handler.FrameworkResponse;
import org.example.server.Request;
import org.example.server.Response;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CrudApi {
    public void getAll(FrameworkRequest request, FrameworkResponse response) throws Exception {
        byte[] responseBody = "List of items".getBytes(StandardCharsets.UTF_8);

        // TODO: if you open -> you close it -> libraries also follow
        //  in your case we don't close out
        OutputStream out = response.getOut();
        out.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    }

    public void getById(FrameworkRequest request, FrameworkResponse response) throws Exception {
        String idParam = request.getPathParam("id").orElseThrow(); // pass custom exception
        long id = Long.parseLong(idParam);

        byte[] responseBody = ("Concrete item with id: " + id).getBytes(StandardCharsets.UTF_8);

        // TODO: if you open -> you close it -> libraries also follow
        //  in your case we don't close out
        OutputStream out = response.getOut();
        out.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
        out.write("Content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(("Content-Length: " + responseBody.length + "\r\n").getBytes(StandardCharsets.UTF_8));
        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
        out.write(responseBody);
    }
}
