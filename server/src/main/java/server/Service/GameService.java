package server.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import server.model.Board;
import server.model.Game;
import server.model.Player;
import com.google.gson.*;
import java.util.ArrayList;

@Service
public class GameService {
    private ArrayList<Game> games = new ArrayList<>();

    Gson gson = new Gson();
    /*
    TODO på server :
        - Modtag post omkring spil
        - Opret et spil objekt og gem det.
        - Sæt id på spillet.
        - Returner spillet
     */
    public GameService(){
        Game tstgm = new Game();
        Board brd = new Board();
        tstgm.setMaxPlayers(2);
        tstgm.setHostName("Test");
        tstgm.setGameID(0);
        Player np = new Player();
        np.setpID(1);
        brd.getCurrentPlayers().add(np);
        tstgm.setBoard(brd);
        createGame(tstgm);

        Game tstgm2 = new Game();
        tstgm2.setMaxPlayers(3);
        tstgm2.setHostName("Test Multi");
        tstgm2.setGameID(1);
        tstgm2.setBoard(brd);
        createGame(tstgm2);
    }
        public Game gameUWrap(String game){
            return gson.fromJson(game,Game.class);
        }

        public Game createGame(Game game) {
        Game gm = new Game();

        gm.setGameID(game.getGameID());
        gm.setMaxPlayers(game.getMaxPlayers());
        gm.setHostName(game.getHostName());
        gm.setBoard(game.getBoard());

        games.add(gm.getGameID(),gm);
        return gm;
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

    public ArrayList<Game> deleteGame(int id){
        games.remove(getGameById(id));
        return games;
    }


    public ArrayList<Game> loadGames() {
        return games;
    }
}
