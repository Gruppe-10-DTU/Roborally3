package server.mapper;

import server.dto.GameDTO;
import server.model.Game;

import java.util.ArrayList;

public class GameDTOMapper {
    public GameDTO map(Game game) {
        int currentPlayers = game.getBoard().getCurrentPlayers().size();
        String gameId = game.getBoard().getId();

        return new GameDTO(game.getMaxPlayers(), currentPlayers, game.getHostName(), gameId);
    }

    public ArrayList<GameDTO> mapList (ArrayList<Game> gameList) {
        ArrayList<GameDTO> gameDtoList = new ArrayList<>();

        for ( Game game: gameList) {
            GameDTO gameDTO = map(game);
            gameDtoList.add(gameDTO);
        }

        return gameDtoList;
    }
}
