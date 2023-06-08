package server.model;

import jakarta.persistence.*;
import server.dto.PlayerDTO;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int currentPlayers;

    private int maxPlayers;
    private String name;

    private int version = 1;

    @Column(columnDefinition = "TEXT")
    private String board;

    private GameState state;

    public String getBoard() {
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameID(){return id;}

    public void setGameID(int gameID){
        this.id = gameID;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Game() {
    }
    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<PlayerDTO> getPlayers() {
        List<PlayerDTO> playerlst = new ArrayList<PlayerDTO>();
        return playerlst;
    }
}
