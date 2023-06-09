package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.controller.SequenceActions.SequenceVisitor;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Checkpoint extends Space implements SequenceAction {

    private Checkpoint previous;

    private Set<Player> players;

    private int number;

    /**
     * @param board  The playing board
     * @param x      The coordinate on the x axis
     * @param y      The coordinate on the y axis
     * @param number The position of the checkpoint in the array
     * @author Nilas Thoegersen
     * @author Sandie Petersen
     */
    public Checkpoint(Board board, int x, int y, int number) {
        super(board, x, y);
        this.players = new HashSet<>(board.getPlayersNumber());
        this.number = number;
        board.addBoardActions(this);
    }

    /**
     * @param board    The playing board
     * @param x        The coordinate on the x axis
     * @param y        The coordinate on the y axis
     * @param number   The position of the checkpoint in the array
     * @param previous The previous checkpoint
     * @author Nilas Thoegersen
     * @author Sandie Petersen
     */
    public Checkpoint(Board board, int x, int y, int number, Checkpoint previous) {
        super(board, x, y);
        this.players = new HashSet<>(board.getPlayersNumber());
        this.number = number;
        this.previous = previous;
        board.addBoardActions(this);
        board.setWincondition(this);
    }

    public Checkpoint getPrevious() {
        return previous;
    }

    public void setPrevious(Checkpoint previous) {
        this.previous = previous;
    }

    /**
     * Adds a player to the checkpoint.
     *
     * @param player The player to be addedee
     * @return True if it was possible to add the player
     * @author Nilas
     */
    protected boolean addPlayer(Player player) {
        if (previous == null) {
            return players.add(player);
        } else if (previous.checkPlayer(player)) {
            return players.add(player);
        }
        return false;
    }


    /**
     * Simple method that checks if the player is already added
     *
     * @param player The player to be added
     * @return If the player is on the checkpoint.
     * @author Nilas Thoegersen
     */
    public boolean checkPlayer(Player player) {
        return this.players.contains(player);
    }


    /**
     * @param gameController The main controller for the game
     * @author Nilas Thoegersen
     */
    @Override
    public void doAction(GameController gameController) {
        for (Player player : board.getPlayers()
        ) {
            if (player.getSpace().getClass().equals(this.getClass())) {
                if(((Checkpoint) player.getSpace()).addPlayer(player)){
                    gameController.board.addGameLogEntry(player, "Reached checkpoint " + ((Checkpoint) player.getSpace()).getNumber());
                }
            }
        }
    }

    public void setPlayers(List<Player> players){
        this.players.addAll(players);
    }

    public List<Player> getPlayers(){

        return players.stream().toList();
    }
    public int getNumber(){
        return this.number;
    }

    /**
     * @return The prio for the class
     * @author Nilas Thoegersen
     */
    @Override
    public int getPrio() {
        return 8;
    }

    @Override
    public void accept(Player[] players, SequenceVisitor visitor){
        visitor.visit(players, this);
    }
}
