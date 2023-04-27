package dk.dtu.compute.se.pisd.roborally.model.Cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Damage {

    SPAM("Spam", 20),
    TROJAN_HORSE("Trojan Horse",18),
    WORM("Worm",18),
    VIRUS("Virus",18),
    OPTIONAL("Player Choice",0, SPAM, TROJAN_HORSE, VIRUS, WORM);

    final private String displayName;

    final private int cardAmount;
    private List<Damage> options;


    public List<Damage> getOptions() {
        return options;
    }

    Damage(String displayName, int cardAmount, Damage... options) {
        this.displayName = displayName;
        this.cardAmount = cardAmount;
        this.options = Arrays.asList(options);
    }

    public int getAmount(){
        return this.cardAmount;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void removeOption(Damage option){
        this.options.remove(option);
    }
}
