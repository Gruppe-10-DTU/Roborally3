package server.dto;

public class GameDTO {
    int maxPlayers;
    int currentPlayers;
    String hostName;
    int gameId;

    /**
     *
     * @param maxPlayers
     * @param currentPlayers
     * @param hostName
     * @param gameId
     *  Creates the game dto
     * @author Sandie Petersen
     */
    public GameDTO(int maxPlayers, int currentPlayers, String hostName, int gameId) {
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.hostName = hostName;
        this.gameId = gameId;
    }
}
