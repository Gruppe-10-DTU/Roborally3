package dk.dtu.compute.se.pisd.roborally.controller.SequenceAction;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.Pit;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Conveyorbelt extends Space implements SequenceAction {


    protected final Heading direction;
    protected Heading turn;

    /**
     * @param board   The playing board
     * @param x       The coordinate on the x axis
     * @param y       The coordinate on the y axis
     * @param direction The way the conveyorbelt is moving
     * @author Nilas Thoegersen
     */
    public Conveyorbelt(Board board, int x, int y, Heading direction) {
        super(board, x, y);
        this.direction = direction;
        board.addBoardActions(this);
    }

    /**
     * @param board   The playing board
     * @param x       The coordinate on the x axis
     * @param y       The coordinate on the y axis
     * @param direction The way the conveyorbelt is moving
     * @param turn    The way the conveyorbelt is turning
     * @author Nilas Thoegersen
     */
    public Conveyorbelt(Board board, int x, int y, Heading direction, Heading turn) {
        super(board, x, y);
        board.addBoardActions(this);
        this.direction = direction;
        this.turn = turn;
    }

    /**
     * Turns the player either left or right, depending on the arrow.
     *
     * @param player The player to be turned
     * @author Nilas
     */
    protected void turnPlayer(Player player) {
        Heading heading1 = player.getHeading();
        if (turn == Heading.EAST) {
            player.setHeading(heading1.prev());
        } else if (turn == Heading.WEST) {
            player.setHeading(heading1.next());
        }
    }

    /**
     * Get the exit heading when pushed alongside the field. If the field has a turn, the exit is also moved.
     *
     * @return The Heading of the exit
     * @author Nilas Thoegersen
     * @author Sandie Petersen
     */
    protected Heading getExit(){
        if (turn == null){
            return direction;
        }
        if (turn == Heading.EAST) {
            return direction.prev();
        } else if (turn == Heading.WEST) {
            return direction.next();
        }else{
            return direction;
        }
    }

    /**
     * Moves a player on the board. The player will not push another player.
     * If two players ends up on the same space, the action will not happend for either of them.
     *
     * @param gameController The main controller for the game
     * @author Nilas
     */

    //TODO: Gør funktionen mere generel så den kan bruges i FastConveyorbelt
    @Override
    public void doAction(GameController gameController) {
        //Target of the move
        Map<Player, Space> targetSpace = new HashMap<>();
        Player player;
        Space space;
        for (int i = 0; i < board.getNumberOfPlayers(); i++) {
            player = board.getPlayer(i);
            space = player.getSpace();
            if (space.getClass().equals(this.getClass())) {
                space = board.getNeighbour(player.getSpace(), ((Conveyorbelt) space).getExit());
                if (space == null || space instanceof Pit) {
                    gameController.rebootRobot(player);
                } else if (space.getPlayer() == null || space.getClass().equals(this.getClass())) {
                    targetSpace.put(player, space);
                }
            }
        }
        HashSet<Space> filterMap = new HashSet<>();
        List<Space> distinct = targetSpace.values().stream().filter(x -> !filterMap.add(x)).toList();
        for (Map.Entry<Player, Space> entry : targetSpace.entrySet()
        ) {
            if (!distinct.contains(entry.getValue())) {
                entry.getKey().setSpace(entry.getValue());
            }
        }
    }

    public Heading getDirection() {
        return direction;
    }

    public Heading getTurn() {
        return turn;
    }

    /**
     * @return The priority of the element
     * @author Nilas Thoegersen
     */
    @Override
    public int getPrio() {
        return 2;
    }
}
