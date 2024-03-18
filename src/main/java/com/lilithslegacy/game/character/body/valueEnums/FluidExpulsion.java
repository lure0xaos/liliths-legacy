package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.2.7
 * @since 0.2.7
 */
public enum FluidExpulsion {

    ZERO_NONE("tiny", 0, 20, PresetColour.GENERIC_SIZE_ONE),

    ONE_SMALL("small", 20, 40, PresetColour.GENERIC_SIZE_TWO),

    TWO_MODERATE("moderate", 40, 60, PresetColour.GENERIC_SIZE_THREE),

    THREE_LARGE("large", 60, 80, PresetColour.GENERIC_SIZE_FOUR),

    FOUR_HUGE("huge", 80, 100, PresetColour.GENERIC_SIZE_FIVE);

    private final int minimumValue;
    private final int maximumValue;
    private final String descriptor;
    private final Colour colour;

    FluidExpulsion(String descriptor, int minimumValue, int maximumValue, Colour colour) {
        this.descriptor = descriptor;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.colour = colour;
    }

    public static FluidExpulsion getFluidExpulsionFromInt(int value) {
        for (FluidExpulsion expulsion : FluidExpulsion.values()) {
            if (value >= expulsion.getMinimumValue() && value < expulsion.getMaximumValue()) {
                return expulsion;
            }
        }
        return FOUR_HUGE;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public int getMedianValue() {
        return minimumValue + ((maximumValue - minimumValue) / 2);
    }

    public String getDescriptor() {
        return descriptor;
    }

    public Colour getColour() {
        return colour;
    }
}
