package server.dto;

import server.model.GameState;

public class GamePatchDTO {
    GameState gameState;

    String board;

    int version;

    public GamePatchDTO() {
    }

    public GamePatchDTO(GameState gameState, String board, int version) {
        this.gameState = gameState;
        this.board = board;
        this.version = version;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
