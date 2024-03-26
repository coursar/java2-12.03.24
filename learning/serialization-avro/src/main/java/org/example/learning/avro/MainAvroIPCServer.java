package org.example.learning.avro;

import org.apache.avro.ipc.Server;
import org.apache.avro.ipc.jetty.HttpServer;
import org.apache.avro.ipc.specific.SpecificResponder;
import org.example.learning.avro.protocol.MessageProtocol;
import org.example.learning.avro.protocol.model.Message;

import java.io.IOException;

public class MainAvroIPCServer {
    public static void main(String[] args) throws IOException {
        final Server server = new HttpServer(new SpecificResponder(
                MessageProtocol.class,
                new MessageProtocolImpl()
        ), 8080);
        server.start();
    }

    public static class MessageProtocolImpl implements MessageProtocol {
        @Override
        public Message rpc(final Message req) {
            // handler analogue (CrudApi)
            return Message.newBuilder(req)
                    .setValue("response")
                    .build();
        }
    }
}
