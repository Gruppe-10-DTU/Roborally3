package server.Service;

import org.springframework.stereotype.Service;
import server.model.Game;
import server.model.GameState;
import server.repository.GameRepository;

import java.util.Arrays;
import java.util.List;

import com.google.gson.*;

@Service
public class GameService {

    Gson gson = new Gson();
    List<Game> games = null;

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
       this.gameRepository = gameRepository;
    }
        public Game createGame(Game game) {
        Game game2 = gameRepository.save(game);
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
        return games.get(id);
    }

    public Game updateGame (Game game) {
        games.get(game.getId()).setBoard(game.getBoard());
        return games.get(game.getId());
    }

    public List<Game> deleteGame(int id){
        games.remove(getGameById(id));
        return games;
    }

    public List<Game> loadGames() {
        List<GameState> states = Arrays.asList(GameState.INITIALIZING, GameState.SAVED);
        List<Game> games = gameRepository.findAllByStateIn(states);
        return games;
    }
}
