package com.lilithslegacy.game.character.fetishes;

/**
 * @author Innoxia
 * @version 0.2.12
 * @since 0.2.12
 */
public enum FetishExperience {

    BASE_EXPERIENCE_GAIN(2),
    BASE_RARE_EXPERIENCE_GAIN(10),
    BASE_VERY_RARE_EXPERIENCE_GAIN(25);

    int experience;

    private FetishExperience(int experience) {
        this.experience = experience;
    }

    public int getExperience() {
        return experience;
    }
}
