package dk.dtu.compute.se.pisd.roborally.utils;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.controller.HttpController;
import dk.dtu.compute.se.pisd.roborally.controller.JSONReader;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import org.json.JSONObject;

public class BoardUpdateThread extends Thread {

    private int gameId;
    private volatile boolean gameEnded = false;

    private GameController gameController;


    private Integer currentVersion =  -1;

    public BoardUpdateThread(int gameId, GameController gameController) {
        this.gameController = gameController;
        this.gameId = gameId;
    }


    public void run() {
        while (!gameEnded) {

            Game result = HttpController.getGameUpdate(gameId, currentVersion);
            if(result == null){

            }
            else if (result.getState().equals("STARTED")) {

                currentVersion = result.getVersion();
                JSONObject jsonBoard = new JSONObject(result.getBoard());
                Board newBoard = JSONReader.parseBoard(jsonBoard);

                gameController.replaceBoard(newBoard, currentVersion);
                gameController.refreshView();
            }else if(result.getState().equals("ENDED")){
                gameEnded = true;
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}
