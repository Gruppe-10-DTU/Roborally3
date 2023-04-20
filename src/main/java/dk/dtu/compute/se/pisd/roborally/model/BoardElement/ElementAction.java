package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

public interface ElementAction {
    public BoardActionType getBoardElementType();
    public void doAction(GameController gameController);
}
