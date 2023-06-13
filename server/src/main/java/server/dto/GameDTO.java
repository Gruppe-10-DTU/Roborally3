package server.dto;

public class GameDTO {
    private int maxPlayers;
    private int currentPlayers;
    private String name;
    private int id;

    private int version;

    private String Board;

    public GameDTO(){

    }

    /**
     *
     * @param maxPlayers
     * @param currentPlayers
     * @param name
     * @param id
     *  Creates the game dto
     * @author Sandie Petersen
     */
    public GameDTO(int maxPlayers, int currentPlayers, String name, int id, int version) {
        this.name = name;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.id = id;
        this.version = version;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }
}
