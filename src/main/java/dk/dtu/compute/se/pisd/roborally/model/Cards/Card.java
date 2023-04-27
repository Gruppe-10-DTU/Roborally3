package dk.dtu.compute.se.pisd.roborally.model.Cards;

public interface Card <T extends Enum<T>> {
    T getType();
    String getName();
    int getAmount();

}
