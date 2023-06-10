package server.Service;

import org.springframework.stereotype.Service;
import server.model.Game;
import server.model.GameState;
import server.repository.GameRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class GameService {
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }
    public Game createGame(Game game) {
        game.setState(GameState.INITIALIZING);
        gameRepository.save(game);
        return game;
    }

    public void updateGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGame(int id) {
        gameRepository.deleteById(id);
    }

    public List<Game> loadGames() {
        List<GameState> states = Arrays.asList(GameState.INITIALIZING, GameState.SAVED);
        return gameRepository.findAllByStateIn(states);
    }

    public Game getGame(int id) {
        return gameRepository.findById(id).orElse(null);
    }

    public Game getGameWithVersion(int id, int version){
        return gameRepository.findGameByIdAndVersionGreaterThan(id, version);
    }

    public void updateCurrPlayers(int gameId, int count) {
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game != null) {
            game.setCurrentPlayers(count);
            gameRepository.save(game);
        }
    }
}
