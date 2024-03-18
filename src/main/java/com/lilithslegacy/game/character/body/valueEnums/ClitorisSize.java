package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * Anything of size TWO_LARGE and over is treated as a pseudo penis.
 *
 * @author Innoxia
 * @version 0.4
 * @since 0.1.0
 */
public enum ClitorisSize {

    ZERO_AVERAGE("small", 0, 1, PresetColour.GENERIC_SIZE_ONE) {
        @Override
        public boolean isPseudoPenisSize() {
            return false;
        }
    },

    ONE_BIG("big", 1, 5, PresetColour.GENERIC_SIZE_TWO) {
        @Override
        public boolean isPseudoPenisSize() {
            return false;
        }
    },

    TWO_LARGE("large", 5, 10, PresetColour.GENERIC_SIZE_THREE),

    THREE_HUGE("huge", 10, 25, PresetColour.GENERIC_SIZE_FOUR),

    FOUR_MASSIVE("massive", 25, 40, PresetColour.GENERIC_SIZE_FIVE),

    FIVE_ENORMOUS("enormous", 40, 50, PresetColour.GENERIC_SIZE_SIX),

    SIX_GIGANTIC("gigantic", 50, 60, PresetColour.GENERIC_SIZE_SEVEN),

    SEVEN_STALLION("absurdly colossal", 60, 100, PresetColour.GENERIC_SIZE_EIGHT);

    private final int minimumValue;
    private final int maximumValue;
    private final String descriptor;
    private final Colour colour;

    ClitorisSize(String descriptor, int minimumValue, int maximumValue, Colour colour) {
        this.descriptor = descriptor;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.colour = colour;
    }

    public static ClitorisSize getMinimumClitorisSizeForPseudoPenis() {
        return TWO_LARGE;
    }

    public static ClitorisSize getClitorisSizeFromInt(int cm) {
        for (ClitorisSize cs : ClitorisSize.values()) {
            if (cm >= cs.getMinimumValue() && cm < cs.getMaximumValue()) {
                return cs;
            }
        }
        return SEVEN_STALLION;
    }

    public boolean isPseudoPenisSize() {
        return true;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public int getMedianValue() {
        return minimumValue + (maximumValue - minimumValue) / 2;
    }

    /**
     * To fit into a sentence: "Your clitoris is "+getDescriptor()+"." "Your "
     * getDescriptor()+" cock is dribbling precum."
     */
    public String getDescriptor() {
        return descriptor;
    }

    public Colour getColour() {
        return colour;
    }
}
