package dk.dtu.compute.se.pisd.roborally.model.BoardElement;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public class Gear extends BoardElement {

    @Override
    public void action(Player player) {

    }

    private Heading heading;

    public Gear(Heading heading){
        this.heading = heading;
    }
}
