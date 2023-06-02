package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;

@RestController
public class GameController {
    private ArrayList<String> gameList = new ArrayList<>();
    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public ArrayList<String> getGameList(){
        return gameList;
    }
    @RequestMapping(value = "/games/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getSpecificGame() throws HttpServerErrorException.NotImplemented {
        return null;
    }
    @RequestMapping(value = "/games", method = RequestMethod.POST)
    public ResponseEntity<String> createGame() throws HttpServerErrorException.NotImplemented {
        return null;
    }
    @RequestMapping(value = "/games/{ID}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeGame() throws HttpServerErrorException.NotImplemented {
        return null;
    }
}
