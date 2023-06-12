package dk.dtu.compute.se.pisd.roborally.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import dk.dtu.compute.se.pisd.roborally.model.PlayerDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HttpController {

    private static final HttpClient client = HttpClient.newHttpClient();;
//    private static String serverUrl = "http://127.0.0.1";
    private static String serverUrl = "http://localhost:8080";
    private static HttpResponse<String> lastResponse;
    private static Gson gson = JSONReader.setupGson();

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
    public static String joinGame(int gameID, PlayerDTO player){
        HttpRequest postPlayerRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameID + "/players"))
                .setHeader("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(player)))
                .build();
        try {
            lastResponse = client.send(postPlayerRequest, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            exception.printStackTrace();
        }
        //return lastResponse.statusCode();
        return lastResponse.body();
    }

    /**
     *
     * @param gameId
     * @param player
     * @return statues code
     * @author Asbjørn Nielsen
     */
    public static String leaveGame(int gameId, PlayerDTO player){
        HttpRequest deletePlayerRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameId + "/players/" + player.getName()))
                .setHeader("Content-Type","application/json")
                .DELETE()
                .build();
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(deletePlayerRequest, HttpResponse.BodyHandlers.ofString());
        try {
            String result = response.thenApply(HttpResponse::body).get();
            return result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<PlayerDTO> playersInGame(int gameID) throws ExecutionException, InterruptedException {
        HttpRequest getPlayerRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameID + "/players"))
                .GET()
                .build();
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(getPlayerRequest, HttpResponse.BodyHandlers.ofString());
        String result = response.thenApply(HttpResponse::body).get();
        List<PlayerDTO> Player = gson.fromJson(result,new TypeToken<List<PlayerDTO>>(){}.getType());
        return Player;
    }

    public static int createGame(Game game){
         String sGame = gson.toJson(game);
         HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games"))
                 .setHeader("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(sGame))
                .build();
         Game createdGameId;

         try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            createdGameId = gson.fromJson(lastResponse.body(), Game.class);
            return createdGameId.getId();
        } catch (Exception exception){
            exception.printStackTrace();
            return 418;
        }
    }
    public static int startGame(int gameID){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameID + "/gamestates"))
                .PUT(HttpRequest.BodyPublishers.ofString("STARTED"))
                .build();
        try{
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return lastResponse.statusCode();
    }

    public static int pushGameUpdate(Game game, Board board){
        game.setBoard(gson.toJson(board));
        return pushGameUpdate(game, game.getId());
    }

    public static int pushGameUpdate(Game game, int gameID){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameID))
                .setHeader("Content-Type","application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(game)))
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            exception.printStackTrace();
            return 418;
        }
        return lastResponse.statusCode();
    }

    public static void updateBoard(Board board, int version){

        Game game = new Game(JSONReader.saveGame(board), version);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/"+board.getGameId()))
                .setHeader("Content-Type","application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(game)))
                .build();

        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static Game getGame(int gameID){
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
            return gson.fromJson(lastResponse.body(),Game.class);
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
                .uri(URI.create(serverUrl + "/games"))
                .GET()
                .build();
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String result = response.thenApply(HttpResponse::body).get();
        return gson.fromJson(result,new TypeToken<List<Game>>(){}.getType());
    }
    public static String removeGame(int id) throws Exception{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl+"/games/" + id))
                .DELETE()
                .build();
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        try {
            String result = response.thenApply(HttpResponse::body).get();
            return result;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Game getGameUpdate(int id, int version) {

        Game game = null;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + id + "?version=" + version ))
                .GET()
                .build();
        try {
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (!lastResponse.body().equals("")) {
                game = gson.fromJson(lastResponse.body(), Game.class);
            }
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return game;
    }

}
