package server.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import server.Service.PlayerService;
import server.model.Player;

import java.util.ArrayList;

@RestController
public class PlayerController {

    private ArrayList<String> playerList = new ArrayList<>();
    @RequestMapping(value = "/games/{id}/players/", method = RequestMethod.GET)
    public ArrayList<String> getPlayers() {
        return playerList;
    }
    @RequestMapping(value = "/games/{id}/players/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Player> joinPlayers(@PathVariable int id, @RequestBody Player player) throws HttpServerErrorException.NotImplemented {
        return ResponseEntity.ok().build();
    }
}
