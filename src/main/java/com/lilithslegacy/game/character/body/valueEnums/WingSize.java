package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.3.1
 * @since 0.1.95
 */
public enum WingSize {

    ZERO_TINY(0, "tiny", PresetColour.GENERIC_SIZE_ONE),
    ONE_SMALL(1, "small", PresetColour.GENERIC_SIZE_TWO),
    TWO_AVERAGE(2, "average-sized", PresetColour.GENERIC_SIZE_THREE),
    THREE_LARGE(3, "large", PresetColour.GENERIC_SIZE_FOUR),
    FOUR_HUGE(4, "huge", PresetColour.GENERIC_SIZE_FIVE);


    private final int value;
    private final String descriptor;
    private final Colour colour;

    WingSize(int value, String descriptor, Colour colour) {
        this.value = value;
        this.descriptor = descriptor;
        this.colour = colour;
    }

    public static WingSize getWingSizeFromInt(int size) {
        for (WingSize ls : WingSize.values()) {
            if (size == ls.getValue()) {
                return ls;
            }
        }
        return ZERO_TINY;
    }

    public static int getLargest() {
        int largest = ZERO_TINY.value;
        for (WingSize ls : WingSize.values()) {
            largest = Math.max(largest, ls.value);
        }
        return largest;
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
