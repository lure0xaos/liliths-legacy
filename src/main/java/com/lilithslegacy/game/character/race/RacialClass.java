package com.lilithslegacy.game.character.race;

/**
 * @author Innoxia
 * @version 0.4.0
 * @since 0.4
 */
public enum RacialClass {

    MAMMAL(true, true),
    BIRD(true, true),
    REPTILE(false, false),
    AMPHIBIAN(false, false),
    FISH(false, false),
    INSECT(false, false),

    OTHER(true, true); // 'OTHER' could be things like slimes or elementals

    private final boolean anthroHair;
    private final boolean anthroBreasts;

    RacialClass(boolean anthroHair, boolean anthroBreasts) {
        this.anthroHair = anthroHair;
        this.anthroBreasts = anthroBreasts;
    }

    public boolean isAnthroHair() {
        return anthroHair;
    }

    public boolean isAnthroBreasts() {
        return anthroBreasts;
    }
}
