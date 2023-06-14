package server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.dto.GameDTO;
import server.dto.GamePatchDTO;
import server.exception.CustomExceptionNoSavedGames;
import server.mapper.DtoMapper;
import server.mapper.GameDTOMapper;
import server.model.Game;
import server.model.GameState;
import server.service.GameService;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name="Games", description = "Games endpoint")
public class GameController {

    private final GameService gameService;

    private final DtoMapper dtoMapper;
    private final GameDTOMapper gameDTOMapper;

    public GameController(DtoMapper dtoMapper, GameService gameService, GameDTOMapper gameDTOMapper){
        this.dtoMapper = dtoMapper;
        this.gameService = gameService;
        this.gameDTOMapper = gameDTOMapper;
    }

    /**
     * @author Nilas Thoegersen & Asbjørn Nielsen
     */
    @RequestMapping(value = "/games", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<GameDTO>> getGameList(@RequestParam(name = "state") Optional<GameState> state){
        List<Game> games;

        games = gameService.loadGames(state);

        List<GameDTO> gameString = dtoMapper.gameToGameDto(games);
        return ResponseEntity.ok().body(gameString);

    }

    /**
     * @author Nilas Thoegersen & Sandie Petersen & Søren Wünsche
     */
    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<GameDTO> getSpecificGame(@RequestParam(name = "version") Optional<Integer> version, @PathVariable int id) {
        Game game;
        if(version.isPresent()){
            game = gameService.getGameWithVersion(id, version.get());
        }else{
            game = gameService.getGame(id);
        }

        if(game != null){
            GameDTO gameDTO = dtoMapper.gameToGameDto(game);
            return ResponseEntity.ok().body(gameDTO);
        }
        return ResponseEntity.notFound().build();
   }


    /**
     * @author Nilas Thoegersen
     */
    @PostMapping(value = "/games", produces = "application/json")
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
    @RequestMapping(value = "/games/{id}", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<GameDTO> updateGame(@RequestBody Game game) {
        gameService.updateGame(game);
        GameDTO gameDTO = dtoMapper.gameToGameDto(game);
        return ResponseEntity.ok().body(gameDTO);
    }

    /**
     * @author Nilas Thoegersen
     */
    @PatchMapping(value = "games/{id}")
    public ResponseEntity patchGame(@PathVariable int id, @RequestBody GamePatchDTO gamePatch){
        Game game = gameService.getGame(id);
        if(game == null) {
            return ResponseEntity.notFound().build();
        }
        dtoMapper.updateGameFromDto(gamePatch, game);

        gameService.updateGame(game);

        return ResponseEntity.noContent().build();
    }

}
