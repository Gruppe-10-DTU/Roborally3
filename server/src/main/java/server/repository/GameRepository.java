package server.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import server.model.Game;
import server.model.GameState;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> findAll();

    List<Game> findAllByState(GameState state);
}
