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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public enum Command {

    // This is a very simplistic way of realizing different commands.

    FORWARD("Fwd", 6),
    REVERSE("Reverse", 1),
    RIGHT("Turn Right", 3),
    LEFT("Turn Left", 3),
    UTURN("U-Turn", 1),
    FAST_FORWARD("Fast Fwd", 4),

    MOVE_3("Move 3", 1),

    // XXX Assignment P3
    OPTION_LEFT_RIGHT("Left OR Right", 1, LEFT, RIGHT),
    AGAIN("Repeat", 1, OPTION_LEFT_RIGHT);

    public final String displayName;

    final int cardAmount;

    // XXX Assignment P3
    // Command(String displayName) {
    //     this.displayName = displayName;
    // }
    //
    // replaced by the code below:

    private List<Command> options;

    Command(String displayName, int cardAmount, Command... options) {
        this.displayName = displayName;
        this.cardAmount = cardAmount;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }

    /**
     * @return boolean showing if the card is interactive
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public boolean isInteractive() {
        return !options.isEmpty();
    }

    public List<Command> getOptions() {
        return options;
    }

    public int getAmount() {
        return this.cardAmount;
    }
}
