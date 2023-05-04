package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Push extends Space implements SequenceAction {

    //TODO: Sæt step til at være array
    private final int step;
    private final Heading heading;

    /**
     * @param board   The playing board
     * @param x       The coordinate on the x axis
     * @param y       The coordinate on the y axis
     * @param step    When the field needs to activate
     * @param heading The pushing direction
     * @author Nilas Thoegersen
     */
    public Push(Board board, int x, int y, int step, Heading heading) {
        super(board, x, y);
        this.step = step;
        this.heading = heading;
        board.addBoardActions(this);
    }

    /**
     * @return The prio of the class
     * @author Nilas Thoegersen
     */
    public int getStep() {
        return step;
    }

    public Heading getHeading() {
        return heading;
    }

    /**
     * Iterate over each player. If the player is on an instance of this field, it'll turn him.
     *
     * @param gameController The main controller for the game
     * @author Nilas
     */
    @Override
    public void doAction(GameController gameController) {
        for (Player player : gameController.board.getPlayers()
        ) {
            if (player.getSpace().getClass().equals(this.getClass())) {
                ((Push) player.getSpace()).pushPlayer(gameController, player);
            }
        }
    }

    @Override
    public int getPrio() {
        return 3;
    }

    /**
     * @param gameController The gamecontroller used to move player
     * @param player         the player to be moved
     * @author Nilas
     */
    private void pushPlayer(GameController gameController, Player player) {
        if (gameController.board.getStep() == step) {
            gameController.movePlayer(player, heading);
        }
    }


}
