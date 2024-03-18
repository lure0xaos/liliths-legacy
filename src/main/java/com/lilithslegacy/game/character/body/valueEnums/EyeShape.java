package com.lilithslegacy.game.character.body.valueEnums;

/**
 * @author Innoxia
 * @version 0.1.83
 * @since 0.1.83
 */
public enum EyeShape {

    ROUND("round"),
    HORIZONTAL("horizontal"),
    VERTICAL("vertical"),
    HEART("heart-shaped"),
    STAR("star-shaped");

    private final String descriptor;

    EyeShape(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getName() {
        return descriptor;
    }
}
