package dk.dtu.compute.se.pisd.roborally.controller;

import com.google.gson.Gson;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpControllerTest {
    GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(new Board(8, 8), null);
        HttpController.setServerUrl("http://localhost:8080");
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Test
    void getAvailableGamesTest() {
        Object returnedObj = HttpController.getAvailableGames();
        assertEquals(200, HttpController.getLastResponseCode());
    }
    @Test
    void createGameTest() throws Exception {
        Board testBoard = new Board();
        Gson gson = new Gson();
        Game testGame = new Game("test", 1, 2, gson.toJson(testBoard));
        HttpController.createGame(testGame);
        assertEquals(200, HttpController.getLastResponseCode());
        assertEquals(1,HttpController.getAvailableGames().size());
        HttpController.removeGame(1);
    }
    @Test
    void joinGameTest() throws Exception {
        Board testBoard = new Board();
        Gson gson = new Gson();
        Game testGame = new Game( "test", 1, 2, gson.toJson(testBoard));
        HttpController.createGame(testGame);
        HttpController.joinGame(1,new PlayerDTO("TestPlayer"));
        assertEquals(200,HttpController.getLastResponseCode());
        assertEquals(1,HttpController.getGame(1).getCurrentPlayers());
        HttpController.removeGame(1);
    }
    @Test
    void leaveGameTest() throws Exception {
        Board testBoard = new Board();
        Gson gson = new Gson();
        Game tGame = new Game("Test1",1,2,gson.toJson(testBoard));
        HttpController.createGame(tGame);
        PlayerDTO pDTO = new PlayerDTO("TestPlayer1");
        PlayerDTO pDTO2 = new PlayerDTO("TestPlayer2");
        HttpController.joinGame(1,pDTO);
        HttpController.joinGame(1,pDTO2);
        HttpController.leaveGame(1,pDTO2);
        assertEquals(200,HttpController.getLastResponseCode());
        assertEquals(1,HttpController.getGame(1).getCurrentPlayers());
        HttpController.removeGame(1);
    }

}