package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.model.Player;

public abstract class BoardElement {
    public abstract void action(Player player);

    private BoardElementType boardElementType;

    public BoardElementType getBoardElementType() {
        return boardElementType;
    }
}
