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
package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class CommandCard extends Subject implements Card{

    private Command command;
    String type = "Command";


    public CommandCard(@NotNull Command command) {
        this.command = command;
    }

    public CommandCard(){

    }
    @Override
    public String getName() {
        return command.displayName;
    }

    /**
     * @author Philip Astrup Cramer
     * @author Nilas Thoegersen
     * @param gameController
     */
    @Override
    public void doAction(GameController gameController) {
        Player player = gameController.board.getCurrentPlayer();
        switch (command) {
            case FORWARD:
                gameController.moveForward(player, 1);
                break;
            case RIGHT:
                gameController.turnRight(player);
                break;
            case LEFT:
                gameController.turnLeft(player);
                break;
            case FAST_FORWARD:
                gameController.moveForward(player,2);
                break;
            case MOVE_3:
                gameController.moveForward(player,3);
                break;
            case REVERSE:
                gameController.reverse(player);
                break;
            case UTURN:
                gameController.uTurn(player);
                break;
            case AGAIN:
                gameController.again(player);
                break;
            case OPTION_LEFT_RIGHT:
                gameController.board.setPhase(Phase.PLAYER_INTERACTION);
                break;
            default:
                // DO NOTHING (for now)
        }
    }

    @Override
    public boolean isInteractive() {
        return this.command.isInteractive();
    }

    public List<Command> getOptions(){
        return this.command.getOptions();
    }

    @Override
    public String getType() {
        return "Command";
    }
}
