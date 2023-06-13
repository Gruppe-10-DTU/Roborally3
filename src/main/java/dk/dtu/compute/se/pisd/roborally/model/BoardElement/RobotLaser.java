package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Cards.Damage;
import dk.dtu.compute.se.pisd.roborally.model.Cards.DamageCard;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;


public class RobotLaser implements SequenceAction{

    public RobotLaser() {
    }

    /**
     * @param space
     * Space from which the laser is shot
     * @param heading
     * Direction the laser is heading
     *
     * The method returns the player that is hit by the laser shot. If it does not hit anyone, it will return null.
     * @author Asbjørn Nielsen
     */
    public void shootLaser(Board board,@NotNull Space space, Heading heading){
        Space oSpace = space;
        while(space!=null){
            if(space.getOut(heading) || space.hasWall(heading)){
                return;
            }
            if(oSpace != space && space.getPlayer() != null){
                space.getPlayer().receiveCard(new DamageCard(Damage.SPAM));

                board.addGameLogEntry(space.getPlayer(), "was shot by a laser!");
                return;
            }
            space = board.getNeighbour(space, heading);
        }
    }

    /**
     *
     * @param gameController The main controller for the game
     * @author Asbjørn Nielsen
     */
    @Override
    public void doAction(GameController gameController) {
        gameController.board.getPlayers().parallelStream().forEach(player -> {
            this.shootLaser(gameController.board,player.getSpace(),player.getHeading());
        });
    }


    @Override
    public int getPrio() {
        return 6;
    }
}
