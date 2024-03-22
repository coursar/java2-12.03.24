package org.example.example.rest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GetItem {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (
                HttpClient client = HttpClient.newHttpClient();
        ) {
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/api/items/1"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
    }
}
