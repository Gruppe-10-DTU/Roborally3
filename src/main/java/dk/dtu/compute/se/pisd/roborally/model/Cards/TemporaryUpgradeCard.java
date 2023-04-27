package dk.dtu.compute.se.pisd.roborally.model.Cards;

public class TemporaryUpgradeCard implements Card<TemporaryUpgrade>{
    private String name;
    private TemporaryUpgrade temporaryUpgrade;

    public TemporaryUpgradeCard(String name, TemporaryUpgrade temporaryUpgrade) {
        this.name = name;
        this.temporaryUpgrade = temporaryUpgrade;
    }

    @Override
    public TemporaryUpgrade getType() {
        return temporaryUpgrade;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int getAmount() {
        return 0;
    }

}
