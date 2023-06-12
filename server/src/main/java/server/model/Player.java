package server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 *
 * @author Søren Wünsche & Nilas Thoegersen
 */
@Entity
public class Player {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private int gameId;

    public Player(int id, int gameId, String name) {
        this.id = id;
        this.gameId = gameId;
        this.name = name;
    }
    public Player(String name, int gameId) {
        this.name = name;
        this.gameId = gameId;
    }

    public Player(){

    }

    public void setId(int id) {
        this.id = id;

    }
    public void setGameId(int gameId){this.gameId = gameId;}

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }
}


