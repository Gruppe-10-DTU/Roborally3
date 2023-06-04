package server.mapper;

import org.mapstruct.Mapper;
import server.dto.BoardDTO;
import server.dto.GameDTO;
import server.model.Board;
import server.model.Game;

import java.util.List;

@Mapper(
        componentModel = "spring"
)
public interface DtoMapper {

    GameDTO gameToGameDto(Game game);

    List<GameDTO> gameToGameDto(List<Game> game);

    Game gameDtoToGame(GameDTO gameDTO);

    BoardDTO boardToBoardDto(Board board);

    Board boardDtoToBoard(BoardDTO boardDTO);
}
