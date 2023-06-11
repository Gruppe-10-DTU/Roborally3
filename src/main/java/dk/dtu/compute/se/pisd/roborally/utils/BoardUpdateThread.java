package dk.dtu.compute.se.pisd.roborally.utils;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.controller.HttpController;
import dk.dtu.compute.se.pisd.roborally.controller.JSONReader;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import org.json.JSONObject;

public class BoardUpdateThread extends Thread {

    private int gameId;
    private boolean gameEnded = false;

    private GameController gameController;


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

                Board currentBoard = gameController.board;
                if(newBoard.getPhase() == Phase.PLAYER_INTERACTION){

                }
                else if (currentBoard.getPhase() == Phase.PROGRAMMING) {
                    gameController.updatePlayers(newBoard, currentVersion);
                } else if (currentBoard.getPhase() == Phase.WAITING && newBoard.getPhase() == Phase.PROGRAMMING && currentBoard.getProgrammingItemsLeft() != 0) {
                //} else if (currentBoard.getPhase() == Phase.WAITING && currentBoard.getProgrammingItemsLeft() != 0) {
                    gameController.updatePlayers(newBoard, currentVersion);
                } else {
                    gameController.replaceBoard(newBoard, currentVersion);
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
