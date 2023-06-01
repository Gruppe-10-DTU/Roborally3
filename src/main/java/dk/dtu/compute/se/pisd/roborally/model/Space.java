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

import java.util.EnumSet;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class Space extends Subject {

    protected transient final Board board;

    protected final int x;

    protected final int y;

    protected transient Player player;

    private String background = "Empty";
    private EnumSet<Heading> walls;


    /**
     * @param board The playing board
     * @param x     The coordinate on the x axis
     * @param y     The coordinate on the y axis
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        player = null;
        board.setSpace(this);
        walls = EnumSet.noneOf(Heading.class);
    }

    public void setWalls(EnumSet<Heading> walls) {
        this.walls = walls;
    }

    public void setWall(Heading wall){
        this.walls.add(wall);
    }

    /**
     *
     * Checks if there's a wall in the field the object is trying to move into
     *
     * @param heading The moving direction
     * @return true if there's a wall in the way, otherwise false.
     */
    public boolean hasWall(Heading heading) {
        return walls.contains(heading.reverse());
    }

    /**
     * @param heading The way a player is moving
     * @return Boolean saying if it's possible to move out of the space
     * @author Nilas Thoegersen
     */
    public boolean getOut(Heading heading) {
        return walls.contains(heading);
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * Method to move a player onto the field
     * Dublivated in the Player class
     *
     * @param player The player moving there
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void setPlayer(Player player) {
        Player oldPlayer = this.player;
        if (player != oldPlayer &&
                (player == null || board == player.board)) {
            this.player = player;
            if (oldPlayer != null) {
                // this should actually not happen
                oldPlayer.setSpace(null);
            }
            if (player != null) {
                player.setSpace(this);
            }
            notifyChange();
        }
    }

    void playerChanged() {
        // This is a minor hack; since some views that are registered with the space
        // also need to update when some player attributes change, the player can
        // notify the space of these changes by calling this method.
        notifyChange();
    }

    public EnumSet<Heading> getWalls() {
        return walls;
    }
    public String toString(){ return x + " " + y;}

    public int getX() {
        return x;
    }

    public Board getBoard() {
        return board;
    }

    public int getY() {
        return y;
    }
}
