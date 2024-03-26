package org.example.learning.protobuf;

import org.example.learning.protobuf.model.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainProtobuf {

    public static void main(String[] args) throws IOException {
        final Path file = Paths.get("message.protobuf");

        {
            final Message proto = Message.newBuilder()
                    .setId(1L)
                    .setValue("req")
                    .build();

            try (final OutputStream out = Files.newOutputStream(file);) {
                proto.writeTo(out);
            }
        }

        {
            try (final InputStream in = Files.newInputStream(file);) {
                final Message message = Message.parseFrom(in);
                System.out.println("message = " + message);

            }
        }
    }
}
