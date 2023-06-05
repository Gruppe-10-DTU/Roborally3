package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import server.Service.GameService;
import server.mapper.GameDTOMapper;

@RestController
public class GameStateController {
    Gson gson = new Gson();
    @Autowired
    private GameService gameService;
    private GameDTOMapper gameDTOMapper = new GameDTOMapper();
    @PutMapping( "/games/{id}")
    public String getState(@RequestBody int id){
        gameService.updateGame(gameService.getGameById(id));
        return  gson.toJson(gameService.getGameById(id).getBoard());
    }

    @RequestMapping(value = "/games/{id}/gamestates", method = RequestMethod.PUT)
    public ResponseEntity<String> updateState() throws HttpServerErrorException.NotImplemented {
        return null;
    }
}
