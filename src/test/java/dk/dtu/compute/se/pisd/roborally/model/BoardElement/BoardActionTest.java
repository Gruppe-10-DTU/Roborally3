package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardActionTest {
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
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @Test
    void gear_turn_player() {
        Gear gear = new Gear(Heading.EAST, board, 0,0);
        gear.setPlayer(board.getPlayer(0));
        gear.doAction(gameController);
        assertEquals(board.getPlayer(0).getHeading(), Heading.SOUTH);
        gear.setHeading(Heading.WEST);
        gear.doAction(gameController);
        assertEquals(board.getPlayer(0).getHeading(), Heading.SOUTH);
        assertNotEquals(board.getPlayer(0).getHeading(), Heading.NORTH);
    }

    @Test
    void checkPoint_add_player_first_checkpoint(){
        Checkpoint checkpoint = new Checkpoint(board, 0,0);
        Player player = board.getPlayer(0);

        checkpoint.addPlayer(player);
        checkpoint.doAction(gameController);
        assertTrue(checkpoint.checkPlayer(player));
        assertFalse(checkpoint.checkPlayer(board.getPlayer(1)));
    }

    @Test
    void checkPoint_add_player_second_checkpoint() {
        Checkpoint checkpoint = new Checkpoint(board, 0, 0);
        Checkpoint checkpoint2 = new Checkpoint(board, 0, 0, checkpoint);

        Player player = board.getPlayer(0);

        checkpoint2.addPlayer(player);
        checkpoint2.doAction(gameController);
        assertFalse(checkpoint2.checkPlayer(player));
        checkpoint.addPlayer(player);
        checkpoint.doAction(gameController);
        checkpoint2.addPlayer(player);
        checkpoint2.doAction(gameController);
        assertTrue(checkpoint2.checkPlayer(player));
    }

    @Test
    public void conveyorbelt_move_one(){
        Conveyorbelt conveyorbelt = new Conveyorbelt(board, 5, 5, Heading.NORTH);
        conveyorbelt.setPlayer(board.getPlayer(2));
        gameController.executeBoardActions();
        assertEquals(board.getSpace(5,4), board.getPlayer(2).getSpace());
    }
    @Test
    public void conveyorbelt_same_space_nobody_moves(){
        Conveyorbelt conveyorbelt = new Conveyorbelt(board, 5, 5, Heading.NORTH);
        conveyorbelt.setPlayer(board.getPlayer(2));
        Conveyorbelt conveyorbeltWest = new Conveyorbelt(board, 5, 3, Heading.SOUTH);
        conveyorbeltWest.setPlayer(board.getPlayer(1));

        gameController.executeBoardActions();
        assertEquals(board.getSpace(5,5), board.getPlayer(2).getSpace());
        assertEquals(board.getSpace(5,3), board.getPlayer(1).getSpace());

    }

    @Test
    public void fast_conveyorbelt_move_two(){
        FastConveyorbelt fastConveyorbelt = new FastConveyorbelt(board, 5, 5, Heading.EAST);
        new FastConveyorbelt(board, 6, 5, Heading.EAST);

        Player player = board.getPlayer(2);
        fastConveyorbelt.setPlayer(player);
        gameController.executeBoardActions();
        assertEquals(board.getSpace(7,5), player.getSpace());
    }

    @Test
    public void fast_conveyorbelt_move_one(){
        FastConveyorbelt fastConveyorbelt = new FastConveyorbelt(board, 5, 5, Heading.EAST);

        Player player = board.getPlayer(2);
        fastConveyorbelt.setPlayer(player);
        gameController.executeBoardActions();
        assertNotEquals(board.getSpace(7,5), player.getSpace());
        assertEquals(board.getSpace(6,5), player.getSpace());
    }

    @Test
    public void fast_conveyorbelt_move_two_same_space(){
        FastConveyorbelt fastConveyorbeltEast = new FastConveyorbelt(board, 3, 5, Heading.EAST);
        new FastConveyorbelt(board, 4, 5, Heading.EAST);

        FastConveyorbelt fastConveyorbeltWest = new FastConveyorbelt(board, 7, 5, Heading.WEST);
        new FastConveyorbelt(board, 6, 5, Heading.WEST);

        Player player = board.getPlayer(1);
        fastConveyorbeltEast.setPlayer(player);
        Player player2 = board.getPlayer(2);
        fastConveyorbeltWest.setPlayer(player2);
        gameController.executeBoardActions();
        assertEquals(board.getSpace(3,5), player.getSpace());
        assertEquals(board.getSpace(7,5), player2.getSpace());

    }

    @Test
    void energy_first_time() {
        Energy energy = new Energy(board, 2, 2);
        Player player = board.getPlayer(1);
        energy.setPlayer(player);

        energy.doAction(gameController);
        assertEquals(1, player.getEnergy());
    }

    @Test
    void energy_second_time_1_step() {
        Energy energy = new Energy(board, 2, 2);
        Player player = board.getPlayer(1);
        energy.setPlayer(player);

        energy.doAction(gameController);
        energy.doAction(gameController);
        assertEquals(1, player.getEnergy());

        assertNotEquals(2, player.getEnergy());

    }

    @Test
    void energy_second_time_5_step() {
        Energy energy = new Energy(board, 2, 2);
        Player player = board.getPlayer(1);
        energy.setPlayer(player);

        energy.doAction(gameController);
        energy.doAction(gameController);
        assertEquals(1, player.getEnergy());
        board.setStep(5);
        energy.doAction(gameController);
        assertEquals(2, player.getEnergy());

    }

    @Test
    void push_correct_step() {
        Push push = new Push(board, 4, 4, 0, Heading.WEST);

        Player player = board.getPlayer(1);
        push.setPlayer(player);
        push.doAction(gameController);
        assertEquals(board.getSpace(3,4), player.getSpace());
    }

    @Test
    void push_wrong_step() {
        Push push = new Push(board, 4, 4, 2, Heading.WEST);

        Player player = board.getPlayer(1);
        push.setPlayer(player);
        push.doAction(gameController);
        assertEquals(board.getSpace(4,4), player.getSpace());
        board.setStep(2);
        push.doAction(gameController);
        assertEquals(board.getSpace(3,4), player.getSpace());
    }

    @Test
    void push_move_two_players() {
        Push push = new Push(board, 4, 4, 0, Heading.WEST);

        Player player = board.getPlayer(1);
        board.getPlayer(2).setSpace(board.getSpace(3,4));
        push.setPlayer(player);
        push.doAction(gameController);
        assertEquals(board.getSpace(3,4), player.getSpace());

        assertEquals(board.getSpace(2,4), board.getPlayer(2).getSpace());
    }



}