package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Pit extends Space implements FieldAction {
    public Pit(Board board, int x, int y) {
        super(board, x, y);
    }

    @Override
    public void doFieldAction(GameController gameController, Player player) {
        gameController.rebootRobot(player);
    }
}