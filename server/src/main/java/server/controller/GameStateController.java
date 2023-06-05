package server.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import server.Service.GameService;
import server.mapper.GameDTOMapper;

@RestController
public class GameStateController {
    Gson gson = new Gson();
    @Autowired
    private GameService gameService;
    private GameDTOMapper gameDTOMapper = new GameDTOMapper();
    @RequestMapping(value = "/games/{id}/gamestates", method = RequestMethod.GET)
    public String getState(@RequestBody int id){
        return  gson.toJson(gameService.getGameById(id).getBoard());
    }

    @RequestMapping(value = "/games/{id}/gamestates", method = RequestMethod.PUT)
    public ResponseEntity<String> updateState() throws HttpServerErrorException.NotImplemented {
        return null;
    }
}
