package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Gear extends Space implements ElementAction {

    private Heading heading;

    public Gear(Heading heading, Board board, int x, int y){
        super(board, x, y);
        this.heading = heading;
        this.boardActionType = BoardActionType.GEAR;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    @Override
    public void doAction(GameController gameController) {
        switch (heading){
            case EAST -> player.getHeading().next();
            case WEST -> player.getHeading().prev();
        }
    }
}
