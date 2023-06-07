package dk.dtu.compute.se.pisd.roborally.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Game;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BoardUpdateThread extends Thread {

    private int gameId;
    private boolean gameEnded = false;

    private static final Gson gson = new GsonBuilder().create();
    private static final HttpClient client = HttpClient.newHttpClient();;
    private static String serverUrl = "http://localhost:8080";
    private static HttpResponse<String> lastResponse;
    private static HttpResponse<String> gameResponse;

    private static GameController gameController;

    private Integer currentVersion = null;

    public BoardUpdateThread(int gameId, GameController gameController) {
        this.gameController = gameController;
        this.gameId = gameId;
    }


    public void run() {
        while (!gameEnded) {

            HttpRequest requestGame = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "/games/" + gameId))
                    .GET()
                    .build();
            try {
                gameResponse = client.send(requestGame, HttpResponse.BodyHandlers.ofString());
                Game game = gson.fromJson(gameResponse.body(), Game.class);
                if (currentVersion < game.getVersion()) {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(serverUrl + "/games/" + gameId + "/boards"))
                            .GET()
                            .build();
                    try {
                        lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
                        Board board = gson.fromJson(lastResponse.body(), Board.class);
                        gameController.replaceBoard(board);
                    } catch (Exception exception){
                        exception.printStackTrace();
                    }
                }
                Thread.sleep(1000);
            } catch (Exception exception) {
                exception.printStackTrace();
            }




        }
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}
