package dk.dtu.compute.se.pisd.roborally.model.SequenceAction;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

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
}
