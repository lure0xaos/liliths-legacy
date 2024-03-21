package com.lilithslegacy.utils.time;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * https://en.wikipedia.org/wiki/Twilight
 *
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.3.5.5
 */
public enum DayPeriod {

    DAY("day-time", PresetColour.BASE_BLUE_LIGHT),

    CIVIL_TWILIGHT("civil twilight", PresetColour.BASE_PURPLE_LIGHT),

    NAUTICAL_TWILIGHT("nautical twilight", PresetColour.BASE_PURPLE),

    ASTRONOMICAL_TWILIGHT("astronomical twilight", PresetColour.BASE_BLUE_STEEL),

    NIGHT("night-time", PresetColour.BASE_BLUE_DARK);


    private String name;
    private Colour colour;

    private DayPeriod(String name, Colour colour) {
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
