package server.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import server.Service.GameService;
import server.Service.PlayerService;
import server.dto.PlayerDTO;
import server.mapper.DtoMapper;
import server.model.Player;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerController {
    private PlayerService playerService;
    private GameService gameService;
    private DtoMapper dtoMapper;

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
    @RequestMapping(value = "/games/{id}/players", method = RequestMethod.GET)
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
        playerService.addPlayer(player);
        int playerCount = playerService.countPlayers(gameId);
        gameService.updateCurrPlayers(gameId,playerCount);
        return ResponseEntity.ok().body(player);
    }
}
