package server.dto;

import server.model.GameState;

/**
 * DTO used for the patch method to control what can be updated.
 *
 * @author Nilas Thoegersen
 */
public class GamePatchDTO {
    private GameState state;

    private String board;

    private Integer version;

    public GamePatchDTO() {
    }

    public GamePatchDTO(GameState state, String board, int version) {
        this.state = state;
        this.board = board;
        this.version = version;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
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
