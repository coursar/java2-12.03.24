import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ImagePostChunkedMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        // java 11+
        try (
                HttpClient client = HttpClient.newHttpClient();
        ) {
            FileInputStream in = new FileInputStream("java.png");
            HttpRequest request = HttpRequest.newBuilder(URI.create("http://localhost:8080/path?key=value1&key=value2"))
                    .POST(HttpRequest.BodyPublishers.ofInputStream(() -> in))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
    }
}
