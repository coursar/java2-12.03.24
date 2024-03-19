import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImagePostMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        // java 11+
        try (
                FileInputStream in = new FileInputStream("java.png");
                HttpClient client = HttpClient.newHttpClient();
        ) {
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080"))
                    //.POST(HttpRequest.BodyPublishers.ofInputStream(() -> in))
                    .POST(HttpRequest.BodyPublishers.ofByteArray(in.readAllBytes()))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
    }
}
