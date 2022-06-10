package request;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HeadRequestJava {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder().build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://github.com"))
                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                .build();

                HttpResponse<Void> response = client.send(request,
                HttpResponse.BodyHandlers.discarding());

        // Returnează o vizualizare multi-hartă nemodificabilă a acestui HttpHeaders.
        // Harta conține cheia șirului de caractere, cu lista de șiruri.
        HttpHeaders headers = response.headers();
        headers.map().forEach((key, values) ->
                System.out.printf("%s = %s%n", key, values));
    }
}
