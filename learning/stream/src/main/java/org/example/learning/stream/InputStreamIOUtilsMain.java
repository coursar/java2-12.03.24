package org.example.learning.stream;

import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InputStreamIOUtilsMain {
    public static void main(String[] args) {
        FileInputStream in = null;
        try {
            in = new FileInputStream("pom.xml");
            byte[] buffer = new byte[4096];
            int read;

            while ((read = in.read(buffer)) != -1) {
                String string = new String(buffer, 0, read, StandardCharsets.UTF_8);
                System.out.print(string);
            }
        } catch (IOException e) {
            e.printStackTrace(); // FIXME: logs
        } finally {
            IOUtils.closeQuietly(in);
        }
    }
}
