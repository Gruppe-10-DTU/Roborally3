package dk.dtu.compute.se.pisd.roborally.model;

public class Game {
    private int id;
    private String name;

    private int version;
    private int currentPlayers;
    private int maxPlayers;

    private String state;
    private String board;

    public Game(String board){
        this.board = board;
    }

    public Game(String name, int currentPlayers, int maxPlayers, String board) {
        this.name = name;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.board = board;
    }

    public Game(int id, String name, int version, int currentPlayers, int maxPlayers) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
    }

    public Game(int id, String name, int version, int currentPlayers, int maxPlayers, String state, String board) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.state = state;
        this.board = board;
    }

    public Game(String board, int version) {
        this.board = board;
        this.version = version;
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

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
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
    public void setBoard(String board){
        this.board = board;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
