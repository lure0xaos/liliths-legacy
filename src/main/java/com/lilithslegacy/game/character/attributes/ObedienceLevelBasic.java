package com.lilithslegacy.game.character.attributes;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.95
 * @since 0.1.95
 */
public enum ObedienceLevelBasic {

    /**
     * -100 to -30
     */
    DISOBEDIENT("disobedient", -100, -30, PresetColour.AFFECTION_NEGATIVE_FIVE),

    /**
     * -30 to 30
     */
    NEUTRAL("neutral", -30, 30, PresetColour.AFFECTION_NEUTRAL),

    /**
     * 30 to 100
     */
    OBEDIENT("obedient", 30, 100, PresetColour.AFFECTION_POSITIVE_FIVE);


    private String name;
    private int minimumValue, maximumValue;
    private Colour colour;

    private ObedienceLevelBasic(String name, int minimumValue, int maximumValue, Colour colour) {
        this.name = name;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public int getMedianValue() {
        return (minimumValue + maximumValue) / 2;
    }

    public Colour getColour() {
        return colour;
    }

    public static ObedienceLevelBasic getObedienceLevelFromValue(float value) {
        for (ObedienceLevelBasic al : ObedienceLevelBasic.values()) {
            if (value >= al.getMinimumValue() && value < al.getMaximumValue())
                return al;
        }
        return OBEDIENT;
    }
}
