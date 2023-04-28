package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DamageCard extends Subject implements Card {
    final private Damage damage;

    public DamageCard(@NotNull Damage damage) {
        this.damage = damage;
    }

    @Override
    public String getName() {
        return damage.getDisplayName();
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
                currentPlayer.discardCard(gameController.board.drawDamageCard(Damage.SPAM));
                currentPlayer.discardCard(gameController.board.drawDamageCard(Damage.SPAM));
                break;
            case WORM:
                gameController.rebootRobot(currentPlayer);
                break;
            case VIRUS:
                ArrayList<Player> withinRange = gameController.board.playersInRange(currentPlayer, 6);
                for (Player affectedPLayer : withinRange) {
                    affectedPLayer.discardCard(gameController.board.drawDamageCard(Damage.VIRUS));
                }
                break;
            case OPTIONAL:

                break;
            default:
                //nothing happens

        }
        gameController.board.returnDamageCard(this);
        Card card = currentPlayer.drawCard();
        currentPlayer.getProgramField(gameController.board.getStep()).setCard(card);
        card.doAction(gameController);
    }

    @Override
    public boolean isInteractive() {
        return this.damage.equals(Damage.OPTIONAL);
    }

    @Override
    public String getType() {
        return "Damage";
    }
    public void removeOptionFromList(Damage damage){
        this.damage.removeOption(damage);
    }
    public List<Damage> getOptions(){
        return this.damage.getOptions();
    }
}
