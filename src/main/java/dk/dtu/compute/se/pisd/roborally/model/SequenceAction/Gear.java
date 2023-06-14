package dk.dtu.compute.se.pisd.roborally.model.SequenceAction;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Gear extends Space implements SequenceAction {

    private Heading direction;

    /**
     * @param heading Which way to turn the player
     * @param board   The playing board
     * @param x       The coordinate on the x axis
     * @param y       The coordinate on the y axis
     * @author Nilas Thoegersen
     */
    public Gear(Heading heading, Board board, int x, int y) {
        super(board, x, y);
        this.direction = heading;
        board.addBoardActions(this);
    }

    public Heading getHeading() {
        return direction;
    }

    public void setHeading(Heading heading) {
        this.direction = heading;
    }

    /**
     * Check each player's standing space, and if on gear, rotates the player.
     *
     * @param gameController The game controller
     * @author Nilas
     */
    @Override
    public void doAction(GameController gameController) {
        for (Player player : board.getPlayers()
        ) {
            if (player.getSpace().getClass().equals(this.getClass())) {
                ((Gear) player.getSpace()).turnPlayer(player);
            }
        }
    }

    /**
     * @return Prio of the field
     */
    @Override
    public int getPrio() {
        return 4;
    }

    /**
     * Turns the player either left or right, depending on the board.
     *
     * @param player The player to be turned
     * @author Nilas
     */
    private void turnPlayer(Player player) {
        if (direction == Heading.EAST) {
            player.setHeading(player.getHeading().next());
        } else {
            player.setHeading(player.getHeading().prev());
        }
    }
}
