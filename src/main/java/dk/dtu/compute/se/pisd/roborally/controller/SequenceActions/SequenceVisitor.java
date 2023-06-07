package dk.dtu.compute.se.pisd.roborally.controller.SequenceActions;

import dk.dtu.compute.se.pisd.roborally.model.BoardElement.*;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public interface SequenceVisitor {
    void visit(Player[] players, BoardLaser boardLaser){

    }

    void visit(Player[] players, Checkpoint checkpoint){

    }
    void visit(Player[] players, Conveyorbelt conveyorbelt){

    }
    void visit(Player[] players, FastConveyorbelt fastConveyorbelt){

    }
    void visit(Player[] players, Energy energy){

    }
    void visit(Player[] players, Gear gear){

    }
    void visit(Player[] players, Push push){

    }
    void visit(Player[] players, RobotLaser robotLaser){

    }
}
