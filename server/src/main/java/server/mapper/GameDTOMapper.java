package server.mapper;

import server.dto.GameDTO;
import server.model.Game;

import java.util.ArrayList;

public class GameDTOMapper {
    /**
     *
     * @param game
     * @return
     * Maps one Game to a Game dto
     * @author Sandie Petersen
     */
    public GameDTO map(Game game) {
        int currentPlayers = game.getBoard().getCurrentPlayers().size();
        int gameId = game.getGameID();

        return new GameDTO(game.getMaxPlayers(), currentPlayers, game.getHostName(), gameId);
    }

    /**
     *
     * @param gameList
     * @return
     *  Maps a list of games to a list of game dto's using the map method above
     * @author Sandie Petersen
     */
    public ArrayList<GameDTO> mapList (ArrayList<Game> gameList) {
        ArrayList<GameDTO> gameDtoList = new ArrayList<>();

        for ( Game game: gameList) {
            GameDTO gameDTO = map(game);
            gameDtoList.add(gameDTO);
        }

        return gameDtoList;
    }
}
