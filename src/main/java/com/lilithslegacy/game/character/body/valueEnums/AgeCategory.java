package com.lilithslegacy.game.character.body.valueEnums;

import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.settings.ContentPreferenceValue;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.2.11
 * @since 0.2.11
 */
public enum AgeCategory {

    // Always at least 18, as returned by valueOf()
    TEENS_LATE("late teens", 18, 20, PresetColour.AGE_TEENS, ContentPreferenceValue.FOUR_HIGH),

    TWENTIES_EARLY("early twenties", 20, 23, PresetColour.AGE_TWENTIES, ContentPreferenceValue.FIVE_ABUNDANT),

    TWENTIES_MIDDLE("mid-twenties", 23, 27, PresetColour.AGE_TWENTIES, ContentPreferenceValue.FIVE_ABUNDANT),

    TWENTIES_LATE("late twenties", 27, 30, PresetColour.AGE_TWENTIES, ContentPreferenceValue.FOUR_HIGH),

    THIRTIES_EARLY("early thirties", 30, 33, PresetColour.AGE_THIRTIES, ContentPreferenceValue.THREE_AVERAGE),

    THIRTIES_MIDDLE("mid-thirties", 33, 37, PresetColour.AGE_THIRTIES, ContentPreferenceValue.THREE_AVERAGE),

    THIRTIES_LATE("late thirties", 37, 40, PresetColour.AGE_THIRTIES, ContentPreferenceValue.TWO_LOW),

    FORTIES_EARLY("early forties", 40, 43, PresetColour.AGE_FORTIES, ContentPreferenceValue.TWO_LOW),

    FORTIES_MIDDLE("mid-forties", 43, 47, PresetColour.AGE_FORTIES, ContentPreferenceValue.ONE_MINIMAL),

    FORTIES_LATE("late forties", 47, 50, PresetColour.AGE_FORTIES, ContentPreferenceValue.ONE_MINIMAL),

    FIFTIES_EARLY("early fifties", 50, 53, PresetColour.AGE_FIFTIES, ContentPreferenceValue.ONE_MINIMAL),

    FIFTIES_MIDDLE("mid-fifties", 53, 57, PresetColour.AGE_FIFTIES, ContentPreferenceValue.ZERO_NONE),

    FIFTIES_LATE("late fifties", 57, 60, PresetColour.AGE_FIFTIES, ContentPreferenceValue.ZERO_NONE),

    SIXTIES_EARLY("early sixties", 60, 63, PresetColour.AGE_SIXTIES, ContentPreferenceValue.ZERO_NONE),

    SIXTIES_MIDDLE("mid-sixties", 63, 67, PresetColour.AGE_SIXTIES, ContentPreferenceValue.ZERO_NONE),

    SIXTIES_LATE("late sixties", 67, 70, PresetColour.AGE_SIXTIES, ContentPreferenceValue.ZERO_NONE),

    SIXTIES_PLUS("past seventy", 70, 100, PresetColour.AGE_SIXTIES, ContentPreferenceValue.ZERO_NONE);

    private final String name;
    private final int minimumAge;
    private final int maximumAge;
    private final Colour colour;
    private final ContentPreferenceValue agePreferenceDefault;

    AgeCategory(String name, int minimumAge, int maximumAge, Colour colour, ContentPreferenceValue agePreferenceDefault) {
        this.name = name;
        this.minimumAge = minimumAge;
        this.maximumAge = maximumAge;
        this.colour = colour;
        this.agePreferenceDefault = agePreferenceDefault;
    }

    public static AgeCategory valueOf(int age) {
        if (age < TEENS_LATE.getMinimumValue()) {
            return TEENS_LATE;
        }
        for (AgeCategory f : AgeCategory.values()) {
            if (age >= f.getMinimumValue() && age < f.getMaximumValue()) {
                return f;
            }
        }
        return SIXTIES_PLUS;
    }

    public static int getAgeFromPreferences(Gender gender) {
        AgeCategory category;
        try {
            category = Util.getRandomObjectFromWeightedMap(Main.getProperties().agePreferencesMap.get(gender.getType()));
        } catch (Exception ex) {
            category = AgeCategory.TWENTIES_MIDDLE;
        }
        if (category == null) {
            category = AgeCategory.TWENTIES_MIDDLE;
        }

        int lowerBound = category.getMinimumValue();
        int upperBound = category.getMaximumValue();
        return lowerBound + Util.random.nextInt(upperBound - lowerBound);
    }

    public int getMinimumValue() {
        return minimumAge;
    }

    public int getMaximumValue() {
        return maximumAge;
    }

    public int getMedianValue() {
        return minimumAge + (maximumAge - minimumAge) / 2;
    }

    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }

    public ContentPreferenceValue getAgePreferenceDefault() {
        return agePreferenceDefault;
    }
}