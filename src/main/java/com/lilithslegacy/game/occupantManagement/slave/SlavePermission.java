package com.lilithslegacy.game.occupantManagement.slave;

import java.util.List;

import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.3.9.2
 * @since 0.1.87
 */
public enum SlavePermission {


    BEHAVIOUR(PresetColour.BASE_TEAL,
            "Behaviour",
            Util.newArrayListOfValues(
                    SlavePermissionSetting.BEHAVIOUR_SLUTTY,
                    SlavePermissionSetting.BEHAVIOUR_SEDUCTIVE,
                    SlavePermissionSetting.BEHAVIOUR_STANDARD,
                    SlavePermissionSetting.BEHAVIOUR_PROFESSIONAL,
                    SlavePermissionSetting.BEHAVIOUR_WHOLESOME),
            true),

    GENERAL(PresetColour.TRANSFORMATION_GENERIC,
            "General",
            Util.newArrayListOfValues(
                    SlavePermissionSetting.GENERAL_SILENCE,
                    SlavePermissionSetting.GENERAL_CRAWLING,
                    SlavePermissionSetting.GENERAL_HOUSE_FREEDOM,
                    SlavePermissionSetting.GENERAL_OUTSIDE_FREEDOM),
            false),

    SEX(PresetColour.GENERIC_SEX,
            "Sex",
            Util.newArrayListOfValues(
                    SlavePermissionSetting.SEX_MASTURBATE,
                    SlavePermissionSetting.SEX_INITIATE_SLAVES,
                    SlavePermissionSetting.SEX_INITIATE_PLAYER,
                    SlavePermissionSetting.SEX_RECEIVE_SLAVES,
                    SlavePermissionSetting.SEX_SAVE_VIRGINITY,
                    SlavePermissionSetting.SEX_IMPREGNATED,
                    SlavePermissionSetting.SEX_IMPREGNATE),
            false),

    PILLS(PresetColour.BASE_PURPLE_LIGHT,
            "Pills",
            Util.newArrayListOfValues(
                    SlavePermissionSetting.PILLS_PROMISCUITY_PILLS,
                    SlavePermissionSetting.PILLS_NO_PILLS,
                    SlavePermissionSetting.PILLS_VIXENS_VIRILITY,
                    SlavePermissionSetting.PILLS_BROODMOTHER),
            true),

    PREGNANCY(PresetColour.BASE_PINK,
            "Pregnancy",
            Util.newArrayListOfValues(
                    SlavePermissionSetting.PREGNANCY_MOTHERS_MILK,
                    SlavePermissionSetting.PREGNANCY_ALLOW_BIRTHING,
                    SlavePermissionSetting.PREGNANCY_ALLOW_EGG_LAYING),
            false),

    DIET(PresetColour.BODY_SIZE_TWO,
            "Diet",
            Util.newArrayListOfValues(
                    SlavePermissionSetting.FOOD_DIET_EXTREME,
                    SlavePermissionSetting.FOOD_DIET,
                    SlavePermissionSetting.FOOD_NORMAL,
                    SlavePermissionSetting.FOOD_PLUS,
                    SlavePermissionSetting.FOOD_LAVISH),
            true),

    EXERCISE(PresetColour.MUSCLE_TWO,
            "Exercise",
            Util.newArrayListOfValues(
                    SlavePermissionSetting.EXERCISE_FORBIDDEN,
                    SlavePermissionSetting.EXERCISE_REST,
                    SlavePermissionSetting.EXERCISE_NORMAL,
                    SlavePermissionSetting.EXERCISE_TRAINING,
                    SlavePermissionSetting.EXERCISE_BODY_BUILDING),
            true),

    CLEANLINESS(PresetColour.BASE_BLUE_LIGHT,
            "Cleanliness",
            Util.newArrayListOfValues(
                    SlavePermissionSetting.CLEANLINESS_WASH_CLOTHES,
                    SlavePermissionSetting.CLEANLINESS_WASH_BODY),
            false),

    SLEEPING(PresetColour.BASE_PURPLE_LIGHT,
            "Sleeping",
            Util.newArrayListOfValues(
                    SlavePermissionSetting.SLEEPING_DEFAULT,
                    SlavePermissionSetting.SLEEPING_NIGHT,
                    SlavePermissionSetting.SLEEPING_DAY),
            true);

    private final Colour colour;
    private final String name;
    private final List<SlavePermissionSetting> settings;
    private final boolean mutuallyExclusiveSettings;

    SlavePermission(Colour colour, String name, List<SlavePermissionSetting> settings, boolean mutuallyExclusiveSettings) {
        this.colour = colour;
        this.name = name;
        this.settings = settings;
        this.mutuallyExclusiveSettings = mutuallyExclusiveSettings;
    }

    public Colour getColour() {
        return colour;
    }

    public String getName() {
        return name;
    }

    public List<SlavePermissionSetting> getSettings() {
        return settings;
    }

    public boolean isMutuallyExclusiveSettings() {
        return mutuallyExclusiveSettings;
    }

}
