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
import dk.dtu.compute.se.pisd.roborally.model.BoardElement.*;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.Pit;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.PriorityAntenna;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 60; // 75;
    final public static int SPACE_WIDTH = 60; // 75;

    public final Space space;

    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        Image spaceImg = new Image("spaces/empty60.png");

        String simpleName = space.getClass().getSimpleName();

        if (space instanceof BoardLaser) {

            BoardLaser boardLaser = (BoardLaser) space;

            if (boardLaser.getShootingDirection() == Heading.EAST) {
                spaceImg = new Image("spaces/wall/wall_EAST.png");
            } else if (boardLaser.getShootingDirection() == Heading.SOUTH) {
                spaceImg = new Image("spaces/wall/wall_SOUTH.png");
            } else if (boardLaser.getShootingDirection() == Heading.WEST) {
                spaceImg = new Image("spaces/wall/wall_WEST.png");
            } else if (boardLaser.getShootingDirection() == Heading.NORTH) {
                spaceImg = new Image("spaces/wall/wall_NORTH.png");
            }
        } else if (space instanceof Checkpoint) {
            //More is needed
            Checkpoint checkpoint = (Checkpoint) space;
            int count = 1;

            spaceImg = new Image("spaces/checkpoint/checkpoint1.png");

        } else if (space instanceof PriorityAntenna) {
            spaceImg = new Image("spaces/priorityAntenna.png");

        } else if (space instanceof FastConveyorbelt) {
            //More is needed

            FastConveyorbelt fastConveyorbelt = (FastConveyorbelt) space;

            Heading heading = fastConveyorbelt.getHeading();
            Heading turn = fastConveyorbelt.getTurn();
            if (turn != null) {
                spaceImg = new Image("spaces/FastConveyorbelt/fastConveyot.png");

                if (heading == Heading.SOUTH && turn == Heading.WEST) {
                    spaceImg = new Image("spaces/FastConveyorbelt/fastConveyor_SOUTH_WEST.png");
                }
            } else {
                if (heading == Heading.EAST) {
                    spaceImg = new Image("spaces/FastConveyorbelt/fastConveyor_EAST.png");
                } else if (heading == Heading.SOUTH) {
                    spaceImg = new Image("spaces/FastConveyorbelt/fastConveyor_SOUTH.png");
                } else if (heading == Heading.WEST) {
                    spaceImg = new Image("spaces/FastConveyorbelt/fastConveyor_WEST.png");
                } else if (heading == Heading.NORTH) {
                    spaceImg = new Image("spaces/FastConveyorbelt/fastConveyot_NORTH.png");
                }
            }

        } else if (space instanceof Conveyorbelt) {
            spaceImg = new Image("spaces/conveyorbelt/conveyer_NORTH.png");

        } else if (space instanceof Energy) {
            spaceImg = new Image("spaces/energy.png");

        }  else if (space instanceof Gear) {

        } else if (space instanceof Push) {

        } else if (space instanceof Pit) {

        } else if (simpleName == "Spawn"){
            //Create class first
        } else {
            EnumSet<Heading> walls = space.getWalls();

            if (!walls.isEmpty()) {
                int numberOfWalls = walls.size();

                if (numberOfWalls == 1) {
                    if (walls.contains(Heading.EAST)) {
                        spaceImg = new Image("spaces/wall/wall_EAST.png");
                    } else if (walls.contains(Heading.SOUTH)) {
                        spaceImg = new Image("spaces/wall/wall_SOUTH.png");
                    } else if (walls.contains(Heading.WEST)) {
                        spaceImg = new Image("spaces/wall/wall_WEST.png");
                    } else if (walls.contains(Heading.NORTH)) {
                        spaceImg = new Image("spaces/wall/wall_NORTH.png");
                    }
                }

            }

        }
        ImageView spaceImgView = new ImageView(spaceImg);
        this.getChildren().add(0,spaceImgView);



        //this.setBackground(new Background(new BackgroundImage(spaceImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));



        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {
        try {
            this.getChildren().remove(1);
        }catch (IndexOutOfBoundsException e){
            // Do nothing. This is so the image doesn't get removed.
        }
        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0 );
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90*player.getHeading().ordinal())%360);
            this.getChildren().add(1, arrow);
        }
    }

    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            updatePlayer();
        }
    }

}
