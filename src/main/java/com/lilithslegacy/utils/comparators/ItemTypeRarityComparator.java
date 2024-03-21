package com.lilithslegacy.utils.comparators;

import java.util.Comparator;

import com.lilithslegacy.game.inventory.item.AbstractItemType;

/**
 * Compares by rarity.
 *
 * @author Innoxia
 * @version 0.3.2
 * @since 0.1.2
 */
public class ItemTypeRarityComparator implements Comparator<AbstractItemType> {
    @Override
    public int compare(AbstractItemType first, AbstractItemType second) {
        int result = first.getRarity().compareTo(second.getRarity());
        if (result != 0) {
            return result;
        } else {
            return first.getValue() - second.getValue();
        }
    }
}
