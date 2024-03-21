package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.86
 * @since 0.1.86
 */
public enum LabiaSize {

    ZERO_TINY(0, "tiny", PresetColour.GENERIC_SIZE_ONE),
    ONE_SMALL(1, "small", PresetColour.GENERIC_SIZE_TWO),
    TWO_AVERAGE(2, "average-sized", PresetColour.GENERIC_SIZE_THREE),
    THREE_LARGE(3, "large", PresetColour.GENERIC_SIZE_FOUR),
    FOUR_MASSIVE(4, "massive", PresetColour.GENERIC_SIZE_FIVE);


    private int value;
    private String descriptor;
    private Colour colour;

    private LabiaSize(int value, String descriptor, Colour colour) {
        this.value = value;
        this.descriptor = descriptor;
        this.colour = colour;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return descriptor;
    }

    public static LabiaSize getRandomLabiaSize() {
        return LabiaSize.values()[Util.random.nextInt(LabiaSize.values().length)];
    }

    public static LabiaSize getLabiaSizeFromInt(int size) {
        for (LabiaSize as : LabiaSize.values()) {
            if (size == as.getValue()) {
                return as;
            }
        }
        return ZERO_TINY;
    }

    public Colour getColour() {
        return colour;
    }
}
