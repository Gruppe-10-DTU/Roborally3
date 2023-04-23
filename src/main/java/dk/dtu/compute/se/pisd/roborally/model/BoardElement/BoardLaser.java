package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class BoardLaser extends Space implements SequenceAction {
    private Heading shootingDirection;

    public BoardLaser(Board board, int x, int y, Heading shootingDirection) {
        super(board, x, y);
        this.shootingDirection = shootingDirection;
        board.addBoardActions(this);
    }

    /**
     * Checks if the LoS hits a laser.
     * @param heading Direction of the shot
     * @return
     */
    public boolean hit(Heading heading){
        return this.shootingDirection == heading.reverse();
    }

    /**
     * Implements the board laser action.
     * @param gameController The gamecontroller
     */
    @Override
    public void doAction(GameController gameController) {
        Space space;
        Heading heading = Heading.WEST;
        for (Player player: gameController.board.getPlayers()
             ) {
            space = player.getSpace();
            for (int i = 0; i < 4; i++) {
                isHit(gameController.board, space, heading);
                heading.next();
            }
        }
    }

    @Override
    public int getPrio() {
        return 5;
    }

    /**
     * Action to see if a player is in a firing line of a laser on the board. Returns true if it is, otherwise false.
     * @param board The playing board
     * @param space Space of the player
     * @param heading firing line of the laser
     * @return boolean saying if the player is it
     */
    protected boolean isHit(Board board, Space space, Heading heading){

        while(space != null){
            if(space.getPlayer() != null || getOut(heading) || hasWall(heading)){
                return false;
            }
            if(space instanceof BoardLaser && ((BoardLaser) space).shootingDirection == heading.reverse()){
                return true;
            }

            space = board.getNeighbour(space, heading);
        }
        return false;
    }
}
