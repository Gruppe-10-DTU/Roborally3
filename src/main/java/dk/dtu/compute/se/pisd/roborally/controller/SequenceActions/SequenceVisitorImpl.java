package dk.dtu.compute.se.pisd.roborally.controller.SequenceActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement.*;
import dk.dtu.compute.se.pisd.roborally.model.Cards.Damage;
import dk.dtu.compute.se.pisd.roborally.model.Cards.DamageCard;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class SequenceVisitorImpl implements SequenceVisitor {
    @Override
    public void visit(GameController gameController, BoardLaser boardLaser) {
        Board board = gameController.board;
        board.getPlayers().parallelStream().forEach(player -> {
            Space space = player.getSpace();
            Heading heading = Heading.WEST;
            if(space instanceof BoardLaser){
                player.discardCard(new DamageCard(Damage.SPAM));
                board.addGameLogEntry(player, "Was hit by a laser");
            }else {
                for (int i = 0; i < 4; i++) {
                    //Set isHit in the if statement and add the dmg card inside the statement.
                    if (!space.getOut(heading)) {
                        if (isHit(board, space, heading)) {
                            player.discardCard(new DamageCard(Damage.SPAM));
                            board.addGameLogEntry(player, "Was hit by a laser");
                        }
                    }
                    heading = heading.next();
                }
            }
        });
    }

    @Override
    public void visit(GameController gameController, Checkpoint checkpoint) {
        for (Player player : gameController.board.getPlayers()
        ) {
            if (player.getSpace().getClass().equals(checkpoint.getClass())) {
                if(((Checkpoint) player.getSpace()).addPlayer(player)){
                    gameController.board.addGameLogEntry(player, "Reached checkpoint " + ((Checkpoint) player.getSpace()).getNumber());
                }
            }
        }
    }

    @Override
    public void visit(GameController gameController, Conveyorbelt conveyorbelt) {
        Board board = gameController.board;
        //Target of the move
        Map<Player, Space> targetSpace = new HashMap<>();
        Player player;
        Space space;
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            player = board.getPlayer(i);
            space = player.getSpace();
            if (space.getClass().equals(conveyorbelt.getClass())) {
                space = board.getNeighbour(player.getSpace(), ((Conveyorbelt) space).getExit());
                if (space == null) {
                    gameController.rebootRobot(player);
                } else if (space.getPlayer() == null || space.getClass().equals(conveyorbelt.getClass())) {
                    targetSpace.put(player, space);
                }
            }
        }
        HashSet<Space> filterMap = new HashSet<>();
        List<Space> distinct = targetSpace.values().stream().filter(x -> !filterMap.add(x)).toList();
        for (Map.Entry<Player, Space> entry : targetSpace.entrySet()
        ) {
            if (!distinct.contains(entry.getValue())) {
                entry.getKey().setSpace(entry.getValue());
            }
        }
    }

    @Override
    public void visit(GameController gameController, FastConveyorbelt fastConveyorbelt) {

    }

    @Override
    public void visit(GameController gameController, Energy energy) {

    }

    @Override
    public void visit(GameController gameController, Gear gear) {

    }

    @Override
    public void visit(GameController gameController, Push push) {

    }

    @Override
    public void visit(GameController gameController, RobotLaser robotLaser) {

    }
}
