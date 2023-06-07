package dk.dtu.compute.se.pisd.roborally.model;

public class Game {
    private final int id;
    private final String name;

    private int version;
    private final int currentPlayers;
    private int currentPlayers;
    private final int maxPlayers;
    private final String board;
    public Game(int id, String name, int currentPlayers, int maxPlayers, String board) {
        this.id = id;
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
