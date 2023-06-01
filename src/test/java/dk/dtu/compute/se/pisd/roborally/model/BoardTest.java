package dk.dtu.compute.se.pisd.roborally.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;
    Player player1;
    Player player2;

    @BeforeEach
    public void init() {
        board = new Board(11, 8,"Test",2, null);

        player1 = new Player(board, "red", "Player 1");
        player1.setSpace(board.getSpace(0,1));
        player2 = new Player(board, "green", "Player 2");
        player2.setSpace(board.getSpace(0,3));

        board.addPlayer(player1);
        board.addPlayer(player2);
    }

    @Test
    void calculatePlayerOrderTest() {
        board.playerOrder.add(player1);
        board.playerOrder.add(player2);

        board.calculatePlayerOrder();

        assertEquals(2, board.playerOrder.size());
        board.nextPlayer();
        assertEquals(player2, board.getCurrentPlayer());
        board.nextPlayer();
        assertEquals(player1, board.getCurrentPlayer());

    }

    @Test
    void nextPlayerFull() {
        board.calculatePlayerOrder();

        assertTrue(board.nextPlayer());
    }

    @Test
    void nextPlayerEmpty() {
        assertFalse(board.nextPlayer());
    }

    @Test
    void players_in_range_calculates_correctly(){
        assertEquals(1, board.playersInRange(player1, 6).size());

        player2.setSpace(board.getSpace(2,4));
        Player player3 = new Player(board,null,"Player 3");
        player3.setSpace(board.getSpace(3,7));
        board.addPlayer(player3);

        assertEquals(2, board.playersInRange(player2, 6).size());
    }
}