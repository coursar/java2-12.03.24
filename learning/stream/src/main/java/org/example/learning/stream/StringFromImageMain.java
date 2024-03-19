package org.example.learning.stream;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StringFromImageMain {
    public static void main(String[] args) {
        try (FileInputStream fileInputStream = new FileInputStream("java.png")) {
            byte[] bytes = fileInputStream.readAllBytes();
            String string = new String(bytes, StandardCharsets.UTF_8);
            System.out.println(string);
        } catch (IOException e) {
        }
    }
}
