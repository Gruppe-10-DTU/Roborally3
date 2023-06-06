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
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.Cards.Card;
import dk.dtu.compute.se.pisd.roborally.model.Cards.CommandCardField;
import dk.dtu.compute.se.pisd.roborally.model.Cards.PlayerCardDeck;
import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.EAST;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class Player extends Subject implements Comparable<Player> {

    final public static int NO_REGISTERS = 5;
    final public static int NO_CARDS = 8;

    transient public Board board;

    private String name;
    private String color;

    private Space space;
    private int priority;
    private Heading heading = EAST;
    private final PlayerCardDeck deck;

    private int energy;

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Increase the energy of the player
     *
     * @author Nilas Thoegersen
     */
    public void incrementEnergy() {
        this.energy++;
    }

    private final CommandCardField[] program;
    private final CommandCardField[] cards;

    /**
     * @param board The playing board
     * @param color The color of the plyaer
     * @param name  The name of the player
     * @authorEkkart Kindler, ekki@dtu.dk
     * @author Sandie Petersen
     * @author Philip Astrup Cramer
     * @author Nilas Thoegersen
     */
    public Player(@NotNull Board board, String color, @NotNull String name) {
        this.board = board;
        this.name = name;
        this.color = color;
        this.deck = new PlayerCardDeck();
        this.space = null;
        this.energy = 0;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
        }
    }

    Player(){
        this.deck = new PlayerCardDeck();
        this.space = null;
        this.energy = 0;

        program = new CommandCardField[NO_REGISTERS];
        cards = new CommandCardField[NO_CARDS];

    }

    public String getName() {
        return name;
    }

    /**
     * Updates the players name
     *
     * @param name The player name
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public String getColor() {
        return color;
    }

    /**
     * Update the players color
     *
     * @param color The new color
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }

    public Space getSpace() {
        return space;
    }

    /**
     * Sets the players new position, and update the old fields plyaer value.
     * Will not execute if the space doesn't change
     *
     * @param space The new space of the player
     * @author
     */
    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }

    public Heading getHeading() {
        return heading;
    }

    /**
     * Changes the players heading and update the player
     *
     * @param heading The new heading of the player
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    public CommandCardField getCardField(int i) {
        return cards[i];
    }

    /**
     * Draw a card from the players deck
     *
     * @return A card from the deck
     * @author Philip Astrup Cramer
     */
    public Card drawCard() {
        return this.deck.drawCard();
    }

    public void discardCard(Card card) {
        this.deck.discard(card);
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setPlayer(){
        for (int i = 0; i < program.length; i++) {
            program[i].setPlayer(this);
        }

        for (int i = 0; i < cards.length; i++) {
            cards[i].setPlayer(this);
        }
    }

    /**
     * @author Sandie Petersen
     * @param o the object to be compared.
     * @return The priority of the object
     * Used to compare the priorities of the players
     */
    @Override
    public int compareTo(@NotNull Player o) {
        return Integer.compare(this.priority, o.priority);
    }

}
