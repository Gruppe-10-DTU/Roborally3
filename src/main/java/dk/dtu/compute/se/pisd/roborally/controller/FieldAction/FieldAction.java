package dk.dtu.compute.se.pisd.roborally.controller.FieldAction;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public interface FieldAction {
    void doFieldAction(GameController gameController, Player player);
}
