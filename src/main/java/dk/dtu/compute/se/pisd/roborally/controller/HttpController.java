package dk.dtu.compute.se.pisd.roborally.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class HttpController {

    private static final HttpClient client = HttpClient.newHttpClient();;
//    private static String serverUrl = "http://127.0.0.1";
    private static String serverUrl = "http://localhost:8080";
    private static HttpResponse<String> lastResponse;
    private static Gson gson = new Gson();

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
                .uri(URI.create(serverUrl + "/games"))
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
                .uri(URI.create(serverUrl + "/games/" + gameID + "/players/"))
                .POST(HttpRequest.BodyPublishers.ofString(String.valueOf(playerID)))
                .build();
        try {
            lastResponse = client.send(postPlayerRequest, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            exception.printStackTrace();
        }
        return lastResponse.statusCode();
    }

    public static ArrayList<String> isPlayerInGame(int gameID, int playerID){
        HttpRequest getPlayerRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameID + "/players"))
                .GET()
                .build();
        try {
            lastResponse = client.send(getPlayerRequest, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public static int createGame(GameController gameController){
        if(gameController.board.getGameId() == null) gameController.board.setGameId((int) (Math.random() * 1_000_000));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameController.board.getGameId()))
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
                .uri(URI.create(serverUrl + "/games/" + gameID))
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
                .uri(URI.create(serverUrl + "/games/" + gameID))
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

    public static boolean serverIsConnected(){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games"))
                .GET()
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            return false;
        }

        if(lastResponse == null) {
            return false;
        } else if (lastResponse.statusCode() < 300 && lastResponse.statusCode() >= 200) {
            return true;
        }

        return false;
    }

    public static int getLastResponseCode() {
        return lastResponse.statusCode();
    }

    public static List<Game> getGameList() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/games2"))
                .GET()
                .build();
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String result = response.thenApply(HttpResponse::body).get();
        List<Game> games = gson.fromJson(result,new TypeToken<List<Game>>(){}.getType());
        return games;
    }

}
