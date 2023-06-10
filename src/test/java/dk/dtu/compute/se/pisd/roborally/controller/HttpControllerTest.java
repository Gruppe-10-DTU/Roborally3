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
    void createGameTest() {
        Board testBoard = new Board();
        Gson gson = new Gson();
        Game testGame = new Game("test", 1, 2, gson.toJson(testBoard));
        Integer returnCode = HttpController.createGame(testGame);
        assertEquals(200, returnCode);
    }
/*
    @Test
    void joinGameTest() {
        Board testBoard = new Board();
        Gson gson = new Gson();
        Game testGame = new Game(1, "test", 1, 2, gson.toJson(testBoard));
        Integer returnCode = HttpController.joinGame(1, new PlayerDTO("player"));
        assertEquals(200, returnCode);
    }
    */
}