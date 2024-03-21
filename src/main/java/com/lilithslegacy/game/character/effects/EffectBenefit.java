package com.lilithslegacy.game.character.effects;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.3.8.2
 * @since 0.3.8.2
 */
public enum EffectBenefit {

    BENEFICIAL(PresetColour.GENERIC_GOOD),

    NEUTRAL(PresetColour.GENERIC_NEUTRAL),

    DETRIMENTAL(PresetColour.GENERIC_BAD);

    private Colour colour;

    private EffectBenefit(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

}
