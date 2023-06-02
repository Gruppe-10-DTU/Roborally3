package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HttpControllerTest {
    GameController gameController;



    @BeforeEach
    void setUp() {
        gameController = new GameController(new Board(8, 8), new EndGame() {
            @Override
            public void endGame(Player player) {

            }
        });
        HttpController.setServerUrl("https://dummyjson.com/http");
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Test
    void getAvailableGamesTest() {

    }

    @Test
    void joinGameTest() {
        HttpController.setServerUrl("https://dummyjson.com/http");
        Integer returnCode = HttpController.joinGame(200, 3);
        assertEquals(200,returnCode);
    }

    @Test
    void createGameTest() {
        HttpController.setServerUrl("https://dummyjson.com/http");
        Integer returnCode = HttpController.createGame(gameController);
        assertEquals(200,returnCode);
    }
}