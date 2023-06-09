package server.Service;

import org.springframework.stereotype.Service;
import server.model.Player;
import server.repository.PlayerRepository;

import java.util.List;

@Service
public class PlayerService {
    private PlayerRepository playerRepository;
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void addPlayer(Player player){
        playerRepository.save(player);
    }
    public void removePlayer(String playerName, int gameId) {
        playerRepository.deletePlayerByNameAndGameId(playerName,gameId);
    }
    public int countPlayers(int id) {
        return playerRepository.countPlayerByGameId(id);
    }
    public List<Player> getPlayerList(int id){
        List<Player> playerList = playerRepository.findAllByGameId(id);
        return playerList;
    }
}
