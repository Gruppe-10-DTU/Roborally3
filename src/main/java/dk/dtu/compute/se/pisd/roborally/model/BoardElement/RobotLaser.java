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

public class RobotLaser implements SequenceAction{
    private Board board;
    public RobotLaser(Board board) {
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
     * the method should throw an out-of-bounds error at some point.
     */
    public void shootLaser(@NotNull Space space, Heading heading) throws SpaceOutOfBoundsException {
        if(board.getNeighbour(space, heading).getPlayer() != null) {
            if(board.getNeighbour(space,heading) == null){
                return;
            }
            shootLaser(board.getNeighbour(space,heading),heading);
        }else{
            board.getSpace(space.x,space.y).getPlayer().discardCard(new DamageCard(Damage.SPAM));
        }
    }

    @Override
    public void doAction(GameController gameController) {
        gameController.board.getPlayers().parallelStream().forEach(player -> {
            shootLaser(player.getSpace(),player.getHeading());
        });
    }

    @Override
    public int getPrio() {
        return 6;
    }
}
