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
    private GameRepository gameRepository;

    public GameService(GameRepository gameRepository){
       this.gameRepository = gameRepository;
    }
    public void SaveGame (Game game) {

    }
        public Game gameUWrap(String game){
            return gson.fromJson(game,Game.class);
        }

        public Game createGame(Game game) {
        games.add(game.getGameID(),game);
        return game;
    }
    public Game getGameById(int id){
        return games.get(id);
    }
    public Game SaveGame (int id) {
        return games.get(id);
    }

    public Game updateGame (Game game) {
        games.get(game.getGameID()).setBoard(game.getBoard());
        return games.get(game.getGameID());
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
