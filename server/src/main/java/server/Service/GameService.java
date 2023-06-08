package server.Service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import server.model.Game;
import server.model.GameState;
import server.repository.GameRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    Gson gson = new Gson();

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    public Game createGame(Game game) {
        game.setState(GameState.INITIALIZING);
        gameRepository.save(game);
        return game;
    }

    public Game getGameById(int id) {
        for (Game gms : gameRepository.findAll()) {
            if (gms.getGameID() == id)
                return gms;
        }
        return null;
    }

    public Game SaveGame(int id) {
        return getGameById(id);
    }

    public void updateGame(Game game) {
        gameRepository.save(game);
    }

    public List<Game> deleteGame(int id) {
        gameRepository.delete(getGameById(id));
        return gameRepository.findAll();
    }

    public List<Game> loadGames() {
        List<GameState> states = Arrays.asList(GameState.INITIALIZING, GameState.SAVED);
        return gameRepository.findAllByStateIn(states);
    }

    public Game getGame(int id) {
        return gameRepository.findById(id).orElse(null);
    }

    public void updateCurrPlayers(int gameId, int count) {
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game != null) {
            game.setCurrentPlayers(count);
            gameRepository.save(game);
        }
    }

}
