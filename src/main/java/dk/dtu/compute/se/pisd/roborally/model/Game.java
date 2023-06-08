package dk.dtu.compute.se.pisd.roborally.model;

public class Game {
    private int id;
    private final String name;

    private int version;
    private int currentPlayers;
    private final int maxPlayers;
    private final String board;
    public Game(String name, int currentPlayers, int maxPlayers, String board) {
        this.name = name;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.board = board;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void IncCurrPlayer(){if(currentPlayers < maxPlayers) currentPlayers++;};

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentPlayers=" + currentPlayers +
                ", maxPlayers=" + maxPlayers +
                '}';
    }
}
