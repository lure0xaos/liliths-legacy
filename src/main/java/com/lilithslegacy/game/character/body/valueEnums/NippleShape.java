package com.lilithslegacy.game.character.body.valueEnums;

/**
 * @author Innoxia
 * @version 0.3.21
 * @since 0.1.83
 */
public enum NippleShape {

    NORMAL("normal", false),

    INVERTED("inverted", false),

    VAGINA("nipple-cunts", true),

    LIPS("lipples", true);

    private final String descriptor;
    private final boolean associatedWithPenetrationContent;

    NippleShape(String descriptor, boolean associatedWithPenetrationContent) {
        this.descriptor = descriptor;
        this.associatedWithPenetrationContent = associatedWithPenetrationContent;
    }

    public String getName() {
        return descriptor;
    }

    public boolean isAssociatedWithPenetrationContent() {
        return associatedWithPenetrationContent;
    }

}
