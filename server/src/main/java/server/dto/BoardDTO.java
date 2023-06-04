package server.dto;

import server.model.Player;

import java.util.ArrayList;

public class BoardDTO {
    private ArrayList<Player> currentPlayers;

    private String id;

    public ArrayList<Player> getCurrentPlayers() {
        return currentPlayers;
    }

    public String getId() {
        return id;
    }
}
