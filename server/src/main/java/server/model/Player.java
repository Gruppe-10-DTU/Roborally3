package server.model;

public class Player {

    private int pID, gameID;
    private String name;

    public Player(int pID, int gameID) {
        this.pID = pID;
        this.gameID = gameID;
    }

    public Player(String name, int gameID) {
        this.name = name;
        this.gameID = gameID;
    }


    public Player() {

    }

    public void setpID(int pID) {
        this.pID = pID;
    }
    public void setGameID(int gameID){this.gameID = gameID;}

    public void setName(String name) {
        this.name = name;
    }
}
