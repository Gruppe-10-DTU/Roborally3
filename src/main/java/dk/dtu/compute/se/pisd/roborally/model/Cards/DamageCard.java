package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import org.jetbrains.annotations.NotNull;

public class DamageCard extends Subject implements Card<Damage> {
    final private Damage damage;

    public DamageCard(@NotNull Damage damage) {
        this.damage = damage;
    }

    @Override
    public String getName() {
        return damage.getDisplayName();
    }

    @Override
    public Damage getAction() {
        return damage;
    }

    @Override
    public String getType() {
        return "Damage";
    }
}
