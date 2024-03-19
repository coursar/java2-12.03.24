package org.example.server;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.primitives.Bytes;
import lombok.RequiredArgsConstructor;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class Server {
    public static final byte[] CRLFCRL = {'\r', '\n', '\r', '\n'};
    public static final byte[] CRLF = {'\r', '\n'};
    public static final int DEFAULT_BUFFER_SIZE = 8192;
    public static final int MAX_BODY_LENGTH = 10 * 1024 * 1024;
    private final String host;
    // TODO: check port in constructor
    private final int port;
    private final Handler handler;

    public void start() throws Exception {
        try (ServerSocket serverSocket = new ServerSocket()) { // bind
            serverSocket.bind(new InetSocketAddress(this.host, this.port));
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        InputStream in = new BufferedInputStream(socket.getInputStream(), DEFAULT_BUFFER_SIZE);
                        OutputStream out = new BufferedOutputStream(socket.getOutputStream());
                ) { // socket (server side) <-> socket (client side)
                    // 2Gb & stream end
                    // byte[] bytes = in.readAllBytes();
                    // boolean markSupported = in.markSupported();
                    in.mark(8192);

                    byte[] buffer = new byte[8192];
                    int read = in.read(buffer);
                    if (read == -1) {
                        // TODO: log
                        continue;
                    }

                    // 0 read <- \r\n\r\n
                    // TODO:
                    //  1. Guava
                    //  2. Apache Commons IO, Lang

                    int CRFLCRLFIndex = Bytes.indexOf(buffer, CRLFCRL); // FIXME: remove guava
                    if (CRFLCRLFIndex == -1) {
                        byte[] body = "Entity Too Large".getBytes(StandardCharsets.UTF_8);

                        out.write("HTTP/1.1 413 Entity Too Large\r\n".getBytes(StandardCharsets.UTF_8));
                        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
                        out.write("Content-Type: text/plain\r\n".getBytes(StandardCharsets.UTF_8));
                        out.write(("Content-Length: " + body.length + "\r\n").getBytes(StandardCharsets.UTF_8));
                        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
                        out.write(body);
                        continue;
                    }

                    // TODO:
                    //  RQ\r\n<requestLineEndIndex>
                    //  Header: value\r\n
                    //  Header: value<CRLFCRLFEndIndex>\r\n<headersEndIndex>
                    //  \r\n
                    //  <bodyStartIndex>[Optional Body]

                    int requestLineEndIndex = Bytes.indexOf(buffer, CRLF) + CRLF.length;
                    // HTTP/1.1 ISO-..., US_ASCII -> simplify UTF-8 (ASCII)
                    String requestLine = new String(buffer, 0, requestLineEndIndex, StandardCharsets.UTF_8);

                    int headersEndIndex = CRFLCRLFIndex + CRLF.length;
                    String headersString = new String(buffer, requestLineEndIndex, headersEndIndex - requestLineEndIndex, StandardCharsets.UTF_8);

                    String[] headersArray = headersString.split("\r\n");
                    // TODO: key-value -> Map
                    //  1. MultiMap -> Guava, Apache Common Collections
                    //  2. First Value
                    //  Header: value
                    //  Header: value
                    //  1.1. Header -> value, value (List)
                    //  1.2. Header -> value (Set)

                    //  Host: localhost:8080

                    Multimap<String, String> headersMap = LinkedHashMultimap.create(headersArray.length, 1);
                    for (String headerLine : headersArray) {
                        String[] splitHeader = headerLine.split(": ", 2);
                        // FIXME: validation
                        String headerKey = splitHeader[0];
                        String headerValue = splitHeader[1]; // TODO: headerValue split
                        headersMap.put(headerKey, headerValue);
                    }

                    // Content-Length:
                    int bodyStartIndex = CRFLCRLFIndex + CRLFCRL.length;
                    // ???

                    // TODO: everything is String: Content-Length: 4<- String | Framework/Library
                    int contentLength = headersMap.get("Content-Length")
                            .stream()
                            .map(Integer::parseInt)
                            .findFirst()
                            .orElse(0);

                    if (contentLength > MAX_BODY_LENGTH) {
                        byte[] body = "Entity Too Large".getBytes(StandardCharsets.UTF_8);

                        out.write("HTTP/1.1 413 Entity Too Large\r\n".getBytes(StandardCharsets.UTF_8));
                        out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
                        out.write("Content-Type: text/plain\r\n".getBytes(StandardCharsets.UTF_8));
                        out.write(("Content-Length: " + body.length + "\r\n").getBytes(StandardCharsets.UTF_8));
                        out.write("\r\n".getBytes(StandardCharsets.UTF_8));
                        out.write(body);
                        continue;
                    }

                    byte[] body = new byte[]{};
                    if (contentLength > 0) {
                        in.reset();
                        long skipped = in.skip(bodyStartIndex); // TODO: if
                        body = in.readNBytes(contentLength); // while() { read(buf) } === contentLength
                    }

                    Request request = Request.builder()
                            // TODO: path
                            // TODO: query
                            .headers(headersMap)
                            .body(body)
                            .build();
                    Response response = Response.builder()
                            .out(out)
                            .build();

                    this.handler.handle(request, response);
                    // flush()
                    // close() -> flush()

                    // close socket -> close connection
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
