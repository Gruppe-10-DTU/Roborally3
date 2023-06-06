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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}
