package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;
import java.util.List;

public class Conveyorbelt extends Space implements SequenceAction {


    protected Heading heading;
    protected Heading turn;
    public Conveyorbelt(Board board, int x, int y, Heading heading){
        super(board, x, y);
        this.heading = heading;
    }
    public Conveyorbelt(Board board, int x, int y, Heading heading, Heading turn){
        super(board, x, y);
        this.heading = heading;
        this.turn = turn;
    }

    /**
     * Turns the player either left or right, depending on the arrow.
     * @param player The player to be turned
     */
    protected void turnPlayer(Player player){
        if(turn == Heading.EAST) {
            player.getHeading().next();
        }else if (turn == Heading.WEST) {
            player.getHeading().prev();
        }
    }

    /**
     * Moves a player on the board. The player will no push another player.
     * If two players will end up on the same space, the action will not happend for either of them.
     * @param gameController The main controller for the game
     */
    @Override
    public void doAction(GameController gameController) {
        //Target of the move
        List<Space> targetSpace = new ArrayList<>(board.getPlayersNumber());
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            if(player.getSpace().getClass().equals(this.getClass())){
               targetSpace.add(i, board.getNeighbour(player.getSpace(), ((Conveyorbelt) player.getSpace()).heading));
            }
        }
        for (int i = 0; i < targetSpace.size(); i++) {
            if(targetSpace.get(i) == null){
                continue;
            }
            Space space = targetSpace.get(i);
            targetSpace.remove(i);
            if(!targetSpace.contains(space) || space.getPlayer() != null){
                player.setSpace(space);
            }
        }
    }

    @Override
    public int getPrio() {
        return 2;
    }
}
