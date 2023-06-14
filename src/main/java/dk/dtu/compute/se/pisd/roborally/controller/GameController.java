/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.FieldAction.Pit;
import dk.dtu.compute.se.pisd.roborally.model.SequenceAction.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.model.SequenceAction.SequenceAction;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.Cards.*;
import javafx.application.Platform;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    public Board board;
    final public AppController appController;
    private String clientName;
    private final AtomicInteger version;

    public GameController(@NotNull Board board, AppController appController) {
        this.board = board;
        this.appController = appController;
        this.version = new AtomicInteger(-1);
    }


    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space) {
        if (space.getPlayer() == null) {
            Player currentPlayer = board.getCurrentPlayer();
            if (!currentPlayer.getSpace().equals(space) || !space.hasWall(currentPlayer.getHeading())) {
                // board.setStep(board.getStep() + 1);
                currentPlayer.setSpace(space);
                board.setCurrentPlayer(board.getPlayer((board.getPlayerNumber(currentPlayer) + 1) % board.getNumberOfPlayers()));
            }
        }
    }

    /**
     * Start the programming phase
     *
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    // XXX: V2
    public void startProgrammingPhase() {
        for (Player players: board.getPlayers()) {
            players.setRebooting(false);
        }
        board.setPhase(Phase.PROGRAMMING);
        board.calculatePlayerOrder();
        board.setStep(0);

        for (int i = 0; i < board.getNumberOfPlayers(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCanMoveCard(true);
                    if (field.getCard() != null) {
                        player.discardCard(field.getCard());
                        field.setCard(null);
                        field.setVisible(true);
                    }
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCanMoveCard(true);
                    if (field.getCard() == null) {
                        field.setCard(player.drawCard());
                        field.setVisible(true);
                    }
                }
            }
        }
        board.nextPlayer();

    }

    /**
     * Ends the programming phase
     *
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void finishProgrammingPhase() {
        if(clientName == null || !board.nextPlayer()) {
            for (Player player : board.getPlayers()) {
                player.registerChaos();
            }
            makeProgramFieldsInvisible();
            makeProgramFieldsVisible(0);
            board.setPhase(Phase.ACTIVATION);
            board.calculatePlayerOrder();
            board.nextPlayer();
            //board.setCurrentPlayer(board.getPlayer(0));
            board.setStep(0);
            //updateBoard();
        }
        if(clientName != null){
            this.getClient().registerChaos();
            updateBoard();
        }
    }

    /**
     * Make a field visible for all players
     *
     * @param register the index of the register
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    // XXX: V2
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getNumberOfPlayers(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    /**
     * Hide all fields so nobody can see which cards have been played
     *
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getNumberOfPlayers(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    /**
     * Automatically execute the rest of the cards
     *
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }


    /**
     * Execute the cards one at a time
     *
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    /**
     * Loop to execute the cards continuous. Stops if either the mode is set to step or if a card needs player interaction
     *
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    private void continuePrograms() {
        do {
            executeNextStep();

        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
        if(clientName != null){
            updateBoard();
        }
    }

    /**
     * Execute a card and then increment the step by one
     *
     * @author Søren og Philip
     */
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            Card card = currentPlayer.getProgramField(step).getCard();
            if (card != null && card.isInteractive()) {
                card.doAction(this);
                return;
            } else if (card != null) {
                card.doAction(this);
            }
            incrementStep(step);
        }
    }

    /**
     * Go to the next card to be executed.
     *
     * @param step The current step
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void incrementStep(int step) {
        boolean playerIsSet = board.nextPlayer();

        if (!playerIsSet) {
            step++;
            //End the phase by activating the board.
            executeBoardActions();
            if (step < Player.NO_REGISTERS) {
                makeProgramFieldsVisible(step);
                board.setStep(step);
                board.calculatePlayerOrder();
                board.nextPlayer();
                checkIfGameIsDone();

            } else {
                startProgrammingPhase();
            }
        }
    }

    /**
     * Executes current command from command card for player
     * @param player The player being affected
     * @param command The command being executed
     * @author Søren Wünsche
     */
    private void executeCommand(@NotNull Player player, Command command) {
        if (player.board == board && command != null) {
            switch (command) {
                case FORWARD:
                    this.moveForward(player, 1);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.moveForward(player,2);
                    break;
                case MOVE_3:
                    this.moveForward(player,3);
                    break;
                case REVERSE:
                    this.reverse(player);
                    break;
                case UTURN:
                    this.uTurn(player);
                    break;
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    /**
     * A function used to execute a player interactive card.
     *
     * @param command The command to be executed
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void executeCommandOptionAndContinue(Command command) {
        board.setPhase(Phase.ACTIVATION);
        executeCommand(board.getCurrentPlayer(), command);
        incrementStep(board.getStep());
        updateBoard();
    }

    /**
     * @param player  The player to be moved
     * @param heading The way the player is moving
     *                Moves the player one step in a specific direction
     * @author Asbjørn Nielsen
     * @author Nilas
     */
    public void movePlayer(@NotNull Player player, Heading heading) {
        Space space = board.getNeighbour(player.getSpace(), heading);

        if (space != null && !space.hasWall(heading) && !player.getSpace().getOut(heading) ) {
            if (space instanceof Pit){
                board.getPit().doFieldAction(this,player);
                return;
            }
            if (space.getPlayer() != null) {
                pushRobot(player, space.getPlayer());
            }
            if (space.getPlayer() == null) {
                player.setSpace(board.getNeighbour(player.getSpace(), heading));
            }
        } else if (space == null) {
            board.addGameLogEntry(player,"Fell of the map");
            rebootRobot(player);
        }
    }

    /**
     * @param player       Moves the player forwards, if the target space don't have a wall.
     * @param SpacesToMove Amount of steps a player is moving
     * @author Asbjørn Nielsen
     */
    public void moveForward(@NotNull Player player, int SpacesToMove) {
        for (int i = 0; i < SpacesToMove; i++){
            movePlayer(player, player.getHeading());
            if(player.getIsRebooting())
                break;
        }
    }

    /**
     * Move the player backwards
     *
     * @param player The moving player
     * @author Asbjørn Nielsen
     * @author Nilas Thoegersen
     */
    public void reverse(@NotNull Player player) {
        player.setHeading(player.getHeading().reverse());
        moveForward(player, 1);
        player.setHeading(player.getHeading().reverse());
    }

    /**
     * @author Asbjørn Nielsen
     * @param pushing The robot who is doing the pushing
     * @param pushed  The pushed robot
     *                Pushes a row of robots.
     * @author Asbjørn Nielsen
     */
    public void pushRobot(@NotNull Player pushing, @NotNull Player pushed) {
        if (pushable(pushing)) {
            Space neighbour = board.getNeighbour(pushed.getSpace(), pushing.getHeading());
            if (neighbour == null || neighbour instanceof Pit) {
                rebootRobot(pushed);
                return;
            }
            if (neighbour.getPlayer() != null) {
                pushRobot(pushing, board.getPlayer(board.getPlayerNumber(board.getNeighbour(pushed.getSpace(), pushing.getHeading()).getPlayer())));
            }
            pushed.setSpace(neighbour);
        } else {
            pushed.setSpace(pushed.getSpace());
        }
    }

    /**
     * Pushes a player
     *
     * @param pusher The pushing player
     * @return If the push was possible
     * @author Asbjørn Nielsen
     */
    public boolean pushable(@NotNull Player pusher) {
        boolean able = true;
        Player nxt = board.getNeighbour(pusher.getSpace(), pusher.getHeading()).getPlayer();
        while (nxt != null) {
            if (nxt.getSpace().hasWall(nxt.getHeading()) || nxt.getSpace().getOut(pusher.getHeading())) {
                able = false;
                break;
            } else {
                Space space = board.getNeighbour(nxt.getSpace(), pusher.getHeading());
                if(space == null){
                    return true;
                }
                nxt = space.getPlayer();
            }
        }
        return able;
    }

    /**
     * Execute all actions, by prio. Checks if the game is done afterwards
     *
     * @author Nilas Thoegersen
     */
    public void executeBoardActions() {
        for (SequenceAction sequenceAction : board.getBoardActions()
        ) {
            sequenceAction.doAction(this);
        }
    }

    /**
     * @param player Turns the aforementioned player one nook to the right
     * @author Asbjørn Nielsen
     */
    public void turnRight(@NotNull Player player) {
        player.setHeading(player.getHeading().next());
    }

    /**
     * @param player The turning player
     * @author Søren
     */
    public void turnLeft(@NotNull Player player) {
        player.setHeading(player.getHeading().prev());
    }

    /**
     * Turn the player around
     *
     * @param player The turning player
     * @author Asbjørn Nielsen
     */
    public void uTurn(@NotNull Player player) {
        player.setHeading(player.getHeading().prev().prev());
    }

    /**
     * @param source The source of the card
     * @param target The target of the card
     * @return Boolean saying if the move was possible
     * @author Ekkart Kindler, ekki@dtu.dk
     * @author Nilas Thoegersen
     */
    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        Card sourceCard = source.getCard();
        Card targetCard = target.getCard();

        //Again is not allowed in the first program field.
        if(target.equals(target.getPlayer().getProgramField(0)) && Command.AGAIN.displayName.equals(sourceCard.getName())){
            return false;
        }

        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Check if a game is done, and if yes, end it.
     *
     * @author Nilas og Søren
     */
    public void checkIfGameIsDone() {
        Checkpoint checkpoint = board.getWincondition();
        for (Player player : board.getPlayers()) {
            if (checkpoint.checkPlayer(player)) {
                board.setPhase(Phase.FINISHED);
                updateBoard();
                appController.endGame(player);
                return;
            }
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }


    /**
     * @param player The player getting rebooted
     * @author Philip
     */
    public void rebootRobot(Player player){
        player.receiveCard(new DamageCard(Damage.SPAM));
        player.receiveCard(new DamageCard(Damage.SPAM));
        player.discardRegisters();
        player.discardHand();
        board.getRebootToken().doFieldAction(this, player);

    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * Get the clients object
     *
     * @return The client
     * @author Philip
     */
    public Player getClient(){
        return this.board.getPlayerByName(this.clientName);
    }

    /**
     * Executes the card from the previous register
     * @param player the affected player
     * @author Nilas Thoegersen
     * @author Philip
     */
    public void again(Player player) {
        if (this.board.getStep() == 0) {
            incrementStep(board.getStep());
            return;
        }
        //Get the previous card
        Card oldCard = player.getProgramField(board.getStep()-1).getCard();
        if (oldCard != null) {
            oldCard.doAction(this);
            if (!oldCard.isInteractive()) {
                incrementStep(board.getStep());
            }
        }
    }

    /**
     * Replaces the board
     * @param board instance of board object
     * @param version version number
     * @author Nilas Thoegersen & Sandie Petersen
     */
    public void replaceBoard (Board board, int version) {
        this.board = board;
        this.version.set(version);

    }

    /**
     * Refresh the board view, by creating a new one.
     *
     * @author Nilas Thoegersen
     */
    public void refreshView(){
        Platform.runLater(appController::updateBoard);
    }

    /**
     * Pushed the updated board to the server using the HttpController
     * @author Nilas Thoegersen
     */
    public void updateBoard(){
        if(clientName != null) {
            HttpController.updateBoard(board, version.incrementAndGet());
        }
    }


    /**
     * Helper function to see if the player is done programming.
     *
     * @author Nilas Thoegersen
     */
    public boolean showButtonsIfCurrent(Player player) {
        boolean client = clientName != null;
        boolean current = !player.board.getCurrentPlayer().equals(player);

        return client && current;
    }

    /**
     * Update all players on the current board, from the new board
     *
     * @param newBoard       The new board from the server
     * @param currentVersion version from the thread
     * @author Sandie Petersen
     */
    public  void updatePlayers (Board newBoard, Integer currentVersion) {
        board.updatePlayers(newBoard.getPlayers(), clientName);
        board.setPlayerOrder(newBoard.getPlayerOrder());
        board.setCurrentPlayer(board.getPlayerByName(newBoard.getCurrentPlayer().getName()));
        version.set(currentVersion);
    }

}
