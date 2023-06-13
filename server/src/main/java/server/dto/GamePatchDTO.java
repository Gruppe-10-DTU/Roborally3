package server.dto;

import server.model.GameState;

/**
 * DTO used for the patch method to control what can be updated.
 *
 * @author Nilas Thoegersen
 */
public class GamePatchDTO {
    GameState gameState;

    String board;

    Integer version;

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
