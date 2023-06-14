package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import dk.dtu.compute.se.pisd.roborally.model.PlayerDTO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class LobbyView extends VBox implements ViewObserver{
    private final AppController appController;
    private final TableView<Game> tableView;
    private final PlayerDTO playerDTO;
    private final int gameId;

    /**
     * @param appController Appcontroller
     * @param gameId id of the game
     * @param maxPlayers amount of players
     * @param stageNew the stage to put the view into
     * @param playerDTO The player
     * @author Søren Wünsche og Asbjørn
     */
    public LobbyView(AppController appController, int gameId, int maxPlayers, Stage stageNew, PlayerDTO playerDTO) {
        this.appController = appController;
        this.gameId = gameId;
        this.playerDTO = playerDTO;
        tableView = new TableView<Game>();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        tableView.prefWidthProperty().bind(tableView.widthProperty().add(300));
        TableColumn<Game, String> name = new TableColumn<Game, String>("Players");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.prefWidthProperty().bind(tableView.widthProperty());

        tableView.getColumns().setAll(name);
        refreshList(tableView, name, maxPlayers);
        this.getChildren().addAll(tableView, addButtons(name, maxPlayers));
        Scene scene = new Scene(this);

        stageNew.setScene(scene);
        stageNew.setOnCloseRequest(
                e -> {
                    appController.setLobbyView(null);
                    stageNew.close();
                });
        stageNew.setTitle("Lobby");
        stageNew.setMinWidth(300);
        stageNew.show();
    }

    /**
     * @param currentPlayers amount of players
     * @param maxPlayers amount of players needed to start the game
     * @return A summary of the game state atm.
     * @author Søren Wünsche
     */
    private String playerOnServer(int currentPlayers, int maxPlayers) {
        return "Players: (" + currentPlayers + "/" + maxPlayers + ")";
    }

    @Override
    public void updateView(Subject subject) {

    }

    /**
     * Set buttons on the lobby view
     *
     * @param nameColumn
     * @param maxPlayers max aomunt of buttons
     * @return Create buttons
     * @author Søren Wünsche og Philip Astrup Cramer
     */
    private ButtonBar addButtons(TableColumn<Game, String> nameColumn, int maxPlayers){
        Button leave = new Button("Leave");
        leave.setOnAction(e -> leaveGame(tableView,playerDTO));

        ButtonBar buttonBar = new ButtonBar();
        Button refresh = new Button("Refresh");
        Button start = new Button("Start");
        start.setVisible(maxPlayers == tableView.getItems().size());
        start.setOnAction(event -> {
            System.out.println("Start Game");
            startGame();
        });
        refresh.setOnAction(e -> {
            refreshList(tableView,nameColumn, maxPlayers);
            if(maxPlayers == tableView.getItems().size()){

                start.setVisible(true);
            }
        });


        buttonBar.getButtons().addAll(leave, refresh, start);
        return buttonBar;
    }

    /**
     * When pressing the leave button in the game lobby, this method is called. Leaves the game from the server side,
     * updates the board and returns a board without the leaving player.
     * @param tableview
     * @param playerDTO the current player trying to leave.
     * @author Asbjørn
     */
    private void leaveGame(TableView tableview, PlayerDTO playerDTO){
        appController.leaveGame(gameId,playerDTO);
        ((Stage) tableview.getScene().getWindow()).close();
        appController.setLobbyView(null);
    }

    /**
     *
     * @author Søren Wünsche
     */
    private void refreshList(TableView tableView, TableColumn<Game, String> nameColumn, int maxPlayers) {
        try {
            getPlayerList(tableView);
            nameColumn.setText(playerOnServer(tableView.getItems().size(), maxPlayers));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @author Nilas Thoegersen & Philip Astrup Cramer
     */
    private void startGame(){
        int responseCode = appController.launchGame(gameId);
        if (responseCode < 200 || responseCode > 300) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Connection Error");
            error.setHeaderText("Server response not OK.\nPlease try again!");
            error.showAndWait();
        }
    }

    /**
     *
     * @author Søren Wünsche
     */
    private void getPlayerList(TableView<PlayerDTO> tableView) throws Exception {
        tableView.getItems().clear();
        List<PlayerDTO> playerList = appController.getPlayerList(gameId);
        tableView.getItems().addAll(playerList);
    }
}
