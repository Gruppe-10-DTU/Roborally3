package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.controller.SequenceActions.SequenceVisitor;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public interface SequenceAction {
    /**
     * Function for the logic of each field
     *
     * @param gameController The main controller for the game
     */
    void doAction(GameController gameController);

    /**
     * What priority of the sequence each action is. This value will reference the rules.
     *
     * @return The priority of the action.
     */
    int getPrio();

    void accept(Player[] players, SequenceVisitor visitor);
}
