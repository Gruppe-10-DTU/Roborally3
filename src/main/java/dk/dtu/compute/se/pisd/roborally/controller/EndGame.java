package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Player;

/**
 * A class used to pass down an end game method to the game controller
 */
public interface EndGame {
    /**
     * @param player The player who has won
     * @author Nilas Thoegersen
     */
    void endGame(Player player);
}
