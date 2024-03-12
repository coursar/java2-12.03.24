package org.example.learning.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ByteArrayOutputStreamMain {
    public static void main(String[] args) {
        // sometimes bytearrayoutputstream not closed, because close - noops
        try (ByteArrayOutputStream out = new ByteArrayOutputStream(4096)) {
            out.write(60);
            out.write(60);
            out.write(60);

            byte[] buffer = out.toByteArray();
            System.out.println(buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
