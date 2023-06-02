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
    @RequestMapping(value = "/games/{id}/players", method = RequestMethod.GET)
    public ResponseEntity<String> getPlayers() throws HttpServerErrorException.NotImplemented {
        return null;
    }
}
