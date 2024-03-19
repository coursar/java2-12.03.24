package org.example.learning.stream;

import java.util.Arrays;

public class SplitMain {
    public static void main(String[] args) {
        String[] split = "Host: localhost:8080".split(": ", 2);
        System.out.println(Arrays.asList(split));
    }
}
