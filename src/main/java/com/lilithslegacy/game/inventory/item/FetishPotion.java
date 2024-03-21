package com.lilithslegacy.game.inventory.item;

import java.util.List;

import com.lilithslegacy.game.inventory.enchanting.PossibleItemEffect;

/**
 * Container class for fetish potions
 *
 * @author Stadler76
 * @version 0.3.6.4
 * @since 0.3.6.4
 */
public class FetishPotion extends AbstractPotion {

    public FetishPotion(AbstractItemType itemType, List<PossibleItemEffect> effects) {
        super(itemType, effects);
    }
}
