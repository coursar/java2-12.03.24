package org.example.server;

public interface Handler {
    void handle(Request request, Response response) throws Exception;
}
