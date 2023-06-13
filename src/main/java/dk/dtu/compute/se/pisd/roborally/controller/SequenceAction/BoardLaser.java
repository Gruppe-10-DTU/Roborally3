package dk.dtu.compute.se.pisd.roborally.controller.SequenceAction;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Cards.Damage;
import dk.dtu.compute.se.pisd.roborally.model.Cards.DamageCard;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class BoardLaser extends Space implements SequenceAction {
    private final Heading direction;


    /**
     * @param board             The playing board
     * @param x                 The coordinate on the x axis
     * @param y                 The coordinate on the y axis
     * @param shootingDirection Which way the laser points
     * @author Nilas Thoegersen
     */
    public BoardLaser(Board board, int x, int y, Heading shootingDirection) {
        super(board, x, y);
        this.direction = shootingDirection;
        board.getSpace(x,y).setWall(shootingDirection.prev().prev());
        board.addBoardActions(this);
    }

    /**
     * Checks if the LoS hits a laser.
     *
     * @param heading Direction of the shot
     * @return boolean if the robot is hit
     * @author Nilas Thoegersen
     */
    public boolean hit(Heading heading) {
        return this.direction == heading.reverse();
    }

    public Heading getShootingDirection() {
        return direction;
    }

    /**
     * Implements the board laser action.
     *
     * @param gameController The gamecontroller
     * @author Nilas Thoegersen
     */
    @Override
    public void doAction(GameController gameController) {
        gameController.board.getPlayers().parallelStream().forEach(player -> {
            Space space = player.getSpace();
            Heading heading = Heading.WEST;
            if(space instanceof BoardLaser){
                player.discardCard(new DamageCard(Damage.SPAM));
                board.addGameLogEntry(player, "Was hit by a laser");
            }else {
                for (int i = 0; i < 4; i++) {
                    //Set isHit in the if statement and add the dmg card inside the statement.
                    if (!space.getOut(heading)) {
                        if (isHit(board, space, heading)) {
                            player.discardCard(new DamageCard(Damage.SPAM));
                            board.addGameLogEntry(player, "Was hit by a laser");
                        }
                    }
                    heading = heading.next();
                }
            }
        });
    }

    @Override
    public int getPrio() {
        return 5;
    }

    /**
     * Action to see if a player is in a firing line of a laser on the board. Returns true if it is, otherwise false.
     *
     * @param board   The playing board
     * @param space   Space of the player
     * @param heading firing line of the laser
     * @return boolean saying if the player is hit
     * @author Nilas
     */
    public boolean isHit(Board board, Space space, Heading heading) {
        Space oSpace = space;
        space = board.getNeighbour(space,heading);
        while (space != null){
            if (space.getPlayer() != null || space.hasWall(heading)){
                return false;
            } else if (space instanceof BoardLaser && ((BoardLaser) space).hit(heading)) {
                return true;
            }
            space = board.getNeighbour(space, heading);
        }
        return false;
    }
}
