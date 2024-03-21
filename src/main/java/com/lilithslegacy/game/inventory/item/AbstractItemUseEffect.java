package com.lilithslegacy.game.inventory.item;

import java.util.List;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;

/**
 * @author Innoxia
 * @version 0.3.1
 * @since 0.3.1
 */
public abstract class AbstractItemUseEffect {

    protected List<ItemEffect> effects;

    public AbstractItemUseEffect(List<ItemEffect> effects) {
        this.effects = effects;
    }

    public abstract String getUseName(GameCharacter target);

    public abstract String getUseDescription(GameCharacter target);

    public List<ItemEffect> getEffects() {
        return effects;
    }

    public boolean isAbleToBeUsed(GameCharacter target) {
        return getCannotUseDescription(target) == null || getCannotUseDescription(target).isEmpty();
    }

    public String getCannotUseDescription(GameCharacter target) {
        return null;
    }

    public boolean isAbleToBeUsedInSex() {
        return true;
    }

    public boolean isAbleToBeUsedInCombat() {
        return true;
    }

    public boolean isConsumedOnUse() {
        return true;
    }

    public boolean isTransformative() {
        return false;
    }

    public boolean isGift() {
        return false;
    }

    public boolean isFetishGiving() {
        return false;
    }

}
