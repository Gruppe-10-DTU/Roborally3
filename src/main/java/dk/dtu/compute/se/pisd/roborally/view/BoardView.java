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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class BoardView extends VBox implements ViewObserver {

    private final Board board;
    private TextFlow gameLog;
    private ScrollPane logPane;

    private final GridPane mainPane;
    private final GridPane boardPane;
    private final SpaceView[][] spaces;

    private final PlayersView playersView;

    private final Label statusLabel;

    private final SpaceEventHandler spaceEventHandler;

    /**
     *
     * @author Ekkart Kindler & Philip Astrup Cramer
     */
    public BoardView(@NotNull GameController gameController) {
        board = gameController.board;

        mainPane = new GridPane();
        playersView = new PlayersView(gameController);
        statusLabel = new Label("<no status>");
        boardPane = new GridPane();
        logPane = new ScrollPane();
        gameLog = new TextFlow();

        gameLog.getChildren().addAll(logAsText(board.getGameLog()));
        logPane.setContent(gameLog);
        logPane.setMinWidth(250);
        logPane.setMaxHeight(480);


        this.getChildren().add(mainPane);
        this.getChildren().add(playersView);
        this.getChildren().add(statusLabel);

        spaces = new SpaceView[board.getWidth()][board.getHeight()];

        spaceEventHandler = new SpaceEventHandler(gameController);

        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {
                Space space = board.getSpace(x, y);
                SpaceView spaceView = new SpaceView(space);
                spaces[x][y] = spaceView;
                boardPane.add(spaceView, x, y);
                spaceView.setOnMouseClicked(spaceEventHandler);
            }
        }
        mainPane.add(boardPane, 0,0);
        mainPane.add(logPane,1, 0);
        board.attach(this);
        update(board);
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == board) {
            Phase phase = board.getPhase();
            statusLabel.setText(board.getStatusMessage());
            gameLog = new TextFlow();
            gameLog.getChildren().addAll(logAsText(board.getGameLog()));
            logPane.setContent(gameLog);
            logPane.setVvalue(logPane.getVmax());
        }
    }

    /**
     * Converts the provided List of String pairs into a list of
     * colorized JavaFX Text objects
     *
     * @author Philip Astrup Cramer
     */
    private List<Text> logAsText(List<Pair<String, String>> log){
        List<Text> result = new ArrayList<>();
        for (Pair<String, String> entry : log) {
            Text text = new Text();
            text.setText(entry.getValue());
            switch (entry.getKey()) {
                case "red":
                    text.setStyle("-fx-fill: RED;");
                    break;
                case "green":
                    text.setStyle("-fx-fill: GREEN;");
                    break;
                case "blue":
                    text.setStyle("-fx-fill: BLUE;");
                    break;
                case "orange":
                    text.setStyle("-fx-fill: ORANGE;");
                    break;
                case "grey":
                    text.setStyle("-fx-fill: GREY;");
                    break;
                case "magenta":
                    text.setStyle("-fx-fill: MAGENTA;");
                    break;
                default:
                    text.setStyle("-fx-fill: BLACK; -fx-font-weight: BOLD");
            }
            result.add(text);
            //comment
        }
        return result;
    }

    // XXX this handler and its uses should eventually be deleted! This is just to help test the
    //     behaviour of the game by being able to explicitly move the players on the board!
    private class SpaceEventHandler implements EventHandler<MouseEvent> {

        final private GameController gameController;

        public SpaceEventHandler(@NotNull GameController gameController) {
            this.gameController = gameController;
        }
        @Override
        public void handle(MouseEvent event) {
            /*
            Object source = event.getSource();
            if (source instanceof SpaceView spaceView) {
                Space space = spaceView.getSpace();
                Board board = space.getBoard();

                if (board == gameController.board) {
                    gameController.moveCurrentPlayerToSpace(space);
                    event.consume();
                }
            }
            */
        }
    }
}
