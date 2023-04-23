package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class Gear extends Space implements SequenceAction {

    private Heading heading;

    public Gear(Heading heading, Board board, int x, int y){
        super(board, x, y);
        this.heading = heading;
        board.addBoardActions(this);
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }

    /**
     * Check each players standing space, and if on gear, rotates the player.
     * @param gameController The game controller
     */
    @Override
    public void doAction(GameController gameController) {
        for (Player player : board.getPlayers()
        ) {
            if(player.getSpace().getClass().equals(this.getClass())){
                ((Gear)player.getSpace()).turnPlayer(player);
            }
        }
    }

    @Override
    public int getPrio() {
        return 4;
    }

    /**
     * Turns the player either left or right, depending on the board.
     * @param player The player to be turned
     */
    private void turnPlayer(Player player){
        if(heading == Heading.EAST){
            player.getHeading().next();
        }else{
            player.getHeading().prev();
        }
    }
}
