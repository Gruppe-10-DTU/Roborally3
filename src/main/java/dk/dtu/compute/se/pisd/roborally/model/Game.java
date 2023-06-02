package dk.dtu.compute.se.pisd.roborally.model;

public class Game {
    private final int id;
    private final String name;
    private final int currentPlayers;
    private final int maxPlayers;

    public Game(int id, String name, int currentPlayers, int maxPlayers) {
        this.id = id;
        this.name = name;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
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
