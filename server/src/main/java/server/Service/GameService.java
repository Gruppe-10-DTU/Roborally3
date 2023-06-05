package server.Service;

import org.springframework.stereotype.Service;
import server.model.Game;
import server.model.GameState;
import server.repository.GameRepository;

import java.util.List;

@Service
public class GameService {
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
       this.gameRepository = gameRepository;
    }
    public void SaveGame (Game game) {

    }

    public void updateGame (Game game) {

    }

    public List<Game> loadGames() {
        List<Game> games = gameRepository.findAllByState(GameState.INITIALIZING);
        return games;
    }
}
