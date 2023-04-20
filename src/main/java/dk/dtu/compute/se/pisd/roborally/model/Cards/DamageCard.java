package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import org.jetbrains.annotations.NotNull;

public class DamageCard extends Subject implements Card {
    final public Damage damage;

    public DamageCard(@NotNull Damage damage) {
        this.damage = damage;
    }

    public String getName() {
        return damage.displayName;
    }

    @Override
    public String getType() {
        return "Damage";
    }
}
