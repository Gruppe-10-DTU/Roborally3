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
        gameController.board.getPlayers().parallelStream().forEach(player -> {
            Space space = player.getSpace();
            Heading heading = Heading.WEST;
            if(space instanceof BoardLaser){
                player.discardCard(new DamageCard(Damage.SPAM));
                gameController.board.addGameLogEntry(player, "Was hit by a laser");
            }else {
                for (int i = 0; i < 4; i++) {
                    //Set isHit in the if statement and add the dmg card inside the statement.
                    if (!space.getOut(heading)) {
                        if (isHit(board, space, heading)) {
                            player.discardCard(new DamageCard(Damage.SPAM));
                            gameController.board.addGameLogEntry(player, "Was hit by a laser");
                        }
                    }
                    heading = heading.next();
                }
            }
        });
    }
    private boolean isHit(Board board, Space space, Heading heading) {
        Space oSpace = space;
        space = board.getNeighbour(space,heading);
        while (space != null){
            if (space.getPlayer() != null || space.hasWall(heading)){
                return false;
            } else if (space instanceof BoardLaser && ((BoardLaser) space).hit(heading)) {
                return true;
            }
            space = board.getNeighbour(space, heading);
        }
        return false;
    }

    @Override
    public void visit(GameController gameController, Checkpoint checkpoint) {
        for (Player player : gameController.board.getPlayers()
        ) {
            if (player.getSpace().getClass().equals(this.getClass())) {
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
        for (int i = 0; i < gameController.board.getNumberOfPlayers(); i++) {
            player = gameController.board.getPlayer(i);
            space = player.getSpace();
            if (space.getClass().equals(conveyorbelt.getClass())) {
                space = gameController.board.getNeighbour(player.getSpace(), ((Conveyorbelt) space).getExit());
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
        Map<Player, Space> targetSpace = new HashMap<>();
        Player player;
        Space space;
        for (int i = 0; i < gameController.board.getNumberOfPlayers(); i++) {
            player = gameController.board.getPlayer(i);
            for (int j = 0; j < 2; j++) {
                space = targetSpace.get(player) == null ? player.getSpace() : targetSpace.get(player);
                if (space.getClass().equals(this.getClass())) {
                    space = gameController.board.getNeighbour(space, ((FastConveyorbelt) space).getExit());
                    if (space == null) {
                        gameController.rebootRobot(player);
                    } else if (space.getPlayer() == null || space instanceof FastConveyorbelt) {
                        targetSpace.put(player, space);
                        if (space instanceof Conveyorbelt) {
                            ((Conveyorbelt) space).turnPlayer(player);
                        }
                    }
                }
            }
        }
        HashSet<Space> filterMap = new HashSet<>();
        List<Space> distinct = targetSpace.values().stream().filter(x -> !filterMap.add(x)).toList();
        for (Map.Entry<Player, Space> entry : targetSpace.entrySet()
        ) {
            player = entry.getKey();
            if (!distinct.contains(entry.getValue())) {
                player.setSpace(entry.getValue());
            }
        }
    }

    @Override
    public void visit(GameController gameController, Energy energy) {
        Space space;
        for (Player player : gameController.board.getPlayers()
        ) {
            space = player.getSpace();
            if (player.getSpace().getClass().equals(this.getClass())) {
                if (gameController.board.getStep() == 5) {
                    player.incrementEnergy();
                } else if (((Energy) space).getEnergyAndStop()) {
                    player.incrementEnergy();
                }
            }
        }
    }

    @Override
    public void visit(GameController gameController, Gear gear) {
        for (Player player : gameController.board.getPlayers()
        ) {
            if (player.getSpace().getClass().equals(gear.getClass())) {
                ((Gear) player.getSpace()).turnPlayer(player);
            }
        }
    }

    @Override
    public void visit(GameController gameController, Push push) {
        for (Player player : gameController.board.getPlayers()
        ) {
            if (player.getSpace().getClass().equals(push.getClass())) {
                ((Push) player.getSpace()).pushPlayer(gameController, player);
            }
        }
    }

    @Override
    public void visit(GameController gameController, RobotLaser robotLaser) {
        gameController.board.getPlayers().parallelStream().forEach(player -> {
            RobotLaser rblsr = new RobotLaser(gameController.board,player);
            if(rblsr.shootLaser(player.getSpace(),player.getHeading()) != null){
                rblsr.shootLaser(player.getSpace(),player.getHeading()).discardCard(new DamageCard(Damage.SPAM));
                gameController.board.addGameLogEntry(rblsr.shootLaser(player.getSpace(),player.getHeading()), "Was shot by " + player.getName());
            }
        });
    }
}
