package dk.dtu.compute.se.pisd.roborally.model.Cards;

public enum Damage {

    SPAM("Spam", 20),
    TROJAN_HORSE("Trojan Horse", 18),
    WORM("Worm", 18),
    VIRUS("Virus", 18);

    final public String displayName;

    final int cardAmount;

    /**
     * @param displayName The name of the card
     * @param cardAmount  How many cards to create
     * @author Philip Astrup Cramer
     */
    Damage(String displayName, int cardAmount) {
        this.displayName = displayName;
        this.cardAmount = cardAmount;
    }

    public int getAmount() {
        return this.cardAmount;
    }
}
