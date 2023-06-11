package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.Player;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    List<Player> findAllByGameId(int gameId);

    int countPlayerByGameId(int id);

    int countPlayerByGameIdAndName(int id, String name);


}
