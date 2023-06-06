package server.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.stereotype.Component;

@Component
public class Board {

    @Id
    @GeneratedValue
    private int id;

    private int gameId;

    private String gameBoard;

    public Board(){

    }
    public Board(int id, int gameId, String gameBoard) {
        this.id = id;
        this.gameId = gameId;
        this.gameBoard = gameBoard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(String gameBoard) {
        this.gameBoard = gameBoard;
    }
}
