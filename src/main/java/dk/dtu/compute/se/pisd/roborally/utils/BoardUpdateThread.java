package dk.dtu.compute.se.pisd.roborally.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.controller.HttpController;
import dk.dtu.compute.se.pisd.roborally.controller.JSONReader;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import org.json.JSONObject;

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


    private Integer currentVersion =  -1;

    public BoardUpdateThread(int gameId, GameController gameController) {
        this.gameController = gameController;
        this.gameId = gameId;

    }


    public void run() {
        while (!gameEnded) {

            Game result = HttpController.getGameUpdate(gameId, currentVersion);

            if (result != null) {
                currentVersion = result.getVersion();
                JSONObject jsonBoard = new JSONObject(result.getBoard());
                Board newBoard = JSONReader.parseBoard(jsonBoard);

                Board currentBoard = gameController.getBoard();

                if (currentBoard.getPhase() == Phase.PROGRAMMING) {

                } else {
                    gameController.replaceBoard(newBoard);
                }


            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}
