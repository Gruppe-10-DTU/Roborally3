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
    private Board board;
    private Player player;
    public RobotLaser(Board board, Player player) {
        this.player = player;
        this.board = board;
        board.addBoardActions(this);
    }

    /**
     * @author Asbjørn
     * @param space
     * Space from which the laser is shot
     * @param heading
     * Direction the laser is heading
     *
     * The method returns the player that is hit by the laser shot. If it does not hit anyone, it will return null.
     */
    public Player shootLaser(@NotNull Space space, Heading heading){
        Space oSpace = space;
        while(space!=null){
            if(space.getOut(heading) || space.hasWall(heading)){
                return null;
            }
            if(oSpace != space && space.getPlayer() != null){
                return space.getPlayer();
            }
            space = board.getNeighbour(space, heading);
        }
        return null;
    }

    @Override
    public void doAction(GameController gameController) {
        gameController.board.getPlayers().parallelStream().forEach(player -> {
            RobotLaser rblsr = new RobotLaser(gameController.board,player);
            if(rblsr.shootLaser(player.getSpace(),player.getHeading()) != null){
                rblsr.shootLaser(player.getSpace(),player.getHeading()).discardCard(new DamageCard(Damage.SPAM));
                gameController.board.addGameLogEntry(rblsr.shootLaser(player.getSpace(),player.getHeading()), "Was shot by " + player.getName());
            }
        });
    }


    @Override
    public int getPrio() {
        return 6;
    }
}
