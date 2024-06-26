package com.lilithslegacy.game.sex;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.3.8.8
 * @since 0.1.6?
 */
public enum LubricationType {

    SALIVA("saliva", "saliva", false, PresetColour.BASE_BLUE_LIGHT),

    MILK("[npc.milk]", "milk", false, PresetColour.MILK),

    PRECUM("precum", "precum", false, PresetColour.CUM),

    CUM("[npc.cum]", "cum", false, PresetColour.CUM),

    GIRLCUM("girlcum", "girlcum", false, PresetColour.GIRLCUM),

    ANAL_LUBE("anal lubricant", "anal lubricant", false, PresetColour.BASE_BLUE_LIGHT), // This is only present if the anus has been transformed to be 'wetter' than usual

    SLIME("slime", "slime", false, PresetColour.RACE_SLIME),

    WATER("water", "water", false, PresetColour.BASE_AQUA),

    OTHER("lubrication", "lubrication", false, PresetColour.BASE_BLUE_LIGHT);

    private String name;
    private String nullOwnerName;
    private boolean plural;
    private Colour colour;

    private LubricationType(String name, String nullOwnerName, boolean plural, Colour colour) {
        this.name = name;
        this.nullOwnerName = nullOwnerName;
        this.plural = plural;
        this.colour = colour;
    }

    public boolean isPlural() {
        return plural;
    }

    public String getName(GameCharacter owner) {
        if (owner == null) {
            return nullOwnerName;
        }
        return UtilText.parse(owner, name);
    }

    ;

    public Colour getColour() {
        return colour;
    }
}
