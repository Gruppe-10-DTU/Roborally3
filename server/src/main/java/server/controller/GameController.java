package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import server.Service.GameService;
import server.dto.GameDTO;
import server.dto.GamePatchDTO;
import server.mapper.DtoMapper;
import server.mapper.GameDTOMapper;
import server.model.Game;
import server.model.GameState;

import java.util.List;
import java.util.Optional;

@RestController
public class GameController {

    private GameService gameService;

    private DtoMapper dtoMapper;
    private GameDTOMapper gameDTOMapper;

    public GameController(DtoMapper dtoMapper, GameService gameService, GameDTOMapper gameDTOMapper){
        this.dtoMapper = dtoMapper;
        this.gameService = gameService;
        this.gameDTOMapper = gameDTOMapper;
    }

    /**
     * @author Nilas Thoegersen & Asbjørn Nielsen
     */
    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public ResponseEntity<List<GameDTO>> getGameList(){
        List<Game> games = gameService.loadGames();
        List<GameDTO> gameString = gameDTOMapper.mapList(games);
        return ResponseEntity.ok().body(gameString);
    }

    /**
     * @author Nilas Thoegersen & Sandie Petersen & Søren Wünsche
     */
    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public ResponseEntity<Game> getSpecificGame(@RequestParam(name = "version") Optional<Integer> version, @PathVariable int id) {
        Game game;
        if(version.isPresent()){
            game = gameService.getGameWithVersion(id, version.get());
        }else{
            game = gameService.getGame(id);
        }

        if(game != null){
            //GameDTO gameDTO = dtoMapper.gameToGameDto(game);
            return ResponseEntity.ok().body(game);
        }
        return ResponseEntity.notFound().build();
   }


    /**
     * @author Nilas Thoegersen
     */
    @PostMapping("/games")
    public ResponseEntity<GameDTO> createGame(@RequestBody Game game) {
        game = gameService.createGame(game);
        GameDTO gameDTO = dtoMapper.gameToGameDto(game);
        return ResponseEntity.ok().body(gameDTO);
    }

    /**
     * @author Asbjørn Nielsen
     */
    @DeleteMapping( "/games/{id}")
    public ResponseEntity<String> removeGame(@PathVariable int id){
        gameService.deleteGame(id);
        return ResponseEntity.ok().body("deleted");
    }

    /**
     * @author Søren Wünsche
     */
    @RequestMapping(value = "/games/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateGame(@RequestBody Game game) {
        gameService.updateGame(game);
        return ResponseEntity.ok().body(game);
    }

    //This SHOULD be a patch request but our http client doesn't support that method.

    /**
     * @author Nilas Thoegersen
     */
    @RequestMapping(value = "/games/{id}/gamestates", method = RequestMethod.PUT)
    public ResponseEntity updateState(@PathVariable int id, @RequestBody String state) throws HttpServerErrorException.NotImplemented {
        Game requestedGame = gameService.getGame(id);
        try {
            requestedGame.setState(GameState.valueOf(state));
            requestedGame.setVersion(requestedGame.getVersion() + 1);
            gameService.updateGame(requestedGame);
            return ResponseEntity.ok().build();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * @author Nilas Thoegersen
     */
    @PatchMapping(value = "games/{id}")
    public ResponseEntity patchGame(@PathVariable int id, @RequestBody GamePatchDTO gamePatch){
        Game game = gameService.getGame(id);

        dtoMapper.updateGameFromDto(gamePatch, game);

        gameService.updateGame(game);

        return ResponseEntity.status(204).build();
    }

}
