package com.lilithslegacy.game.inventory.enchanting;

/**
 * Container class for TF effects
 *
 * @author Stadler76
 * @version 0.3.6.4
 * @since 0.3.6.4
 */
public class PossibleItemEffect {
    private final ItemEffect effect;
    private final String message;
    private final int chance;

    public PossibleItemEffect(ItemEffect effect, String message) {
        this(effect, message, 0);
    }

    public PossibleItemEffect(ItemEffect effect, int chance) {
        this(effect, "", chance);
    }

    public PossibleItemEffect(ItemEffect effect, String message, int chance) {
        this.effect = effect;
        this.message = message;
        this.chance = chance;
    }

    public ItemEffect getEffect() {
        return effect;
    }

    public String getMessage() {
        return message;
    }

    public int getChance() {
        return chance;
    }
}
