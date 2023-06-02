package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GamesView implements ViewObserver{

    private final AppController appController;
    private final TableView<Game> tableView;
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

        VBox vbox = new VBox(tableView, addButtons());
        Scene scene = new Scene(vbox);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private ButtonBar addButtons(){
        Button join = new Button("Join");
        join.setOnAction(e -> appController.joinGame(tableView.getSelectionModel().getSelectedItem()));

        Button refresh = new Button("Refresh");
        refresh.setOnAction(e -> refreshList());

        ButtonBar buttonBar = new ButtonBar();
        buttonBar.getButtons().addAll(join, refresh);
        return buttonBar;
    }

    private void refreshList() {
        System.out.println("GamesView - Refreshlist not implemented");
    }

    @Override
    public void updateView(Subject subject) {

    }
}
