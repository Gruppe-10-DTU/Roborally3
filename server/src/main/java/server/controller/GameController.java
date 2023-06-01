package server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class GameController {
    private ArrayList<String> gameList = new ArrayList<>();
    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public ArrayList<String> getGameList(){
        return gameList;
    }
}
