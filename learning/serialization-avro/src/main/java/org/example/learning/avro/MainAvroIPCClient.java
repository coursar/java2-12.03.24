package org.example.learning.avro;

import org.apache.avro.ipc.HttpTransceiver;
import org.apache.avro.ipc.specific.SpecificRequestor;
import org.example.learning.avro.protocol.MessageProtocol;
import org.example.learning.avro.protocol.model.Message;

import java.io.IOException;
import java.net.URI;

public class MainAvroIPCClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (final HttpTransceiver transceiver = new HttpTransceiver(URI.create("http://localhost:8080").toURL());) {
            final MessageProtocol client = SpecificRequestor.getClient(MessageProtocol.class, transceiver);
            final Message res = client.rpc(new Message(1L, "req"));
            System.out.println("res = " + res);
        }
    }
}
