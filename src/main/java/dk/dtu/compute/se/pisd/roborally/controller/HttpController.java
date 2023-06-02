package dk.dtu.compute.se.pisd.roborally.controller;

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
     * @param url used for changing url in tests
     * @author Philip Astrup Cramer
     */
    public static void setServerUrl(String url){
        serverUrl = url;
    }

    public static List<String> getAvailableGames(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/AvailableGames"))
                .GET()
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception){
            exception.printStackTrace();
            return null;
        }
        if(lastResponse.statusCode() < 300 && lastResponse.statusCode() >= 200){
            // Parsing to correct format
        }
        return null;
    }
    public static int joinGame(int gameID, int playerID){
        HttpRequest postPlayerRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/Games/" + gameID + "/Player"+ playerID))
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(playerID)))
                .build();
        try {
            lastResponse = client.send(postPlayerRequest, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            exception.printStackTrace();
        }
        return lastResponse.statusCode();
    }
    public static int createGame(GameController gameController){
        if(gameController.board.getGameId() == null) gameController.board.setGameId((int) (Math.random() * 1_000_000));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/" + gameController.board.getGameId()))
                .POST(HttpRequest.BodyPublishers.ofString(JSONReader.saveGame(gameController)))
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception exception){
            exception.printStackTrace();
            return 418;
        }
        return lastResponse.statusCode();
    }

    public static int pushNewGameState(GameController gameController, int gameID){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/" + gameID))
                .PUT(HttpRequest.BodyPublishers.ofString(JSONReader.saveGame(gameController)))
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            exception.printStackTrace();
            return 418;
        }
        return lastResponse.statusCode();
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
            return null;
        }
        if(lastResponse.statusCode() < 300 && lastResponse.statusCode() >= 200) {
            return new JSONObject(lastResponse.body());
        }
        return null;
    }

    public static int getLastResponseCode() {
        return lastResponse.statusCode();
    }
}
