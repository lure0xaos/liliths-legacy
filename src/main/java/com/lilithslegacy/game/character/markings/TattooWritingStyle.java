package com.lilithslegacy.game.character.markings;

/**
 * @author Innoxia
 * @version 0.2.6
 * @since 0.2.6
 */
public enum TattooWritingStyle {

    ITALICISED("italicised"),

    BOLD("bold");

    private final String name;

    TattooWritingStyle(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
