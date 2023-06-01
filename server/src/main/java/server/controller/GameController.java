package server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {
    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public String getGame(){
        return "Test is nice";
    }
}
