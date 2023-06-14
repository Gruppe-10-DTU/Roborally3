package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.FieldAction.RebootToken;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.PriorityAntenna;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

class GameControllerTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board, null);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        //board.setSpace(new PriorityAntenna(board, 7, 7));
        board.setPriorityAntenna(new PriorityAntenna(board, 7, 7));
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Test
    void moveCurrentPlayerToSpace() {
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        gameController.moveCurrentPlayerToSpace(board.getSpace(0, 4));

        Assertions.assertEquals(player1, board.getSpace(0, 4).getPlayer(), "Player " + player1.getName() + " should beSpace (0,4)!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
        Assertions.assertEquals(player2, board.getCurrentPlayer(), "Current player should be " + player2.getName() +"!");
    }

    @Test
    void moveForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.moveForward(current, 1);

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(), "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(), "Player 0 should be heading SOUTH!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
    }

    @Test
    void pushRobot(){
        Board board = gameController.board;
        Player pusher = board.getCurrentPlayer();
        Player pushed = board.getPlayer(1);
        gameController.moveForward(pusher, 1);
        pusher.setHeading(Heading.EAST);
        gameController.moveForward(pusher,1);

        Assertions.assertEquals(pusher,board.getSpace(1,1).getPlayer(), "Player "+ pusher.getName() + " should be space (1,1)");
        Assertions.assertEquals(pushed,board.getSpace(2,1).getPlayer(), "Player " + pushed.getName() + " should be space (2,1)");
    }

    @Test
    void uTurn(){
        Board board = gameController.board;
        Player TimmyTurner = board.getCurrentPlayer();

        Assertions.assertEquals(TimmyTurner.getHeading(),Heading.SOUTH, "Player " + TimmyTurner.getName() + " Should be facing south by default");
        gameController.uTurn(TimmyTurner);
        Assertions.assertEquals(TimmyTurner.getHeading(),Heading.NORTH, "Player " + TimmyTurner.getName() + " Should be facing north after a u-turn");

    }
    @Test
    void moveForwardWithWall() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        board.getNeighbour(current.getSpace(), current.getHeading()).setWalls(EnumSet.range(Heading.SOUTH, Heading.EAST));

        gameController.moveForward(current, 1);

        Assertions.assertNotEquals(current, board.getSpace(0, 1).getPlayer(), "Player " + current.getName() + " not have moved!");
        Assertions.assertEquals(current, board.getSpace(0,0).getPlayer(), "Player 0 should be at Space(0,0)");

        Assertions.assertEquals(Heading.SOUTH, current.getHeading(), "Player 0 should be heading SOUTH!");
    }

    @Test
    void pushRobotsOnWalls(){
        Board board = gameController.board;
        gameController.startProgrammingPhase();
        board.getNeighbour(board.getSpace(3,3),Heading.EAST).setWalls(EnumSet.range(Heading.SOUTH, Heading.EAST));
        Player pusher = board.getCurrentPlayer();
        Player pushed1 = board.getPlayer(1);
        Player pushed2 = board.getPlayer(2);

        board.getCurrentPlayer().setSpace(gameController.board.getSpace(1,3));
        board.getPlayer(1).setSpace(gameController.board.getSpace(2,3));
        board.getPlayer(2).setSpace(gameController.board.getSpace(3,3));
        Assertions.assertEquals(pusher.getSpace(),board.getSpace(1,3), "Player " + pusher.getName() + " should be on space (1,3)");
        Assertions.assertEquals(pushed1.getSpace(),board.getSpace(2,3), "Player " + pushed1.getName() + " should be on space (2,3)");
        Assertions.assertEquals(pushed2.getSpace(),board.getSpace(3,3), "Player " + pushed2.getName() + " should be on space (3,3)");
        Assertions.assertEquals(true,board.getSpace(4,3).hasWall(Heading.EAST), "Space " + board.getSpace(3,3) + " should have a wall facing the east side");
        pusher.setHeading(Heading.EAST);
        gameController.moveForward(board.getCurrentPlayer(),2);
        gameController.moveForward(board.getCurrentPlayer(), 1);
        Assertions.assertEquals(pusher.getSpace(),board.getSpace(2,3), "Player " + pusher.getName() + " should be on space (2,3)");
        Assertions.assertEquals(pushed1.getSpace(),board.getSpace(3,3), "Player " + pushed1.getName() + " should be on space (3,3)");
        Assertions.assertEquals(pushed2.getSpace(),board.getSpace(4,3), "Player " + pushed2.getName() + " should be on space (4,3)");
    }
    @Test
    void rebootRobot() {
        Board board = gameController.board;
        gameController.startProgrammingPhase();
        Player moveOutOfBounds = board.getCurrentPlayer();
        RebootToken rb = new RebootToken(board,2,2,Heading.EAST);
        board.setRebootToken(rb);
        moveOutOfBounds.setSpace(board.getSpace(0,0));
        moveOutOfBounds.setHeading(Heading.NORTH);
        moveOutOfBounds.getProgramField(0).setCard(moveOutOfBounds.drawCard());
        Assertions.assertSame(moveOutOfBounds.drawCard().getType(),moveOutOfBounds.getProgramField(0).getCard().getType(), "Player only has command type cards in deck, and the programmed card should alas be a command!");
        //Check to see if player is moved to reboot token square.
        Assertions.assertSame(moveOutOfBounds.getSpace(), board.getSpace(0, 0), "Player " + moveOutOfBounds.getName() + " should be on space (0,0)!");
        gameController.moveForward(moveOutOfBounds, 1);
        Assertions.assertSame(moveOutOfBounds.getSpace(), board.getSpace(2, 2), "Player " + moveOutOfBounds.getName() + " should be moved to space (2,2)!");
        //Checking to see if reboot has set card to null!
        Assertions.assertSame(null,moveOutOfBounds.getProgramField(0).getCard(), "Since player is rebooting; Program field should be empty!");
    }
}