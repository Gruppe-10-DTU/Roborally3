package dk.dtu.compute.se.pisd.roborally.model.Cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Damage {

    SPAM("Spam", 20),
    TROJAN_HORSE("Trojan Horse",18),
    WORM("Worm",18),
    VIRUS("Virus",18);

    final public String displayName;

    final int cardAmount;

    Damage(String displayName,int cardAmount) {
        this.displayName = displayName;
        this.cardAmount = cardAmount;
    }


    public int getAmount(){
        return this.cardAmount;
    }
}
