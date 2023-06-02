package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * UI to show all games currently hosted on the server
 *
 * @author Nilas
 */
public class GamesView extends VBox implements ViewObserver{
    private final AppController appController;
    private final TableView<Game> tableView;

    /**
     * Controller for the view
     *
     * @param appController The appcontroller
     * @author Nilas Thoegersen
     */
    public GamesView(AppController appController){
        this.appController = appController;

        tableView = new TableView<Game>();
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        TableColumn<Game, String> name = new TableColumn<Game, String>("Name");
        name.setCellValueFactory(new PropertyValueFactory("name"));

        TableColumn<Game, Integer> currentPlayers = new TableColumn<Game, Integer>("Current players");
        currentPlayers.setCellValueFactory(new PropertyValueFactory<>("currentPlayers"));

        TableColumn<Game, Integer> maxPlayers = new TableColumn<Game, Integer>("Max players");
        maxPlayers.setCellValueFactory(new PropertyValueFactory<>("maxPlayers"));

        tableView.getColumns().setAll(name, currentPlayers, maxPlayers);

        tableView.getItems().add(
                new Game(1, "Test1", 4,5));
        tableView.getItems().add(
                new Game(1, "Test2", 0,4));

        this.getChildren().addAll(tableView, addButtons());
        Scene scene = new Scene(this);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setOnCloseRequest(
                e -> {
                    appController.setGamesView(null);
                    stage.close();
                });
        stage.show();
    }

    /**
     * Setup a bar of buttons
     *
     * @return The buttonbar
     * @author Nilas Thoegersen
     */
    private ButtonBar addButtons(){
        Button join = new Button("Join");
        join.setOnAction(e -> appController.joinGame(tableView.getSelectionModel().getSelectedItem()));

        Button refresh = new Button("Refresh");
        refresh.setOnAction(e -> refreshList());

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(join, refresh);
        return buttonBar;
    }

    /**
     * Method to fetch a new list of items, and set it to the tableview.
     * @author Nilas Thoegersen
     */
    private void refreshList() {
        System.out.println("GamesView - Refreshlist not implemented");
    }

    @Override
    public void updateView(Subject subject) {

    }
}
