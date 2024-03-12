package org.example.learning.stream;

import java.io.*;

public class BufferedStreamsMain {
    public static void main(String[] args) {
        // BufferedInputStream -> FilterInputStream (wrapper of stream)
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream("pom.xml"))) {
            byte[] bytes = in.readNBytes(1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("out.txt"))) {
            out.write(60); // don't write to disk -> write to buffer
            // TODO:
            //  1. flush or close
            //  2. when buffer filled
            out.flush(); // or close -> buffer -> OS
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        finally {
            out.close() -> inner.close()
        }
         */
    }
}
