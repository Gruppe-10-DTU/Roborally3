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

import com.google.gson.Gson;
import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.GamesView;
import javafx.scene.control.Alert;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class AppController implements Observer, EndGame {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> BOARD_OPTIONS = Arrays.asList("Burnout", "Risky Crossing");
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    final private RoboRally roboRally;

    private GamesView gamesView;

    private Gson gson = new Gson();
    private String selectedBoard;
    private GameController gameController;

    public GamesView getGamesView() {
        return gamesView;
    }

    public void setGamesView(GamesView gamesView) {
        this.gamesView = gamesView;
    }

    /**
     * @param roboRally The game
     */
    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    /**
     * Creates a new game
     *
     * @author Sandie
     */
    public void newGame() {
        ChoiceDialog boardDialog = new ChoiceDialog(BOARD_OPTIONS.get(0), BOARD_OPTIONS);
        boardDialog.setTitle("Course");
        boardDialog.setHeaderText("Select course");
        Optional<String> boardresult = boardDialog.showAndWait();

        if (boardresult.isPresent()) {
            selectedBoard = boardresult.get();
        }

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);

        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();


        if (result.isPresent()) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }


            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.

            Board board = new Board(11, 8, selectedBoard, result.get(), null);

            gameController = new GameController(board, this);
            int numberOfPlayers = result.get();
            for (int i = 0; i < numberOfPlayers; i++) {


                /*TextInputDialog nameDialog = new TextInputDialog("Player" + (i + 1));
                nameDialog.setTitle("Player name");
                nameDialog.setHeaderText("Select player name");
                Optional<String> resultName = nameDialog.showAndWait();

                String entered = "Player" + (i + 1);
                if (resultName.isPresent()) {
                    entered = resultName.get();
                }*/

                Player player = new Player(board, PLAYER_COLORS.get(i), playerName(i));
                board.addPlayer(player);
                Space spawnSpace = board.nextSpawn();
                player.setSpace(board.getSpace(spawnSpace.getX(),spawnSpace.getY()));
            }
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    /**
     * Save a game into a file.
     *
     * @author Nilas Thoegersen
     */
    public void saveGame() {
        String file = "";
        String savedGameController = JSONReader.saveGame(gameController);
        TextInputDialog saveNameDialog = new TextInputDialog();
        saveNameDialog.setTitle("Save game");

        saveNameDialog.setHeaderText("Please name your save");
        Optional<String> resultName = saveNameDialog.showAndWait();
        while(resultName.isPresent() && Files.exists(Path.of("src/main/java/dk/dtu/compute/se/pisd/roborally/controller/savedGames", resultName.get()+".json"))) {
            saveNameDialog.setHeaderText("That name is taken, please write a new one");

            resultName = saveNameDialog.showAndWait();
        }

        try {
            //TODO: Gøre den mere dynamis. Ikke sikker på det virker med Jar
            File newSave = new File("src/main/java/dk/dtu/compute/se/pisd/roborally/controller/savedGames/"+resultName.get()+ ".json");
            newSave.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newSave));
            bufferedWriter.write(savedGameController);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Choose a file to load
     *
     * @author Nilas Thoegersen
     */
    public void loadGame() {
        File file;
        URI pathUri;
        try {
            //TODO: Gør stien dynamisk.
            file = new File("src/main/java/dk/dtu/compute/se/pisd/roborally/controller/savedGames");
        }catch (Exception e){
            System.out.println("No files found");
            return;
        }
        boolean test2 = file.isDirectory();
        String[] test = file.list();

        Optional<String> gameName = new ChoiceDialog<String>("None", file.list()).showAndWait();
        if(!gameName.equals("None")) {
            Board board = JSONReader.loadGame(Path.of(file.getPath(), gameName.get()).toString());
            gameController = new GameController(board, this);


            roboRally.createBoardView(gameController);
        }
    }

    /**
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     */
    public boolean stopGame() {
        if (gameController != null) {

            Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to save the game?", ButtonType.YES, ButtonType.NO );
            alert.setTitle("Stop game");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                saveGame();
            }

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    /**
     * Closes the game
     *
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public void exit() {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game

        if (gameController == null || stopGame()) {
            System.exit(0);
        }
    }

    /**
     * Implement the method from the interface, which will be passsed to the game controller, closing the program.
     *
     * @param player The player who have won
     * @author Nilas Thoegersen
     */
    @Override
    public void endGame(Player player) {
        Alert won = new Alert(AlertType.INFORMATION);
        won.setTitle("We have a winner");
        won.setHeaderText(null);
        won.setContentText(player.getName() + " has won");
        won.show();
        gameController = null;
        roboRally.createBoardView(null);
    }

    /**
     * Checks if a game is already running
     *
     * @return Returns a boolean, true if the game is running
     * @author Ekkart Kindler, ekki@dtu.dk
     */
    public boolean isGameRunning() {
        return gameController != null;
    }


    @Override
    public void update(Subject subject) {
    }

    public void hostGame() {
        ChoiceDialog boardDialog = new ChoiceDialog(BOARD_OPTIONS.get(0), BOARD_OPTIONS);
        boardDialog.setTitle("Course");
        boardDialog.setHeaderText("Select course");
        Optional<String> boardresult = boardDialog.showAndWait();

        if (boardresult.isPresent()) {
            selectedBoard = boardresult.get();
        }

        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);

        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();
            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            Board board = new Board(11, 8, selectedBoard, result.get(), null);
            gameController = new GameController(board, this);
            int numberOfPlayers = result.get();

        TextInputDialog nameDialog = new TextInputDialog("");
        nameDialog.setTitle("Player name");
        nameDialog.setHeaderText("Select player name");
        Optional<String> resultName = nameDialog.showAndWait();

        String entered = "";
        if (resultName.isPresent()) {
            entered = resultName.get();
        }

        Player player = new Player(board, PLAYER_COLORS.get(0), entered);
        board.addPlayer(player);
        Space spawnSpace = board.nextSpawn();
        player.setSpace(board.getSpace(spawnSpace.getX(),spawnSpace.getY()));

        Game nG = new Game(1, board.getBoardName(), 0,numberOfPlayers,gson.toJson(board));
        PlayerDTO playerDTO = new PlayerDTO(player.getName());
        HttpController.createGame(nG);
        HttpController.joinGame(nG.getId(),playerDTO);
    }

    /**
     * Create the view to show online games
     *
     * @author Nilas Thoegersen
     */
    public void showOnlineGames() {
        if (gamesView == null) {
            gamesView = new GamesView(this);
        }
    }

    public void joinGame(Game selectedItem) {
        List<PlayerDTO> playerList = new ArrayList<PlayerDTO>();
        String playerName = playerName(0);
        try {
            playerList = HttpController.playersInGame(selectedItem.getId());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        String finalPlayerName = playerName;
        int count = (int) playerList.stream().filter(x -> x.getName().startsWith(finalPlayerName)).count();
        if (count > 0) playerName = playerName + " [" + count + "]";

        PlayerDTO player = new PlayerDTO(playerName);

        if(count == selectedItem.getMaxPlayers()){
            HttpController.joinGame(selectedItem.getId(),player);
            System.out.println("Player: "+ playerName + " trying to join " + selectedItem);
        }
    }

    public List<Game> getGameList() throws Exception {
        return HttpController.getGameList();
    }


    public void StartServer() {
        String[] args = new String[0];
        //ServerApp.main(args);

    }

    public String playerName(int playerIndex){
        TextInputDialog nameDialog = new TextInputDialog("Player" + (playerIndex + 1));
        nameDialog.setTitle("Player name");
        nameDialog.setHeaderText("Select player name");
        Optional<String> resultName = nameDialog.showAndWait();

        String entered = "Player" + (playerIndex + 1);
        if (resultName.isPresent()) {
            entered = resultName.get();
        }
        return entered;
    }
}
