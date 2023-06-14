package server.service;

import org.springframework.stereotype.Service;
import server.exception.CustomExceptionNoSavedGames;
import server.model.Game;
import server.model.GameState;
import server.repository.GameRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public List<Game> loadGames(Optional<GameState> state) {
        List<Game> games;
        if(state.isPresent()){
            games = gameRepository.findAllByStateIn(state.stream().toList());
        } else {
            games = gameRepository.findAllByStateIn(Arrays.asList(GameState.INITIALIZING, GameState.SAVED));
        }
        
        return games;
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
        return gameRepository.findGameByIdAndVersionGreaterThanOrderByVersionDesc(id, version);
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
