package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class RebootToken extends Space implements FieldAction {

    public Heading getExit() {
        return exit;
    }

    public void setExit(Heading exit) {
        this.exit = exit;
    }

    Heading exit;

    public RebootToken(Board board, int x, int y, Heading exit) {
        super(board, x, y);
        board.setRebootToken(this);
        this.exit = exit;
    }

    /**
     * @author Nilas Thoegersen
     * @param gameController
     * @param player
     */
    @Override
    public void doFieldAction(GameController gameController, Player player){
        if(player != null){
            //gameController.movePlayer(player, exit);
        }
        player.setSpace(this);
    }
}
