package dk.dtu.compute.se.pisd.roborally.model.Cards;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import org.jetbrains.annotations.NotNull;

public class DamageCard extends Subject implements Card<Damage> {
    final public Damage damage;

    public DamageCard(@NotNull Damage damage) {
        this.damage = damage;
    }

    @Override
    public String getName() {
        return damage.displayName;
    }

    @Override
    public int getAmount() {
        return damage.cardAmount;
    }

    @Override
    public Damage getType() {
        return damage;
    }
}
