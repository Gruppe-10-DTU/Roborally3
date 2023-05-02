package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement.SequenceAction;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PriorityAntenna extends Space {


    public PriorityAntenna(Board board, int x, int y) {
        super(board, x, y);
    }


    public Integer[] getSpace() {
        return new Integer[] {x,y} ;
    }
}
