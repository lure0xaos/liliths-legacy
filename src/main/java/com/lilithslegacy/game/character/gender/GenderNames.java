package com.lilithslegacy.game.character.gender;

/**
 * @author Innoxia
 * @version 0.1.97
 * @since 0.1.86
 */
public enum GenderNames {

    Y_PENIS_Y_VAGINA_Y_BREASTS(true, true, true, "futanari", "hermaphrodite", "hermaphrodite"),
    Y_PENIS_Y_VAGINA_N_BREASTS(true, true, false, "futanari", "hermaphrodite", "hermaphrodite"),
    Y_PENIS_N_VAGINA_Y_BREASTS(true, false, true, "shemale", "shemale", "busty-boy"),
    Y_PENIS_N_VAGINA_N_BREASTS(true, false, false, "trap", "trap", "male"),
    N_PENIS_Y_VAGINA_Y_BREASTS(false, true, true, "female", "tomboy", "butch"),
    N_PENIS_Y_VAGINA_N_BREASTS(false, true, false, "female", "tomboy", "cuntboy"),
    N_PENIS_N_VAGINA_Y_BREASTS(false, false, true, "mannequin", "neuter", "mannequin"),
    N_PENIS_N_VAGINA_N_BREASTS(false, false, false, "mannequin", "neuter", "mannequin");


    private final boolean hasPenis;
    private final boolean hasVagina;
    private final boolean hasBreasts;
    private final String feminine;
    private final String masculine;
    private final String neutral;

    GenderNames(boolean hasPenis, boolean hasVagina, boolean hasBreasts, String feminine, String neutral, String masculine) {
        this.hasPenis = hasPenis;
        this.hasVagina = hasVagina;
        this.hasBreasts = hasBreasts;
        this.feminine = feminine;
        this.neutral = neutral;
        this.masculine = masculine;
    }

    public boolean isHasPenis() {
        return hasPenis;
    }

    public boolean isHasVagina() {
        return hasVagina;
    }

    public boolean isHasBreasts() {
        return hasBreasts;
    }

    public String getFeminine() {
        return feminine;
    }

    public String getMasculine() {
        return masculine;
    }

    public String getNeutral() {
        return neutral;
    }
}
