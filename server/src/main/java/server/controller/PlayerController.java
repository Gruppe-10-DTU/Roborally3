package server.controller;

import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;

@RestController
public class PlayerController {

    private ArrayList<String> playerList = new ArrayList<>();
    @RequestMapping(value = "/games/{id}/players", method = RequestMethod.GET)
    public ArrayList<String> getPlayers() {
        return playerList;
    }
    @RequestMapping(value = "/games/{id}/players", method = RequestMethod.POST)
    public ResponseEntity joinPlayers() throws HttpServerErrorException.NotImplemented {
        return ResponseEntity.ok().build();
    }
}
