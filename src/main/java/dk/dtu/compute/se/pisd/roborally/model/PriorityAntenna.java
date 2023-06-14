package dk.dtu.compute.se.pisd.roborally.model;

public class PriorityAntenna extends Space {


    /**
     * Contructor for the priorityAntenna.
     *
     * @param board The playing board
     * @param x     The coordinate on the x axis
     * @param y     The coordinate on the y axis
     * @author Sandie Petersen
     */
    public PriorityAntenna(Board board, int x, int y) {
        super(board, x, y);
    }


    /**
     * Get the position of the space
     *
     * @return The position of the space, as an array
     * @author Sandie Petersen
     */
    public Integer[] getSpace() {
        return new Integer[]{x, y};
    }
}
