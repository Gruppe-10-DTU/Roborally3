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
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.PriorityAntenna;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.RebootToken;
import javafx.util.Pair;
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
    private int width;

    private int maxPlayers;

    private int height;

    private String boardName;


    private int number = 1;

    private Integer gameId;
    private PriorityAntenna priorityAntenna;

    private Space[][] spaces;

    private List<Player> players = new ArrayList<>();

    private Player current;

    private Phase phase = INITIALISATION;

    private int step = 0;

    private boolean stepMode;
    private Checkpoint wincondition;
    private List<Pair<String, String>> gameLog;

    private PriorityQueue<Spawn> spawnPriority = new PriorityQueue<>();

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

    private Pit pit;
    public Pit getPit() {return pit;}
    public void setPit(Pit pit) {
        this.pit = pit;
    }

    public void setRebootToken(RebootToken rebootToken) {
        this.rebootToken = rebootToken;
    }

    PriorityQueue<Player> playerOrder = new PriorityQueue<>();

    public Board(){
        this.boardActions = new TreeSet<>(new SequenceActionComparator());
    }

    /**
     * @param width        width of the board
     * @param height       height of the board
     * @param boardName    the board name
     * @param playerAmount The amount of players in the game
     * @param boardArray   Json array of the board
     *                     Loads the file of the requested board and creates all the indicidual spacess on the board
     * @author Sandie Petersen
     */
    public Board(int width, int height, @NotNull String boardName, int playerAmount, JSONArray boardArray) {
        this.boardActions = new TreeSet<>(new SequenceActionComparator());
        this.boardName = boardName;
        this.maxPlayers = playerAmount;
        this.width = width;
        this.height = height;
        this.gameLog = new ArrayList<>();
        JSONArray courseArray = new JSONArray();

        if(boardArray == null) {
            JSONArray spawnArray = new JSONReader("src/main/resources/boards/spawnBoard" + playerAmount + ".json").getJsonSpaces();

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
            for (int i = 0; i < spawnArray.length(); i++) {

                courseArray.put(spawnArray.get(i));
            }
        }else{
            JSONArray tmp;
            for (int i = 0; i < boardArray.length(); i++) {
                tmp = boardArray.getJSONArray(i);
                for (int j = 0; j < tmp.length(); j++) {
                    courseArray.put(tmp.get(j));
                }
            }
        }
        spaces = new Space[width][height];

        //Loop and create the remaining spaces of the first 3 rows, the course section
        Checkpoint prevChekpoint = null;
        Heading heading = null;
        JSONArray tmp;
        Checkpoint[] checkpoints = new Checkpoint[3];
        for (int i = 0; i < courseArray.length(); i++) {

            JSONObject current = courseArray.getJSONObject(i);
            int x = current.getInt("x");
            int y = current.getInt("y");

            switch (current.getString("type")) {
                case "Wall":
                    EnumSet<Heading> walls = EnumSet.copyOf(List.of(Heading.valueOf(current.getString("direction"))));
                    Space wall = new Space(this, x, y);
                    wall.setWalls(walls);
                    spaces[x][y] = wall;
                    break;
                case "Energy":
                    Energy energy = new Energy(this, x, y);
                    spaces[x][y] = energy;
                    break;
                case "Conveyor":
                    heading = Heading.valueOf(current.getString("direction"));
                    Conveyorbelt conveyorbelt;
                    if (!current.has("turn") || current.getString("turn").equals("")) {
                        conveyorbelt = new Conveyorbelt(this, x, y, heading);
                    } else {
                        Heading turn = Heading.valueOf(current.getString("turn"));
                        conveyorbelt = new Conveyorbelt(this, x, y, heading, turn);
                    }
                    spaces[x][y] = conveyorbelt;
                    break;
                case "FastConveyorbelt":
                    heading = Heading.valueOf(current.getString("direction"));
                    FastConveyorbelt fastConveyorbelt;
                    if (!current.has("turn") || current.getString("turn").equals("")) {
                        fastConveyorbelt = new FastConveyorbelt(this, x, y, heading);
                    } else {
                        Heading turn = Heading.valueOf(current.getString("turn"));
                        fastConveyorbelt = new FastConveyorbelt(this, x, y, heading, turn);
                    }
                    spaces[x][y] = fastConveyorbelt;
                    break;
                case "Checkpoint":
                    Checkpoint checkpoint = new Checkpoint(this, x, y, current.getInt("number"));
                    spaces[x][y] = checkpoint;
                    checkpoints[checkpoint.getNumber() - 1] = checkpoint;
                    break;
                case "Lazer":
                    Heading shootingDirection = Heading.valueOf(current.getString("direction"));
                    BoardLaser boardLaser = new BoardLaser(this, x, y, shootingDirection);
                    spaces[x][y] = boardLaser;
                    break;
                case "Gear":
                    Heading turnDirection = Heading.valueOf(current.getString("direction"));
                    Gear gear = new Gear(turnDirection, this, x, y);
                    spaces[x][y] = gear;
                    break;
                case "Push":
                    Heading pushDirection = Heading.valueOf(current.getString("direction"));
                    int step = current.getInt("number");
                    Push push = new Push(this, x, y, step, pushDirection);
                    spaces[x][y] = push;
                    break;
                case "Pit":
                    Pit pit = new Pit(this, x, y);
                    spaces[x][y] = pit;
                    break;
                case "Priority":
                    this.priorityAntenna = new PriorityAntenna(this, x, y);
                    spaces[x][y] = priorityAntenna;
                    break;
                case "Reboot" :
                    Heading exit = Heading.valueOf(current.getString("direction"));
                    RebootToken rebootToken = new RebootToken(this, x, y, exit);
                    spaces[x][y] = rebootToken;
                    break;
                case "Spawn":
                    int priority = current.getInt("number");
                    Spawn spawn = new Spawn(this,x,y,priority);
                    spawnPriority.add(spawn);
                    spaces[x][y] = spawn;
                    //Spawn point
                    break;
                default:
                    Space space = new Space(this, x, y);
                    spaces[x][y] = space;
            }

            if(current.has("walls") && (tmp = current.getJSONArray("walls")).length() > 0){
                for (int j = 0; j < tmp.length(); j++) {
                    spaces[x][y].setWall(tmp.getEnum(Heading.class, j));
                }

            }
        }
        for (int i = checkpoints.length - 1; i > 0; i--) {
            checkpoints[i].setPrevious(checkpoints[i-1]);
        }
        this.wincondition = checkpoints[2];
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
        spaces[space.getX()][space.getY()] = space;
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

    public Space getSpace(Space space){
        return getSpace(space.getX(), space.getY());
    }

    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    public int getNumberOfPlayers() {
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

    public Player getPlayerByName(String name){
        return players.stream().filter(x-> x.getName().equals(name)).findAny().orElse(null);
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
            player.setPriority(Math.abs((playerSpace.getX() - start[0])) + Math.abs(playerSpace.getY() - start[1]));
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

    /**
     * @Auther Sandie Petersen
     * @return The next available spawn space
     */
    public Spawn nextSpawn() {
        return spawnPriority.remove();
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
        int x = space.getX();
        int y = space.getY();
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
            int distX = Math.abs((centerPlayer.getSpace().getX() - otherPLayer.getSpace().getX()));
            int distY = Math.abs((centerPlayer.getSpace().getY() - otherPLayer.getSpace().getY()));
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

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getBoardName(){
        return boardName;
    }

    public int getMaxPlayers(){
        return this.maxPlayers;
    }
    public void setMaxPlayers(int maxPlayers){
        this.maxPlayers = maxPlayers;
    }

    public List<Pair<String, String>> getGameLog(){
        return gameLog;
    }
    public void addGameLogEntry(Player player, String event){
        if(gameLog == null) return; //Allows testing without instantiating log
        if(gameLog.size() == 50) gameLog.remove(0);
        if(player == null){
            gameLog.add(new Pair<>("black", event + "\n"));
        } else {
            gameLog.add(new Pair<>(player.getColor(), player.getName() + ": "+ event + "\n"));
        }
    }
    public void addPlayerToOder(Player player) {
        playerOrder.add(player);
    }

    public void updatePlayers(List<Player> newPlayers, String clientName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName() != clientName) {
                players.set(i, newPlayers.get(i));
            }
        }
    }


}
