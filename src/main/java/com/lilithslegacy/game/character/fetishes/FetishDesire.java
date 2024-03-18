package com.lilithslegacy.game.character.fetishes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.utils.SvgUtil;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.utils.fs.FS;

/**
 * @author Innoxia
 * @version 0.2.9
 * @since 0.1.99
 */
public enum FetishDesire {

    ZERO_HATE(0, "hate", "hate", "hates", "fondness1", PresetColour.BASE_CRIMSON),

    ONE_DISLIKE(1, "dislike", "dislike", "dislikes", "fondness2", PresetColour.BASE_RED),

    TWO_NEUTRAL(2, "indifferent", "are indifferent to", "is indifferent to", "fondness3", PresetColour.BASE_BLUE_STEEL),

    THREE_LIKE(3, "like", "like", "likes", "fondness4", PresetColour.BASE_PINK_LIGHT),

    FOUR_LOVE(4, "love", "love", "loves", "fondness5", PresetColour.BASE_PINK);

    private final int value;
    private final String name;
    private final String nameAsPlayerVerb;
    private final String nameAsVerb;
    private String SVGImage;
    private String SVGImageDesaturated;
    private final Colour colour;
    private final List<String> modifiersList;

    FetishDesire(int value, String name, String nameAsPlayerVerb, String nameAsVerb, String pathName, Colour colour) {
        this.value = value;
        this.name = name;
        this.nameAsPlayerVerb = nameAsPlayerVerb;
        this.nameAsVerb = nameAsVerb;
        this.colour = colour;

        modifiersList = new ArrayList<>();
        modifiersList.add("Modifies sex actions' [style.boldLust(" + Util.capitaliseSentence(Attribute.LUST.getAbbreviatedName()) + " increments)]");


        try {
            InputStream is = FS.newResourceAsStream("/com/lilithslegacy/res/fetishes/" + pathName + ".svg");
            if (is == null) {
                System.getLogger("").log(System.Logger.Level.ERROR, "Error! FetishDesire icon file does not exist (Trying to read from '" + pathName + "')!");
            }
            String base = Util.inputStreamToString(is);

            SVGImage = base;

            SVGImage = SvgUtil.colourReplacement(this.toString(), colour, SVGImage);

            SVGImageDesaturated = base;

            SVGImageDesaturated = SvgUtil.colourReplacement(this.toString(), PresetColour.BASE_GREY, SVGImageDesaturated);

            is.close();

        } catch (IOException e) {
            System.getLogger("").log(System.Logger.Level.ERROR, e.getMessage(), e);
        }
    }

    public static int getCostToChange() {
        return 0;
    }

    public static FetishDesire getDesireFromValue(int value) {
        for (FetishDesire desire : FetishDesire.values()) {
            if (desire.getValue() == value) {
                return desire;
            }
        }

        if (value <= ZERO_HATE.getValue()) {
            return ZERO_HATE;
        } else {
            return FOUR_LOVE;
        }
    }

    public boolean isNegative() {
        return this == ZERO_HATE || this == ONE_DISLIKE;
    }

    public boolean isPositive() {
        return this == THREE_LIKE || this == FOUR_LOVE;
    }

    public FetishDesire getPreviousDesire() {
        switch (this) {
            case ZERO_HATE:
                return ZERO_HATE;
            case ONE_DISLIKE:
                return ZERO_HATE;
            case TWO_NEUTRAL:
                return ONE_DISLIKE;
            case THREE_LIKE:
                return TWO_NEUTRAL;
            case FOUR_LOVE:
                return THREE_LIKE;
        }
        return TWO_NEUTRAL;
    }

    public FetishDesire getNextDesire() {
        switch (this) {
            case ZERO_HATE:
                return ONE_DISLIKE;
            case ONE_DISLIKE:
                return TWO_NEUTRAL;
            case TWO_NEUTRAL:
                return THREE_LIKE;
            case THREE_LIKE:
                return FOUR_LOVE;
            case FOUR_LOVE:
                return FOUR_LOVE;
        }
        return TWO_NEUTRAL;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public String getNameAsPlayerVerb() {
        return nameAsPlayerVerb;
    }

    public String getNameAsVerb() {
        return nameAsVerb;
    }

    public String getSVGImage() {
        return SVGImage;
    }

    public String getSVGImageDesaturated() {
        return SVGImageDesaturated;
    }

    public Colour getColour() {
        return colour;
    }

    public List<String> getModifiersAsStringList() {
        return modifiersList;
    }

}
