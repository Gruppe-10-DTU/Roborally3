package server.mapper;

import org.mapstruct.*;
import server.dto.GameDTO;
import server.dto.GamePatchDTO;
import server.dto.PlayerDTO;
import server.model.Game;
import server.model.Player;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DtoMapper {

    GameDTO gameToGameDto(Game game);

    List<GameDTO> gameToGameDto(List<Game> game);

    void updateGameFromDto(GamePatchDTO dto, @MappingTarget Game entity);


    Game gameDtoToGame(GameDTO gameDTO);

    List<PlayerDTO> playerToPlayerDto(List<Player> player);
}
