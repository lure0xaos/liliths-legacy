package com.lilithslegacy.game.character.persona;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.3.5
 * @since 0.3.5
 */
public enum PersonalityCategory {

    CORE("core", PresetColour.GENERIC_EXCELLENT),

    COMBAT("combat", PresetColour.GENERIC_COMBAT),

    SEX("sex", PresetColour.GENERIC_SEX),

    SPEECH("speech", PresetColour.BASE_PURPLE_LIGHT);

    private final String name;
    private final Colour colour;

    PersonalityCategory(String name, Colour colour) {
        this.colour = colour;
        this.name = name;
    }

    public Colour getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }
}
