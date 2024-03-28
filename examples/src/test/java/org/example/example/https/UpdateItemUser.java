package org.example.example.https;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UpdateItemUser {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.setProperty("javax.net.ssl.trustStore", "certs/truststore.jks"); // ca cert
        System.setProperty("javax.net.ssl.trustStorePassword", "secret");
        System.setProperty("javax.net.ssl.keyStore", "certs/user.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "secret");

        try (
                HttpClient client = HttpClient.newHttpClient();
        ) {
            HttpRequest request = HttpRequest.newBuilder(URI.create("https://server.local:8443/api/items"))
                    .POST(HttpRequest.BodyPublishers.ofString(
                            """
                                  {
                                    "id": 2,
                                    "value": "Updated"
                                  }
                                  """
                    ))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.statusCode());
            System.out.println(response.body());
        }
    }
}
