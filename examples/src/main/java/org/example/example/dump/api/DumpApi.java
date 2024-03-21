package org.example.example.dump.api;

import org.example.framework.handler.FrameworkRequest;
import org.example.framework.handler.FrameworkResponse;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DumpApi {
    // TODO: Dynamic Web Pages
    public void dump(FrameworkRequest request, FrameworkResponse response) throws Exception {
        byte[] responseBody = "ok\n".getBytes(StandardCharsets.UTF_8);

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
