package server.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Board {


    private ArrayList<Player> currentPlayers = new ArrayList<>();

    private int id;

    public ArrayList<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    public int getId() {
        return id;
    }
    public int setId(int newId) { return this.id = newId; }


}
