package dk.dtu.compute.se.pisd.roborally.model.Cards;

public enum Damage {

    SPAM("Spam"),
    TROJAN_HORSE("Trojan Horse"),
    WORM("Worm"),
    VIRUS("Virus");

    final public String displayName;


    /**
     * @param displayName The name of the card
     * @author Philip Astrup Cramer
     */
    Damage(String displayName) {
        this.displayName = displayName;
    }

}
