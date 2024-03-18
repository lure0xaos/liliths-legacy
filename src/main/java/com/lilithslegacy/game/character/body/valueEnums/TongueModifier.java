package com.lilithslegacy.game.character.body.valueEnums;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.1.83
 */
public enum TongueModifier {

    RIBBED("ribbed"),

    TENTACLED("tentacled"),

    BIFURCATED("bifurcated"),

    WIDE("wide"),

    FLAT("flat"),

    STRONG("strong"),

    TAPERED("tapered");

    private final String descriptor;

    TongueModifier(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getName() {
        return descriptor;
    }
}
