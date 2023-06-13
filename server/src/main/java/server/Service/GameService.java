package server.Service;

import org.springframework.stereotype.Service;
import server.model.Game;
import server.model.GameState;
import server.repository.GameRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /**
     * @param game Game to be created
     * @return the game
     * @author Nilas Thoegersen
     */
    public Game createGame(Game game) {
        if(game.getState() == null){
            game.setState(GameState.INITIALIZING);
        }
        gameRepository.save(game);
        return game;
    }

    /**
     * @param game Update the game. If the game doesn't exist, it gets inserted.
     * @author Asbjørn og Nilas Thoegersen
     */
    public void updateGame(Game game) {
        gameRepository.save(game);
    }

    /**
     * @param id Id of the game
     * @author Asbjørn
     */
    public void deleteGame(int id) {
        gameRepository.deleteById(id);
    }

    /**
     * Get active games
     *
     * @author Asbjørn Nielsen & Sandie & Nilas Thoegersen
     */
    public List<Game> loadGames() {
        List<GameState> states = Arrays.asList(GameState.INITIALIZING, GameState.SAVED);
        return gameRepository.findAllByStateIn(states);
    }

    /**
     * @param id id of game
     * @return Get the game, or null if not exists
     * @author Sandie Petersen & Nilas Thoegersen & Søren Wünsche & Asbjørn
     */
    public Game getGame(int id) {
        return gameRepository.findById(id).orElse(null);
    }

    /**
     * @param id Game id
     * @param version version id
     * @return A game
     * @author Sandie Petersen og Nilas Thoegersen
     */
    public Game getGameWithVersion(int id, int version){
        return gameRepository.findGameByIdAndVersionGreaterThan(id, version);
    }

    /**
     * @author Søren Wünsche
     */
    public void updateCurrPlayers(int gameId, int count) {
        Game game = gameRepository.findById(gameId).orElse(null);
        if (game != null) {
            game.setCurrentPlayers(count);
            gameRepository.save(game);
        }
    }
}
