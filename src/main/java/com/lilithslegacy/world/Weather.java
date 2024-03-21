package com.lilithslegacy.world;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.1.0
 */
public enum Weather {

    CLOUD("cloudy", PresetColour.BASE_GREY),

    CLEAR("clear", PresetColour.BASE_YELLOW_PALE),

    RAIN("raining", PresetColour.BASE_BLUE),

    SNOW("snowing", PresetColour.BASE_BLUE_LIGHT),

    MAGIC_STORM_GATHERING("stormy sky", PresetColour.GENERIC_ARCANE),

    MAGIC_STORM("arcane storm", PresetColour.GENERIC_ARCANE);

    private String name;
    private Colour colour;

    private Weather(String name, Colour colour) {
        this.name = name;
        this.colour = colour;
    }

    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }
}
