package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;
    Board board;
    GameController gameController;
    @BeforeEach
    void setUp() {
        board = new Board(TEST_WIDTH, TEST_HEIGHT);

        gameController = new GameController(board, null);

        for (int i = 0; i < 3; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.EAST);

        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @Test
    void AgainToFirstFieldGivesFalse(){
        Card card = new CommandCard(Command.AGAIN);
        Player player = board.getPlayer(0);

        player.getCardField(0).setCard(card);
        assertFalse(gameController.moveCards(player.getCardField(0), player.getProgramField(0)));
    }

    @Test
    void AgainToFirstFieldGivesTrue(){
        Card card = new CommandCard(Command.AGAIN);
        Player player = board.getPlayer(0);

        player.getCardField(0).setCard(card);
        assertTrue(gameController.moveCards(player.getCardField(0), player.getProgramField(1)));
    }

    @Test
    void AgainRepeatsPreviousCard(){
        Card againCard = new CommandCard(Command.AGAIN);
        Card moveOneCard = new CommandCard(Command.FORWARD);
        Player player = board.getPlayer(0);
        player.setHeading(Heading.EAST);

        player.getProgramField(0).setCard(moveOneCard);
        moveOneCard.doAction(gameController);
        assertEquals(board.getSpace(1,0), player.getSpace());

        board.setStep(1);
        player.getProgramField(1).setCard(againCard);
        againCard.doAction(gameController);
        assertEquals(board.getSpace(2, 0), player.getSpace());
    }

}