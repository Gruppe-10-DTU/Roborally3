package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

public interface Card {
    String getType();
    String getName();
    void doAction(GameController gameController);
    boolean isInteractive();
}
