package dk.dtu.compute.se.pisd.roborally.model;

public class PlayerDTO {
    private String name;

    public PlayerDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
