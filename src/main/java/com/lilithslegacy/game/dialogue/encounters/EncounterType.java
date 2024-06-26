package com.lilithslegacy.game.dialogue.encounters;

/**
 * @author Innoxia, DSG
 * @version 0.3.21
 * @since 0.1.69.9
 */
public enum EncounterType {

    SPECIAL_DOMINION_CULTIST,

    SLAVE_USES_YOU(true),
    SLAVE_USING_OTHER_SLAVE(false),

    // Dominion:

    DOMINION_STREET_FIND_HAPPINESS, // Kinariu
    DOMINION_STREET_RENTAL_MOMMY,
    DOMINION_STREET_PILL_HANDOUT,

    DOMINION_FIND_ITEM,
    DOMINION_FIND_CLOTHING,
    DOMINION_FIND_WEAPON,

    DOMINION_ALLEY_ENFORCERS,
    DOMINION_ALLEY_ATTACK(true),
    DOMINION_STORM_ATTACK(true),

    DOMINION_EXPRESS_CENTAUR,

    WES_QUEST_START,

    DOMINION_PARK_NATALYA,


    // Harpy nests:

    HARPY_NEST_ATTACK(true),
    //    HARPY_NEST_ATTACK_STORM(true),
    HARPY_NEST_FIND_ITEM,


    // Submission:

    SUBMISSION_TUNNEL_ATTACK(true),
    SUBMISSION_FIND_ITEM,

    BAT_CAVERN_LURKER_ATTACK(true),
    BAT_CAVERN_SLIME_ATTACK(true),
    BAT_CAVERN_FIND_ITEM,
    BAT_CAVERN_REBEL_BASE_DISCOVERED,
    BAT_CAVERN_REBEL_PASSWORD_ONE,
    BAT_CAVERN_REBEL_PASSWORD_TWO,

    REBEL_BASE_INSANE_SURVIVOR_ATTACK,

    VENGAR_CAPTIVE_SERVE,
    VENGAR_CAPTIVE_GROPED,
    VENGAR_CAPTIVE_VENGAR_FUCK,
    VENGAR_CAPTIVE_RAT_FUCK,
    VENGAR_CAPTIVE_ORAL_UNDER_TABLE,
    VENGAR_CAPTIVE_GROUP_SEX,

    VENGAR_CAPTIVE_CLEAN_ROOM,
    VENGAR_CAPTIVE_SHADOW_SILENCE_DOMINATE,
    VENGAR_CAPTIVE_ROOM_BARRED;

    private boolean opportunistic;

    EncounterType() {
        opportunistic = false;
    }

    EncounterType(boolean opportunistic) {
        this.opportunistic = opportunistic;
    }

    public boolean isOpportunistic() {
        return opportunistic;
    }
}
