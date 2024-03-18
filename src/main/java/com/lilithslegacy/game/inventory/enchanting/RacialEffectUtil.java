package com.lilithslegacy.game.inventory.enchanting;

/**
 * @author Innoxia
 * @version 0.2.11
 * @since 0.1.7?
 */
public abstract class RacialEffectUtil {

    private final String description;

    public RacialEffectUtil(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public abstract String applyEffect();

}
