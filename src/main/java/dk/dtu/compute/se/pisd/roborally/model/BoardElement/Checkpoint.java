package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;

public class Checkpoint extends Space implements ElementAction{

    private Checkpoint previous;

    private ArrayList<Player> players;

    public Checkpoint(Board board, int x, int y) {
        super(board, x, y);
        this.boardActionType = BoardActionType.CHECKPOINT;
    }
    public Checkpoint(Board board, int x, int y, Checkpoint previous){
        super(board, x, y);
        this.previous = previous;
        board.setWincondition(this);
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

    @Override
    public void doAction(GameController gameController) {
        if(checkPlayer(player)){
            addPlayer(player);
        }
    }
}
