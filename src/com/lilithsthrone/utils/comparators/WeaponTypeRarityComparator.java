package com.lilithsthrone.utils.comparators;

import com.lilithsthrone.game.inventory.weapon.AbstractWeaponType;

import java.util.Comparator;

/**
 * Compares by rarity.
 * 
 * @since 0.1.2
 * @version 0.3.2
 * @author Innoxia
 */
public class WeaponTypeRarityComparator implements Comparator<AbstractWeaponType> {
	@Override
	public int compare(AbstractWeaponType first, AbstractWeaponType second) {
		int result = first.getRarity().compareTo(second.getRarity());
		if (result != 0) {
			return result;
		} else {
			return (int) (first.getDamage() - second.getDamage());
		}
	}
}
