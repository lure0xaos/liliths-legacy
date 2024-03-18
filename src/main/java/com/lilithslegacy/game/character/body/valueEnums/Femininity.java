package com.lilithslegacy.game.character.body.valueEnums;

import java.util.List;

import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.4.2
 * @since 0.1.0
 */
public enum Femininity {

    MASCULINE_STRONG(Util.newArrayListOfValues("very masculine", "manly"), 0, 19, PresetColour.MASCULINE_PLUS, PresetColour.MASCULINE_PLUS_NPC),

    MASCULINE(Util.newArrayListOfValues("masculine", "boyish"), 20, 39, PresetColour.MASCULINE, PresetColour.MASCULINE_NPC),

    ANDROGYNOUS(Util.newArrayListOfValues("androgynous"), 40, 59, PresetColour.ANDROGYNOUS, PresetColour.ANDROGYNOUS_NPC),

    FEMININE(Util.newArrayListOfValues("feminine", "girly"), 60, 79, PresetColour.FEMININE, PresetColour.FEMININE_NPC),

    FEMININE_STRONG(Util.newArrayListOfValues("very feminine", "womanly"), 80, 100, PresetColour.FEMININE_PLUS, PresetColour.FEMININE_PLUS_NPC);

    private final List<String> names;
    private final int minimumFemininity;
    private final int maximumFemininity;
    private final Colour colour;
    private final Colour speechColour;

    Femininity(List<String> names, int minimumFemininity, int maximumFemininity, Colour colour, Colour speechColour) {
        this.names = names;
        this.minimumFemininity = minimumFemininity;
        this.maximumFemininity = maximumFemininity;
        this.colour = colour;
        this.speechColour = speechColour;
    }

    public static Femininity valueOf(int femininity) {
        for (Femininity f : Femininity.values()) {
            if (femininity >= f.getMinimumFemininity() && femininity <= f.getMaximumFemininity()) {
                return f;
            }
        }
        return FEMININE_STRONG;
    }

    public static String getFemininityName(int femininity, boolean withDeterminer) {
        return valueOf(femininity).getName(withDeterminer);
    }

    public String getName(boolean withDeterminer) {
        String name = Util.randomItemFrom(names);

        if (withDeterminer) {
            return UtilText.generateSingularDeterminer(name) + " " + name;
        } else {
            return name;
        }
    }

    public List<String> getNames() {
        return names;
    }

    public int getMinimumFemininity() {
        return minimumFemininity;
    }

    public int getMaximumFemininity() {
        return maximumFemininity;
    }

    public int getMedianFemininity() {
        return minimumFemininity + ((maximumFemininity - minimumFemininity) / 2);
    }

    public Colour getColour() {
        return colour;
    }

    public Colour getSpeechColour() {
        return speechColour;
    }

    public boolean isFeminine() {
        return this != MASCULINE && this != MASCULINE_STRONG;
    }
}
