package com.lilithslegacy.game.character;

import java.util.Arrays;
import java.util.List;

/**
 * @author Innoxia
 * @version 0.3.2
 * @since 0.3.2
 */
public enum EquipClothingSetting {

    ADD_WEAPONS,

    ADD_TATTOOS,

    ADD_ACCESSORIES,

    REPLACE_CLOTHING,

    REMOVE_SEALS;

    public static List<EquipClothingSetting> getAllClothingSettings() {
        return Arrays.asList(EquipClothingSetting.values());
    }
}
