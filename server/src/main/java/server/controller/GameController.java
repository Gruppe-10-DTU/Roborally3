package server.controller;

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

import java.util.ArrayList;
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
    public Game getSpecificGame(@PathVariable int id) {
        return gameService.getGame(id);
    }
/*
    @PostMapping("/games")
    public String createGame(@RequestBody Game game) {
        gameService.createGame(game);
        return gson.toJson(gameService.getGameById(game.getGameID()));
    }

    @DeleteMapping( "/games/{id}")
    public ResponseEntity<String> removeGame(@PathVariable int id){
        gameService.deleteGame(id);
        return ResponseEntity.ok().body("deleted");
    }
*/

    @GetMapping(value = "/games2")
    public ResponseEntity<List<GameDTO>> getGameList2(){
        List<GameDTO> gamestring = new ArrayList<>(gameDTOMapper.mapList(gameService.loadGames()));
        return ResponseEntity.ok().body(gamestring);
    }
}
