package org.example.learning.stream;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class InputStreamMain {
    public static void main(String[] args) throws IOException {
        // FileInputStream (file)
        // ByteArrayInputStream (memory)
        // BufferedInputStream (todo)

        // filename -> path
        // TODO:
        //  1. absolute (Windows: C:/, D:/, Linux: /)
        //  2. relative: java (PWD): root module
        FileInputStream in = new FileInputStream("pom.xml");
        // TODO: interpretation
        //  1. application
        //  2. text -> encoding (byte[] -> symbols)
        // TODO: UTF-8 1 byte, 2 byte
        //  1. Read Whole File ->  > 2Gb -> OutOfMemoryError
        //  2. Read Whole Line -> line > 2Gb -> OutOfMemoryError
        //    - Linux: \n (13, LF)
        //    - Windows: \r\n (10, 13, CR, LF)
        //    - Mac?: \r

        // TODO:
        //  - in.read() -1
        //  - in.read([]) -1
        byte[] buffer = new byte[4096]; // for binary
        int read;
        // TODO: -> content.size > buffer.size
        //  [hello]
        //  [ worl]
        //  [d!(orl)]
        while ((read = in.read(buffer)) != -1) {
            String string = new String(buffer, 0, read, StandardCharsets.UTF_8);
            System.out.print(string);
        }

        in.close();
    }
}
