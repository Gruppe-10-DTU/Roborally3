package server.model;

import org.springframework.stereotype.Component;

@Component
public class Game {
    private Board board;
    private int maxPlayers;
    private String name;

    private int gameID;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameID(){return gameID;}

    public void setGameID(int gameID){
        this.gameID = gameID;
    }

}
