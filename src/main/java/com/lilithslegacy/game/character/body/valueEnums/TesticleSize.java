package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * Arbitrary measurements in increments of 1, going from 0 to 7.
 *
 * @author Innoxia
 * @version 0.1.83
 * @since 0.1.0
 */
public enum TesticleSize {

    /**
     * Little bumps under the skin.
     */
    ZERO_VESTIGIAL("vestigial", 0, PresetColour.GENERIC_SIZE_ONE),

    /**
     * Small for a human.
     */
    ONE_TINY("tiny", 1, PresetColour.GENERIC_SIZE_TWO),

    /**
     * Average for a human.
     */
    TWO_AVERAGE("average-sized", 2, PresetColour.GENERIC_SIZE_THREE),

    /**
     * Large for a human. Average for a dog or wolf-morph.
     */
    THREE_LARGE("large", 3, PresetColour.GENERIC_SIZE_FOUR),

    /**
     * Unrealistically large for a human. Average for a horse-morph.
     */
    FOUR_HUGE("huge", 4, PresetColour.GENERIC_SIZE_FIVE),

    /**
     * Large for a horse-morph.
     */
    FIVE_MASSIVE("massive", 5, PresetColour.GENERIC_SIZE_SIX),

    /**
     * Getting pretty absurd.
     */
    SIX_GIGANTIC("gigantic", 6, PresetColour.GENERIC_SIZE_SEVEN),

    /**
     * "Extreme proportion" content.
     */
    SEVEN_ABSURD("absurdly enormous", 7, PresetColour.GENERIC_SIZE_EIGHT);

    private final int value;
    private final String descriptor;
    private final Colour colour;

    TesticleSize(String descriptor, int value, Colour colour) {
        this.descriptor = descriptor;
        this.value = value;
        this.colour = colour;
    }

    public static TesticleSize getTesticleSizeFromInt(int index) {
        for (TesticleSize ts : TesticleSize.values()) {
            if (index == ts.getValue()) {
                return ts;
            }
        }
        return SEVEN_ABSURD;
    }

    public int getValue() {
        return value;
    }

    /**
     * To fit into a sentence: "Your balls are "+getDescriptor()+"."
     */
    public String getDescriptor() {
        return descriptor;
    }

    public Colour getColour() {
        return colour;
    }
}
