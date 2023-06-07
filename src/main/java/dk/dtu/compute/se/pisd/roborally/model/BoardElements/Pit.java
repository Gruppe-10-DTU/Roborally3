package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Pit extends Space implements FieldAction {
    /**
     * @param board The playing board
     * @param x     The coordinate on the x axis
     * @param y     The coordinate on the y axis
     * @author Nilas Thogersen
     */
    public Pit(Board board, int x, int y) {
        super(board, x, y);
        board.setPit(this);
    }

    @Override
    public void doFieldAction(GameController gameController, Player player) {
        gameController.rebootRobot(player);
        gameController.board.addGameLogEntry(player, "Fell into a pit");
    }
}