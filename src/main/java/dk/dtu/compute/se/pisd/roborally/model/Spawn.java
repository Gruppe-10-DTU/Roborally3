package dk.dtu.compute.se.pisd.roborally.model;

import org.jetbrains.annotations.NotNull;

public class Spawn extends Space implements Comparable<Spawn> {

    private int number;

    public Spawn(Board board, int x, int y, int number) {
        super(board, x, y);
        this.number = number;
    }

    /**
     *
     * @param spawn The elemented being compared to
     * @return The priority of the spawn space
     * used to dertemind where the players should spawn
     */

    @Override
    public int compareTo(@NotNull Spawn spawn) {
        return Integer.compare(this.number, spawn.number);
    }
}
