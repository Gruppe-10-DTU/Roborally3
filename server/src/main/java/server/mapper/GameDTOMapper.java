package server.mapper;

import server.dto.GameDTO;
import server.model.Game;

public class GameDTOMapper {
    public GameDTO map(Game game) {
        int currentPlayers = game.getBoard().getCurrentPlayers().size();
        String gameId = game.getBoard().getId();

        return new GameDTO(game.getMaxPlayers(), currentPlayers, game.getHostName(), gameId);
    }
}
