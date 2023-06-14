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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class HttpController {

    private static final HttpClient client = HttpClient.newHttpClient();
//    private static String serverUrl = "http://127.0.0.1";
    private static String serverUrl = "http://localhost:8080";
    private static HttpResponse<String> lastResponse;
    private final static Gson gson = JSONReader.setupGson();

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
     * @param player player joining
     * @return the player who joined
     * @author Sandie Petersen & Philip Astrup Cramer
     */
    public static PlayerDTO joinGame(int gameID, PlayerDTO player){
        HttpRequest postPlayerRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameID + "/players"))
                .setHeader("Content-Type","application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(player)))
                .build();
        try {
            lastResponse = client.send(postPlayerRequest, HttpResponse.BodyHandlers.ofString());
            if(getLastResponseCode() == 400){
                System.out.println("The game is full");
                return null;
            }
            return gson.fromJson(lastResponse.body(), PlayerDTO.class);
        } catch (Exception exception){
            exception.printStackTrace();
        }
        //return lastResponse.statusCode();
        return null;
    }

    /**
     *
     * @param gameId id of the game
     * @param player player who is leaving
     * @return Body of response
     * @author Asbjørn Nielsen
     */
    public static String leaveGame(int gameId, PlayerDTO player){
        HttpRequest deletePlayerRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameId + "/players/" + player.getId()))
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
    /**
     * Retrieves the players in a given game from the server
     * @param gameID ID of the game
     * @return List of players in the game
     * @throws ExecutionException error when executing
     * @throws InterruptedException error if interrupted
     * @author Sandie Petersen & Søren Wünsche
     */
    public static List<PlayerDTO> playersInGame(int gameID) throws ExecutionException, InterruptedException {
        HttpRequest getPlayerRequest = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameID + "/players"))
                .GET()
                .build();
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(getPlayerRequest, HttpResponse.BodyHandlers.ofString());
        String result = response.thenApply(HttpResponse::body).get();
        List<PlayerDTO> players = gson.fromJson(result,new TypeToken<List<PlayerDTO>>(){}.getType());
        return players;
    }

    /**
     * Sends the game to the server for online play
     * @param game game to be created
     * @return gameId
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
     * @param gameID id of the game
     * @return status code
     * @author Philip Astrup Cramer
     */
    public static int startGame(int gameID, Board board){
        Game game = new Game(gson.toJson(board));
        game.setState("STARTED");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + "/games/" + gameID))
                .setHeader("Content-Type","application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(gson.toJson(game)))
                .build();
        try{
            lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return lastResponse.statusCode();
    }

    /**
     * sends the updated board to ther server
     * @param board instance of board object
     * @param version version number
     * @author Nilas Thoegersen
     * @author Philip Astrup Cramer
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
     * @return List of games
     * @throws Exception error when executing
     * @author Søren Wünsche
     */
    public static List<Game> getGameList(Optional<String> state) throws Exception {
        String url = "/games";
        if(state.isPresent()){
            url += "?state="+state.get();
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(serverUrl + url))
                .GET()
                .build();
        CompletableFuture<HttpResponse<String>> response =
                client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        String result = response.thenApply(HttpResponse::body).get();
        return gson.fromJson(result,new TypeToken<List<Game>>(){}.getType());
    }

    /**
     * Fetches a game update if here exist a newer version on the server
     * @param id id of the game
     * @param version The current version of the game
     * @return The game if found
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

    /**
     * @return StatusCode of the last request
     * @author Philip Astrup Cramer
     */
    public static int getLastResponseCode() {
        return lastResponse.statusCode();
    }
}
