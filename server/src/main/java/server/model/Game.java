package server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import org.hibernate.annotations.Type;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private int maxPlayers;
    private String hostName;

    int version = 0;

    /*
    private Board board;



    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
     */


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Game() {
    }
}
