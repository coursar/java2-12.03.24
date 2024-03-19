package org.example.learning.stream;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class StreamCopyMain {
    public static void main(String[] args) {
        try (
                FileInputStream in = new FileInputStream("pom.xml");
                FileOutputStream out = new FileOutputStream("pom-copy.xml");
        ) {
            byte[] buffer = new byte[4096];
            int read;

            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace(); // FIXME: logs
        }

        // IOUtils.copy(in, out)
    }
}
