package dk.dtu.compute.se.pisd.roborally.view;

import com.google.gson.Gson;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.JSONReader;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.PlayerDTO;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.util.List;

/**
 * UI to show all games currently hosted on the server
 *
 * @author Nilas
 */
public class GamesView extends VBox implements ViewObserver {
    private final AppController appController;
    private final TableView<Game> tableView;

    /**
     * Controller for the view
     *
     * @param appController The appcontroller
     * @author Nilas Thoegersen
     */
    public GamesView(AppController appController) {
        this.appController = appController;



        tableView = new TableView<Game>();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tableView.prefWidthProperty().bind(tableView.widthProperty().add(300));

        TableColumn<Game, String> name = new TableColumn<Game, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        name.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));

        TableColumn<Game, Integer> currentPlayers = new TableColumn<Game, Integer>("Players");
        currentPlayers.setCellValueFactory(new PropertyValueFactory<>("currentPlayers"));
        currentPlayers.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));

        TableColumn<Game, Integer> maxPlayers = new TableColumn<Game, Integer>("Max players");
        maxPlayers.setCellValueFactory(new PropertyValueFactory<>("maxPlayers"));
        maxPlayers.prefWidthProperty().bind(tableView.widthProperty().multiply(0.3));

        tableView.getColumns().setAll(name, currentPlayers, maxPlayers);
        refreshList(tableView);

        this.getChildren().addAll(tableView, addButtons());
        Scene scene = new Scene(this);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(
                e -> {
                    appController.setGamesView(null);
                    stage.close();
                });
        stage.setTitle("Game list");
        stage.show();
    }

    /**
     * Setup a bar of buttons
     *
     * @return The buttonbar
     * @author Nilas Thoegersen
     */
    private ButtonBar addButtons() {
        Button join = new Button("Join");
        join.setOnAction(e -> joinGame());

        Button refresh = new Button("Refresh");
//        refresh.setOnAction(e -> refreshList());
        refresh.setOnAction(e -> {
            refreshList(tableView);
        });

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(join, refresh);
        return buttonBar;
    }

    /**
     * Method to fetch a new list of items, and set it to the tableview.
     *
     * @author Nilas Thoegersen
     */
    private void refreshList(TableView tableView) {
        try {
            getGameList(tableView);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void joinGame() {
        appController.joinGame(tableView.getSelectionModel().getSelectedItem());
        tableView.refresh();
    }

    @Override
    public void updateView(Subject subject) {

    }

    private void getGameList(TableView tableView) throws Exception {
        tableView.getItems().clear();
        List<Game> games = appController.getGameList();
        tableView.getItems().addAll(games);
    }

}
