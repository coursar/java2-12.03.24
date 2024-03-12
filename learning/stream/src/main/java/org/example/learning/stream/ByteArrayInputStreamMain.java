package org.example.learning.stream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ByteArrayInputStreamMain {
    public static void main(String[] args) {
        byte[] bytes = "Hello world".getBytes(StandardCharsets.UTF_8);
        // sometimes bytearrayinputstream not closed, because close - no-ops
        try (ByteArrayInputStream in = new ByteArrayInputStream(bytes);) {
            int read = in.read();
            System.out.println(read);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
