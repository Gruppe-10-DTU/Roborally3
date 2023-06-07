package server.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.model.Board;
import server.model.Game;
import server.model.GameState;
import server.repository.GameRepository;

import java.util.Arrays;
import java.util.List;

import com.google.gson.*;

@Service
public class GameService {

    Gson gson = new Gson();

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
       this.gameRepository = gameRepository;
    }
        public Game createGame(Game game) {
        Game game2 = new Game();
        game2.setBoard(game.getBoard());
        game2.setState(GameState.INITIALIZING);
        game2.setName(game.getName());
        game2.setMaxPlayers(game.getMaxPlayers());
        gameRepository.save(game2);
        return game2;
    }
    public Game getGameById(int id){
        for (Game gms: gameRepository.findAll()) {
            if(gms.getGameID() == id)
                return gms;
        }
        return null;
    }
    public Game SaveGame (int id) {
        return getGameById(id);
    }

    public Game updateGame (Game game) {
        return null;
    }

    public List<Game> deleteGame(int id){
        gameRepository.delete(getGameById(id));
        return gameRepository.findAll();
    }

    public List<Game> loadGames() {
        List<GameState> states = Arrays.asList(GameState.INITIALIZING, GameState.SAVED);
        return gameRepository.findAllByStateIn(states);
    }

    public Game getGame(int id){
        return gameRepository.findById(id).orElse(null);
    }

}
