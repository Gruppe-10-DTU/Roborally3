package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Conveyorbelt extends Space implements ElementAction{


    protected Heading heading;
    protected Heading turn;
    public Conveyorbelt(Board board, int x, int y, Heading heading){
        super(board, x, y);
        this.heading = heading;
        this.boardActionType = BoardActionType.CONVEYORBELT;
    }
    public Conveyorbelt(Board board, int x, int y, Heading heading, Heading turn){
        super(board, x, y);
        this.heading = heading;
        this.boardActionType = BoardActionType.CONVEYORBELT;
        this.turn = turn;
    }
    protected void turnPlayer(Player player){
        if(turn == Heading.EAST) {
            player.getHeading().next();
        }else if (turn == Heading.WEST) {
            player.getHeading().prev();
        }
    }
    protected void movePlayer(GameController gameController, Player player){
        gameController.movePlayer(player, heading);
        if(player.getSpace() instanceof Conveyorbelt){
            ((Conveyorbelt)player.getSpace()).turnPlayer(player);
        }
    }

    @Override
    public void doAction(GameController gameController) {
        movePlayer(gameController, player);

    }
}
