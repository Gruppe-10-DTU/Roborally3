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

    private int gameId;
    public LobbyView(AppController appController, int gameId, int maxPlayers) {
        this.appController = appController;
        this.gameId = gameId;
        tableView = new TableView<Game>();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        tableView.prefWidthProperty().bind(tableView.widthProperty().add(300));
        TableColumn<Game, String> name = new TableColumn<Game, String>("Players");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.prefWidthProperty().bind(tableView.widthProperty());

        tableView.getColumns().setAll(name);
        int currentPlayers = tableView.getItems().size();
        refreshList(tableView, currentPlayers, maxPlayers);

        name.setText(playerOnServer(tableView.getItems().size(),maxPlayers));

        this.getChildren().addAll(tableView, addButtons(maxPlayers));
        Scene scene = new Scene(this);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(
                e -> {
                    appController.setLobbyView(null);
                    stage.close();
                });
        stage.setTitle("Lobby");
        stage.show();
    }

    private String playerOnServer(int currentPlayers, int maxPlayers) {
        return "Players: (" + currentPlayers + "/" + maxPlayers + ")";
    }

    @Override
    public void updateView(Subject subject) {

    }
    private ButtonBar addButtons(int maxPlayers){
        Button leave = new Button("Leave");
        leave.setOnAction(e -> leaveGame());

        Button refresh = new Button("Refresh");
        refresh.setOnAction(e -> { refreshList(tableView, tableView.getItems().size(), maxPlayers);
        });

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(leave, refresh);
        return buttonBar;
    }

    private void leaveGame(){

    }
    private void refreshList(TableView tableView, int currentPlayers, int maxPlayers) {
        try {
            getPlayerList(tableView);
            playerOnServer(currentPlayers, maxPlayers);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void getPlayerList(TableView tableView) throws Exception {
        tableView.getItems().clear();
        List<PlayerDTO> playerList = appController.getPlayerList(gameId);
        tableView.getItems().addAll(playerList);
    }
}
