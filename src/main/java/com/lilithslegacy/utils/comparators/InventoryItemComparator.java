package com.lilithslegacy.utils.comparators;

import java.util.Comparator;

import com.lilithslegacy.game.inventory.item.AbstractItem;

/**
 * @author Innoxia
 * @version 0.3.8
 * @since 0.1.66
 */
public class InventoryItemComparator implements Comparator<AbstractItem> {

    @Override
    public int compare(AbstractItem first, AbstractItem second) {
        int result = first.getRarity().compareTo(second.getRarity());

        if (result != 0) {
            return result;

        } else {
            result = first.getItemType().toString().compareTo(second.getItemType().toString());

            if (result != 0) {
                return result;
            } else {
                if (first.getColour(0) != null) {
                    if (second.getColour(0) != null) {
                        return first.getColour(0).getName().compareTo(second.getColour(0).getName());
                    } else {
                        return 1;
                    }
                }
                return 0;
            }
        }
    }
}
