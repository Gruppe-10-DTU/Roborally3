package server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.hibernate.annotations.Type;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int maxPlayers;
    private String name;

    private int gameID;

    private GameState state;

    /*
    private Board board;



    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
    */


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

    public int getGameID(){return gameID;}

    public void setGameID(int gameID){
        this.gameID = gameID;
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
