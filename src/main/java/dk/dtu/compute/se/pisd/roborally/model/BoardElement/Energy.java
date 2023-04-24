package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Energy extends Space implements SequenceAction{
    private boolean energy = true;
    public Energy(Board board, int x, int y) {
        super(board, x, y);
        board.addBoardActions(this);
    }

    /**
     * Always add an energy to the player on the first time for each field, otherwise will only increment energy
     * on the 5th step.
     * @author Nilas
     * @param gameController The main controller for the game
     */
    @Override
    public void doAction(GameController gameController) {
        for (Player player : board.getPlayers()
             ) {
            if(player.getSpace().getClass().equals(this.getClass())){
                if(energy){
                    energy = false;
                    player.incrementEnergy();
                } else if (board.getStep()==5) {
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
