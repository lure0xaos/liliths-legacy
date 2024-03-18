package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.83
 * @since 0.1.83
 */
public enum AreolaeSize {

    ZERO_TINY(0, "tiny", PresetColour.GENERIC_SIZE_ONE),
    ONE_SMALL(1, "small", PresetColour.GENERIC_SIZE_TWO),
    TWO_BIG(2, "average-sized", PresetColour.GENERIC_SIZE_THREE),
    THREE_LARGE(3, "large", PresetColour.GENERIC_SIZE_FOUR),
    FOUR_MASSIVE(4, "massive", PresetColour.GENERIC_SIZE_FIVE);


    private final int value;
    private final String descriptor;
    private final Colour colour;

    AreolaeSize(int value, String descriptor, Colour colour) {
        this.value = value;
        this.descriptor = descriptor;
        this.colour = colour;
    }

    public static AreolaeSize getAreolaeSizeFromInt(int index) {
        for (AreolaeSize as : AreolaeSize.values()) {
            if (index == as.getValue()) {
                return as;
            }
        }
        return ZERO_TINY;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return descriptor;
    }

    public Colour getColour() {
        return colour;
    }
}
