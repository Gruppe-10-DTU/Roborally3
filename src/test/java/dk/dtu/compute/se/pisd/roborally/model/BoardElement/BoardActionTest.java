package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardActionTest {
    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;
    Board board;
    GameController gameController;
    @BeforeEach
    void setUp() {
        board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @Test
    void gear_turn_player() {
        Gear gear = new Gear(Heading.EAST, board, 0,0);
        gear.setPlayer(board.getPlayer(0));
        gear.doAction(gameController);
        Assertions.assertEquals(board.getPlayer(0).getHeading(), Heading.SOUTH);
        gear.setHeading(Heading.WEST);
        gear.doAction(gameController);
        Assertions.assertEquals(board.getPlayer(0).getHeading(), Heading.SOUTH);
        Assertions.assertNotEquals(board.getPlayer(0).getHeading(), Heading.NORTH);
    }

    @Test
    void checkPoint_add_player_first(){
        Checkpoint checkpoint = new Checkpoint(board, 0,0);
        Player player = board.getPlayer(0);

        checkpoint.addPlayer(player);
        checkpoint.doAction(gameController);
    }
}