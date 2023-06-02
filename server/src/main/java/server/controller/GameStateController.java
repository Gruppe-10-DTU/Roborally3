package server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class GameStateController {
    @RequestMapping(value = "/games/{id}/gamestates", method = RequestMethod.GET)
    public ResponseEntity<String> getState() throws HttpServerErrorException.NotImplemented {
        return null;
    }

    @RequestMapping(value = "/games/{id}/gamestates", method = RequestMethod.PUT)
    public ResponseEntity<String> updateState() throws HttpServerErrorException.NotImplemented {
        return null;
    }
}
