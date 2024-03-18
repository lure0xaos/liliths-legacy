package com.lilithslegacy.game.dialogue.places.dominion.warehouseDistrict;

import com.lilithslegacy.utils.Util.Value;

/**
 * @author Innoxia
 * @version 0.3.7.7
 * @since 0.3.7
 */
public abstract class FillyReward {

    private final String name;
    private final String description;
    private final int cost;

    public FillyReward(String name, String description, int cost) {
        this.name = name;
        this.description = description;
        this.cost = cost;
    }

    public Value<Boolean, String> isAvailable() {
        return new Value<>(true, "");
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public abstract String applyEffect();
}
