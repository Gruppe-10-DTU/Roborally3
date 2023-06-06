package server.Service;

import org.springframework.stereotype.Service;
import server.dto.GameDTO;
import server.model.Board;
import server.model.Game;
import server.repository.GameRepository;

import java.util.List;
import server.model.Player;
import com.google.gson.*;
import java.util.ArrayList;

@Service
public class GameService {
    Gson gson = new Gson();
    List<Game> games = null;

    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
       this.gameRepository = gameRepository;
    }
    public void SaveGame (Game game) {

    }

        public Game createGame(Game game) {
        games.add(game.getId(),game);
        return game;
    }
    public Game getGameById(int id){
        return games.get(id);
    }
    public Game SaveGame (int id) {
        return games.get(id);
    }

    public Game updateGame (Game game) {
        games.get(game.getId()).setBoard(game.getBoard());
        return games.get(game.getId());
    }

    public List<Game> deleteGame(int id){
        games.remove(getGameById(id));
        return games;
    }


    public List<Game> loadGames() {
        List<Game> games = gameRepository.findAll();


        return games;
    }
}
