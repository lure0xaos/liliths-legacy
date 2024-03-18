package com.lilithslegacy.utils.comparators;

import java.util.Comparator;

import com.lilithslegacy.game.inventory.weapon.AbstractWeaponType;

/**
 * Compares by rarity.
 *
 * @author Innoxia
 * @version 0.3.2
 * @since 0.1.2
 */
public class WeaponTypeRarityComparator implements Comparator<AbstractWeaponType> {
    @Override
    public int compare(AbstractWeaponType first, AbstractWeaponType second) {
        int result = first.getRarity().compareTo(second.getRarity());
        if (result != 0) {
            return result;
        } else {
            return first.getDamage() - second.getDamage();
        }
    }
}
