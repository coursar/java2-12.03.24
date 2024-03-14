package org.example.network.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HTTPServer {
    public static void main(String[] args) {
        // TODO:
        //  Socket - client (stream - in/out)
        //  ServerSocket:
        //    1. bind port
        //    2. accept -> Socket
        try (ServerSocket serverSocket = new ServerSocket(8080)) { // bind
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        InputStream in = socket.getInputStream();
                        OutputStream out = socket.getOutputStream();
                ) { // socket (server side) <-> socket (client side)
                    byte[] body = "<h1>response</h1>".getBytes(StandardCharsets.UTF_8);

                    out.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
                    out.write("Connection: close\r\n".getBytes(StandardCharsets.UTF_8));
                    out.write("Content-Type: text/html\r\n".getBytes(StandardCharsets.UTF_8));
                    out.write(("Content-Length: " + body.length + "\r\n").getBytes(StandardCharsets.UTF_8));
                    out.write("\r\n".getBytes(StandardCharsets.UTF_8));
                    out.write(body);

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
