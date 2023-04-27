package dk.dtu.compute.se.pisd.roborally.model.Cards;

import java.util.ArrayList;

import static dk.dtu.compute.se.pisd.roborally.model.Cards.Damage.*;

/**
 *
 * @author Philip Astrup Cramer
 */
public class DamageCardDeck {
    private ArrayList<DamageCard> spamPile;
    private ArrayList<DamageCard> trojanHorsePile;
    private ArrayList<DamageCard> virusPile;
    private ArrayList<DamageCard> wormPile;

    /**
     *
     * Constructs the damage cards from the damage enum and
     * separates them by type.
     *
     * @author Philip Astrup Cramer
     */
    public DamageCardDeck(){
        this.spamPile = new ArrayList<>();
        this.trojanHorsePile = new ArrayList<>();
        this.virusPile = new ArrayList<>();
        this.wormPile = new ArrayList<>();
        for (Damage value : Damage.values()) {
            switch (value){
                case SPAM:
                    for(int i = 0; i < value.getAmount(); i++){
                        this.spamPile.add(new DamageCard(value));
                    }
                    break;
                case TROJAN_HORSE:
                    for(int i = 0; i < value.getAmount(); i++){
                        this.trojanHorsePile.add(new DamageCard(value));
                    }
                    break;
                case WORM:
                    for(int i = 0; i < value.getAmount(); i++){
                        this.wormPile.add(new DamageCard(value));
                    }
                    break;
                case VIRUS:
                    for(int i = 0; i < value.getAmount(); i++){
                        this.virusPile.add(new DamageCard(value));
                    }
                    break;

            }
        }
    }

    /**
     * Returns a damage card, based on what type specified.
     * If there are no more of the given type, a 'PlayerChoice'
     * type is returned instead.
     *
     * @author Philip Astrup Cramer
     */
    public DamageCard getDamageCard(Damage type){
        DamageCard drawn = new DamageCard(OPTIONAL);
        if(trojanHorsePile.isEmpty()) drawn.getAction().removeOption(TROJAN_HORSE);
        if(spamPile.isEmpty()) drawn.getAction().removeOption(SPAM);
        if(wormPile.isEmpty()) drawn.getAction().removeOption(WORM);
        if (virusPile.isEmpty()) drawn.getAction().removeOption(VIRUS);
        switch (type) {
            case SPAM:
                if (!this.spamPile.isEmpty()) {
                    drawn = this.spamPile.get(0);
                    this.spamPile.remove(0);
                }
                break;
            case TROJAN_HORSE:
                if (!this.trojanHorsePile.isEmpty()) {
                    drawn = this.trojanHorsePile.get(0);
                    this.trojanHorsePile.remove(0);
                }
                break;
            case WORM:
                if (!this.wormPile.isEmpty()) {
                    drawn = this.wormPile.get(0);
                    this.wormPile.remove(0);
                }
                break;
            case VIRUS:
                if(!this.virusPile.isEmpty()) {
                    drawn = this.virusPile.get(0);
                    this.virusPile.remove(0);
                }
                break;
        }
        return drawn;
    }

    /**
     * Returns the damage card to the correct pile.
     * @author Philip Astrup Cramer
     */
    public void returnCard(DamageCard card){
        switch (card.getAction()){
            case SPAM:
                this.spamPile.add(card);
                break;
            case TROJAN_HORSE:
                this.trojanHorsePile.add(card);
                break;
            case WORM:
                this.wormPile.add(card);
                break;
            case VIRUS:
                this.virusPile.add(card);
                break;
        }
    }
}
