package dk.dtu.compute.se.pisd.roborally.controller;

import com.google.gson.Gson;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import dk.dtu.compute.se.pisd.roborally.model.PlayerDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HttpControllerTest {
    GameController gameController;
    @BeforeAll
    void serverSetup(){
        Board testBoard = new Board();
        Gson gson = new Gson();
        Game testGame = new Game("test", 1, 2, gson.toJson(testBoard));
        HttpController.createGame(testGame);

    }
    @BeforeEach
    void setUp() {
        gameController = new GameController(new Board(8, 8), null);
        HttpController.setServerUrl("http://localhost:8080");
    }

    @Test
    void getAvailableGamesTest() throws Exception {
        Object returnedObj = HttpController.getGameList();
        assertEquals(200, HttpController.getLastResponseCode());
    }
    @Test
    void createGameTest() throws Exception {
        assertEquals(200, HttpController.getLastResponseCode());
        System.out.println(HttpController.getGameList());
        assertEquals(testGame.getName(),HttpController.getGame(1).getName());
    }
    @Test
    void joinGameTest() throws Exception {
        HttpController.joinGame(1,new PlayerDTO("TestPlayer"));
        assertEquals(200,HttpController.getLastResponseCode());
        assertEquals(1,HttpController.getGame(1).getCurrentPlayers());
    }
    @Test
    void leaveGameTest() throws Exception {
        PlayerDTO pDTO = new PlayerDTO("TestPlayer1");
        HttpController.joinGame(1,pDTO);
        HttpController.leaveGame(1,pDTO);
        assertEquals(200,HttpController.getLastResponseCode());
        assertEquals(1,HttpController.getGame(1).getCurrentPlayers());
    }

}