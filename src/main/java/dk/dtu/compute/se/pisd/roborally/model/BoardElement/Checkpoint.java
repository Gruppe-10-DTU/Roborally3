package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.model.Player;

import java.util.ArrayList;

public class Checkpoint extends BoardElement {

    private Checkpoint previous;

    private ArrayList<Player> players;


    @Override
    public void action(Player payer) {

    }

    public boolean addPlayer(Player player){
        if(previous==null){
            players.add(player);
            return true;
        }else if (previous.checkPlayer(player)){
            players.add(player);
            return true;
        }
        return false;
    }
    protected boolean checkPlayer(Player player){
        return players.contains(player);
    }
}
