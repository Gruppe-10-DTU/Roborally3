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
        if(board.getNeighbour(space, heading).getPlayer() == null && (!board.getNeighbour(space,heading).hasWall(heading) || !board.getNeighbour(space,heading).getOut(heading))) {
            shootLaser(board.getNeighbour(space,heading),heading);
        }else{
            return board.getSpace(space.x,space.y).getPlayer();
            }
        return null;
    }

    @Override
    public void doAction(GameController gameController) {
        for (Player player: gameController.board.getPlayers()) {
            RobotLaser robotLaser = new RobotLaser(board,player);
            Player hit = robotLaser.shootLaser(player.getSpace(),player.getHeading());
            //If the hit player is not null, we will then add a damage card to their discard-pile.
            if(hit != null){
                hit.discardCard(new DamageCard(Damage.SPAM));
                System.out.println("Player: " + robotLaser.shootLaser(player.getSpace(), player.getHeading()) + " was hit by " + player + "'s laser!");
            }
        }
    }

    @Override
    public int getPrio() {
        return 6;
    }
}
