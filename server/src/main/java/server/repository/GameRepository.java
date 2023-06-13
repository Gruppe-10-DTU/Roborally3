package server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.model.Game;
import server.model.GameState;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Integer> {

    /**
     * Get all games
     *
     * @return List of games
     * @author Sandie Petersen
     */
    List<Game> findAll();

    /**
     * Get list of games with one of the specified states
     *
     * @param states List of states
     * @return list of games
     * @author Sandie Petersen og Nilas Thøgersen
     */
    List<Game> findAllByStateIn(List<GameState> states);

    /**
     * @param id id of the game
     * @param version version of the game
     * @return game with a newer version than specified
     * @author Sandie Petersen og Nilas Thøgersen
     */
    Game findGameByIdAndVersionGreaterThan(int id, int version);
}
