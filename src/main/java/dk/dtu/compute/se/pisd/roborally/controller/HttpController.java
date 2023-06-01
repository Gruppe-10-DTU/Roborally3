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

    /**
     *
     * @param url used for changeing url in tests
     * @author Philip Astrup Cramer
     */
    public static void setServerUrl(String url){
        serverUrl = url;
    }

    public static List<String> getAvailableGames(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/AvailableGames"))
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception){
            exception.printStackTrace();
        }
        if(lastResponse != null && lastResponse.statusCode() == 200){
            return Arrays.asList(lastResponse.body().split("SPLIT_CHARACTER"));
        }
        return List.of();
    }
    public static JSONObject joinGame(int gameID){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/" + gameID))
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
                .uri(URI.create(serverUrl + "/" + (int) (Math.random() * 100_000)))
                .POST(HttpRequest.BodyPublishers.ofString(JSONReader.saveGame(gameController)))
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public static void pushNewGamestate(GameController gameController, int gameID){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/" + gameID))
                .PUT(HttpRequest.BodyPublishers.ofString(JSONReader.saveGame(gameController)))
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static JSONObject getNewGameState(int gameID){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/" + gameID))
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
}
