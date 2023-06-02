package server.dto;

public class GameDTO {
    int maxPlayers;
    int currentPlayers;
    String hostName;
    String gameId;

    public GameDTO(int maxPlayers, int currentPlayers, String hostName, String gameId) {
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.hostName = hostName;
        this.gameId = gameId;
    }
}
