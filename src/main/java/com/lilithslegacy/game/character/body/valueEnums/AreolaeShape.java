package com.lilithslegacy.game.character.body.valueEnums;

/**
 * @author Innoxia
 * @version 0.1.83
 * @since 0.1.83
 */
public enum AreolaeShape {

    NORMAL("normal"),
    HEART("heart"),
    STAR("star");

    private String descriptor;

    private AreolaeShape(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getName() {
        return descriptor;
    }
}
