package server.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import server.model.Player;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    List<Player> findAllByGameId(int gameId);

    int countPlayerByGameId(int id);
    @Transactional
    void deletePlayerByNameAndGameId(String name, int GameId);

    int countPlayerByGameIdAndName(int id, String name);


}
