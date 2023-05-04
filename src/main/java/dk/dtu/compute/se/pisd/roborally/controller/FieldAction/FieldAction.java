package dk.dtu.compute.se.pisd.roborally.controller.FieldAction;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public interface FieldAction {
    /**
     * Interface for fields that have an action when a player lands on it
     *
     * @param gameController The gamecontroller
     * @param player         The player landing on the field
     * @author Nilas Thoegersen
     */
    void doFieldAction(GameController gameController, Player player);
}
