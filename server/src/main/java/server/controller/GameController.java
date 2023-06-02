package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.Service.GameService;
import server.dto.GameDTO;
import server.mapper.GameDTOMapper;

import java.util.ArrayList;

@RestController
public class GameController {
    @Autowired
    private GameService gameService;
    private GameDTOMapper gameDTOMapper = new GameDTOMapper();

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public ArrayList<GameDTO> getGameList(){
        return gameDTOMapper.mapList(gameService.loadGames());
    }
    /*
    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getSpecificGame() throws HttpServerErrorException.NotImplemented {
        return null;
    }
    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity<String> createGame() throws HttpServerErrorException.NotImplemented {
        return null;
    }
    @RequestMapping(value = "/games/{ID}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeGame() throws HttpServerErrorException.NotImplemented {
        return null;
    }*/
}
