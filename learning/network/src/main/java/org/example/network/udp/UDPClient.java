package org.example.network.udp;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class UDPClient {
    public static void main(String[] args) {
        // TODO: client -> random port (ephemeral)
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] requestBuffer = "request\n".getBytes(StandardCharsets.UTF_8);
            DatagramPacket request = new DatagramPacket(requestBuffer, requestBuffer.length, new InetSocketAddress("127.0.0.1", 9090));
            socket.send(request);

            byte[] responseBuffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);
            socket.receive(response);

            String message = new String(response.getData(), 0, response.getLength(), StandardCharsets.UTF_8);
            System.out.println(message);
            System.out.println(response.getSocketAddress());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
