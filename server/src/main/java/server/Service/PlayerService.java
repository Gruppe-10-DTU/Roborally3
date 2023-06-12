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
    private PlayerRepository playerRepository;
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void addPlayer(Player player){
        int count = playerRepository.countPlayerByGameIdAndName(player.getGameId(), player.getName());
        if(count > 0){
            player.setName(player.getName() + "["+count+"]");
        }
        playerRepository.save(player);
    }

    public int countPlayers(int id) {
        return playerRepository.countPlayerByGameId(id);
    }
    public List<Player> getPlayerList(int id){
        List<Player> playerList = playerRepository.findAllByGameId(id);
        return playerList;
    }
}
