package server.controller;

import com.google.gson.JsonArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Service.GameService;
import server.dto.GameDTO;
import server.mapper.GameDTOMapper;
import com.google.gson.Gson;
import server.model.Game;

import java.util.ArrayList;

@RestController
public class GameController {
    Gson gson = new Gson();
    @Autowired
    private GameService gameService;
    private GameDTOMapper gameDTOMapper = new GameDTOMapper();

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public String getGameList(){
        ArrayList<GameDTO> gamestring = new ArrayList<>();
        for (GameDTO games: gameDTOMapper.mapList(gameService.loadGames())) {
            gamestring.add(games);
        }
        return gson.toJson(gamestring);
    }

    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public String getSpecificGame(@PathVariable int id) {
        return gson.toJson(gameService.getGameById(id));
    }

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
}
