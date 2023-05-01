package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.HashSet;
import java.util.Set;

public class Checkpoint extends Space implements SequenceAction {

    private Checkpoint previous;

    private Set<Player> players;

    private int checkpointNumber;

    public Checkpoint(Board board, int x, int y, int number) {
        super(board, x, y);
        this.players = new HashSet<>(board.getPlayersNumber());
        this.checkpointNumber = number;
        board.addBoardActions(this);
    }
    public Checkpoint(Board board, int x, int y, Checkpoint previous, int number){
        super(board, x, y);
        this.checkpointNumber = number;
        this.previous = previous;
    }

    /**
     * Adds a player to the checkpoint.
     * @author Nilas
     * @param player The player to be addedee
     * @return True if it was possible to add the player
     */
    protected boolean addPlayer(Player player){
        if(previous==null){
            players.add(player);
            return true;
        }else if (previous.checkPlayer(player)){
            players.add(player);
            return true;
        }
        return false;
    }


    /**
     * Simple method that checks if the player is already added
     * @author Nilas
     * @param player The player to be added
     * @return If the player is on the checkpoint.
     */
    public boolean checkPlayer(Player player){
        return players.contains(player);
    }

    public int getCheckpointNumber() {
        return checkpointNumber;
    }

    @Override
    public void doAction(GameController gameController) {
        for (Player player : board.getPlayers()
             ) {
            if(player.getSpace().getClass().equals(this.getClass())){
                ((Checkpoint) player.getSpace()).addPlayer(player);
            }
        }
    }

    @Override
    public int getPrio() {
        return 8;
    }
}
