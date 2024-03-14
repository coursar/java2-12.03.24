package org.example.network.udp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Slf4j
public class UDPServer {
    public static void main(String[] args) {
        // TODO: client -> random port (ephemeral)
        try (DatagramSocket socket = new DatagramSocket(9090)) {
            while (true) {
                try {
                    byte[] requestBuffer = new byte[1024];
                    DatagramPacket request = new DatagramPacket(requestBuffer, requestBuffer.length);
                    socket.receive(request);

                    String message = new String(request.getData(), 0, request.getLength(), StandardCharsets.UTF_8);
                    System.out.println(message);
                    System.out.println(request.getSocketAddress());

                    byte[] responseBuffer = "response\n".getBytes(StandardCharsets.UTF_8);
                    DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length, request.getSocketAddress());
                    socket.send(response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
