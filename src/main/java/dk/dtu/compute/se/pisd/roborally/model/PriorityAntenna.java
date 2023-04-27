package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.model.BoardElement.SequenceAction;

public class PriorityAntenna extends Space {

    private int x;
    private int y;

    public PriorityAntenna(Board board, int x, int y) {
        super(board, x, y);
        this.x = x;
        this.y = y;
    }


    public Integer[] getSpace() {
        return new Integer[] {x,y} ;
    }
}
