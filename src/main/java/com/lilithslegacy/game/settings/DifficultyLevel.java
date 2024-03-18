package com.lilithslegacy.game.settings;

import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.95
 * @since 0.1.95
 */
public enum DifficultyLevel {

    NORMAL("Human", "The way the game is meant to be played. No level scaling and no damage modifications.", PresetColour.RACE_HUMAN, false, 1, 1),

    LEVEL_SCALING("Morph", "Enemies level up alongside your character, but do normal damage.", PresetColour.RACE_CAT_MORPH, true, 1, 1),

    HARD("Demon", "Enemies level up alongside your character and do 200% damage.", PresetColour.RACE_DEMON, true, 2, 1),

    NIGHTMARE("Lilin", "Enemies level up alongside your character, do 200% damage, and take only 50% damage from all sources.", PresetColour.BASE_PURPLE, true, 2, 0.5f),

    HELL("Lilith", "Enemies are always 2x your character's level, do 400% damage, and take only 25% damage from all sources. Be prepared to lose. A lot.", PresetColour.BASE_CRIMSON, true, 4, 0.25f);

    private final String name;
    private final String description;
    private final Colour colour;
    private final boolean isNPCLevelScaling;
    private final float damageModifierNPC;
    private final float damageModifierPlayer;

    DifficultyLevel(String name, String description, Colour colour, boolean isNPCLevelScaling, float damageModifierNPC, float damageModifierPlayer) {
        this.name = name;
        this.description = description;
        this.colour = colour;
        this.isNPCLevelScaling = isNPCLevelScaling;
        this.damageModifierNPC = damageModifierNPC;
        this.damageModifierPlayer = damageModifierPlayer;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Colour getColour() {
        return colour;
    }

    public boolean isNPCLevelScaling() {
        return isNPCLevelScaling;
    }

    public float getDamageModifierNPC() {
        return damageModifierNPC;
    }

    public float getDamageModifierPlayer() {
        return damageModifierPlayer;
    }
}
