package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.2.1
 */
public enum PenetrationGirth {

    ZERO_THIN(0, -0.8f, "thin", PresetColour.GENERIC_SIZE_ONE),

    ONE_SLENDER(1, -0.4f, "slender", PresetColour.GENERIC_SIZE_TWO),

    TWO_NARROW(2, -0.2f, "narrow", PresetColour.GENERIC_SIZE_THREE),

    THREE_AVERAGE(3, 0, "averagely-girthed", PresetColour.GENERIC_SIZE_FOUR),

    FOUR_GIRTHY(4, 0.2f, "girthy", PresetColour.GENERIC_SIZE_FIVE),

    FIVE_THICK(5, 0.4f, "thick", PresetColour.GENERIC_SIZE_SIX),

    SIX_CHUBBY(6, 0.6f, "chubby", PresetColour.GENERIC_SIZE_SEVEN),

    SEVEN_FAT(7, 0.8f, "fat", PresetColour.GENERIC_SIZE_EIGHT);


    private final int value;
    private final float diameterPercentageModifier;
    private final String descriptor;
    private final Colour colour;

    PenetrationGirth(int value, float diameterPercentageModifier, String descriptor, Colour colour) {
        this.value = value;
        this.diameterPercentageModifier = diameterPercentageModifier;
        this.descriptor = descriptor;
        this.colour = colour;
    }

    public static PenetrationGirth getGirthFromInt(int size) {
        for (PenetrationGirth ls : PenetrationGirth.values()) {
            if (size == ls.getValue()) {
                return ls;
            }
        }
        return ZERO_THIN;
    }

    public static int getMaximum() {
        return SEVEN_FAT.getValue();
    }

    public int getValue() {
        return value;
    }

    /**
     * @return The percentage (as a float from 0->1) by which this girth increases the diameter of a penetration type.
     */
    public float getDiameterPercentageModifier() {
        return diameterPercentageModifier;
    }

    public String getName() {
        return descriptor;
    }

    public Colour getColour() {
        return colour;
    }
}
