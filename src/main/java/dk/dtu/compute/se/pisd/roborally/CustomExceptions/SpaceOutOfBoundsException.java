package dk.dtu.compute.se.pisd.roborally.CustomExceptions;

import dk.dtu.compute.se.pisd.roborally.model.Space;

public class SpaceOutOfBoundsException extends IndexOutOfBoundsException {
    public SpaceOutOfBoundsException() {
        super();
    }


    public SpaceOutOfBoundsException(Space space) {
        super("Space out of range: " + "(" + space.x + "," + space.y + ")");
    }

}
