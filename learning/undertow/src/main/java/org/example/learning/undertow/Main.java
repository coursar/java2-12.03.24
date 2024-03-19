package org.example.learning.undertow;

import io.undertow.Undertow;

public class Main {
    public static void main(String[] args) {
        // Builder
        Undertow server = Undertow.builder()
                .addHttpListener(-8080, "localhost")
                .setHandler(exchange -> {
                    exchange.getResponseSender().send("ok!\n");
                })
                .build();

        server.start();
    }
}
