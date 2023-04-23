package dk.dtu.compute.se.pisd.roborally.model;

import java.util.ArrayList;

/**
 *
 * @author Philip Astrup Cramer
 */
public class CmdCardDeck {
    private ArrayList<CommandCard> deck;
    private ArrayList<CommandCard> discards;

    public CmdCardDeck(){
        this.deck = new ArrayList<>();
        this.discards = new ArrayList<>();
        generateDeck();
        reShuffle();
    }

    /**
     * Draws a card from the playes deck
     * and if the player's deck is empty shuffles the discard pile
     * into the deck
     *
     * @author Philip Astrup Cramer
     */
    public CommandCard drawCard(){
        if(this.deck.size() < 1){
            reShuffle();
        }
        CommandCard drawnCard = this.deck.get(0);
        this.deck.remove(0);
        return drawnCard;
    }

    /**
     * Adds a card to the players discard pile
     * @param addedCard the CommandCard to add
     * @return true if successfully added
     *
     * @author Philip Astrup Cramer
     */
    public boolean discard(CommandCard addedCard){
        return this.discards.add(addedCard);
    }

    /**
     * takes a radom card from the discard pile
     * and adds it to the deck until the discard
     * pile is empty
     *
     * @author Philip Astrup Cramer
     */
    private void reShuffle(){
        while(this.discards.size() > 0){
            int cardIndex = (int)(Math.random() * this.discards.size());
            this.deck.add(this.discards.get(cardIndex));
            this.discards.remove(cardIndex);
        }
    }
    /**
     * Generates the players cards and puts them
     * in the discard pile
     *
     * @author Philip Astrup Cramer
    */
    private void generateDeck(){
        for (Command value : Command.values()) {
            for(int i = 0; i < value.getAmount(); i++){
                discard(new CommandCard(value));
            }
        }
    }
}
