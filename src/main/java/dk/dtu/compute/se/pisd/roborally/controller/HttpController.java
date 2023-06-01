package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;

import org.json.JSONObject;
import java.net.URI;
import java.net.http.*;
import java.util.*;

public class HttpController {

    private static final HttpClient client = HttpClient.newHttpClient();;
    private static String serverUrl = "http://127.0.0.1";
    private static HttpResponse<String> lastResponse;

    public static void setServerUrl(String url){
        serverUrl = url;
    }

    public static List<String> getAvailableGames(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/AvailableGames"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception){
            exception.printStackTrace();
        }
        if(response != null && response.statusCode() == 200){
            return Arrays.asList(response.body().split("SPLIT_CHARACTER"));
        }
        return List.of();
    }
    public static JSONObject joinGame(int gameId){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/" + gameId))
                .GET()
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            exception.printStackTrace();
        }
        if(lastResponse != null && lastResponse.statusCode() == 200) {
            return new JSONObject(lastResponse.body());
        }
        return null;
    }
    public static void createGame(GameController gameController){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(JSONReader.saveGame(gameController)))
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
