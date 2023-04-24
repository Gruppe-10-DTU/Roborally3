package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class FastConveyorbelt extends Conveyorbelt implements SequenceAction{

    public FastConveyorbelt(Board board, int x, int y, Heading heading) {
        super(board, x, y, heading);
    }
    public FastConveyorbelt(Board board, int x, int y, Heading heading, Heading turn) {
        super(board, x, y, heading, turn);
    }

    /**
     * Moves a player two steps if possible. If the player is moved off the belt on the first step, the action will stop.
     * @param gameController The main controller for the game
     */
    @Override
    public void doAction(GameController gameController) {
        //Target of the move

        Map<Player, Space> targetSpace = new HashMap<>();
        Player player;
        Space space;
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            player = board.getPlayer(i);
            space = player.getSpace();
            for (int j = 0; j < 2; j++) {
                if(space.getClass().equals(this.getClass())){
                    space = board.getNeighbour(space, ((Conveyorbelt) player.getSpace()).heading);
                    if(space.getPlayer() == null || space instanceof FastConveyorbelt){
                        targetSpace.put(player, space);
                        if(space instanceof Conveyorbelt){
                            ((Conveyorbelt) space).turnPlayer(player);
                        }
                    }

                }
            }
        }
        HashSet<Space> filterMap = new HashSet<>();
        List<Space> distinct = targetSpace.values().stream().filter(x -> !filterMap.add(x)).toList();
        for (Map.Entry<Player, Space> entry : targetSpace.entrySet()
        ) {
            player = entry.getKey();
            if(!distinct.contains(entry.getValue())){
                player.setSpace(entry.getValue());
            }
        }
    }

    @Override
    public int getPrio() {
        return 1;
    }
}
