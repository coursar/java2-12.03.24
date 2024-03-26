package org.example.learning.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.example.learning.avro.model.Message;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainAvro {
    public static void main(String[] args) throws IOException {
        final Path file = Paths.get("message.avro");
        {
            final Message avro = Message.newBuilder()
                    .setId(1L)
                    .setValue("avro")
                    .build();

            final SpecificDatumWriter<Message> datumWriter = new SpecificDatumWriter<>();
            try (final DataFileWriter<Message> writer = new DataFileWriter<>(datumWriter)) {
                writer.create(avro.getSchema(), file.toFile());
                writer.append(avro);
            }
        }

        {
            final SpecificDatumReader<Message> datumReader = new SpecificDatumReader<>(Message.class);
            try (final DataFileReader<Message> reader = new DataFileReader<>(file.toFile(), datumReader);) {
                while (reader.hasNext()) {
                    final Message avro = reader.next();
                    System.out.println("avro = " + avro);
                }
            }
        }
    }
}
