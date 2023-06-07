package server.controller;

import com.google.gson.JsonObject;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<GameDTO>> getGameList(){
        List<GameDTO> gamestring = new ArrayList<>(gameDTOMapper.mapList(gameService.loadGames()));
        return ResponseEntity.ok().body(gamestring);
    }

    @GetMapping( "/games/{id}")
        public ResponseEntity<String> retrieveGame(@PathVariable int id){
            return ResponseEntity.ok().body(gson.toJson(gameService.getGameById(id)));
        }

    @PostMapping("/games")
    public ResponseEntity<String> createGame(@RequestBody Game game) {
        gameService.createGame(game);
        return ResponseEntity.ok().body("Game Created");
    }

    @DeleteMapping( "/games/{id}")
    public ResponseEntity<String> removeGame(@PathVariable int id){
        gameService.deleteGame(id);
        return ResponseEntity.ok().body("deleted");
    }

}
