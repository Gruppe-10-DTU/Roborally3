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
import dk.dtu.compute.se.pisd.roborally.utils.BoardUpdateThread;
import dk.dtu.compute.se.pisd.roborally.view.GamesView;
import dk.dtu.compute.se.pisd.roborally.view.LobbyView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class AppController implements Observer {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> BOARD_OPTIONS = Arrays.asList("Burnout", "Risky Crossing", "Fractionation");
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");

    final private RoboRally roboRally;
    private PlayerDTO me;
    private GamesView gamesView;
    private Gson gson = JSONReader.setupGson();
    private String selectedBoard;
    private GameController gameController;
    private LobbyView lobbyView;

    private BoardUpdateThread boardUpdateThread;

    public GamesView getGamesView() {
        return gamesView;
    }

    public void setGamesView(GamesView gamesView) {
        this.gamesView = gamesView;
    }

    public LobbyView getLobbyView() {
        return lobbyView;
    }

    public void setLobbyView(LobbyView lobbyView) {
        this.lobbyView = lobbyView;
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

                Player player = new Player(board, PLAYER_COLORS.get(i), playerName(i));
                board.addPlayer(player);
                Space spawnSpace = board.nextSpawn();
                player.setSpace(spawnSpace);
            }
            gameController.board.addGameLogEntry(null, "Game started");
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
        }
    }

    public void saveGame(){
        if(gameController.getClient() != null){
            String saveName = getSavedGameName();
            Game savedGame = new Game().setName(saveName).setState("STOPPEDGAME").setVersion(0).setMaxPlayers(gameController.board.getMaxPlayers()).setBoard(gson.toJson(gameController.board));
            HttpController.createGame(savedGame);
            gameController.board.addGameLogEntry(gameController.getClient(), " saved the game");
        }else {
            saveGameLocally();
        }
    }

    private String getSavedGameName(){
        TextInputDialog saveNameDialog = new TextInputDialog();
        saveNameDialog.setTitle("Save game");

        saveNameDialog.setHeaderText("Please name your save");
        Optional<String> resultName = saveNameDialog.showAndWait();
        return resultName.orElse(UUID.randomUUID().toString());
    }

    /**
     * Save a game into a file.
     *
     * @author Nilas Thoegersen
     */
    public void saveGameLocally() {
        String savedGameController = JSONReader.saveGame(gameController.board);

        String resultName = getSavedGameName();
        while (Files.exists(Path.of("src/main/java/dk/dtu/compute/se/pisd/roborally/controller/savedGames/", resultName + ".json"))) {
            resultName = getSavedGameName();
        }

        try {
            //TODO: Gøre den mere dynamis. Ikke sikker på det virker med Jar
            File newSave = new File("src/main/java/dk/dtu/compute/se/pisd/roborally/controller/savedGames/" + resultName + ".json");
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
        try {
            //TODO: Gør stien dynamisk.
            file = new File("src/main/java/dk/dtu/compute/se/pisd/roborally/controller/savedGames");
        } catch (Exception e) {
            System.out.println("No files found");
            return;
        }
        Optional<String> gameName = new ChoiceDialog<>("None", file.list()).showAndWait();
        if (!gameName.equals("None")) {
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

            Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to save the game?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Stop game");

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.YES) {
                saveGame();
            }

            if(boardUpdateThread != null && boardUpdateThread.isAlive()){
                boardUpdateThread.interrupt();
                try {
                    boardUpdateThread.join();
                }catch (Exception e) {

                }
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

    public void updateBoard() {
        if (gameController != null) {
            roboRally.createBoardView(gameController);
        } else {
            roboRally.createBoardView(null);
        }
    }

    /**
     * Implement the method from the interface, which will be passsed to the game controller, closing the program.
     *
     * @param player The player who have won
     * @author Nilas Thoegersen
     */
    public void endGame(Player player) {
        gameController = null;
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert won = new Alert(AlertType.INFORMATION);
                won.setTitle("We have a winner");
                won.setHeaderText(null);
                won.setContentText(player.getName() + " has won");
                won.show();
                roboRally.createBoardView(null);
            }
        });

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

    /**
     * Creates a board from the available list of board options and the user selection of aforementioned board.
     *
     * @return Board
     * @author Asbjørn Nielsen
     */
    public Board initBoardinfo() {
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
        return board;
    }

    /**
     * Adds a player to specified board.
     *
     * @author Asbjørn Nielsen, Nilas Thørgsen
     */
    public PlayerDTO initPlayerInfo() {
        TextInputDialog nameDialog = new TextInputDialog("");
        nameDialog.setTitle("Player name");
        nameDialog.setHeaderText("Select player name");
        Optional<String> resultName;

        String entered = "";
        while (entered.equals("")) {
            resultName = nameDialog.showAndWait();
            if (resultName.isPresent()) {
                entered = resultName.get();
            }
        }

        PlayerDTO player = new PlayerDTO(entered);
        return player;
    }

    /**
     * Main method for creating an online game and handling the functionality that comes with it.
     *
     * @author Asbjørn Nielsen og Nilas Thoegersen
     */
    public void hostGame() {
        Game newGame = null;
        Board board = null;
        ButtonType newGameButton = new ButtonType("New Game");
        ButtonType loadGame = new ButtonType("Load Game");
        Alert alert = new Alert(AlertType.CONFIRMATION, "Do you want to create a new game, or load an old game", newGameButton, loadGame);
        alert.setTitle("Host game");
        Game game;
        Optional<ButtonType> choice = alert.showAndWait();
        if (choice.isPresent() && choice.get() == newGameButton) {
            board = initBoardinfo();

            newGame = new Game(board.getBoardName(), 0, board.getMaxPlayers(), gson.toJson(board));
            newGame.setState("INITIALIZING");
            gameController = new GameController(board, this);
        } else if (choice.isPresent() && choice.get() == loadGame) {
            newGame = retrieveSavedGame();
            if (newGame != null) {
                newGame.setId(0);
                newGame.setState("SAVED");
                board = JSONReader.parseBoard(new JSONObject(newGame.getBoard()));
                gameController = new GameController(board, this);
            } else {
                return;
            }
        }

        PlayerDTO playerDTO = initPlayerInfo();
        int gameId = HttpController.createGame(newGame);
        playerDTO = HttpController.joinGame(gameId, playerDTO);
        showLobby(gameId, gameController.board.getMaxPlayers(), playerDTO);

        gameController.setClientName(playerDTO.getName());

        try {
            board.setGameId(gameId);
        } catch (IllegalStateException e) {

        }
        showLobby(gameId, board.getMaxPlayers(), playerDTO);
        boardUpdateThread = new BoardUpdateThread(gameId, gameController);
        boardUpdateThread.start();
    }

    public int launchGame(int id) {

        Game game = HttpController.getGame(id);
        if (game != null) {
            Board board = JSONReader.parseBoard(new JSONObject(game.getBoard()));
            try {
                board.setGameId(id);
            } catch (Exception e) {

            }
            List<PlayerDTO> players;
            Player newPlayer;
            try {
                players = HttpController.playersInGame(id);
            } catch (Exception e) {
                return -1;
            }

            for (int i = 0; i < players.size(); i++) {
                if (game.getState().equals("SAVED")) {
                    board.getPlayer(i).setName(players.get(i).getName());
                } else {
                    newPlayer = new Player(board, PLAYER_COLORS.get(i), players.get(i).getName());
                    board.addPlayer(newPlayer);
                    Space spawnSpace = board.nextSpawn();
                    newPlayer.setSpace(spawnSpace);
                }
            }

            gameController.replaceBoard(board, game.getVersion());

            if (board.getPhase() == Phase.INITIALISATION) {
                gameController.startProgrammingPhase();
            }
            gameController.board.addGameLogEntry(null, "Game Started");

            int statusCode = HttpController.startGame(id, board);

            return statusCode;
        } else {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Connection Error");
            error.setHeaderText("Could not retrieve game from server.\nPlease try again!");
            error.showAndWait();
        }
        return -1;
    }

    /**
     * Retrieves a list of available games on the server and lets the player chose one of them to play.
     *
     * @return Game
     * @author Asbjørn Nielsen og Nilas Thoegersen
     */
    public Game retrieveSavedGame() {
        List<Game> savedGames;
        try {
            savedGames = HttpController.getGameList(Optional.of("STOPPEDGAME"));
        }catch (Exception e){
            System.out.println("There are no saved games on the server");
            return null;
        }
        if(savedGames.isEmpty()){
            System.out.println("There are no saved games on the server");
            return null;
        }
        Optional<Game> gameName = new ChoiceDialog<Game>(savedGames.get(0), savedGames).showAndWait();

        return gameName.orElse(null);
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

    /**
     * Join a game
     *
     * @param selectedItem This is the logic for when a player tries to join a server
     * @author Søren Friis Wünsche
     */
    public void joinGame(Game selectedItem) {
        List<PlayerDTO> playerList = new ArrayList<PlayerDTO>();
        int gameId = selectedItem.getId();

        getOnlineGame(gameId);

        PlayerDTO player = initPlayerInfo();
//            System.out.println(selectedItem.getCurrentPlayers());
        if (selectedItem.getCurrentPlayers() < selectedItem.getMaxPlayers()) {
            player = HttpController.joinGame(gameId, player);
            System.out.println("Player: " + player.getName() + " trying to join " + selectedItem);
            //updateGame(gameId,player);
            if (gameController != null) gameController.setClientName(player.getName());
            showLobby(selectedItem.getId(), selectedItem.getMaxPlayers(), player);
            boardUpdateThread = new BoardUpdateThread(gameId, gameController);
            boardUpdateThread.start();

        }
    }

    /**
     * This is the dialog box for player name
     *
     * @param playerIndex
     * @return returns either the input or player + index
     * @author Søren Friis Wünsche
     */
    public String playerName(int playerIndex) {
        TextInputDialog nameDialog = new TextInputDialog("Player" + (playerIndex + 1));
        nameDialog.setTitle("Player name");
        nameDialog.setHeaderText("Select player name");
        Optional<String> resultName = nameDialog.showAndWait();

        String entered = "Player" + (playerIndex + 1);
        if (resultName.isPresent()) {
            if (!resultName.equals(entered)) {
                entered = resultName.get();
            }
            return entered;
        }
        return null;
    }

    /**
     * Get a list of playres in a game
     *
     * @param gameId The id of the game
     * @return A list of plyaers in the game
     * @throws ExecutionException
     * @throws InterruptedException
     * @author Søren Wünsche
     */
    public List<PlayerDTO> getPlayerList(int gameId) throws ExecutionException, InterruptedException {
        return HttpController.playersInGame(gameId);
    }

    /**
     * Lounge of players who've joined the game.
     *
     * @param id The id of the game
     * @param maxPlayers Total amount of player in the game
     * @param playerDTO The player joining
     * @author Søren og Asbjørn
     */
    public void showLobby(int id, int maxPlayers, PlayerDTO playerDTO) {
        if (lobbyView == null) {
            if (gamesView != null) {
                lobbyView = new LobbyView(this, id, maxPlayers, gamesView.getStageHolder(), playerDTO);
                return;
            }
            lobbyView = new LobbyView(this, id, maxPlayers, new Stage(), playerDTO);
        }
    }

    /**
     * Get an game from the server and join it.
     *
     * @param gameID Id of the game
     * @author Philip og Nilas Thoegersen
     */
    public void getOnlineGame(int gameID) {
        Game game = HttpController.getGame(gameID);
        if (game == null) return;
        Board board = JSONReader.parseBoard(new JSONObject(game.getBoard()));
        try {
            board.setGameId(gameID);
        } catch (Exception e) {

        }
        if (gameController == null) {
            gameController = new GameController(board, this);
        } else {
            gameController.replaceBoard(board, game.getVersion());
        }
    }

    /**
     * Once the player chooses to leave the game; the board is retrieved from the server and returned anew without the
     * player present.
     *
     * @param gameId the id of the game
     * @param playerDTO the player leaving
     * @author Asbjørn Nielsen
     */
    public void leaveGame(int gameId, PlayerDTO playerDTO) {
        HttpController.leaveGame(gameId, playerDTO);
        try {
            boardUpdateThread.interrupt();
            boardUpdateThread.join();
        } catch (InterruptedException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isThreadRunning() {
        return !(boardUpdateThread != null && boardUpdateThread.isAlive());
    }
}
