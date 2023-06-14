package server.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import server.dto.PlayerDTO;
import server.exception.CustomExceptionLobbyIsFull;
import server.mapper.DtoMapper;
import server.model.Game;
import server.model.Player;
import server.service.GameService;
import server.service.PlayerService;

import java.util.List;

@Tag(name="Players", description = "Endpoints for players in a game")
@RestController
public class PlayerController {
    private final PlayerService playerService;
    private final GameService gameService;
    private final DtoMapper dtoMapper;

    public PlayerController(PlayerService playerService, GameService gameService, DtoMapper dtoMapper) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.dtoMapper = dtoMapper;
    }

    /**
     * returns a list of players in a game
     *
     * @author Søren Wünsce
     */
    @RequestMapping(value = "/games/{id}/players", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<PlayerDTO>> getPlayers(@PathVariable int id) {
            List<Player> playerList = playerService.getPlayerList(id);
        return ResponseEntity.ok().body(dtoMapper.playerToPlayerDto(playerList));
    }

    /**
     * Adds the received player to the game
     *
     * @author Søren Wünsche
     */
    @RequestMapping(value = "/games/{gameId}/players", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Player> joinPlayers(@PathVariable int gameId, @RequestBody Player player) throws HttpServerErrorException.NotImplemented {
        player.setGameId(gameId);
        Game game = gameService.getGame(player.getGameId());
        if(game == null){
            return ResponseEntity.notFound().build();
        }
        try {
            playerService.addPlayer(player, game.getMaxPlayers());
        } catch (CustomExceptionLobbyIsFull e){
            return ResponseEntity.badRequest().build();
        }
        int playerCount = playerService.countPlayers(gameId);
        gameService.updateCurrPlayers(gameId,playerCount);
        return ResponseEntity.ok().body(player);
    }

    /**
     * @param gameId id of the game
     * @param id player id
     * @return An ok status with who left.
     * @author Asbjørn
     */
    @DeleteMapping(value ="/games/{gameId}/players/{id}", produces = "application/text")
    public ResponseEntity<String> deletePlayer(@PathVariable int gameId, @PathVariable int id) {
        playerService.removePlayer(id,gameId);
        gameService.updateCurrPlayers(gameId,playerService.countPlayers(gameId));
        return ResponseEntity.ok().body("Player " + id + "Left the game");
    }
}
