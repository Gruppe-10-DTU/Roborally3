package server.dto;

public class GameDTO {
    int maxPlayers;
    int currentPlayers;
    String name;
    int gameId;

    /**
     *
     * @param maxPlayers
     * @param currentPlayers
     * @param name
     * @param gameId
     *  Creates the game dto
     * @author Sandie Petersen
     */
    public GameDTO(int maxPlayers, int currentPlayers, String name, int gameId) {
        this.name = name;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.gameId = gameId;
    }
    public String getName(){
        return name;
    }
    public int getGameId(){
        return gameId;
    }
    public int getMaxPlayers(){
        return maxPlayers;
    }
    public int getCurrentPlayers(){
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public String getHostName() {
        return name;
    }

    public void setHostName(String hostName) {
        this.name = hostName;
    }


    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}
