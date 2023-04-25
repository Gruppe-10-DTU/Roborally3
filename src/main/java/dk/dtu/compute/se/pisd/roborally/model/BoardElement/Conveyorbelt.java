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

public class Conveyorbelt extends Space implements SequenceAction {


    protected Heading heading;
    protected Heading turn;
    public Conveyorbelt(Board board, int x, int y, Heading heading){
        super(board, x, y);
        this.heading = heading;
        board.addBoardActions(this);
    }
    public Conveyorbelt(Board board, int x, int y, Heading heading, Heading turn){
        super(board, x, y);
        board.addBoardActions(this);
        this.heading = heading;
        this.turn = turn;
    }

    /**
     * Turns the player either left or right, depending on the arrow.
     * @author Nilas
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
     * @author Nilas
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
            if(space.getClass().equals(this.getClass())){
                space = board.getNeighbour(player.getSpace(), ((Conveyorbelt) space).heading);
                if(space.getPlayer() == null || space.getClass().equals(this.getClass())){
                    targetSpace.put(player, space);
                }
            }
        }
        HashSet<Space> filterMap = new HashSet<>();
        List<Space> distinct = targetSpace.values().stream().filter(x -> !filterMap.add(x)).toList();
        for (Map.Entry<Player, Space> entry : targetSpace.entrySet()
             ) {
            if(!distinct.contains(entry.getValue())){
                entry.getKey().setSpace(entry.getValue());
            }
        }
    }

    @Override
    public int getPrio() {
        return 2;
    }
}
