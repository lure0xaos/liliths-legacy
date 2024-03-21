package com.lilithslegacy.game.character.gender;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.69
 * @since 0.1.69
 */
public enum AndrogynousIdentification {
    FEMININE("feminine", PresetColour.FEMININE),
    CLOTHING_FEMININE("clothing feminine", PresetColour.ANDROGYNOUS),
    CLOTHING_MASCULINE("clothing masculine", PresetColour.ANDROGYNOUS),
    MASCULINE("masculine", PresetColour.MASCULINE);

    private String name;
    private Colour colour;

    private AndrogynousIdentification(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }
}
