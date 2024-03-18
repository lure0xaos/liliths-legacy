package com.lilithslegacy.game.dialogue;

/**
 * @author Innoxia
 * @version 0.3.7.3
 * @since 0.1.0
 */
public enum DialogueNodeType {

    NORMAL("normal"),

    STATUS_EFFECT_MESSAGE("statusEffectMessage"),

    INVENTORY("inventory"),

    PHONE("phone"),

    CHARACTERS_PRESENT("characters present"),

    OCCUPANT_MANAGEMENT("character management"),

    OPTIONS("options"),

    GIFT("gifts");

    private final String name;

    DialogueNodeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
