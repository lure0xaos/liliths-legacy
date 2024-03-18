package com.lilithslegacy.game.inventory.item;

import java.util.List;

import com.lilithslegacy.game.inventory.enchanting.PossibleItemEffect;

/**
 * Container class for potions
 *
 * @author Stadler76
 * @version 0.3.6.4
 * @since 0.3.6.4
 */
public abstract class AbstractPotion {
    private final AbstractItemType itemType;
    private final List<PossibleItemEffect> effects;

    public AbstractPotion(AbstractItemType itemType, List<PossibleItemEffect> effects) {
        this.itemType = itemType;
        this.effects = effects;
    }

    public AbstractItemType getItemType() {
        return itemType;
    }

    public List<PossibleItemEffect> getEffects() {
        return effects;
    }
}
