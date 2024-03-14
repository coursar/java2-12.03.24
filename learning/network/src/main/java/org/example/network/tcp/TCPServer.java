package org.example.network.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPServer {
    public static void main(String[] args) {
        // TODO:
        //  Socket - client (stream - in/out)
        //  ServerSocket:
        //    1. bind port
        //    2. accept -> Socket
        try (ServerSocket serverSocket = new ServerSocket(9090)) { // bind
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        InputStream in = socket.getInputStream();
                        OutputStream out = socket.getOutputStream();
                ) { // socket (server side) <-> socket (client side)
                    out.write("response\n".getBytes(StandardCharsets.UTF_8));
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
