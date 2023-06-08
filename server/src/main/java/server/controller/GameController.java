package server.controller;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.Service.GameService;
import server.dto.GameDTO;
import server.mapper.DtoMapper;
import server.mapper.GameDTOMapper;
import server.model.Board;
import server.model.Game;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GameController {
    Gson gson = new Gson();

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

    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public Game getSpecificGame(@PathVariable int id) {
        return gameService.getGame(id);
    }

    @RequestMapping(value = "/games/{id}/info", method = RequestMethod.GET)
    public GameDTO getGameInfo(@PathVariable int id) {
        return gameDTOMapper.map(gameService.getGameById(id));
    }

    @RequestMapping(value = "/games/{id}/bords", method = RequestMethod.GET)
    public String getGameBoard(@PathVariable int id) {
        return gameService.getGameById(id).getBoard();
    }

    @PostMapping("/games")
    public ResponseEntity<GameDTO> createGame(@RequestBody Game game) {
        game = gameService.createGame(game);
        GameDTO gameDTO = dtoMapper.gameToGameDto(game);
        return ResponseEntity.ok().body(gameDTO);
    }

    @DeleteMapping( "/games/{id}")
    public ResponseEntity<String> removeGame(@PathVariable int id){
        gameService.deleteGame(id);
        return ResponseEntity.ok().body("deleted");
    }


}
