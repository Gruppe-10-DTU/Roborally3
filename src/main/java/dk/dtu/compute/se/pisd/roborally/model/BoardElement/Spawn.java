package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

public class Spawn extends Space implements Comparable<Spawn> {

    int playerPriority;

    public Spawn(Board board, int x, int y, int playerPriority) {
        super(board, x, y);
        this.playerPriority = playerPriority;
    }

    /**
     *
     * @param spawn
     * @return The priority of the spawn space
     * used to dertemind where the players should spawn
     */

    @Override
    public int compareTo(@NotNull Spawn spawn) {
        return Integer.compare(this.playerPriority, spawn.playerPriority);
    }
}
