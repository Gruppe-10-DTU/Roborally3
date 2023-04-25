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

import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement.SequenceAction;
import org.jetbrains.annotations.NotNull;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;
    final public EndGame endGame;

    public GameController(@NotNull Board board, EndGame endGame) {
        this.board = board;
        this.endGame = endGame;
    }


    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        if(space.getPlayer() == null){
            Player currentPlayer = board.getCurrentPlayer();
            if(!currentPlayer.getSpace().equals(space) || !space.hasWall(currentPlayer.getHeading())) {
                board.setStep(board.getStep() + 1);
                currentPlayer.setSpace(space);
                board.setCurrentPlayer(board.getPlayer((board.getPlayerNumber(currentPlayer) + 1) % board.getPlayersNumber()));
            }
        }
    }

    // XXX: V2
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    if(field.getCard() != null) {
                        player.discardCard(field.getCard());
                        field.setCard(null);
                        field.setVisible(true);
                    }
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    if(field.getCard() == null) {
                        field.setCard(player.drawCard());
                        field.setVisible(true);
                    }
                }
            }
        }
    }

    // XXX: V2
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.calculatePlayerOrder();
        board.nextPlayer();
        //board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    // XXX: V2
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    // XXX: V2
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX: V2
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    // XXX: V2
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    // XXX: V2
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX: V2
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    executeCommand(currentPlayer, command);
                }
                    incrementStep(step);

                } else {
                    // this should not happen
                    assert false;
                }
            } else {
                // this should not happen
                assert false;
            }
    }

    public void incrementStep(int step){
        boolean playerIsSet = board.nextPlayer();

        if (!playerIsSet) {
            step++;

            if (step < Player.NO_REGISTERS) {
                makeProgramFieldsVisible(step);
                board.setStep(step);
                board.calculatePlayerOrder();
                board.nextPlayer();
            } else {
                startProgrammingPhase();
            }

        }

        /*int nextPlayerNumber = board.getPlayerNumber(board.getCurrentPlayer()) + 1;
        if (nextPlayerNumber < board.getPlayersNumber()) {
            board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
        } else {
            step++;
            if (step < Player.NO_REGISTERS) {
                makeProgramFieldsVisible(step);
                board.setStep(step);
                board.setCurrentPlayer(board.getPlayer(0));
            } else {
                startProgrammingPhase();
            }
        }*/
    }

    // XXX: V2
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
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

    public void executeCommandOptionAndContinue(Command command){
        board.setPhase(Phase.ACTIVATION);
        executeCommand(board.getCurrentPlayer(), command);
        incrementStep(board.getStep());

    }

    /**
     * @author Nilas
     * @param player The player to be moved
     * @param heading The way the player is moving
     * Moves the player one step in a specific direction
     */
    public void movePlayer(@NotNull Player player, Heading heading){
        Space space = board.getNeighbour(player.getSpace(), heading);
        if(space != null && !space.hasWall(heading) && !player.getSpace().getOut(heading)) {
            if(space.getPlayer() != null){
                pushRobot(player,space.getPlayer());
            }
            if(space.getPlayer() == null) {
                player.setSpace(board.getNeighbour(player.getSpace(),heading));
            }
        }
    }

    /**
     * @author Asbjørn Nielsen
     * @param player
     * Moves the player forwards, if the target space don't have a wall.
     */
    public void moveForward(@NotNull Player player) {
        movePlayer(player, player.getHeading());
    }

    public void reverse(@NotNull Player player){
        player.setHeading(player.getHeading().prev().prev());
        moveForward(player);
        player.setHeading(player.getHeading().prev().prev());
    }

    /**
     * @author Asbjørn Nielsen
     * @param pushing The robot who is doing the pushing
     * @param pushed The pushed robot
     * Pushes a row of robots.
     */
    public void pushRobot(@NotNull Player pushing, @NotNull Player pushed){
        if(board.getNeighbour(pushed.getSpace(),pushing.getHeading()).getPlayer() != null){
            pushRobot(pushing,board.getPlayer(board.getPlayerNumber(board.getNeighbour(pushed.getSpace(),pushing.getHeading()).getPlayer())));
        }
        if(!board.getNeighbour(pushed.getSpace(),pushing.getHeading()).hasWall(pushing.getHeading())){
            if(board.getNeighbour(pushed.getSpace(),pushing.getHeading()).getPlayer() == null) {
                pushed.setSpace(board.getNeighbour(pushed.getSpace(), pushing.getHeading()));
            }
        }
    }

    public void executeBoardActions(){
        for (SequenceAction sequenceAction : board.getBoardActions()
             ) {
            sequenceAction.doAction(this);
        }
    }

    /**
     * @author Asbjørn Nielsen
     * @param player
     * Moves the player forwards twice.
     */
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    /**
     * @author Asbjørn Nielsen
     * @param player
     * Turns the aforementioned player one nook to the right
     */
    public void turnRight(@NotNull Player player) {
        player.setHeading(player.getHeading().next());
    }

    // TODO Assignment V2
    public void turnLeft(@NotNull Player player) {
        player.setHeading(player.getHeading().prev());
    }

    public void uTurn(@NotNull Player player){
        player.setHeading(player.getHeading().prev().prev());
    }

    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }
    private void endGame(){
        Checkpoint checkpoint = board.getWincondition();
        for (Player player: board.getPlayers()
             ) {
            if(checkpoint.checkPlayer(player));{
                endGame.endGame(player);
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

}
