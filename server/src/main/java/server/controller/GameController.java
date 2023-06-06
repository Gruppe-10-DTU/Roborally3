package server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Service.GameService;
import server.dto.GameDTO;
import server.mapper.DtoMapper;
import server.mapper.GameDTOMapper;
import server.model.Game;
import com.google.gson.Gson;
import server.model.Game;

import java.util.List;

@RestController
public class GameController {
    Gson gson = new Gson();
    @Autowired
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

    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public String getSpecificGame(@PathVariable int id) {
        return gson.toJson(gameService.getGameById(id));
    }

    @PostMapping("/games")
    public String createGame(@RequestBody Game game) {
        gameService.createGame(game);
        return gson.toJson(gameService.getGameById(game.getId()));
    }

    @DeleteMapping( "/games/{id}")
    public ResponseEntity<String> removeGame(@PathVariable int id){
        gameService.deleteGame(id);
        return ResponseEntity.ok().body("deleted");
    }
}
