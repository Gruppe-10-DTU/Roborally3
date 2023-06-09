package dk.dtu.compute.se.pisd.roborally.controller.SequenceActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement.*;

public interface SequenceVisitor {
    void visit(GameController gameController, BoardLaser boardLaser);

    void visit(GameController gameController, Checkpoint checkpoint);
    void visit(GameController gameController, Conveyorbelt conveyorbelt);
    void visit(GameController gameController, FastConveyorbelt fastConveyorbelt);
    void visit(GameController gameController, Energy energy);
    void visit(GameController gameController, Gear gear);
    void visit(GameController gameController, Push push);
    void visit(GameController gameController, RobotLaser robotLaser);
}
