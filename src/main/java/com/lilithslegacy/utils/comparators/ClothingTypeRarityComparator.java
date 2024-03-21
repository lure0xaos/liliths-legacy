package com.lilithslegacy.utils.comparators;

import java.util.Comparator;

import com.lilithslegacy.game.inventory.clothing.AbstractClothingType;

/**
 * Compares by rarity, using the InventorySlot in index 0 of available equip slots.
 *
 * @author Innoxia
 * @version 0.3.4
 * @since 0.1.2
 */
public class ClothingTypeRarityComparator implements Comparator<AbstractClothingType> {
    @Override
    public int compare(AbstractClothingType first, AbstractClothingType second) {
        int result = first.getRarity().compareTo(second.getRarity());
        if (result != 0) {
            return result;
        } else {
            return first.getEquipSlots().get(0).getZLayer() - second.getEquipSlots().get(0).getZLayer();
        }
    }
}
