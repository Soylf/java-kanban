package oop.taskTreker.server;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class KVClient {
    private static final String URL = "http://localhost:8055/";

    private final String apiToken;
    private final HttpClient httpClient;

    public KVClient() {
        apiToken = registr();
        httpClient = HttpClient.newHttpClient();
    }



    private String registr() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "register"))
                    .GET()
                    .build();
            HttpResponse<String> response =  httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200) {
                throw new RuntimeException("");
            }
            return response.body();
        }catch (IOException | InterruptedException e) {
            throw new RuntimeException("");
        }
    }

    public String load(String key) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "load/" + key + "?API_TOKEN=" + apiToken))
                    .GET()
                    .build();
            HttpResponse<String> response =  httpClient.send(request, HttpResponse.BodyHandlers.ofString());


            if(response.statusCode() == 404){
                return null;
            }
            if(response.statusCode() != 200) {
                throw new RuntimeException("Хой, нус... Агахь, зырьк" +  response.statusCode());
            }
            return response.body();
        }catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ну...че-то не так, а чет с запросом братишь ^_^");
        }
    }

    public void put(String key, String value) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "save/" + key + "?API_TOKEN=" + apiToken))
                    .POST(HttpRequest.BodyPublishers.ofString(value))
                    .build();

            HttpResponse<Void> response =  httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            if(response.statusCode() != 200) {
                throw new RuntimeException("Хой, нус... Агахь, зырьк" +  response.statusCode());
            }
        }catch (IOException | InterruptedException e) {
            throw new RuntimeException("Ну...че-то не так, а чет с запросом братишь ^_^");
        }
    }
}
