package server.dto;

public class PlayerDTO {
    private int id;
    private int gameId;
    private String name;

    /**
     *
     * @param id
     * @param gameId
     * @param name
     * Creates player DTO
     * @author Søren Friis Wünsche
     */
    public PlayerDTO(int id, int gameId, String name) {
        this.id = id;
        this.gameId = gameId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
