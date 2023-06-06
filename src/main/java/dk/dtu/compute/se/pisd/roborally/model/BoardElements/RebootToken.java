package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class RebootToken extends Space implements FieldAction {

    public Heading getDirection() {
        return direction;
    }

    public void setDirection(Heading direction) {
        this.direction = direction;
    }

    Heading direction;

    /**
     * @param board The playing board
     * @param x     The coordinate on the x axis
     * @param y     The coordinate on the y axis
     * @param direction  Which way to move the player out of the field in case of a push.
     * @author Nilas Thoegersen
     */
    public RebootToken(Board board, int x, int y, Heading direction) {
        super(board, x, y);
        board.setRebootToken(this);
        this.direction = direction;
    }

    /**
     * Set a player on the respawn token.
     *
     * @param gameController The gamecontroller
     * @param player         The player getting respawned
     * @author Nilas Thoegersen
     */
    @Override
    public void doFieldAction(GameController gameController, Player player) {
        player.setHeading(this.direction);
        if (this.player != null) {
            //TODO: Move the old player out
            Player pmove = board.getRebootToken().getPlayer();
            gameController.movePlayer(pmove, direction);
        }
        player.setSpace(this);
    }
}
