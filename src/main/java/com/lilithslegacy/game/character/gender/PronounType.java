package com.lilithslegacy.game.character.gender;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.86
 * @since 0.1.86
 */
public enum PronounType {
    FEMININE("feminine", PresetColour.FEMININE),
    NEUTRAL("androgynous", PresetColour.ANDROGYNOUS),
    MASCULINE("masculine", PresetColour.MASCULINE);

    private String name;
    private Colour colour;

    private PronounType(String name, Colour colour) {
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
