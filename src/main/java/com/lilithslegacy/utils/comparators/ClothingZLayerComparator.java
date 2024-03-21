package com.lilithslegacy.utils.comparators;

import java.util.Comparator;

import com.lilithslegacy.game.inventory.clothing.AbstractClothing;

/**
 * Compares by zLayer, using the InventorySlot in index 0 of available equip slots.
 *
 * @author Innoxia
 * @version 0.3.4
 * @since 0.1.0
 */
public class ClothingZLayerComparator implements Comparator<AbstractClothing> {
    @Override
    public int compare(AbstractClothing o1, AbstractClothing o2) {
        if (o2.getClothingType().getEquipSlots().get(0).getZLayer() > o1.getClothingType().getEquipSlots().get(0).getZLayer()) {
            return 1;

        } else if (o2.getClothingType().getEquipSlots().get(0).getZLayer() == o1.getClothingType().getEquipSlots().get(0).getZLayer()) {
            return 0;

        } else {
            return -1;
        }
    }
}
