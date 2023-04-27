package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.CustomExceptions.SpaceOutOfBoundsException;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Cards.Damage;
import dk.dtu.compute.se.pisd.roborally.model.Cards.DamageCard;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class RobotLaser implements SequenceAction{
    private Board board;
    private Player player;
    public RobotLaser(Board board, Player player) {
        super();
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
     * @throws SpaceOutOfBoundsException
     * Since the method recursively checks if the space is empty, and the space eventually will go out of bounds
     * the method should throw an out-of-bounds error at some point (unless a robot is hit).
     */
    public Player shootLaser(@NotNull Space space, Heading heading) throws SpaceOutOfBoundsException {
        Player hitsPlayer = null;
        if(board.getNeighbour(space, heading).getPlayer() != null) {
            if(board.getNeighbour(space,heading) == null || board.getNeighbour(space, heading).hasWall(heading)){
                return hitsPlayer;
            }
            shootLaser(board.getNeighbour(space,heading),heading);
        }else{
            hitsPlayer = board.getSpace(space.x,space.y).getPlayer();
            }
        return hitsPlayer;
    }

    @Override
    public void doAction(GameController gameController) {
        for (Player player: gameController.board.getPlayers()) {
            RobotLaser robotLaser = new RobotLaser(board,player);
            robotLaser.shootLaser(player.getSpace(),player.getHeading());
            if(robotLaser.shootLaser(player.getSpace(),player.getHeading()) != null){
                robotLaser.shootLaser(player.getSpace(),player.getHeading()).discardCard(new DamageCard(Damage.SPAM));
                System.out.println("Player: " + robotLaser.shootLaser(player.getSpace(), player.getHeading()) + " was shot by " + player + "'s laser!");
            }
        }
    }

    @Override
    public int getPrio() {
        return 6;
    }
}
