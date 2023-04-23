package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;
import java.util.List;

public class FastConveyorbelt extends Conveyorbelt implements SequenceAction{

    public FastConveyorbelt(Board board, int x, int y, Heading heading) {
        super(board, x, y, heading);
        board.addBoardActions(this);
    }

    /**
     * Moves a player two steps if possible. If the player is moved off the belt on the first step, the action will stop.
     * @param gameController The main controller for the game
     */
    @Override
    public void doAction(GameController gameController) {
        for (int k = 0; k < 2; k++) {
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
    }

    @Override
    public int getPrio() {
        return 1;
    }
}
