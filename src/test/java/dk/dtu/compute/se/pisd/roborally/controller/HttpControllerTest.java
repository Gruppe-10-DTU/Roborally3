package dk.dtu.compute.se.pisd.roborally.controller;

import com.google.gson.Gson;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Game;
import dk.dtu.compute.se.pisd.roborally.model.PlayerDTO;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class HttpControllerTest {
    GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController(new Board(8, 8), null);
        HttpController.setServerUrl("http://localhost:8080");
    }

    @Test
    void getAvailableGamesTest() throws Exception {
        Object returnedObj = HttpController.getGameList(Optional.empty());
        assertEquals(200, HttpController.getLastResponseCode());
    }
    @Test
    @Order(1)
    void createGameTest() throws Exception {
        Board testBoard = new Board();
        Gson gson = new Gson();
        Game testGame = new Game("cTest", 0, 2, gson.toJson(testBoard));
        HttpController.createGame(testGame);
        assertEquals(200, HttpController.getLastResponseCode());
        System.out.println(HttpController.getGameList(Optional.empty()));
        assertEquals(testGame.getName(),HttpController.getGame(1).getName());
    }
    @Test
    @Order(2)
    void joinGameTest() throws Exception {
        HttpController.joinGame(1,new PlayerDTO("TestPlayer"));
        assertEquals(200,HttpController.getLastResponseCode());
        assertEquals(1,HttpController.getGame(1).getCurrentPlayers());
    }

    @Test
    @Order(3)
    void leaveGameTest() throws Exception {
        PlayerDTO pDTO = new PlayerDTO("TestPlayer2");
        pDTO.setId(2);
        HttpController.joinGame(1,pDTO);
        HttpController.leaveGame(1,pDTO);
        assertEquals(200,HttpController.getLastResponseCode());
        assertEquals(1,HttpController.getGame(1).getCurrentPlayers());
    }

}