package org.example.learning.stream;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

// TODO: System
//  1. Protocol:
//    - Text, UTF-8, '\r\n'
public class OutputStreamMain {
    public static void main(String[] args) {
        // TODO:
        //  1. FileOutputStream
        //  2. ByteArrayOutputStream
        //  3. BufferedOutputStream
        try (FileOutputStream out = new FileOutputStream("out.txt")) {
            String data = "Привет мир!";
            out.write(data.getBytes(StandardCharsets.UTF_16BE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
