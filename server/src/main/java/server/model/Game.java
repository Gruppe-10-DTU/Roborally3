package server.model;

import org.springframework.stereotype.Component;

@Component
public class Game {
    private Board board;
    private int maxPlayers;
    private String hostName;

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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getGameID(){return gameID;}

    public void setGameID(int gameID){
        this.gameID = gameID;
    }
}
