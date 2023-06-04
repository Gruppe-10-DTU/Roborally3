package server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import server.Service.GameService;
import server.dto.GameDTO;
import server.mapper.DtoMapper;
import server.mapper.GameDTOMapper;
import server.model.Game;

import java.util.List;

@RestController
public class GameController {

    private GameService gameService;

    private DtoMapper dtoMapper;

    private GameDTOMapper gameDTOMapper = new GameDTOMapper();

    public GameController(DtoMapper dtoMapper, GameService gameService){
        this.dtoMapper = dtoMapper;
        this.gameService = gameService;
    }

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public List<GameDTO> getGameList(){
        List<Game> games = gameService.loadGames();
        return dtoMapper.gameToGameDto(games);
        //return gameDTOMapper.mapList(gameService.loadGames());
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
