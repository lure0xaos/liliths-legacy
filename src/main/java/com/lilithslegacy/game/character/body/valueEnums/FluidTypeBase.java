package com.lilithslegacy.game.character.body.valueEnums;

import java.util.List;

import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.3.8.2
 * @since 0.2.0
 */
public enum FluidTypeBase {

    CUM(Util.newArrayListOfValues("cum", "cream", "jism", "jizz", "seed", "spooge"),
            BodyCoveringType.CUM,
            PresetColour.CUM),

    GIRLCUM(Util.newArrayListOfValues("girlcum"),
            BodyCoveringType.GIRL_CUM,
            PresetColour.GIRLCUM),

    MILK(Util.newArrayListOfValues("milk"),
            BodyCoveringType.MILK,
            PresetColour.MILK);

    private final List<String> names;
    private final AbstractBodyCoveringType coveringType;
    private final Colour colour;

    FluidTypeBase(List<String> names, AbstractBodyCoveringType coveringType, Colour colour) {
        this.names = names;
        this.coveringType = coveringType;
        this.colour = colour;
    }

    public List<String> getNames() {
        return names;
    }

    public AbstractBodyCoveringType getCoveringType() {
        return coveringType;
    }

    public Colour getColour() {
        return colour;
    }
}
