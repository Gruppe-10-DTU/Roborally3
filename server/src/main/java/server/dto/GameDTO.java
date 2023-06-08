package server.dto;

public class GameDTO {
    int maxPlayers;
    int currentPlayers;
    String name;
    int id;

    int version;

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
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
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


    public void setId(int id) {
        this.id = id;
    }
}
