package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Energy extends Space implements SequenceAction {
    private boolean energy = true;

    /**
     * Default constructor for the energy field
     *
     * @param board The playing board
     * @param x     The coordinate on the x axis
     * @param y     The coordinate on the y axis
     * @author Nilas Thoegersen
     */
    public Energy(Board board, int x, int y) {
        super(board, x, y);
        board.addBoardActions(this);
    }

    /**
     * Always add an energy to the player on the first time for each field, otherwise will only increment energy
     * on the 5th step.
     *
     * @param gameController The main controller for the game
     * @author Nilas
     */
    @Override
    public void doAction(GameController gameController) {
        for (Player player : board.getPlayers()
        ) {
            if (player.getSpace().getClass().equals(this.getClass())) {
                if (energy) {
                    energy = false;
                    player.incrementEnergy();
                    gameController.board.addGameLogEntry(player, "Picked up energy");
                } else if (board.getStep() == 5) {
                    player.incrementEnergy();
                }
            }
        }
    }

    @Override
    public int getPrio() {
        return 7;
    }
}
