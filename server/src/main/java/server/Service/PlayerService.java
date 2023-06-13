package server.Service;

import org.springframework.stereotype.Service;
import server.model.Player;
import server.repository.PlayerRepository;

import java.util.List;

/**
 *
 * @author Nilas Thoegersen & Søren Wünsche
 */
@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    /**
     * Validate the players name is unique, and if it already exists, add a number to it.
     *
     * @param player The player to be added
     * @author Søren Wünsche og Nilas Thoegersen
     */
    public void addPlayer(Player player){
        int count = playerRepository.countPlayerByGameIdAndNameLike(player.getGameId(), player.getName());
        if(count > 0){
            player.setName(player.getName() + "["+count+"]");
        }
        playerRepository.save(player);
    }

    /**
     * Remove a player
     * @param playerId playerid
     * @param gameId gameid
     * @author Asbjørn
     */
    public void removePlayer(int playerId, int gameId) {
        playerRepository.deletePlayerByIdAndGameId(playerId,gameId);
    }

    /**
     * @param id gameid
     * @return amount of players in the game
     * @author Søren Wünsche
     */
    public int countPlayers(int id) {
        return playerRepository.countPlayerByGameId(id);
    }

    /**
     * @param id Game id
     * @return a list of players
     * Unsure about author
     * @author Sandie Petersen & Nilas Thoegersen & Søren Wünsche & Asbjørn og Philip Astrup Cramer
     */
    public List<Player> getPlayerList(int id){
        List<Player> playerList = playerRepository.findAllByGameId(id);
        return playerList;
    }
}
