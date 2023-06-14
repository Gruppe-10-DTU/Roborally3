package dk.dtu.compute.se.pisd.roborally.model;

/**
 * Class used to send player to server when joining/leaving a game
 *
 * No concrete author, everybody participated.
 * @author all
 */
public class PlayerDTO {
    private String name;

    private int id;

    public PlayerDTO(){

    }
    public PlayerDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
