package com.lilithslegacy.game.character.attributes;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.95
 * @since 0.1.95
 */
public enum AffectionLevelBasic {

    /**
     * -100 to -30
     */
    DISLIKE("dislikes", -100, -30, PresetColour.AFFECTION_NEGATIVE_TWO),

    /**
     * -30 to 30
     */
    NEUTRAL("neutral", -30, 30, PresetColour.AFFECTION_POSITIVE_ONE),

    /**
     * 30 to 100
     */
    LIKE("likes", 30, 100, PresetColour.AFFECTION_POSITIVE_FIVE);


    private final String name;
    private final int minimumValue;
    private final int maximumValue;
    private final Colour colour;

    AffectionLevelBasic(String name, int minimumValue, int maximumValue, Colour colour) {
        this.name = name;
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
        this.colour = colour;
    }

    public static AffectionLevelBasic getAffectionLevelFromValue(float value) {
        for (AffectionLevelBasic al : AffectionLevelBasic.values()) {
            if (value >= al.getMinimumValue() && value < al.getMaximumValue())
                return al;
        }
        return LIKE;
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
}
