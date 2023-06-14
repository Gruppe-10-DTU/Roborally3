package server.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import server.model.Player;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    /**
     * Get all players in the game
     *
     * @param gameId Id of the game
     * @return List of players
     * @author Nilas Thoegersen
     */
    List<Player> findAllByGameId(int gameId);

    /**
     * @param id id of the game
     * @return Amount of players in the game
     * @author Asbjørn
     */
    int countPlayerByGameId(int id);

    /**
     * Delete a player in the game
     *
     * @param playerId Id of the player
     * @param GameId Id of the game
     * @author Asbjørn
     */
    @Transactional
    void deletePlayerByIdAndGameId(int playerId, int GameId);

    /**
     * See how many players with the same name are in the game.
     *
     * @param id id of the game
     * @param name Name of the player
     * @return amount of players in the game
     * @author Søren Wünsche og Nilas Thøgersen
     */
    int countPlayerByGameIdAndNameLike(int id, String name);


}
