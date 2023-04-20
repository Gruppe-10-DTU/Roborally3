package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public class FastConveyorbelt extends Conveyorbelt implements ElementAction{

    public FastConveyorbelt(Board board, int x, int y, Heading heading) {
        super(board, x, y, heading);
        this.boardActionType = BoardActionType.FASTCONVEYORBELT;
    }

    @Override
    public void doAction(GameController gameController) {
        Player localPlayer = player;
        movePlayer(gameController, player);
        if(localPlayer.getSpace() instanceof Conveyorbelt){
            ((Conveyorbelt) localPlayer.getSpace()).movePlayer(gameController, player);
        }
    }
}
