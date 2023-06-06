package server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;
    private int maxPlayers;
    private String hostName;

    private GameState state;



    private String board;



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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public Game() {
    }
}
