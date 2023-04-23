package dk.dtu.compute.se.pisd.roborally.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    Board board;
    Player player1;
    Player player2;

    @BeforeEach
    public void init() {
        board = new Board(8, 8);

        player1 = new Player(board, "red", "Player 1");
        player1.setSpace(board.getSpace(0,1));
        player2 = new Player(board, "green", "Player 2");
        player2.setSpace(board.getSpace(1,1));

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
}