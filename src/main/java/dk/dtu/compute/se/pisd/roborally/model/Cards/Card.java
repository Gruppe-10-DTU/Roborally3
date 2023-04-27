package dk.dtu.compute.se.pisd.roborally.model.Cards;

public interface Card<T> {
    String getType();
    String getName();

    T getAction();

}
