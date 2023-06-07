package dk.dtu.compute.se.pisd.roborally.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.roborally.model.Board;

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

    public BoardUpdateThread(int gameId) {
        this.gameId = gameId;
    }

    public void run() {
        while (!gameEnded) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(serverUrl + "/games/" + gameId + "/boards"))
                    .GET()
                    .build();
            try {
                lastResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
                Board board = gson.fromJson(lastResponse.body(), Board.class);
                // TODO: call board updater method with response
                Thread.sleep(1000);
            } catch (Exception exception){
                exception.printStackTrace();
            }
        }
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}
