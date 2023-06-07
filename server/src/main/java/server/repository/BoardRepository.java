package server.repository;

import org.springframework.data.repository.CrudRepository;
import server.model.Board;

public interface BoardRepository extends CrudRepository<Board, Integer> {

    Board getBoardByGameId(int gameId);

}
