package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.83
 * @since 0.1.83
 */
public enum Muscle {

    ZERO_SOFT("soft", 0, 20, PresetColour.MUSCLE_ZERO),
    ONE_LIGHTLY_MUSCLED("lightly muscled", 20, 40, PresetColour.MUSCLE_ONE),
    TWO_TONED("toned", 40, 60, PresetColour.MUSCLE_TWO),
    THREE_MUSCULAR("muscular", 60, 80, PresetColour.MUSCLE_THREE),
    FOUR_RIPPED("ripped", 80, 100, PresetColour.MUSCLE_FOUR);

    private final String name;
    private final int minimumMuscle;
    private final int maximumMuscle;
    private final Colour colour;

    Muscle(String name, int minimumMuscle, int maximumMuscle, Colour colour) {
        this.name = name;
        this.minimumMuscle = minimumMuscle;
        this.maximumMuscle = maximumMuscle;
        this.colour = colour;
    }

    public static Muscle valueOf(int muscle) {
        for (Muscle f : Muscle.values()) {
            if (muscle >= f.getMinimumValue() && muscle < f.getMaximumValue()) {
                return f;
            }
        }
        return FOUR_RIPPED;
    }

    public int getMinimumValue() {
        return minimumMuscle;
    }

    public int getMaximumValue() {
        return maximumMuscle;
    }

    public int getMedianValue() {
        return minimumMuscle + (maximumMuscle - minimumMuscle) / 2;
    }

    public String getName(boolean withDeterminer) {
        if (withDeterminer) {
            return UtilText.generateSingularDeterminer(name) + " " + name;
        } else {
            return name;
        }
    }

    public Colour getColour() {
        return colour;
    }
}
