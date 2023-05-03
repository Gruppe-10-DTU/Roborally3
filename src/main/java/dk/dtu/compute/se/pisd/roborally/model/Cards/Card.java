package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

public interface Card {
    /**
     * @return The type of the card
     * @author Philip Astrup Cramer
     */
    String getType();

    /**
     * @return The name of the card
     * @author Philip Astrup Cramer
     */
    String getName();

    /**
     * @param gameController The games controller
     * @author Phillip
     * @author Nilas Thoegersen
     */
    void doAction(GameController gameController);

    /**
     * @return True if the card is interactive, otherwise false
     * @author Philip Astrup Cramer
     */
    boolean isInteractive();
}
