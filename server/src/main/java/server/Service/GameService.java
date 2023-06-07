package server.Service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import server.model.Game;
import server.model.GameState;
import server.repository.GameRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class GameService {

    Gson gson = new Gson();

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
       this.gameRepository = gameRepository;
    }
    public Game createGame(Game game) {
        gameRepository.save(game);
        return game;
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
}
