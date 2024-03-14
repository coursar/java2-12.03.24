package org.example.network.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPClient {
    public static void main(String[] args) {
        // TODO: TCP
        try (
                Socket socket = new Socket("127.0.0.1", 9090);
                InputStream in = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
        ) { // connect
            out.write("request\n".getBytes(StandardCharsets.UTF_8));
            byte[] buffer = new byte[1024];
            int read = in.read(buffer);
            // TODO: read value
            String response = new String(buffer, 0, read, StandardCharsets.UTF_8);
            System.out.println("response = " + response);
            // disconnect
        } catch (IOException e) {
        }
    }
}
