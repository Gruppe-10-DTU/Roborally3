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
import dk.dtu.compute.se.pisd.roborally.controller.JSONReader;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement.*;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.Pit;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.RebootToken;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class Board extends Subject {
    public final int width;

    public final int height;

    public final String boardName;

    public int playerAmount;

    private Integer gameId;
    private PriorityAntenna priorityAntenna;

    private final Space[][] spaces;

    private final List<Player> players = new ArrayList<>();

    private Player current;

    private Phase phase = INITIALISATION;

    private int step = 0;

    private boolean stepMode;
    private Checkpoint wincondition;

    public Checkpoint getWincondition() {
        return wincondition;
    }

    /**
     * Sets the final win condition
     *
     * @param wincondition The final checkpoint
     * @author Nilas Thoegersen
     */
    public void setWincondition(Checkpoint wincondition) {
        this.wincondition = wincondition;
    }

    private final TreeSet<SequenceAction> boardActions;


    private RebootToken rebootToken;

    public RebootToken getRebootToken() {
        return rebootToken;
    }

    public void setRebootToken(RebootToken rebootToken) {
        this.rebootToken = rebootToken;
    }

    final PriorityQueue<Player> playerOrder = new PriorityQueue<>();


    /**
     * @param width        width of the board
     * @param height       height of the board
     * @param boardName    the board name
     * @param playerAmount The amount of players in the game
     *                     Loads the file of the requested board and creates all the indicidual spacess on the board
     * @author Sandie Petersen
     */
    public Board(int width, int height, @NotNull String boardName, int playerAmount) {
        this.boardActions = new TreeSet<>(new SequenceActionComparator());
        this.boardName = boardName;
        this.playerAmount = playerAmount;
        this.width = width;
        this.height = height;

        JSONArray spawnArray = new JSONReader("src/main/resources/boards/spawnBoard" + playerAmount + ".json").getJsonSpaces();

        JSONArray courseArray;
        switch (boardName) {
            case "Risky Crossing":
                courseArray = new JSONReader("src/main/resources/boards/RiskyCrossing.json").getJsonSpaces();
                break;
            case "Test":
                courseArray = new JSONReader("src/main/resources/boards/Test.json").getJsonSpaces();
                break;
            case "Burnout":
                courseArray = new JSONReader("src/main/resources/boards/Burnout.json").getJsonSpaces();
                break;
            default:
                courseArray = new JSONReader("src/main/resources/boards/RiskyCrossing.json").getJsonSpaces();
        }

        spaces = new Space[width][height];

        //Loop and create the spaces of the first 3 rows, the spawn section
        for (int i = 0; i < spawnArray.length(); i++) {

            JSONObject current = spawnArray.getJSONObject(i);
            int x = Integer.parseInt(current.getString("x"));
            int y = Integer.parseInt(current.getString("y"));

            switch (current.getString("Type")) {
                case "Priority":
                    this.priorityAntenna = new PriorityAntenna(this, x, y);
                    spaces[x][y] = priorityAntenna;
                    break;
                case "Wall":
                    EnumSet<Heading> walls = EnumSet.copyOf(List.of(Heading.valueOf(current.getString("Direction"))));
                    Space wall = new Space(this, x, y);
                    wall.setWalls(walls);
                    spaces[x][y] = wall;
                    break;
                case "Spawn":
                    Space spawn = new Space(this, x, y);
                    spaces[x][y] = spawn;
                    //Spawn point
                    break;
                default:
                    Space space = new Space(this, x, y);
                    spaces[x][y] = space;
                    break;
            }
        }

        //Loop and create the remaining spaces of the first 3 rows, the course section
        Checkpoint prevChekpoint = null;
        for (int i = 0; i < courseArray.length(); i++) {

            JSONObject current = courseArray.getJSONObject(i);
            int x = current.getInt("x");
            int y = current.getInt("y");

            switch (current.getString("Type")) {
                case "Wall":
                    EnumSet<Heading> walls = EnumSet.copyOf(List.of(Heading.valueOf(current.getString("Direction"))));
                    Space wall = new Space(this, x, y);
                    wall.setWalls(walls);
                    spaces[x][y] = wall;
                    break;
                case "Energy":
                    Energy energy = new Energy(this, x, y);
                    spaces[x][y] = energy;
                    break;
                case "Conveyor":
                    Heading heading = Heading.valueOf(current.getString("Direction"));
                    if (current.getInt("Number") == 1) {
                        Conveyorbelt conveyorbelt;
                        if (current.getString("Turn").equals("")) {
                            conveyorbelt = new Conveyorbelt(this, x, y, heading);
                        } else {
                            Heading turn = Heading.valueOf(current.getString("Turn"));
                            conveyorbelt = new Conveyorbelt(this, x, y, heading, turn);
                        }
                        spaces[x][y] = conveyorbelt;
                    } else {
                        FastConveyorbelt fastConveyorbelt;
                        if (current.getString("Turn").equals("")) {
                            fastConveyorbelt = new FastConveyorbelt(this, x, y, heading);
                        } else {
                            Heading turn = Heading.valueOf(current.getString("Turn"));
                            fastConveyorbelt = new FastConveyorbelt(this, x, y, heading, turn);
                        }
                        spaces[x][y] = fastConveyorbelt;
                    }
                    break;
                case "CheckPoint":
                    Checkpoint checkpoint;
                    if (prevChekpoint != null) {
                        checkpoint = new Checkpoint(this, x, y, current.getInt("Number"), prevChekpoint);
                    } else {
                        checkpoint = new Checkpoint(this, x, y, current.getInt("Number"));
                    }
                    prevChekpoint = checkpoint;
                    spaces[x][y] = checkpoint;
                    break;
                case "Lazer":
                    Heading shootingDirection = Heading.valueOf(current.getString("Direction"));
                    BoardLaser boardLaser = new BoardLaser(this, x, y, shootingDirection);
                    spaces[x][y] = boardLaser;
                    break;
                case "Gear":
                    Heading turnDirection = Heading.valueOf(current.getString("Direction"));
                    Gear gear = new Gear(turnDirection, this, x, y);
                    spaces[x][y] = gear;
                    break;
                case "Push":
                    Heading pushDirection = Heading.valueOf(current.getString("Direction"));
                    int step = current.getInt("Number");
                    Push push = new Push(this, x, y, step, pushDirection);
                    spaces[x][y] = push;
                    break;
                case "Pit":
                    Pit pit = new Pit(this, x, y);
                    spaces[x][y] = pit;
                    break;
                default:
                    Space space = new Space(this, x, y);
                    spaces[x][y] = space;
            }
        }
        this.stepMode = false;

    }

    /**
     * Contructor of the board
     *
     * @param width     Width of the board
     * @param height    Height of the board
     * @param boardName Name of the board
     */
    public Board(int width, int height, @NotNull String boardName) {
        this.boardName = boardName;
        this.boardActions = new TreeSet<>(new SequenceActionComparator());
        this.width = width;
        this.height = height;
        spaces = new Space[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Space space = new Space(this, x, y);
                spaces[x][y] = space;
            }
        }
        this.stepMode = false;
    }

    public void setSpace(Space space) {
        spaces[space.x][space.y] = space;
    }


    /**
     * @param sequenceAction Adds a sequence action to the array of actions needing to be executed at the end of each turn
     * @author Nilas Thoegersen
     */
    public void addBoardActions(SequenceAction sequenceAction) {
        this.boardActions.add(sequenceAction);
    }

    public Set<SequenceAction> getBoardActions() {
        return boardActions;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Board(int width, int height) {
        this(width, height, "defaultboard");
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }

    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    public int getPlayersNumber() {
        return players.size();
    }

    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }

    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            return players.get(i);
        } else {
            return null;
        }
    }

    /**
     * @author Sandie Petersen
     * clears the queue if needed
     * calculates the player priority and adds them to the queue
     */
    public void calculatePlayerOrder() {
        playerOrder.clear();

        Integer[] start = priorityAntenna.getSpace();

        for (Player player : players) {
            Space playerSpace = player.getSpace();
            player.setPriority(Math.abs((playerSpace.x - start[0])) + Math.abs(playerSpace.y - start[1]));
            playerOrder.add(player);
        }
    }

    /**
     * @return true if possible
     * @author Sandie Petersen
     * polls the next player if possible
     */
    public boolean nextPlayer() {
        if (playerOrder.size() > 0) {
            current = playerOrder.poll();
            return true;
        } else return false;


    }

    public Player getCurrentPlayer() {
        return current;
    }

    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }

    public boolean isStepMode() {
        return stepMode;
    }

    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }

    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }

    /**
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space   the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                y = (y + 1);
                break;
            case WEST:
                x = (x - 1);
                break;
            case NORTH:
                y = (y - 1);
                break;
            case EAST:
                x = (x + 1);
                break;
        }
        return getSpace(x, y);
    }

    /**
     * Calculates which players are within a given range
     * and returns an ArrayList of them
     *
     * @param centerPlayer The player calculating the range from
     * @return An list of players within the range of the players
     * @author Philip Astrup Cramer
     */
    public ArrayList<Player> playersInRange(Player centerPlayer, int range) {
        ArrayList<Player> result = new ArrayList<>();
        for (Player otherPLayer : this.players) {
            int distX = Math.abs((centerPlayer.getSpace().x - otherPLayer.getSpace().x));
            int distY = Math.abs((centerPlayer.getSpace().y - otherPLayer.getSpace().y));
            if (distX + distY <= range) {
                result.add(otherPLayer);
            }
        }
        result.remove(centerPlayer);
        return result;
    }

    public String getStatusMessage() {
        // this is actually a view aspect, but for making assignment V1 easy for
        // the students, this method gives a string representation of the current
        // status of the game

        // XXX: V2 changed the status so that it shows the phase, the player and the step
        return "Phase: " + getPhase().name() +
                ", Player = " + getCurrentPlayer().getName() +
                ", Step: " + getStep();
    }


}
