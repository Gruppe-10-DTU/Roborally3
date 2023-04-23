package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Push extends Space implements SequenceAction {
    private int step;
    private Heading heading;
    public Push(Board board, int x, int y, int step, Heading heading) {
        super(board, x, y);
        this.step = step;
        this.heading = heading;
        board.addBoardActions(this);
    }

    /**
     * Iterate over each player. If the player is on an instance of this field, it'll turn him.
     *
     * @param gameController The main controller for the game
     */
    @Override
    public void doAction(GameController gameController) {
        for (Player player : gameController.board.getPlayers()
        ) {
            if(player.getSpace().getClass().equals(this.getClass())){
                ((Push)player.getSpace()).pushPlayer(gameController, player);
            }
        }
    }

    @Override
    public int getPrio() {
        return 3;
    }

    private void pushPlayer(GameController gameController, Player player){
        if(gameController.board.getStep() == step){
            gameController.movePlayer(player, heading);
        }
    }


}
