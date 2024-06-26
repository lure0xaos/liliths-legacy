package com.lilithslegacy.game.character.markings;

/**
 * @author Innoxia
 * @version 0.2.6
 * @since 0.2.6
 */
public enum ScarType {

    CLAW_MARKS("claw mark", "claw marks", true),

    BURNS("burn mark", "burn marks", false),

    STRAIGHT_SCAR("straight scar", "straight scars", false),

    JAGGED_SCAR("jagged scar", "jagged scars", false),

    BRUISE("bruise", "bruises", false);

    private String name;
    private String namePlural;
    private boolean alwaysPlural;

    private ScarType(String name, String namePlural, boolean alwaysPlural) {
        this.name = name;
        this.namePlural = namePlural;
        this.alwaysPlural = alwaysPlural;
    }

    public String getName() {
        return name;
    }

    public String getNamePlural() {
        return namePlural;
    }

    public boolean isAlwaysPlural() {
        return alwaysPlural;
    }

    public static ScarType getScarTypeFromString(String value) {
        if (value == "BRUIS") {
            return BRUISE;
        }
        return ScarType.valueOf(value);
    }
}
