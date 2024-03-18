package com.lilithslegacy.game.character.effects;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.2.11
 * @since 0.1.53
 */
public enum PerkCategory {

    JOB(PresetColour.BASE_BROWN),

    PHYSICAL(PresetColour.ATTRIBUTE_PHYSIQUE),

    LUST(PresetColour.ATTRIBUTE_CORRUPTION),

    ARCANE(PresetColour.ATTRIBUTE_ARCANE),

    // Just for colouring:
    PHYSICAL_EARTH(PresetColour.SPELL_SCHOOL_EARTH),
    PHYSICAL_WATER(PresetColour.SPELL_SCHOOL_WATER),
    ARCANE_FIRE(PresetColour.SPELL_SCHOOL_FIRE),
    ARCANE_AIR(PresetColour.SPELL_SCHOOL_AIR);

    private final Colour colour;

    PerkCategory(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }
}
