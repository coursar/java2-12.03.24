package org.example.learning.stream;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ReaderWriterMain {
    public static void main(String[] args) {
        // TODO: IO*Stream -> byte
        // TODO: Reader/Writer
        try (FileReader fileReader = new FileReader("pom.xml", StandardCharsets.UTF_8)) {

        } catch (IOException e) {
        }
    }
}
