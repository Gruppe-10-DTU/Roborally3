package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DamageCard extends Subject implements Card {
    final public Damage damage;

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
                currentPlayer.discardCard(new DamageCard(Damage.SPAM));
                currentPlayer.discardCard(new DamageCard(Damage.SPAM));
                break;
            case WORM:
                gameController.rebootRobot(currentPlayer);
                break;
            case VIRUS:
                ArrayList<Player> withinRange = gameController.board.playersInRange(currentPlayer, 6);
                for (Player affectedPLayer : withinRange) {
                    affectedPLayer.discardCard(new DamageCard(Damage.VIRUS));
                }
                break;
            default:
                //nothing happens

        }
        Card card = currentPlayer.drawCard();
        currentPlayer.getProgramField(gameController.board.getStep()).setCard(card);
        card.doAction(gameController);
    }

    @Override
    public String getType() {
        return "Damage";
    }
}
