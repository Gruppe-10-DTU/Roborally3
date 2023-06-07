package server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Game {
    @Id
    @GeneratedValue
    private int id;
    private int maxPlayers;
    private String name;

    private GameState state;
    
    @Column(columnDefinition = "TEXT")
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
}
