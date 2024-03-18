package com.lilithslegacy.game.character.race;

import com.lilithslegacy.rendering.SVGImages;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

/**
 * @author Innoxia
 * @version 0.1.99
 * @since 0.1.78
 */
public enum FurryPreference {

    /**
     * No furry parts at all. (NPCs will spawn as regular humans.)
     */
    HUMAN("Disabled", PresetColour.TRANSFORMATION_HUMAN, "X") {
        @Override
        public String getDescriptionFeminine(AbstractSubspecies subspecies) {
            return "Feminine " + subspecies.getNamePlural(null) + " will be completely disabled in random encounters. If all feminine preferences are set to 'Disabled', random encounters will default to feminine humans.";
        }

        @Override
        public String getDescriptionMasculine(AbstractSubspecies subspecies) {
            return "Masculine " + subspecies.getNamePlural(null) + " will be completely disabled in random encounters. If all masculine preferences are set to 'Disabled', random encounters will default to masculine humans.";
        }

        @Override
        public String getSVGImage(boolean disabled) {
            return disabled ? SVGImages.SVG_IMAGE_PROVIDER.getScaleZeroDisabled() : SVGImages.SVG_IMAGE_PROVIDER.getScaleZero();
        }
    },

    /**
     * NPCs will spawn with furry ears and eyes, and where applicable, furry tails, horns, antenna, and wings. They will <b>not</b> spawn with furry breasts or genitalia.
     */
    MINIMUM("Minimum", PresetColour.TRANSFORMATION_PARTIAL, "--") {
        @Override
        public String getDescriptionFeminine(AbstractSubspecies subspecies) {
            return "Feminine " + subspecies.getNamePlural(null) + " will spawn with furry ears and eyes, and where applicable, furry tails, horns, antenna, and wings. They will <b>not</b> spawn with furry breasts or genitalia.";
        }

        @Override
        public String getDescriptionMasculine(AbstractSubspecies subspecies) {
            return "Masculine " + subspecies.getNamePlural(null) + " will spawn with furry ears and eyes, and where applicable, furry tails, horns, antenna, and wings. They will <b>not</b> spawn with furry breasts or genitalia.";
        }

        @Override
        public String getSVGImage(boolean disabled) {
            return disabled ? SVGImages.SVG_IMAGE_PROVIDER.getScaleOneDisabled() : SVGImages.SVG_IMAGE_PROVIDER.getScaleOne();
        }
    },

    /**
     * NPCs will spawn with all of the furry parts that the 'Minimum' setting enables (ears, eyes, tails, horns, antenna, and wings). They also have the chance to spawn with furry breasts, genitalia, arms, and legs.
     */
    REDUCED("Lesser", PresetColour.TRANSFORMATION_PARTIAL_FULL, "-") {
        @Override
        public String getDescriptionFeminine(AbstractSubspecies subspecies) {
            return "Feminine " + subspecies.getNamePlural(null) + " will spawn with all of the furry parts that the 'Minimum' setting enables (ears, eyes, tails, horns, antenna, and wings)."
                    + " They also have the chance to spawn with furry breasts, genitalia, arms, and legs.";
        }

        @Override
        public String getDescriptionMasculine(AbstractSubspecies subspecies) {
            return "Masculine " + subspecies.getNamePlural(null) + " will spawn with all of the furry parts that the 'Minimum' setting enables (ears, eyes, tails, horns, antenna, and wings)."
                    + " They also have the chance to spawn with furry breasts, genitalia, arms, and legs.";
        }

        @Override
        public String getSVGImage(boolean disabled) {
            return disabled ? SVGImages.SVG_IMAGE_PROVIDER.getScaleTwoDisabled() : SVGImages.SVG_IMAGE_PROVIDER.getScaleTwo();
        }
    },

    /**
     * NPCs will spawn with all of the furry parts that the 'Minimum' setting enables (ears, eyes, tails, horns, antenna, and wings). They also have the chance to spawn with furry breasts, genitalia, arms, legs, skin/fur, and faces.
     */
    NORMAL("Greater", PresetColour.TRANSFORMATION_LESSER, "+") {
        @Override
        public String getDescriptionFeminine(AbstractSubspecies subspecies) {
            return "Feminine " + subspecies.getNamePlural(null) + " will spawn with all of the furry parts that the 'Minimum' setting enables (ears, eyes, tails, horns, antenna, and wings)."
                    + " They also have the chance to spawn with furry breasts, genitalia, arms, legs, skin/fur, and faces.";
        }

        @Override
        public String getDescriptionMasculine(AbstractSubspecies subspecies) {
            return "Masculine " + subspecies.getNamePlural(null) + " will spawn with all of the furry parts that the 'Minimum' setting enables (ears, eyes, tails, horns, antenna, and wings)."
                    + " They also have the chance to spawn with furry breasts, genitalia, arms, legs, skin/fur, and faces.";
        }

        @Override
        public String getSVGImage(boolean disabled) {
            return disabled ? SVGImages.SVG_IMAGE_PROVIDER.getScaleThreeDisabled() : SVGImages.SVG_IMAGE_PROVIDER.getScaleThree();
        }
    },

    /**
     * NPCs will <b>always</b> spawn with as many furry parts as is possible (ears, eyes, tails, horns, antenna, wings, breasts, genitalia, arms, legs, skin/fur, and face).
     */
    MAXIMUM("Maximum", PresetColour.TRANSFORMATION_GREATER, "++") {
        @Override
        public String getDescriptionFeminine(AbstractSubspecies subspecies) {
            return "Feminine " + subspecies.getNamePlural(null) + " will <b>always</b> spawn with as many furry parts as is possible (ears, eyes, tails, horns, antenna, wings, breasts, genitalia, arms, legs, skin/fur, and face).";
        }

        @Override
        public String getDescriptionMasculine(AbstractSubspecies subspecies) {
            return "Masculine " + subspecies.getNamePlural(null) + " will <b>always</b> spawn with as many furry parts as is possible (ears, eyes, tails, horns, antenna, wings, breasts, genitalia, arms, legs, skin/fur, and face).";
        }

        @Override
        public String getSVGImage(boolean disabled) {
            return disabled ? SVGImages.SVG_IMAGE_PROVIDER.getScaleFourDisabled() : SVGImages.SVG_IMAGE_PROVIDER.getScaleFour();
        }
    };

    private final String name;
    private final Colour colour;
    private final String buttonText;

    FurryPreference(String name, Colour colour, String buttonText) {
        this.name = name;
        this.colour = colour;
        this.buttonText = buttonText;
    }

    public abstract String getSVGImage(boolean disabled);

    public String getName() {
        return name;
    }

    public Colour getColour() {
        return colour;
    }

    public String getButtonText() {
        return buttonText;
    }

    public abstract String getDescriptionFeminine(AbstractSubspecies subspecies);

    public abstract String getDescriptionMasculine(AbstractSubspecies subspecies);
}
