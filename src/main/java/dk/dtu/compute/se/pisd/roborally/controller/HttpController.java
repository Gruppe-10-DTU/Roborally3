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

    /**
     *
     * @param gameID ID of the game to be joined
     * @param player
     * @return
     * @author Sandie Petersen & Philip Astrup Cramer
     */
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
     * Retrieves the players in a given game from the server
     * @param gameID ID of the game
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     * @author Sandie Petersen & Søren Wünsche
     */
    /**
     *
     * @param gameId
     * @param player
     * @return status code
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

    /**
     * Sends the game to the server for online play
     * @param game
     * @return
     * @author Asbjørn Nielsen
     * @author Sandie Petersen
     * @author Philip Astrup Cramer
     */
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
            return 0;
        }
    }

    /**
     * Updates the gamestate on the server to started
     * @param gameID
     * @return
     * @author Philip Astrup Cramer
     */
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

    /**
     *
     * @param game the game instance
     * @param gameID Id of the online game
     * @return HTTP status code of transaction
     * @author Philip Astrup Cramer
     */
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

    /**
     * sends the updated board to ther server
     * @param board instance of board object
     * @param version version number
     * @author Nilas Thoegersen
     */
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

    /**
     * Fetches the game form the server
     * @param gameID id of game
     * @return a Game object to process further.
     * @author Philip Astrup Cramer
     */
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

    /**
     * fetches a list of available games from the server
     * @return
     * @throws Exception
     * @author Søren Wünsche
     */
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

    /**
     *
     * @param id
     * @return
     * @throws Exception
     * @author Asbjørn Nielsen
     */
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

    /**
     * Fetches a game update if here exist a newer version on the server
     * @param id
     * @param version
     * @return
     * @author Sandie Petersen
     */
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
