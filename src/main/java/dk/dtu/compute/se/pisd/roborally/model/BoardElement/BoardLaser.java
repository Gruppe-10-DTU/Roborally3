package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Cards.Damage;
import dk.dtu.compute.se.pisd.roborally.model.Cards.DamageCard;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class BoardLaser extends Space implements SequenceAction {
    private final Heading shootingDirection;

    public BoardLaser(Board board, int x, int y, Heading shootingDirection) {
        super(board, x, y);
        this.shootingDirection = shootingDirection;
        board.addBoardActions(this);
    }

    /**
     * Checks if the LoS hits a laser.
     * @author Nilas
     * @param heading Direction of the shot
     * @return boolean if the robot is hit
     */
    public boolean hit(Heading heading){
        return this.shootingDirection == heading.reverse();
    }

    public Heading getShootingDirection () {
        return shootingDirection;
    }

    /**
     * Implements the board laser action.
     * @author Nilas
     * @param gameController The gamecontroller
     */
    @Override
    public void doAction(GameController gameController) {
        Heading heading = Heading.WEST;
        gameController.board.getPlayers().parallelStream().forEach(player -> {
            Space space = player.getSpace();
            for (int i = 0; i < 4; i++) {
                //Set isHit in the if statement and add the dmg card inside the statement.
                if(!space.getOut(heading)) {
                    if(isHit(gameController.board, space, heading))
                        space.getPlayer().discardCard(new DamageCard(Damage.SPAM));
                }
                heading.next();
            }
        });
    }

    @Override
    public int getPrio() {
        return 5;
    }

    /**
     * Action to see if a player is in a firing line of a laser on the board. Returns true if it is, otherwise false.
     * @author Nilas
     * @param board The playing board
     * @param space Space of the player
     * @param heading firing line of the laser
     * @return boolean saying if the player is it
     */
    protected boolean isHit(Board board, Space space, Heading heading){

        while(space != null){
            if(space.getPlayer() != null || hasWall(heading)){
                return false;
            }
            if(space instanceof BoardLaser && ((BoardLaser) space).hit(heading)){
                return true;
            }
            space = board.getNeighbour(space, heading);
        }
        return false;
    }
}
