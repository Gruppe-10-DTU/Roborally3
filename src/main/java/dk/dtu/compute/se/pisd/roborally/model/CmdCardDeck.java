package dk.dtu.compute.se.pisd.roborally.model;

import java.util.ArrayList;

public class CmdCardDeck {
    private ArrayList<CommandCard> deck;
    private ArrayList<CommandCard> discards;

    public CmdCardDeck(){
        this.deck = new ArrayList<>();
        this.discards = new ArrayList<>();
        generateDeck();
        reShuffle();
    }

    public CommandCard drawCard(){
        if(this.deck.size() < 1){
            reShuffle();
        }
        CommandCard drawnCard = this.deck.get(0);
        this.deck.remove(0);
        return drawnCard;
    }
    public boolean discard(CommandCard addedCard){
        return this.discards.add(addedCard);
    }
    private void reShuffle(){
        while(this.discards.size() > 0){
            int cardIndex = (int)(Math.random() * this.discards.size());
            this.deck.add(this.discards.get(cardIndex));
            this.discards.remove(cardIndex);
        }
    }
    private void generateDeck(){
        for (Command value : Command.values()) {
            for(int i = 0; i < value.getAmount(); i++){
                discard(new CommandCard(value));
            }
        }
    }
}
