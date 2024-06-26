package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * Arbitrary measurements in increments of 1, going from 0 to 7.
 *
 * @author Innoxia
 * @version 0.2.11
 * @since 0.1.0
 */
public enum HipSize {

    ZERO_NO_HIPS("completely straight", 0, PresetColour.GENERIC_SIZE_ONE),

    ONE_VERY_NARROW("very narrow", 1, PresetColour.GENERIC_SIZE_TWO),

    TWO_NARROW("narrow", 2, PresetColour.GENERIC_SIZE_THREE),

    THREE_GIRLY("girly", 3, PresetColour.GENERIC_SIZE_FOUR),

    FOUR_WOMANLY("womanly", 4, PresetColour.GENERIC_SIZE_FIVE),

    FIVE_VERY_WIDE("very wide", 5, PresetColour.GENERIC_SIZE_SIX),

    SIX_EXTREMELY_WIDE("extremely wide", 6, PresetColour.GENERIC_SIZE_SEVEN),

    SEVEN_ABSURDLY_WIDE("absurdly wide", 7, PresetColour.GENERIC_SIZE_EIGHT);


    private String descriptor;
    private int size;
    private Colour colour;

    private HipSize(String descriptor, int hipSize, Colour colour) {
        this.descriptor = descriptor;
        this.size = hipSize;
        this.colour = colour;
    }

    public static HipSize getHipSizeFromInt(int hipSize) {
        for (HipSize hs : HipSize.values()) {
            if (hipSize == hs.getValue()) {
                return hs;
            }
        }
        return SEVEN_ABSURDLY_WIDE;
    }

    /**
     * To fit into a sentence: "You have "+getDescriptor()+" hips."
     */
    public String getDescriptor() {
        if (Main.game.isSillyModeEnabled()) {
            switch (this) {
                case FIVE_VERY_WIDE:
                    return "thicc";
                case SIX_EXTREMELY_WIDE:
                    return "extremely thicc";
                case SEVEN_ABSURDLY_WIDE:
                    return "absurdly thicc";
                case ZERO_NO_HIPS:
                case ONE_VERY_NARROW:
                case TWO_NARROW:
                case THREE_GIRLY:
                case FOUR_WOMANLY:
                    break;
            }
        }
        return descriptor;
    }

    public int getValue() {
        return size;
    }

    public Colour getColour() {
        return colour;
    }
}
