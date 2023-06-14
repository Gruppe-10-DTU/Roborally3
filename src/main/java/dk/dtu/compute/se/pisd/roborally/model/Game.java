package dk.dtu.compute.se.pisd.roborally.model;

public class Game {
    private int id;
    private String name;

    private int version;
    private int currentPlayers;
    private int maxPlayers;

    private String state;
    private String board;

    public Game(){

    }

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

    public int getId() {
        return id;
    }

    public Game setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Game setName(String name) {
        this.name = name;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public Game setVersion(int version) {
        this.version = version;
        return this;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public Game setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
        return this;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public Game setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        return this;
    }

    public String getState() {
        return state;
    }

    public Game setState(String state) {
        this.state = state;
        return this;
    }

    public String getBoard() {
        return board;
    }

    public Game setBoard(String board) {
        this.board = board;
        return this;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
