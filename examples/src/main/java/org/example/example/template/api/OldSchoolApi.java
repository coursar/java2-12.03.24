package org.example.example.template.api;

import lombok.SneakyThrows;
import org.example.framework.handler.FrameworkRequest;
import org.example.framework.handler.FrameworkResponse;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

public class OldSchoolApi {
    private final String html;

    @SneakyThrows // FIXME: bad code!!!
    public OldSchoolApi() {
        // TODO: Path -> interface
        //  /part1/part2/part2
        //  Paths.get() & Path.of()
        this.html = Files.readString(Path.of("template.index.html"), StandardCharsets.UTF_8);
    }

    // TODO: Dynamic Web Pages
    public void getIndexHtml(FrameworkRequest request, FrameworkResponse response) throws Exception {
        String processed = this.html.replace("%date%", LocalDate.now().toString());
        byte[] responseBody = processed.getBytes(StandardCharsets.UTF_8);

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
