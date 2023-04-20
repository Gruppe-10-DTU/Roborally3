package dk.dtu.compute.se.pisd.roborally.model.Cards;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Damage {

    SPAM("SPAM", 20),
    TROJANHORSE("Trojan Horse",18),
    WORM("WORM",18),
    VIRUS("VIRUS",18);

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
