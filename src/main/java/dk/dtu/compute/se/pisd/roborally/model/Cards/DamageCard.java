package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DamageCard extends Subject implements Card {
    private Damage damage;

    DamageCard(){

    }
    public DamageCard(@NotNull Damage damage) {
        this.damage = damage;
    }

    @Override
    public String getName() {
        return damage.displayName;
    }

    /**
     * @author Philip Astrup Cramer
     * @author Nilas Thoegersen
     * @param gameController
     */
    @Override
    public void doAction(GameController gameController) {
        Player currentPlayer = gameController.board.getCurrentPlayer();
        switch (damage){
            case SPAM:
                //this has no further effect
                break;
            case TROJAN_HORSE:
                gameController.board.addGameLogEntry(currentPlayer, "Took 2 Spam damage");
                currentPlayer.receiveCard(new DamageCard(Damage.SPAM));
                currentPlayer.receiveCard(new DamageCard(Damage.SPAM));
                break;
            case WORM:
                gameController.board.addGameLogEntry(currentPlayer, "Rebooted");
                gameController.rebootRobot(currentPlayer);
                return;
            case VIRUS:
                ArrayList<Player> withinRange = gameController.board.playersInRange(currentPlayer, 6);
                for (Player affectedPLayer : withinRange) {
                    affectedPLayer.receiveCard(new DamageCard(Damage.VIRUS));
                    gameController.board.addGameLogEntry(affectedPLayer, "Got virus from "+ currentPlayer.getName());
                }
                break;
            default:
                //nothing happens

        }
        Card card = currentPlayer.drawCard();
        while (card.isInteractive()){
            currentPlayer.discardCard(card);
            card = currentPlayer.drawCard();
        }
        currentPlayer.getProgramField(gameController.board.getStep()).setCard(card);
        card.doAction(gameController);
    }

    @Override
    public boolean isInteractive() {
        return false;
    }

    @Override
    public String getType() {
        return "Damage";
    }
}
