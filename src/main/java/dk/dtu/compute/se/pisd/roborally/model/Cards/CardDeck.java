package dk.dtu.compute.se.pisd.roborally.model.Cards;

import java.util.ArrayList;

public abstract class CardDeck<E> {
    private ArrayList<E> deck;
    private ArrayList<E> discards;

    public CardDeck(){
        this.deck = new ArrayList<>();
        this.discards = new ArrayList<>();
        generateDeck();
        reShuffle();
    }

    public E drawCard(){
        return null;
    }

    public boolean discard(E addedCard){
        return this.discards.add(addedCard);
    }
    private void reShuffle(){
    }
    private void generateDeck(){
    }
}
