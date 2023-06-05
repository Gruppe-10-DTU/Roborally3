package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
        ArrayList<String> gamestring = new ArrayList<>();
        for (GameDTO games: gameDTOMapper.mapList(gameService.loadGames())) {
            gamestring.add(games.toString());
        }
        return gson.toJson(gamestring);
    }

    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public String getSpecificGame(@PathVariable int id) {
        return gson.toJson(gameService.getGameById(id));
    }

    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public String createGame(@PathVariable Game game) {
        gameService.createGame(game);
        return gson.toJson(gameService.getGameById(game.getGameID()));
    }

    @RequestMapping(value = "/games/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeGame(@PathVariable int id){
        gameService.deleteGame(id);
        return ResponseEntity.ok().body("deleted");
    }
}
