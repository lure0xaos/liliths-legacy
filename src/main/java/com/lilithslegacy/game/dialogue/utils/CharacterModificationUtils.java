package com.lilithslegacy.game.dialogue.utils;

import com.lilithslegacy.controller.eventListeners.tooltips.TooltipInformationEventListener;
import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.PropertyValue;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.AffectionLevel;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.character.attributes.ObedienceLevel;
import com.lilithslegacy.game.character.body.Antenna;
import com.lilithslegacy.game.character.body.Arm;
import com.lilithslegacy.game.character.body.Breast;
import com.lilithslegacy.game.character.body.BreastCrotch;
import com.lilithslegacy.game.character.body.Eye;
import com.lilithslegacy.game.character.body.Horn;
import com.lilithslegacy.game.character.body.Leg;
import com.lilithslegacy.game.character.body.Tail;
import com.lilithslegacy.game.character.body.Tentacle;
import com.lilithslegacy.game.character.body.Testicle;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractAntennaType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractArmType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractAssType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractBreastType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractEarType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractEyeType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractFaceType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractHairType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractHornType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractLegType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractPenisType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractTailType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractTorsoType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractVaginaType;
import com.lilithslegacy.game.character.body.abstractTypes.AbstractWingType;
import com.lilithslegacy.game.character.body.coverings.AbstractBodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.tags.BodyPartTag;
import com.lilithslegacy.game.character.body.types.AntennaType;
import com.lilithslegacy.game.character.body.types.ArmType;
import com.lilithslegacy.game.character.body.types.AssType;
import com.lilithslegacy.game.character.body.types.BreastType;
import com.lilithslegacy.game.character.body.types.EarType;
import com.lilithslegacy.game.character.body.types.EyeType;
import com.lilithslegacy.game.character.body.types.FaceType;
import com.lilithslegacy.game.character.body.types.HairType;
import com.lilithslegacy.game.character.body.types.HornType;
import com.lilithslegacy.game.character.body.types.LegType;
import com.lilithslegacy.game.character.body.types.PenisType;
import com.lilithslegacy.game.character.body.types.TailType;
import com.lilithslegacy.game.character.body.types.TorsoType;
import com.lilithslegacy.game.character.body.types.VaginaType;
import com.lilithslegacy.game.character.body.types.WingType;
import com.lilithslegacy.game.character.body.valueEnums.AreolaeSize;
import com.lilithslegacy.game.character.body.valueEnums.AssSize;
import com.lilithslegacy.game.character.body.valueEnums.BodyHair;
import com.lilithslegacy.game.character.body.valueEnums.BodyMaterial;
import com.lilithslegacy.game.character.body.valueEnums.BodySize;
import com.lilithslegacy.game.character.body.valueEnums.BreastShape;
import com.lilithslegacy.game.character.body.valueEnums.Capacity;
import com.lilithslegacy.game.character.body.valueEnums.ClitorisSize;
import com.lilithslegacy.game.character.body.valueEnums.CoveringModifier;
import com.lilithslegacy.game.character.body.valueEnums.CoveringPattern;
import com.lilithslegacy.game.character.body.valueEnums.CumProduction;
import com.lilithslegacy.game.character.body.valueEnums.CupSize;
import com.lilithslegacy.game.character.body.valueEnums.EyeShape;
import com.lilithslegacy.game.character.body.valueEnums.Femininity;
import com.lilithslegacy.game.character.body.valueEnums.FluidFlavour;
import com.lilithslegacy.game.character.body.valueEnums.FluidModifier;
import com.lilithslegacy.game.character.body.valueEnums.FluidRegeneration;
import com.lilithslegacy.game.character.body.valueEnums.FootStructure;
import com.lilithslegacy.game.character.body.valueEnums.GenitalArrangement;
import com.lilithslegacy.game.character.body.valueEnums.HairLength;
import com.lilithslegacy.game.character.body.valueEnums.HairStyle;
import com.lilithslegacy.game.character.body.valueEnums.HipSize;
import com.lilithslegacy.game.character.body.valueEnums.HornLength;
import com.lilithslegacy.game.character.body.valueEnums.LabiaSize;
import com.lilithslegacy.game.character.body.valueEnums.Lactation;
import com.lilithslegacy.game.character.body.valueEnums.LegConfiguration;
import com.lilithslegacy.game.character.body.valueEnums.LipSize;
import com.lilithslegacy.game.character.body.valueEnums.Muscle;
import com.lilithslegacy.game.character.body.valueEnums.NippleShape;
import com.lilithslegacy.game.character.body.valueEnums.NippleSize;
import com.lilithslegacy.game.character.body.valueEnums.OrificeDepth;
import com.lilithslegacy.game.character.body.valueEnums.OrificeElasticity;
import com.lilithslegacy.game.character.body.valueEnums.OrificeModifier;
import com.lilithslegacy.game.character.body.valueEnums.OrificePlasticity;
import com.lilithslegacy.game.character.body.valueEnums.PenetrationGirth;
import com.lilithslegacy.game.character.body.valueEnums.PenetrationModifier;
import com.lilithslegacy.game.character.body.valueEnums.PenisLength;
import com.lilithslegacy.game.character.body.valueEnums.PiercingType;
import com.lilithslegacy.game.character.body.valueEnums.TesticleSize;
import com.lilithslegacy.game.character.body.valueEnums.TongueLength;
import com.lilithslegacy.game.character.body.valueEnums.TongueModifier;
import com.lilithslegacy.game.character.body.valueEnums.Wetness;
import com.lilithslegacy.game.character.body.valueEnums.WingSize;
import com.lilithslegacy.game.character.effects.StatusEffect;
import com.lilithslegacy.game.character.fetishes.AbstractFetish;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.fetishes.FetishDesire;
import com.lilithslegacy.game.character.markings.AbstractTattooType;
import com.lilithslegacy.game.character.markings.Tattoo;
import com.lilithslegacy.game.character.markings.TattooCountType;
import com.lilithslegacy.game.character.markings.TattooCounter;
import com.lilithslegacy.game.character.markings.TattooCounterType;
import com.lilithslegacy.game.character.markings.TattooType;
import com.lilithslegacy.game.character.markings.TattooWriting;
import com.lilithslegacy.game.character.markings.TattooWritingStyle;
import com.lilithslegacy.game.character.persona.PersonalityTrait;
import com.lilithslegacy.game.character.persona.SexualOrientation;
import com.lilithslegacy.game.character.race.AbstractRace;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.game.character.race.RacialBody;
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.places.dominion.shoppingArcade.SuccubisSecrets;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.rendering.RenderingEngine;
import com.lilithslegacy.rendering.SVGImages;
import com.lilithslegacy.utils.Units;
import com.lilithslegacy.utils.Units.ValueType;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;

import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Innoxia
 * @version 0.3.9.1
 * @since 0.1.7?
 */
public class CharacterModificationUtils {

    public static final int FLUID_INCREMENT_SMALL = 5;
    public static final int FLUID_INCREMENT_AVERAGE = 50;
    public static final int FLUID_INCREMENT_LARGE = 500;
    public static final int FLUID_INCREMENT_HUGE = 5000;
    public static final int FLUID_REGEN_INCREMENT_SMALL = 100;
    public static final int FLUID_REGEN_INCREMENT_AVERAGE = 1000;
    public static final int FLUID_REGEN_INCREMENT_LARGE = 10000;
    public static final int FLUID_REGEN_INCREMENT_HUGE = 100000;
    public static final int MAX_AGE_PLAYER = 50;
    public static final int MAX_AGE_NPC = 60;
    public static final int maxSexExperience = 500;
    public static final int[] normalSexExperienceValues = new int[]{0, 1, 25, 100, 250};
    public static final float[] sexExperienceCorruption = new float[]{0, 0.5f, 1, 2.5f, 5};
    public static final String[] feminineNames = new String[]{"Virgin", "Inexperienced", "Experienced", "Expert", "Slut"};

    // Basics:
    public static final String[] masculineNames = new String[]{"Virgin", "Inexperienced", "Experienced", "Expert", "Stud"};
    public static InventorySlot tattooInventorySlot = null;
    public static Tattoo tattoo = null;
    private static final StringBuilder contentSB = new StringBuilder();
    private static final Colour[] sexColours = new Colour[]{PresetColour.GENERIC_EXCELLENT, PresetColour.BASE_PINK_LIGHT, PresetColour.BASE_PINK_SALMON, PresetColour.BASE_PINK, PresetColour.BASE_PINK_DEEP};
    //TODO reset on open
    private static Map<AbstractBodyCoveringType, Covering> coveringsToBeApplied = new HashMap<>();

    public static String getInformationDiv(String id, TooltipInformationEventListener information) {
        return getInformationDiv(id, information, false);
    }

    public static String getInformationDiv(String id, TooltipInformationEventListener information, boolean left) {
        Game.informationTooltips.put(id, information);
        return "<div class='title-button no-select' id='" + id + "' style='position:absolute; " + (left ? "left:8px; right:auto;" : "left:auto; right:8px;") + " top:8px; background:transparent; padding:0; margin:0;'>"
                + SVGImages.SVG_IMAGE_PROVIDER.getInformationIcon()
                + "</div>";
    }

    public static String getStartDateDiv() {
        contentSB.setLength(0);

        contentSB.append("<div class='cosmetics-inner-container full'>");
        contentSB.append("<p style='text-align:center; margin:0; padding:0;'><b>Start Date</b></p>");
        contentSB.append("<p style='text-align:center;'>"
                + "Select the month in which the game starts."
                + "</p>");

        for (Month month : Month.values()) {
            if (Main.game.getStartingDate().getMonth() == month) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_MINOR_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH))).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='STARTING_MONTH_").append(month).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.GENERIC_MINOR_GOOD.getShades()[0]).append(";'>").append(Util.capitaliseSentence(month.getDisplayName(TextStyle.FULL, Locale.ENGLISH))).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static String getGenderChoiceDiv() {
        contentSB.setLength(0);

        contentSB.append("<div class='container-half-width' style='text-align:center;'>");
        contentSB.append("<p style='text-align:center; margin:0; padding:0;'><b>Gender</b></p>");

        if (BodyChanging.getTarget().isPlayer()) {
            contentSB.append("<p style='text-align:center;'>"
                    + "Your gender is used to determine what genitals you start the game with."
                    + "</p>");
        }

        if (BodyChanging.getTarget().getGender().getGenderName().isHasVagina()) {
            contentSB.append("<div id='CHOOSE_GENDER_MALE' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.MASCULINE.getShades()[0]).append(";'>Male</span>").append("</div>").append("<div class='cosmetics-button active'>").append("[style.boldFeminine(Female)]").append("</div>");
        } else {
            contentSB.append("<div class='cosmetics-button active'>" + "[style.colourMasculine(Male)]" + "</div>" + "<div id='CHOOSE_GENDER_FEMALE' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.FEMININE.getShades()[0]).append(";'>Female</span>").append("</div>");
        }

        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static String getFemininityChoiceDiv() {
        contentSB.setLength(0);

        contentSB.append("<div class='container-half-width' style='text-align:center;'>");
        contentSB.append("<p style='text-align:center; margin:0; padding:0;'><b>Femininity</b></p>");

        if (BodyChanging.getTarget().isPlayer()) {
            contentSB.append("<p style='text-align:center;'>"
                    + "Femininity is a measure of how masculine or feminine your face and body are."
                    + "</p>");
        }

        if (BodyChanging.getTarget().getGender().getGenderName().isHasVagina()) {
            if (BodyChanging.getTarget().getFemininity() == Femininity.ANDROGYNOUS) {
                contentSB.append(
                        "<div class='cosmetics-button active'>"
                                + "[style.colourAndrogynous(Androgynous)]"
                                + "</div>");
            } else {
                contentSB.append("<div id='CHOOSE_FEM_ANDROGYNOUS' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.ANDROGYNOUS.getShades()[0]).append(";'>Androgynous</span>").append("</div>");
            }
            if (BodyChanging.getTarget().getFemininity() == Femininity.FEMININE) {
                contentSB.append(
                        "<div class='cosmetics-button active'>"
                                + "[style.colourFeminine(Feminine)]"
                                + "</div>");
            } else {
                contentSB.append("<div id='CHOOSE_FEM_FEMININE' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.FEMININE.getShades()[0]).append(";'>Feminine</span>").append("</div>");
            }
            if (BodyChanging.getTarget().getFemininity() == Femininity.FEMININE_STRONG) {
                contentSB.append(
                        "<div class='cosmetics-button active'>"
                                + "[style.colourFeminineStrong(Very Feminine)]"
                                + "</div>");
            } else {
                contentSB.append("<div id='CHOOSE_FEM_FEMININE_STRONG' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.FEMININE_PLUS.getShades()[0]).append(";'>Very Feminine</span>").append("</div>");
            }

        } else {
            if (BodyChanging.getTarget().getFemininity() == Femininity.ANDROGYNOUS) {
                contentSB.append(
                        "<div class='cosmetics-button active'>"
                                + "[style.colourAndrogynous(Androgynous)]"
                                + "</div>");
            } else {
                contentSB.append("<div id='CHOOSE_FEM_ANDROGYNOUS' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.ANDROGYNOUS.getShades()[0]).append(";'>Androgynous</span>").append("</div>");
            }
            if (BodyChanging.getTarget().getFemininity() == Femininity.MASCULINE) {
                contentSB.append(
                        "<div class='cosmetics-button active'>"
                                + "[style.colourMasculine(Masculine)]"
                                + "</div>");
            } else {
                contentSB.append("<div id='CHOOSE_FEM_MASCULINE' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.MASCULINE.getShades()[0]).append(";'>Masculine</span>").append("</div>");
            }
            if (BodyChanging.getTarget().getFemininity() == Femininity.MASCULINE_STRONG) {
                contentSB.append(
                        "<div class='cosmetics-button active'>"
                                + "[style.colourMasculineStrong(Very Masculine)]"
                                + "</div>");
            } else {
                contentSB.append("<div id='CHOOSE_FEM_MASCULINE_STRONG' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.MASCULINE_PLUS.getShades()[0]).append(";'>Very Masculine</span>").append("</div>");
            }
        }

        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static String getPersonalityChoiceDiv(boolean allowSpecials) {
        contentSB.setLength(0);

        contentSB.append("<div class='container-full-width' style='text-align:center;'>");

        contentSB.append("<p style='text-align:center; margin:0; padding:0;'><b>Personality</b></p>");

        if (BodyChanging.getTarget().isPlayer()) {
            contentSB.append("<p style='text-align:center;'>"
                    + "Your personality will have a minor influence in some situations."
                    + " It will not lock out any options during the game, and is more for roleplaying purposes."
                    + "</p>");
        }

        for (PersonalityTrait trait : PersonalityTrait.values()) {
            if (allowSpecials || !trait.isSpecialRequirements()) {
                if (BodyChanging.getTarget().hasPersonalityTrait(trait)) {
                    contentSB.append("<div id='PERSONALITY_TRAIT_").append(trait).append("' class='cosmetics-button active'>").append("<span style='color:").append(trait.getColour().getShades()[2]).append(";'>").append(Util.capitaliseSentence(trait.getName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='PERSONALITY_TRAIT_").append(trait).append("' class='cosmetics-button'>").append("[style.colourDisabled(").append(Util.capitaliseSentence(trait.getName())).append(")]").append("</div>");
                }
            }
        }

        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static String getObedienceChoiceDiv() {
        contentSB.setLength(0);

        contentSB.append("<div class='container-full-width'>");

        contentSB.append("<div class='cosmetics-inner-container full'>");
        contentSB.append("<p style='text-align:center; margin:0; padding:0;'><b>Obedience</b></p>");

        for (ObedienceLevel obedience : ObedienceLevel.values()) {
            if (BodyChanging.getTarget().getObedience() == obedience) {
                contentSB.append("<div id='OBEDIENCE_LEVEL_").append(obedience).append("' class='cosmetics-button active'>").append("<span style='color:").append(obedience.getColour().getShades()[2]).append(";'>").append(Util.capitaliseSentence(obedience.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='OBEDIENCE_LEVEL_").append(obedience).append("' class='cosmetics-button'>").append("[style.colourDisabled(").append(Util.capitaliseSentence(obedience.getName())).append(")]").append("</div>");
            }
        }

        contentSB.append("</div>");

        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static String getAffectionChoiceDiv() {
        contentSB.setLength(0);

        contentSB.append("<div class='container-full-width'>");

        contentSB.append("<div class='cosmetics-inner-container full'>");
        contentSB.append("<p style='text-align:center; margin:0; padding:0;'><b>Affection</b></p>");

        for (AffectionLevel affection : AffectionLevel.values()) {
            if (BodyChanging.getTarget().getAffectionLevel(Main.game.getPlayer()) == affection) {
                contentSB.append("<div id='AFFECTION_LEVEL_").append(affection).append("' class='cosmetics-button active'>").append("<span style='color:").append(affection.getColour().getShades()[2]).append(";'>").append(Util.capitaliseSentence(affection.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='AFFECTION_LEVEL_").append(affection).append("' class='cosmetics-button'>").append("[style.colourDisabled(").append(Util.capitaliseSentence(affection.getName())).append(")]").append("</div>");
            }
        }

        contentSB.append("</div>");

        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static void performAgeCheck() {
        int age = (int) ChronoUnit.YEARS.between(BodyChanging.getTarget().getBirthday(), Main.game.getDateNow());

        if (BodyChanging.getTarget().isPlayer() && age > CharacterModificationUtils.MAX_AGE_PLAYER) {
            BodyChanging.getTarget().setBirthday(BodyChanging.getTarget().getBirthday().plusYears(age - CharacterModificationUtils.MAX_AGE_PLAYER));

        } else if (!BodyChanging.getTarget().isPlayer() && age > (CharacterModificationUtils.MAX_AGE_NPC - GameCharacter.MINIMUM_AGE)) {
            BodyChanging.getTarget().setBirthday(BodyChanging.getTarget().getBirthday().plusYears(age - (CharacterModificationUtils.MAX_AGE_NPC - GameCharacter.MINIMUM_AGE)));
        }

        if (BodyChanging.getTarget().isPlayer() && age < 18) {
            BodyChanging.getTarget().setBirthday(BodyChanging.getTarget().getBirthday().minusYears(18 - age));
        }

        if (age < 0) {
            BodyChanging.getTarget().setBirthday(Main.game.getDateNow());
        }
    }

    public static String getBirthdayChoiceDiv() {
        contentSB.setLength(0);

        contentSB.append("<div class='container-full-width'>");
        contentSB.append("<p style='text-align:center; margin:0; padding:0;'><b>Birthday</b></p>");

        if (BodyChanging.getTarget().isPlayer()) {
            contentSB.append("<p style='text-align:center;'>" + "You were born on the ").append(Units.date(BodyChanging.getTarget().getBirthday(), Units.DateType.LONG)).append(", making you ").append(Util.intToString(BodyChanging.getTarget().getAgeValue())).append(" years old.").append("</p>");
        }

        contentSB.append("<div class='container-full-width' style='margin:0;padding;0;width:100%;'>");

        contentSB.append("<div class='container-full-width' style='width:calc(33.3% - 16px);'>");
        contentSB.append(applyDateWrapper("Day", "BIRTH_DAY", "", "", String.valueOf(BodyChanging.getTarget().getBirthday().getDayOfMonth()), false, false));
        contentSB.append("</div>");

        contentSB.append("<div class='container-full-width' style='width:calc(33.3% - 16px);'>");
        contentSB.append(applyDateWrapper("Month", "BIRTH_MONTH", "", "", BodyChanging.getTarget().getBirthday().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH), false, false));
        contentSB.append("</div>");

        contentSB.append("<div class='container-full-width' style='width:calc(33.3% - 16px);'>");
        contentSB.append(applyDateWrapper("Age", "AGE", "", "",
                String.valueOf(BodyChanging.getTarget().getAgeValue()),
                BodyChanging.getTarget().getAgeValue() <= 18,
                BodyChanging.getTarget().isPlayer()
                        ? BodyChanging.getTarget().getAgeValue() >= MAX_AGE_PLAYER
                        : BodyChanging.getTarget().getAgeValue() >= MAX_AGE_NPC));
        contentSB.append("</div>");

        contentSB.append("</div>");

        contentSB.append("</div>");

        return contentSB.toString();
    }

    private static String applyDateWrapper(String title, String id, String measurement, String measurementPlural, String value, boolean decreaseDisabled, boolean increaseDisabled) {
        return "<p style='width:100%;  margin:0; padding:0; text-align:center;'>"
                + "<b>" + title + "</b>"
                + "</p>"
                + "<div class='container-half-width' style='width:30%; margin:0; padding:0; text-align:center;'>"
                + "<div id='" + id + "_DECREASE_LARGE' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:48%; margin:0 1%;'>"
                + (decreaseDisabled ? "[style.boldDisabled(--" + measurementPlural + ")]" : "[style.boldBad(--)]")
                + "</div>"
                + "<div id='" + id + "_DECREASE' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:48%; margin:0 1%;'>"
                + (decreaseDisabled ? "[style.boldDisabled(-" + measurement + ")]" : "[style.boldMinorBad(-)]")
                + "</div>"
                + "</div>"
                + "<div class='container-half-width' style='width:40%; margin:0; padding:0; text-align:center;'>"
                + value
                + "</div>"
                + "<div class='container-half-width' style='width:30%; margin:0; padding:0; text-align:center;'>"
                + "<div id='" + id + "_INCREASE' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:48%; margin:0 1%;'>"
                + (increaseDisabled ? "[style.boldDisabled(+" + measurement + ")]" : "[style.boldMinorGood(+)]")
                + "</div>"
                + "<div id='" + id + "_INCREASE_LARGE' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:48%; margin:0 1%;'>"
                + (increaseDisabled ? "[style.boldDisabled(++" + measurementPlural + ")]" : "[style.boldGood(++)]")
                + "</div>"
                + "</div>";
    }

//	public static String[] virginityLossesGynephilic = new String[] {"your girlfriend", "", "some girl in your apartment", "some girl in a club's restroom"};
//	public static String[] virginityLossesAmbiphilic = new String[] {"your girlfriend in her apartment", "your girlfriend in your apartment", "some girl in her apartment", "some girl in your apartment", "some girl in a club's restroom",
//			"your boyfriend in his apartment", "your boyfriend in your apartment", "some guy in his apartment", "some guy in your apartment", "some guy in a club's restroom"};
//	public static String[] virginityLossesAndrophilic = new String[] {"your boyfriend in his apartment", "your boyfriend in your apartment", "some guy in his apartment", "some guy in your apartment", "some guy in a club's restroom"};
//

    public static String getAgeChoiceDiv() {
        contentSB.setLength(0);

        contentSB.append("<div class='container-half-width'>");
        contentSB.append(applyDateWrapper("Age", "AGE", "", "",
                String.valueOf(BodyChanging.getTarget().getAgeValue()),
                BodyChanging.getTarget().getAgeValue() <= 18,
                BodyChanging.getTarget().isPlayer()
                        ? BodyChanging.getTarget().getAgeValue() >= MAX_AGE_PLAYER
                        : BodyChanging.getTarget().getAgeValue() >= MAX_AGE_NPC));
        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static String getOrientationChoiceDiv() {
        contentSB.setLength(0);

        if (BodyChanging.getTarget().isPlayer()) {
            contentSB.append("<div class='container-full-width' style='text-align:center;'>");
        } else {
            contentSB.append("<div class='container-half-width' style='text-align:center;'>");
        }
        contentSB.append("<p style='text-align:center; margin:0; padding:0;'><b>Sexual Orientation</b></p>");

        if (BodyChanging.getTarget().isPlayer()) {
            contentSB.append("<p style='text-align:center;'>"
                    + "Sexual orientation is determined by your attraction towards femininity or masculinity."
                    + "<br/><i>Hover over the orientation icon in your character's status effects panel to see the effects.</i>"
                    + "</p>");
        }

        for (SexualOrientation orientation : SexualOrientation.values()) {
            if (BodyChanging.getTarget().getSexualOrientation() == orientation) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(orientation.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(orientation.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='SEXUAL_ORIENTATION_").append(orientation).append("' class='cosmetics-button'>").append("<span style='color:").append(orientation.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(orientation.getName())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static String getFetishChoiceDiv() {
        contentSB.setLength(0);

        contentSB.append("<div class='container-full-width'>");
        contentSB.append("<div class='container-full-width' style='padding:0;'>");
        contentSB.append("<p style='text-align:center; margin:0; padding:0;'><b>Fetishes</b></p>");

        int i = 0;
        for (AbstractFetish fetish : Fetish.getAllFetishes()) {
            if (fetish.isAvailable(BodyChanging.getTarget()) && fetish.getFetishesForAutomaticUnlock().isEmpty()) {
                contentSB.append("<div class='container-full-width inner' style='width:100%; margin:0; padding:0; background:").append((i % 2 == 0 ? PresetColour.BACKGROUND : PresetColour.BACKGROUND_ALT).toWebHexString()).append(";'>");

                contentSB.append("<div class='container-full-width inner' style='margin:0; padding:0 0 0 20px; width:25%; text-align:center;background:transparent;'>");
                contentSB.append("<span style='color:").append(BodyChanging.getTarget().getFetishDesire(fetish).getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(fetish.getName(BodyChanging.getTarget()))).append("</span>");
                contentSB.append("</div>");

                contentSB.append(
                        getInformationDiv(
                                "FETISH_INFO_" + Fetish.getIdFromFetish(fetish),
                                new TooltipInformationEventListener().setInformation(Util.capitaliseSentence(fetish.getName(BodyChanging.getTarget())), fetish.getDescription(null)),
                                true));

                contentSB.append("<div class='container-full-width inner' style='margin:0; padding:0; width:75%; text-align:center; background:transparent;'>");
                for (FetishDesire desire : FetishDesire.values()) {
                    if (BodyChanging.getTarget().getFetishDesire(fetish) == desire) {
                        contentSB.append("<div class='cosmetics-button active' style='width:18%; margin:1%; min-width:0;'>" + "<span style='color:").append(desire.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(desire.getName())).append("</span></div>");
                    } else {
                        contentSB.append("<div id='FETISH_DESIRE_").append(Fetish.getIdFromFetish(fetish)).append(desire).append("' class='cosmetics-button' style='width:18%; margin:1%; min-width:0;'>").append("<span style='color:").append(desire.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(desire.getName())).append("</span></div>");
                    }
                }
                contentSB.append("</div>");

                contentSB.append("</div>");
            }
            i++;
        }

        contentSB.append("</div>");
        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static String getSexualExperienceDiv() {
        contentSB.setLength(0);

        contentSB.append("<div class='container-full-width'>"
                + "<div class='container-full-width' style='text-align:center;'><b>Sex Actions Performed</b></div>");

        contentSB.append(getSexExperienceEntry("HANDJOBS_GIVEN", "Handjobs Given",
                new SexType(SexParticipantType.NORMAL, SexAreaPenetration.FINGER, SexAreaPenetration.PENIS))).append(getSexExperienceEntry("FINGERINGS_GIVEN", "Fingerings Performed",
                new SexType(SexParticipantType.NORMAL, SexAreaPenetration.FINGER, SexAreaOrifice.VAGINA))).append(getSexExperienceEntry("BLOWJOBS_GIVEN", "Blowjobs Given",
                new SexType(SexParticipantType.NORMAL, SexAreaOrifice.MOUTH, SexAreaPenetration.PENIS))).append(getSexExperienceEntry("CUNNILINGUS_GIVEN", "Cunnilingus Performed",
                new SexType(SexParticipantType.NORMAL, SexAreaPenetration.TONGUE, SexAreaOrifice.VAGINA))).append(Main.game.isAnalContentEnabled()
                ? getSexExperienceEntry("ANILINGUS_GIVEN", "Anilingus Performed",
                new SexType(SexParticipantType.NORMAL, SexAreaPenetration.TONGUE, SexAreaOrifice.ANUS))
                : "").append(getSexExperienceEntry("VAGINAL_GIVEN", "Vaginal Sex Performed",
                new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.VAGINA))).append(Main.game.isAnalContentEnabled()
                ? getSexExperienceEntry("ANAL_GIVEN", "Anal Sex Performed",
                new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.ANUS))
                : "");
        contentSB.append("</div>");

        contentSB.append("<div class='container-full-width'>"
                + "<div class='container-full-width' style='text-align:center;'><b>Sex Actions Received</b></div>");
        contentSB.append(BodyChanging.getTarget().hasPenis()
                ? getSexExperienceEntry("HANDJOBS_TAKEN", "Handjobs Received",
                new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaPenetration.FINGER))
                : "").append(BodyChanging.getTarget().hasVagina()
                ? getSexExperienceEntry("FINGERINGS_TAKEN", "Fingerings Received",
                new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.FINGER))
                : "").append(BodyChanging.getTarget().hasPenis()
                ? getSexExperienceEntry("BLOWJOBS_TAKEN", "Blowjobs Received",
                new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH))
                : "").append(BodyChanging.getTarget().hasVagina()
                ? getSexExperienceEntry("CUNNILINGUS_TAKEN", "Cunnilingus Received",
                new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.TONGUE))
                : "").append(Main.game.isAnalContentEnabled()
                ? getSexExperienceEntry("ANILINGUS_TAKEN", "Anilingus Received",
                new SexType(SexParticipantType.NORMAL, SexAreaOrifice.ANUS, SexAreaPenetration.TONGUE))
                : "").append(BodyChanging.getTarget().hasVagina()
                ? getSexExperienceEntry("VAGINAL_TAKEN", "Vaginal Sex Received",
                new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.PENIS))
                : "").append(Main.game.isAnalContentEnabled()
                ? getSexExperienceEntry("ANAL_TAKEN", "Anal Sex Received",
                new SexType(SexParticipantType.NORMAL, SexAreaOrifice.ANUS, SexAreaPenetration.PENIS))
                : "");
        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static void resetImpossibeSexExperience() {
        if (!BodyChanging.getTarget().hasVagina()) {
            CharacterModificationUtils.setSexExperience(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.PENIS), 0);
            CharacterModificationUtils.setSexExperience(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.FINGER), 0);
            CharacterModificationUtils.setSexExperience(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.TONGUE), 0);
        }
        if (!BodyChanging.getTarget().hasPenis()) {
            CharacterModificationUtils.setSexExperience(new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH), 0);
            CharacterModificationUtils.setSexExperience(new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaPenetration.FINGER), 0);
        }
    }

    public static void incrementSexExperience(SexType type, int increment) {
        int count = BodyChanging.getTarget().getSexCount(null, type) + increment;
        setSexExperience(type, count);
    }


    // Advanced:

    public static void setSexExperience(SexType type, int count) {
        count = Math.min(maxSexExperience, Math.max(0, count));
        int index = getSexExperienceIndex(type);
        BodyChanging.getTarget().incrementAttribute(Attribute.MAJOR_CORRUPTION, -sexExperienceCorruption[index]);
        BodyChanging.getTarget().setSexCount(null, type, count);
        index = getSexExperienceIndex(type);
        BodyChanging.getTarget().incrementAttribute(Attribute.MAJOR_CORRUPTION, sexExperienceCorruption[index]);


        if (count != 0) {
            if (BodyChanging.getTarget().getSexualOrientation() == SexualOrientation.GYNEPHILIC
                    || (BodyChanging.getTarget().getSexualOrientation() == SexualOrientation.AMBIPHILIC && !BodyChanging.getTarget().isFeminine())) {
                BodyChanging.getTarget().setVirginityLoss(type, "", BodyChanging.getTarget().isPlayer() ? "your girlfriend" : "a stranger");
            } else {
                BodyChanging.getTarget().setVirginityLoss(type, "", BodyChanging.getTarget().isPlayer() ? "your boyfriend" : "a stranger");
            }
        } else {
            BodyChanging.getTarget().resetVirginityLoss(type);
        }

//		System.out.println(BodyChanging.getTarget().getNameIgnoresPlayerKnowledge()+": "+type+" | "+BodyChanging.getTarget().getVirginityLossDescription(type));

        if (type.getPerformingSexArea() == SexAreaPenetration.PENIS) {
            BodyChanging.getTarget().setPenisVirgin(count == 0 || type.getTargetedSexArea().isPenetration() || (type.getTargetedSexArea().isOrifice() && !((SexAreaOrifice) type.getTargetedSexArea()).isInternalOrifice()));
        }

        if (type.getTargetedSexArea() == SexAreaPenetration.PENIS) {
            if (type.getPerformingSexArea().isOrifice()) {
                switch ((SexAreaOrifice) type.getPerformingSexArea()) {
                    case ANUS:
                        BodyChanging.getTarget().setAssVirgin(count == 0);
                        break;
                    case ARMPITS:
                        break;
                    case ASS:
                        break;
                    case BREAST:
                        break;
                    case BREAST_CROTCH:
                        break;
                    case MOUTH:
                        BodyChanging.getTarget().setFaceVirgin(count == 0);
                        break;
                    case NIPPLE:
                        break;
                    case NIPPLE_CROTCH:
                        break;
                    case THIGHS:
                        break;
                    case URETHRA_PENIS:
                        break;
                    case URETHRA_VAGINA:
                        break;
                    case VAGINA:
                        BodyChanging.getTarget().setVaginaVirgin(count == 0);
                        break;
                    case SPINNERET:
                        BodyChanging.getTarget().setSpinneretVirgin(count == 0);
                        break;
                }
            }
        }
    }

    private static int getSexExperienceIndex(SexType associatedSexType) {
        int index = 0;
        for (int i = normalSexExperienceValues.length - 1; i >= 0; i--) {
            if (BodyChanging.getTarget().getTotalSexCount(associatedSexType) >= normalSexExperienceValues[i]) {
                index = i;
                break;
            }
        }
        return index;
    }

    private static String getSexExperienceEntry(String id, String title, SexType associatedSexType) {
        int index = getSexExperienceIndex(associatedSexType);

        String[] names = BodyChanging.getTarget().isFeminine() ? feminineNames : masculineNames;

        int sexCount = BodyChanging.getTarget().getTotalSexCount(associatedSexType);
        boolean decreaseDisabled = sexCount <= 0;
        boolean increaseDisabled = sexCount >= maxSexExperience;
        int minorStep = 1;
        int majorStep = 10;

        return "<div class='container-full-width inner'>"
                + "<div class='container-full-width inner' style='width:calc(30%);margin:0;padding:0;'>"
                + title
                + "</div>"
                + "<div class='container-full-width inner' style='width:calc(70%);margin:0;padding:0;'>"
                + "<div class='container-full-width' style='width:15%; text-align:center; float:left; position:relative; padding:0; margin:0;'>"
                + "<div id='" + id + "_DECREASE_LARGE' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:48%; margin:1%; padding:0;'>"
                + (decreaseDisabled ? "[style.boldDisabled(-" + majorStep + ")]" : "[style.boldBad(-" + majorStep + ")]")
                + "</div>"
                + "<div id='" + id + "_DECREASE' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:48%; margin:1%; padding:0;'>"
                + (decreaseDisabled ? "[style.boldDisabled(-" + minorStep + ")]" : "[style.boldBadMinor(-" + minorStep + ")]")
                + "</div>"
                + "</div>"
                + "<div class='container-full-width' style='width:18%; margin:1%; padding:0; text-align:center; float:left; position:relative;'>"
                + sexCount
                + "</div>"
                + "<div class='container-full-width' style='width:15%; text-align:center; float:left; position:relative; padding:0; margin:0;'>"
                + "<div id='" + id + "_INCREASE' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:48%; margin:1%; padding:0;'>"
                + (increaseDisabled ? "[style.boldDisabled(+" + minorStep + ")]" : "[style.boldGoodMinor(+" + minorStep + ")]")
                + "</div>"
                + "<div id='" + id + "_INCREASE_LARGE' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:48%; margin:1%; padding:0;'>"
                + (increaseDisabled ? "[style.boldDisabled(+" + majorStep + ")]" : "[style.boldGood(+" + majorStep + ")]")
                + "</div>"
                + "</div>"
                + "<div class='container-full-width inner' style='width:50%; margin:0;padding:0;text-align:center;'>"
                + "<span style='color:" + sexColours[index].toWebHexString() + ";'>" + names[index] + "</span>"
                + " ([style.colourCorruption(+" + sexExperienceCorruption[index] + " corruption)])"
                + "</div>"
                + "</div>"
                + "</div>";
    }

    private static String applyWrapper(String title, String description, String id, String input, boolean halfWidth) {
        if (halfWidth) {
            return "<div class='cosmetics-inner-container' style='margin:1% 1%; width:48%; padding:1%; box-sizing:border-box; position:relative;'>"
                    + getInformationDiv(id, new TooltipInformationEventListener().setInformation(title, description))
                    + "<p style='text-align:center; margin:0; padding:0;'>"
                    + "<b>" + title + "</b>"
                    + "</p>"
                    + input
                    + "</div>";

        } else {
            return "<div class='cosmetics-inner-container' style='margin:1% 1%; width:98%; padding:1%; box-sizing:border-box; position:relative;'>"
                    + getInformationDiv(id, new TooltipInformationEventListener().setInformation(title, description))
//						+"<div class='cosmetics-inner-container left'>"
//							+ "<p style='text-align:center; margin:0; padding:0;'>"
//								+ "<b>" +title+"</b>"
//							+"</p>"
//						+ "</div>"
//						+ "<div class='cosmetics-inner-container right'>"
//							+ input
//						+ "</div>"
                    + "<p style='text-align:center; margin:0; padding:0;'>"
                    + "<b>" + title + "</b>"
                    + "</p>"
                    + input
                    + "</div>";
        }
    }

    private static String applyFullVariableWrapper(String title, String description, String id, String minorStep, String majorStep, String value, boolean decreaseDisabled, boolean increaseDisabled) {
        return applyFullVariableWrapper(title, description, id, minorStep, majorStep, value, decreaseDisabled, increaseDisabled, null);
    }

    private static String applyFullVariableWrapper(String title, String description, String id, String minorStep, String majorStep, String value, boolean decreaseDisabled, boolean increaseDisabled, String additionalDescription) {
        return "<div class='cosmetics-inner-container' style='margin:1% 1%; width:48%; padding:1%; box-sizing:border-box; position:relative;'>"
                + "<p style='margin:0; padding:0;'>"
                + getInformationDiv(id, new TooltipInformationEventListener().setInformation(title, description))
                + "<b>" + title + "</b>"
                + "</p>"
                + "<div class='container-full-width' style='width:25%; text-align:center; float:left; position:relative; padding:0; margin:0;'>"
                + "<div id='" + id + "_DECREASE_LARGE' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:48%; margin:1%; padding:0;'>"
                + (decreaseDisabled ? "[style.boldDisabled(-" + majorStep + ")]" : "[style.boldBad(-" + majorStep + ")]")
                + "</div>"
                + "<div id='" + id + "_DECREASE' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:48%; margin:1%; padding:0;'>"
                + (decreaseDisabled ? "[style.boldDisabled(-" + minorStep + ")]" : "[style.boldBadMinor(-" + minorStep + ")]")
                + "</div>"
                + "</div>"
                + "<div class='container-full-width' style='width:48%; margin:1%; padding:0; text-align:center; float:left; position:relative;'>"
                + value
                + "</div>"
                + "<div class='container-full-width' style='width:25%; text-align:center; float:left; position:relative; padding:0; margin:0;'>"
                + "<div id='" + id + "_INCREASE' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:48%; margin:1%; padding:0;'>"
                + (increaseDisabled ? "[style.boldDisabled(+" + minorStep + ")]" : "[style.boldGoodMinor(+" + minorStep + ")]")
                + "</div>"
                + "<div id='" + id + "_INCREASE_LARGE' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:48%; margin:1%; padding:0;'>"
                + (increaseDisabled ? "[style.boldDisabled(+" + majorStep + ")]" : "[style.boldGood(+" + majorStep + ")]")
                + "</div>"
                + "</div>"
                + (additionalDescription != null && !additionalDescription.isEmpty()
                ? "<div class='container-full-width' style='width:98%; margin:1%; text-align:center; float:left; position:relative; padding:0; margin:0;'>" + additionalDescription + "</div>"
                : "")
                + "</div>";
    }

    private static String applyFullVariableWrapperSizes(String title, String description, String id, double value, boolean decreaseDisabled, boolean increaseDisabled) {
        return applyFullVariableWrapper(title, description, id, Units.size(1), Units.size(5),
                Units.size(value, Units.ValueType.PRECISE, Units.UnitType.SHORT), decreaseDisabled, increaseDisabled);
    }

    private static String applyVariableWrapperFluids(String title, String description, String id, String value, boolean decreaseDisabled, boolean increaseDisabled, int incrementSmall, int incrementAverage, int incrementLarge, int incrementHuge) {
        return "<div class='cosmetics-inner-container' style='margin:1% 1%; width:48%; padding:1%; box-sizing:border-box; position:relative'>"
                + "<p style='margin:0; padding:0;'>"
                + getInformationDiv(id, new TooltipInformationEventListener().setInformation(title, description))
                + "<b>" + title + "</b>"
                + "</p>"
                + "<div class='container-full-width'>"
                + "<div class='container-full-width' style='width:25%; text-align:center; float:left; position:relative; padding:0; margin:0;'>"
                + "<div id='" + id + "_DECREASE_SMALL' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:98%; margin:1%; padding:0;'>"
                + (decreaseDisabled ? "[style.boldDisabled(" + Units.fluid(-incrementSmall) + ")]" : "[style.boldBadMinor(" + Units.fluid(-incrementSmall) + ")]")
                + "</div>"
                + "<div id='" + id + "_DECREASE' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:98%; margin:1%; padding:0;'>"
                + (decreaseDisabled ? "[style.boldDisabled(" + Units.fluid(-incrementAverage) + ")]" : "[style.boldBadMinor(" + Units.fluid(-incrementAverage) + ")]")
                + "</div>"
                + "<div id='" + id + "_DECREASE_LARGE' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:98%; margin:1%; padding:0;'>"
                + (decreaseDisabled ? "[style.boldDisabled(" + Units.fluid(-incrementLarge) + ")]" : "[style.boldBad(" + Units.fluid(-incrementLarge) + ")]")
                + "</div>"
                + "<div id='" + id + "_DECREASE_HUGE' class='normal-button" + (decreaseDisabled ? " disabled" : "") + "' style='width:98%; margin:1%; padding:0;'>"
                + (decreaseDisabled ? "[style.boldDisabled(" + Units.fluid(-incrementHuge) + ")]" : "[style.boldBad(" + Units.fluid(-incrementHuge) + ")]")
                + "</div>"
                + "</div>"
                + "<div class='container-full-width' style='width:48%; margin:1%; padding:0; text-align:center; float:left; position:relative;'>"
                + value
                + "</div>"
                + "<div class='container-full-width' style='width:25%; text-align:center; float:left; position:relative; padding:0; margin:0;'>"
                + "<div id='" + id + "_INCREASE_SMALL' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:98%; margin:1%; padding:0;'>"
                + (increaseDisabled ? "[style.boldDisabled(+" + Units.fluid(incrementSmall) + ")]" : "[style.boldGoodMinor(+" + Units.fluid(incrementSmall) + ")]")
                + "</div>"
                + "<div id='" + id + "_INCREASE' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:98%; margin:1%; padding:0;'>"
                + (increaseDisabled ? "[style.boldDisabled(+" + Units.fluid(incrementAverage) + ")]" : "[style.boldGoodMinor(+" + Units.fluid(incrementAverage) + ")]")
                + "</div>"
                + "<div id='" + id + "_INCREASE_LARGE' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:98%; margin:1%; padding:0;'>"
                + (increaseDisabled ? "[style.boldDisabled(+" + Units.fluid(incrementLarge) + ")]" : "[style.boldGood(+" + Units.fluid(incrementLarge) + ")]")
                + "</div>"
                + "<div id='" + id + "_INCREASE_HUGE' class='normal-button" + (increaseDisabled ? " disabled" : "") + "' style='width:98%; margin:1%; padding:0;'>"
                + (increaseDisabled ? "[style.boldDisabled(+" + Units.fluid(incrementHuge) + ")]" : "[style.boldGood(+" + Units.fluid(incrementHuge) + ")]")
                + "</div>"
                + "</div>"
                + "</div>"
                + "</div>";
    }

    public static String getAgeAppearanceChoiceDiv() {
        return applyFullVariableWrapper(
                "Age Appearance",
                UtilText.parse(BodyChanging.getTarget(), "Change how old [npc.name] [npc.verb(appear)] to be. [npc.She] [npc.is] limited to looking as young as 18, or up to ten years older than [npc.her] real age."
                        + "<br/><i>This is purely a cosmetic change, and doesn't affect any in-game choices.</i>"),
                "AGE_APPEARANCE",
                "1",
                "5",
                String.valueOf(BodyChanging.getTarget().getAppearsAsAgeValue()),
                BodyChanging.getTarget().getAppearsAsAgeValue() <= 18,
                BodyChanging.getTarget().getAppearsAsAgeValue() >= (BodyChanging.getTarget().getAgeValue() + 10))

                + applyWrapper("Birthday",
                UtilText.parse(BodyChanging.getTarget(), "[npc.NamePos] birthday can not ever be changed, but by transforming [npc.her] body, [npc.she] may appear to be younger or older than [npc.she] really [npc.is]."),
                "BIRTHDAY",
                "<p style='text-align:center; margin:0; padding:0;'>"
                        + BodyChanging.getTarget().getBirthdayString()
                        + UtilText.parse(BodyChanging.getTarget(),
                        "</br>Making [npc.namePos] real age: <b style='color:" + BodyChanging.getTarget().getAge().getColour().toWebHexString() + "'>" + BodyChanging.getTarget().getAgeValue() + "</b>")
                        + "</p>",
                true);
    }

    public static String getHeightChoiceDiv() {
        return applyFullVariableWrapperSizes("Height",
                UtilText.parse(BodyChanging.getTarget(), "Change how tall [npc.name] [npc.is]."
                        + "<br/><i>This affects some minor descriptions and is also used for determining if a sex scene is categorised as 'size-difference' or not.</i>"),
                "HEIGHT",
                BodyChanging.getTarget().getHeightValue(),
                BodyChanging.getTarget().getHeightValue() <= BodyChanging.getTarget().getMinimumHeight(),
                BodyChanging.getTarget().getHeightValue() >= BodyChanging.getTarget().getMaximumHeight());
    }

    public static String getSelfTransformFemininityChoiceDiv() {
        return applyFullVariableWrapper(
                "Femininity",
                UtilText.parse(BodyChanging.getTarget(), "Change how feminine or masculine [npc.namePos] body is."
                        + "<br/><i>This affects speech colour text, determines whether clothing is too feminine or masculine for [npc.herHim] to wear, and is also used for determining sexual attraction (based on others' orientation).</i>"),
                "FEMININITY",
                "1",
                "5",
                BodyChanging.getTarget().getFemininityValue()
                        + "<br/><i style='color:" + BodyChanging.getTarget().getFemininity().getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(BodyChanging.getTarget().getFemininity().getName(false)) + "</i>",
                BodyChanging.getTarget().getFemininityValue() <= 0,
                BodyChanging.getTarget().getFemininityValue() >= 100);
    }

    public static String getSelfTransformBodyMaterialChoiceDiv(GameCharacter target) {
        contentSB.setLength(0);

        List<BodyMaterial> materials = new ArrayList<>();
        switch (target.getBodyMaterial()) {
            case AIR:
                materials.add(BodyMaterial.AIR);
                break;
            case ARCANE:
                materials.add(BodyMaterial.ARCANE);
                break;
            case FIRE:
                materials.add(BodyMaterial.FIRE);
                break;
            case FLESH:
                materials.add(BodyMaterial.FLESH);
                break;
            case SILICONE:
                materials.add(BodyMaterial.SILICONE);
                break;
            case ICE:
            case WATER:
                materials.add(BodyMaterial.WATER);
                materials.add(BodyMaterial.ICE);
                break;
            case SLIME:
                materials.add(BodyMaterial.SLIME);
                break;
            case RUBBER:
            case STONE:
                materials.add(BodyMaterial.STONE);
                materials.add(BodyMaterial.RUBBER);
                break;
        }

        for (BodyMaterial mat : materials) {
            if (BodyChanging.getTarget().getBodyMaterial() == mat) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(mat.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(mat.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='BODY_MATERIAL_").append(mat).append("' class='cosmetics-button'>").append("<span style='color:").append(mat.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(mat.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Body Material",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] body material."
                        + "<br/><i>Different body material types give different passive bonuses. Hover over this character's racial status effect tooltip to see them!</i>"),
                "BODY_MATERIAL",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformTailChoiceDiv(List<AbstractRace> availableRaces, boolean removeNone) {
        contentSB.setLength(0);

        for (AbstractTailType tail : TailType.getAllTailTypes()) {
            if ((tail.getRace() != null && availableRaces.contains(tail.getRace()))
                    || (!removeNone && tail == TailType.NONE)) {
                if (!BodyChanging.getTarget().getLegConfiguration().isAbleToGrowTail() && tail != TailType.NONE) {
                    continue;
                }
                if (!BodyChanging.getTarget().isYouko()) {
                    if (BodyChanging.getTarget().getTailType() == TailType.FOX_MORPH_MAGIC && tail != TailType.FOX_MORPH_MAGIC) {
                        continue;
                    }
                    if (BodyChanging.getTarget().getTailType() != TailType.FOX_MORPH_MAGIC && tail == TailType.FOX_MORPH_MAGIC) {
                        continue;
                    }
                }
                Colour c = PresetColour.TEXT_GREY;

                if (tail.getRace() != null) {
                    c = tail.getRace().getColour();
                }

                if (BodyChanging.getTarget().getTailType() == tail) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(c.toWebHexString()).append(";'>").append(Util.capitaliseSentence(tail.getTransformName())).append(tail.getTags().contains(BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION) || Main.game.isFurryTailPenetrationContentEnabled() ? "*" : "").append(tail.isPrehensile() ? "&#8314;" : "").append(tail.isOvipositor() ? "&deg;" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='TAIL_").append(TailType.getIdFromTailType(tail)).append("' class='cosmetics-button'>").append("<span style='color:").append(c.getShades()[0]).append(";'>").append(Util.capitaliseSentence(tail.getTransformName())).append(tail.getTags().contains(BodyPartTag.TAIL_SUITABLE_FOR_PENETRATION) || Main.game.isFurryTailPenetrationContentEnabled() ? "*" : "").append(tail.isPrehensile() ? "&#8314;" : "").append(tail.isOvipositor() ? "&deg;" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Tail",
                UtilText.parse(BodyChanging.getTarget(),
                        "<i>Tails must be suitable for penetration (*), and either be prehensile (&#8314;) or at least 50% of [npc.namePos] height to be used to penetrate orifices in sex."
                                + " Ovipositor-equipped tails (&deg;) can lay eggs in orifices during sex.</i>"),
                "TAIL_TYPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformTailLengthDiv() {
        if (BodyChanging.getTarget().getLegConfiguration() == LegConfiguration.TAIL_LONG) {
            float percentageMinimum = BodyChanging.getTarget().isFeral() ? Leg.LENGTH_PERCENTAGE_MIN_FERAL : Leg.LENGTH_PERCENTAGE_MIN;

            return applyFullVariableWrapper(Util.capitaliseSentence(LegConfiguration.TAIL_LONG.getName()) + " Length",
                    UtilText.parse(BodyChanging.getTarget(),
                            "Change the length of [npc.namePos] [npc.tail]. This is defined as a percentage of [npc.namePos] height,"
                                    + " and is limited to values of between " + Math.round(percentageMinimum * 100) + "% and " + Math.round(Leg.LENGTH_PERCENTAGE_MAX * 100) + "%."),
                    "TAIL_LENGTH",
                    "5%",
                    "25%",
                    Math.round(BodyChanging.getTarget().getLegTailLengthAsPercentageOfHeight() * 100) + "%"
                            + "<br/>"
                            + Units.size(BodyChanging.getTarget().getLegTailLength(false)),
                    BodyChanging.getTarget().getLegTailLengthAsPercentageOfHeight() <= percentageMinimum,
                    BodyChanging.getTarget().getLegTailLengthAsPercentageOfHeight() >= Leg.LENGTH_PERCENTAGE_MAX);

        } else {
            return applyFullVariableWrapper("Tail Length",
                    UtilText.parse(BodyChanging.getTarget(),
                            BodyChanging.getTarget().hasTail()
                                    ? "Change the length of [npc.namePos] [npc.tail]. This is defined as a percentage of [npc.namePos] height,"
                                    + " and is limited to values of between " + Math.round(Tail.LENGTH_PERCENTAGE_MIN * 100) + "% and " + Math.round(Tail.LENGTH_PERCENTAGE_MAX * 100) + "%."
                                    : "[npc.Name] [npc.do] not have a tail, so the length cannot be changed!"),
                    "TAIL_LENGTH",
                    "5%",
                    "25%",
                    Math.round(BodyChanging.getTarget().getTailLengthAsPercentageOfHeight() * 100) + "%"
                            + "<br/>"
                            + Units.size(BodyChanging.getTarget().getTailLength(false))
                            + "<br/><i>Base Diameter: " + Units.size(BodyChanging.getTarget().getTailDiameter(0)) + "</i>"
                            + "<br/><i>Tip Diameter: " + Units.size(BodyChanging.getTarget().getTailDiameter(BodyChanging.getTarget().getTailLength(false))) + "</i>",
                    BodyChanging.getTarget().getTailLengthAsPercentageOfHeight() <= Tail.LENGTH_PERCENTAGE_MIN || !BodyChanging.getTarget().hasTail(),
                    BodyChanging.getTarget().getTailLengthAsPercentageOfHeight() >= Tail.LENGTH_PERCENTAGE_MAX || !BodyChanging.getTarget().hasTail());
        }
    }

    public static String getSelfTransformTailCountDiv() {
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasTail()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Zero")).append("</div>");

        } else {
            for (int i = 1; i <= Tail.MAXIMUM_COUNT; i++) {
                if (BodyChanging.getTarget().getTailCount() == i) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

                } else {
                    if (!BodyChanging.isDebugMenu() && (BodyChanging.getTarget().isYouko() && i > BodyChanging.getTarget().getMaxTailCount())) {
                        contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</div>");

                    } else {
                        contentSB.append("<div id='TAIL_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
                    }
                }
            }
        }

        return applyWrapper("Tail Count",
                UtilText.parse(BodyChanging.getTarget(),
                        !BodyChanging.getTarget().hasTail()
                                ? "As [npc.name] [npc.do] not have a tail, [npc.she] cannot change how many [npc.she] [npc.has]!"
                                : ((BodyChanging.getTarget().isYouko()
                                ? "As [npc.nameIsFull] a youko, [npc.she] can change the number of tails [npc.she] [npc.verb(appear)] to have!"
                                : "Change how many [npc.tails] [npc.name] [npc.has].")
                                + "<br/><i>The number of tails is taken into consideration when checking to see if there's a tail available for penetrative actions during sex.</i>")),
                "TAIL_COUNT",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformTailGirthDiv() {
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasTail()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("N/A")).append("</div>");

        } else {
            for (PenetrationGirth girth : PenetrationGirth.values()) {
                if (BodyChanging.getTarget().getTailGirth() == girth) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(girth.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(BodyChanging.getTarget().getTailType().getGirthDescriptor(girth))).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='TAIL_GIRTH_").append(girth).append("' class='cosmetics-button'>").append("<span style='color:").append(girth.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(BodyChanging.getTarget().getTailType().getGirthDescriptor(girth))).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Tail Girth",
                UtilText.parse(BodyChanging.getTarget(),
                        !BodyChanging.getTarget().hasTail()
                                ? ("As [npc.name] [npc.do] not have a tail, [npc.she] cannot change [npc.her] tail girth!"
                                + (BodyChanging.getTarget().getLegConfiguration() == LegConfiguration.TAIL_LONG
                                ? "<br/>(<i>The girth of [npc.her] serpent-tail is based on [npc.her] hip size.</i>)"
                                : ""))
                                : ("Change the girth of [npc.namePos] [npc.tail]."
                                + "<br/><i>Tail girth has an impact in determining whether a tail is too large for the orifice it is penetrating.</i>")),
                "TAIL_GIRTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformTentacleLengthDiv() {
        return applyFullVariableWrapper("Tentacle Length",
                UtilText.parse(BodyChanging.getTarget(),
                        BodyChanging.getTarget().hasTentacle()
                                ? "Change the length of [npc.namePos] [npc.tentacles]. This is defined as a percentage of [npc.namePos] height, and is limited to values of between 100% and 500%."
                                : "[npc.Name] [npc.do] not have any tentacles, so the length cannot be changed!"),
                "TENTACLE_LENGTH",
                "5%",
                "25%",
                Math.round(BodyChanging.getTarget().getTentacleLengthAsPercentageOfHeight() * 100) + "%"
                        + "<br/>"
                        + Units.size(BodyChanging.getTarget().getTentacleLength(false)),
                BodyChanging.getTarget().getTentacleLengthAsPercentageOfHeight() <= Tentacle.LENGTH_PERCENTAGE_MIN || !BodyChanging.getTarget().hasTentacle(),
                BodyChanging.getTarget().getTentacleLengthAsPercentageOfHeight() >= Tentacle.LENGTH_PERCENTAGE_MAX || !BodyChanging.getTarget().hasTentacle());
    }

    public static String getSelfTransformTentacleGirthDiv() {
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasTentacle()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("N/A")).append("</div>");

        } else {
            for (PenetrationGirth girth : PenetrationGirth.values()) {
                if (BodyChanging.getTarget().getTentacleGirth() == girth) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(girth.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(BodyChanging.getTarget().getTentacleType().getGirthDescriptor(girth))).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='TENTACLE_GIRTH_").append(girth).append("' class='cosmetics-button'>").append("<span style='color:").append(girth.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(BodyChanging.getTarget().getTentacleType().getGirthDescriptor(girth))).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Tentacle Girth",
                UtilText.parse(BodyChanging.getTarget(),
                        !BodyChanging.getTarget().hasTentacle()
                                ? "As [npc.name] [npc.do] not have a tentacle, [npc.she] cannot change [npc.her] tentacle girth!"
                                : ("Change the girth of [npc.namePos] [npc.tentacle]."
                                + "<br/><i>Tentacle girth has an impact in determining whether a tentacle is too large for the orifice it is penetrating.</i>")),
                "TENTACLE_GIRTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformWingSizeDiv() {
        GameCharacter target = BodyChanging.getTarget();
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasWings()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("N/A")).append("</div>");
        } else {
            for (WingSize wingSize : WingSize.values()) {
                if (target.getWingType().getMinimumSize().getValue() > wingSize.getValue()
                        || target.getWingType().getMaximumSize().getValue() < wingSize.getValue()) {
                    contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence(wingSize.getName())).append(wingSize.getValue() >= target.getLegConfiguration().getMinimumWingSizeForFlight(target.getBody()).getValue() ? "*" : "").append("</div>");

                } else {
                    if (BodyChanging.getTarget().getWingSize() == wingSize) {
                        contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(wingSize.getName())).append(wingSize.getValue() >= target.getLegConfiguration().getMinimumWingSizeForFlight(target.getBody()).getValue() ? "*" : "").append("</span>").append("</div>");

                    } else {
                        contentSB.append("<div id='WING_SIZE_").append(wingSize).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(wingSize.getName())).append(wingSize.getValue() >= target.getLegConfiguration().getMinimumWingSizeForFlight(target.getBody()).getValue() ? "*" : "").append("</span>").append("</div>");
                    }
                }
            }
        }

        return applyWrapper("Wing Size",
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] wings."
                        + "<br/><i>Wing size affects [npc.namePos] ability to fly."
                        + " This varies based on leg configuration, with suitable sizes for [npc.namePos] '[npc.legConfiguration]' body being marked by an asterisk. Some wing types do not support all possible wing sizes.</i>"),
                "WING_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformWingChoiceDiv(List<AbstractRace> availableRaces, boolean removeNone) {
        contentSB.setLength(0);

        List<AbstractWingType> sortedTypes = new ArrayList<>(WingType.getAllWingTypes());
        /*
         * 'None' is first, then the rest of the 'no race' options.
         * Then, the races are sorted alphabetically.
         * Within a race (including 'no race'), options are sorted alphabetically.
         */
        sortedTypes.sort(Comparator.comparingInt((AbstractWingType w) -> w == WingType.NONE ? 0 : 1)
                .thenComparingInt(w -> w.getRace() == Race.NONE ? 0 : 1)
                .thenComparing(w -> w.getRace().getName(false))
                .thenComparing(AbstractWingType::getTransformName));

        for (AbstractWingType wing : sortedTypes) {
            if ((wing.getRace() != null && availableRaces.contains(wing.getRace()))
                    || (!removeNone && wing == WingType.NONE)) {

                Colour c = PresetColour.TEXT_GREY;

                if (wing.getRace() != null) {
                    c = wing.getRace().getColour();
                }

                if (BodyChanging.getTarget().getWingType() == wing) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(c.toWebHexString()).append(";'>").append(Util.capitaliseSentence(wing.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='WING_").append(WingType.getIdFromWingType(wing)).append("' class='cosmetics-button'>").append("<span style='color:").append(c.getShades()[0]).append(";'>").append(Util.capitaliseSentence(wing.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Wings",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] wing type."
                        + "<br/><i>Wing types which enable flight (when their size is also sufficiently large enough) are marked by an asterisk.</i>"),
                "WING_TYPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformHornChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        Set<AbstractHornType> types = new HashSet<>();
        for (AbstractRace race : availableRaces) {
            types.addAll(RacialBody.valueOfRace(race).getHornTypes(false));
        }


        List<AbstractHornType> sortedTypes = new ArrayList<>(types);
        /*
         * 'None' is first, then the rest of the 'no race' options.
         * Then, the races are sorted alphabetically.
         * Within a race (including 'no race'), options are sorted alphabetically.
         */
        sortedTypes.sort(Comparator.comparingInt((AbstractHornType h) -> h == HornType.NONE ? 0 : 1)
                .thenComparingInt(h -> h.getRace() == Race.NONE ? 0 : 1)
                .thenComparing(h -> h.getRace().getName(false))
                .thenComparing(AbstractHornType::getTransformName));

        for (AbstractHornType horn : sortedTypes) {
            if ((horn.getRace() != null && availableRaces.contains(horn.getRace()))
                    || horn.equals(HornType.NONE)) {

                Colour c = PresetColour.TEXT_GREY;

                if (horn.getRace() != null) {
                    c = horn.getRace().getColour();
                }

                if (BodyChanging.getTarget().getHornType().equals(horn)) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(c.toWebHexString()).append(";'>").append(Util.capitaliseSentence(horn.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='HORN_").append(HornType.getIdFromHornType(horn)).append("' class='cosmetics-button'>").append("<span style='color:").append(c.getShades()[0]).append(";'>").append(Util.capitaliseSentence(horn.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Horns",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] horn type."
                        + "<br/><i>Horns of a sufficient length can be used as handles for some sex actions, and are also used for subspecies identification.</i>"),
                "HORN_TYPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformHornSizeDiv() {
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasHorns()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("N/A")).append("</div>");
        } else {
            for (HornLength hornLength : HornLength.values()) {
                if (HornLength.getLengthFromInt(BodyChanging.getTarget().getHornLengthValue()) == hornLength) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(hornLength.getDescriptor())).append(hornLength.isSuitableAsHandles() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='HORN_LENGTH_").append(hornLength).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(hornLength.getDescriptor())).append(hornLength.isSuitableAsHandles() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Horn Length",
                UtilText.parse(BodyChanging.getTarget(), "Change the length of [npc.namePos] horns."
                        + "<br/><i>Horns of a sufficient length can be used as handles for some sex actions (marked by an asterisk), and are also used for subspecies identification.</i>"),
                "HORN_LENGTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformHornCountDiv() {
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasHorns()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("N/A")).append("</div>");
        } else {
            for (int i = 1; i <= Horn.MAXIMUM_ROWS; i++) {
                if (BodyChanging.getTarget().getHornRows() == i) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='HORN_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Horn Rows Count",
                UtilText.parse(BodyChanging.getTarget(), "Change how many pairs of [npc.horns] [npc.name] has."
                        + "<br/><i>This is mostly a cosmetic change, but is also taken into account for subspecies identification (such as unicorns having one 'row' of a single horn).</i>"),
                "HORN_COUNT",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformHornsPerRowCountDiv() {
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasHorns()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("N/A")).append("</div>");
        } else {
            for (int i = 1; i <= Horn.MAXIMUM_HORNS_PER_ROW; i++) {
                if (BodyChanging.getTarget().getHornsPerRow() == i) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='HORN_COUNT_PER_ROW_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Horns per Row Count",
                UtilText.parse(BodyChanging.getTarget(), "Change how many [npc.horns] are in each row."
                        + "<br/><i>This is mostly a cosmetic change, but is also taken into account for subspecies identification (such as unicorns having one 'row' of a single horn).</i>"),
                "HORN_COUNT_ROWS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAntennaChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractAntennaType antenna : AntennaType.getAllAntennaTypes()) {
            if ((antenna.getRace() != null && availableRaces.contains(antenna.getRace())) || antenna == AntennaType.NONE) {

                Colour c = PresetColour.TEXT_GREY;

                if (antenna.getRace() != null) {
                    c = antenna.getRace().getColour();
                }

                if (BodyChanging.getTarget().getAntennaType() == antenna) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(c.toWebHexString()).append(";'>").append(Util.capitaliseSentence(antenna.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='ANTENNA_").append(AntennaType.getIdFromAntennaType(antenna)).append("' class='cosmetics-button'>").append("<span style='color:").append(c.getShades()[0]).append(";'>").append(Util.capitaliseSentence(antenna.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Antennae",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] antenna type."
                        + "<br/><i>This is only used for subspecies identification.</i>"),
                "ANTENNAE_TYPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAntennaSizeDiv() {
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasAntennae()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("N/A")).append("</div>");
        } else {
            for (HornLength antennaLength : HornLength.values()) {
                if (HornLength.getLengthFromInt(BodyChanging.getTarget().getAntennaLength()) == antennaLength) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(antennaLength.getDescriptor())).append(antennaLength.isSuitableAsHandles() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='ANTENNA_LENGTH_").append(antennaLength).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(antennaLength.getDescriptor())).append(antennaLength.isSuitableAsHandles() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Antenna Length",
                UtilText.parse(BodyChanging.getTarget(), "Change the length of [npc.namePos] antennae."
                        + "<br/><i>Antennae of a sufficient length can be used as handles for some sex actions (marked by an asterisk), and are also used for subspecies identification.</i>"),
                "ANTENNA_LENGTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAntennaCountDiv() {
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasAntennae()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("N/A")).append("</div>");
        } else {
            for (int i = 1; i <= Antenna.MAXIMUM_ROWS; i++) {
                if (BodyChanging.getTarget().getAntennaRows() == i) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='ANTENNA_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Antenna Rows Count",
                UtilText.parse(BodyChanging.getTarget(), "Change how many pairs of [npc.antennae] [npc.name] has."
                        + "<br/><i>This is mostly a cosmetic change, but is also taken into account for subspecies identification (such as unicorns having one 'row' of a single antenna).</i>"),
                "ANTENNA_COUNT",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAntennaePerRowCountDiv() {
        contentSB.setLength(0);

        if (!BodyChanging.getTarget().hasAntennae()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("N/A")).append("</div>");
        } else {
            for (int i = 1; i <= Antenna.MAXIMUM_ANTENNAE_PER_ROW; i++) {
                if (BodyChanging.getTarget().getAntennaePerRow() == i) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='ANTENNA_COUNT_PER_ROW_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Antennae per Row Count",
                UtilText.parse(BodyChanging.getTarget(), "Change how many [npc.antennae] are in each row."
                        + "<br/><i>This is mostly a cosmetic change, but is also taken into account for subspecies identification (such as unicorns having one 'row' of a single antenna).</i>"),
                "ANTENNA_COUNT_ROWS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformHairChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractHairType hair : HairType.getAllHairTypes()) {
            if ((hair.getRace() != null && availableRaces.contains(hair.getRace()))) {
                if (BodyChanging.getTarget().getHairType() == hair) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(hair.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(hair.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='HAIR_").append(HairType.getIdFromHairType(hair)).append("' class='cosmetics-button'>").append("<span style='color:").append(hair.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(hair.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Hair",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] hair type."
                        + "<br/><i>This is only used for subspecies identification.</i>"),
                "HAIR_TYPE",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformHairLengthDiv() {
        contentSB.setLength(0);

        for (HairLength hairLength : HairLength.values()) {
            if (BodyChanging.getTarget().getHairLength() == hairLength) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(hairLength.getDescriptor())).append(hairLength.isSuitableForPulling() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='HAIR_LENGTH_").append(hairLength).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(hairLength.getDescriptor())).append(hairLength.isSuitableForPulling() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Hair Length",
                UtilText.parse(BodyChanging.getTarget(), "Change the length of [npc.namePos] [npc.hair(true)]."
                        + "<br/><i>Hair of a sufficient length (marked by an asterisk) can be pulled in some sex actions.</i>"),
                "HAIR_LENGTH",
                contentSB.toString(),
                true);
    }

    public static String getNeckFluffDiv() {
        contentSB.setLength(0);

        if (BodyChanging.getTarget().isNeckFluff()) {
            contentSB.append("<div id='NECK_FLUFF_OFF' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SHRINK.getShades()[0]).append(";'>No neck fluff</span>").append("</div>").append("<div class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GROW.toWebHexString()).append(";'>Neck fluff</span>").append("</div>");
        } else {
            contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SHRINK.toWebHexString()).append(";'>No neck fluff</span>").append("</div>").append("<div id='NECK_FLUFF_ON' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GROW.getShades()[0]).append(";'>Neck fluff</span>").append("</div>");
        }


        return applyWrapper("Neck Fluff",
                UtilText.parse(BodyChanging.getTarget(), "Set whether [npc.name] [npc.has] a lot of additional fluff around [npc.her] neck and upper chest."
                        + "<br/><i>This is a purely cosmetic transformation.</i>"),
                "NECK_FLUFF",
                contentSB.toString(),
                true);
    }

    public static String getSelfDivHairStyles(String title, String description) {
        contentSB.setLength(0);

        for (HairStyle hairStyle : HairStyle.values()) {
            if (BodyChanging.getTarget().getHairStyle() == hairStyle) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(hairStyle.getName(BodyChanging.getTarget()))).append("</span>").append("</div>");
            } else {
                if (BodyChanging.getTarget().getHairRawLengthValue() >= hairStyle.getMinimumLengthRequired()) {
                    contentSB.append("<div id='HAIR_STYLE_").append(hairStyle).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(hairStyle.getName(BodyChanging.getTarget()))).append("</span>").append("</div>");
                } else {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "[style.colourDisabled(").append(Util.capitaliseSentence(hairStyle.getName(BodyChanging.getTarget()))).append(")]").append("</div>");
                }
            }
        }

        return applyWrapper("Hair Style",
                description
                        + "<br/><i>'" + Util.capitaliseSentence(HairStyle.TWIN_TAILS.getName(BodyChanging.getTarget())) + "' and '" + Util.capitaliseSentence(HairStyle.TWIN_BRAIDS.getName(BodyChanging.getTarget())) + "'"
                        + " can be used as handles in some sex actions.</i>",
                "HAIR_STYLE",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformAssChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractAssType ass : AssType.getAllAssTypes()) {
            if ((ass.getRace() != null && availableRaces.contains(ass.getRace()))) {

                if (BodyChanging.getTarget().getAssType().equals(ass)) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(ass.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(ass.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='ASS_").append(AssType.getIdFromAssType(ass)).append("' class='cosmetics-button'>").append("<span style='color:").append(ass.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(ass.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Anus",
                UtilText.parse(BodyChanging.getTarget(), "Change the type of [npc.namePos] asshole."
                        + "<br/><i>Anus type affects the asshole modifiers which characters spawn with, as well as descriptions and subspecies identification.</i>"),
                "ASSHOLE_TYPE",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformBreastChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractBreastType breast : BreastType.getAllBreastTypes()) {
            if ((breast.getRace() != null && breast.getRace() != Race.NONE && availableRaces.contains(breast.getRace()))) {
                if (BodyChanging.getTarget().getBreastType() == breast) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(breast.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(breast.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='BREAST_").append(BreastType.getIdFromBreastType(breast)).append("' class='cosmetics-button'>").append("<span style='color:").append(breast.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(breast.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipples",
                UtilText.parse(BodyChanging.getTarget(), "Change the type of [npc.namePos] nipples."
                        + "<br/><i>Nipple type affects the nipple modifiers which characters spawn with, as well as the type of milk [npc.her] breasts produce. It is also used for character descriptions and subspecies identification.</i>"),
                "NIPPLE_TYPE",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformBreastCrotchChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractBreastType breast : BreastType.getAllBreastTypes()) {
            if ((breast.getRace() == Race.NONE || availableRaces.contains(breast.getRace()))) {
                if (BodyChanging.getTarget().getBreastCrotchType() == breast) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(breast.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(breast.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='BREAST_CROTCH_").append(BreastType.getIdFromBreastType(breast)).append("' class='cosmetics-button'>").append("<span style='color:").append(breast.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(breast.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper(Util.capitaliseSentence(getCrotchBoobName(false)) + " Type",
                UtilText.parse(BodyChanging.getTarget(), "Change the type of the nipples on [npc.namePos] " + getCrotchBoobName(true)
                        + "<br/><i>Crotch-nipple type affects the nipple modifiers which characters spawn with, as well as the type of milk [npc.her] " + getCrotchBoobName(true) + " produce."
                        + " It is also used for character descriptions and subspecies identification.</i>"),
                "CROTCH_NIPPLE_TYPE",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformArmChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractArmType arm : ArmType.getAllArmTypes()) {
            if (arm.getRace() != null && availableRaces.contains(arm.getRace())) {
                if (BodyChanging.getTarget().getArmType().equals(arm)) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(arm.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(arm.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='ARM_").append(ArmType.getIdFromArmType(arm)).append("' class='cosmetics-button'>").append("<span style='color:").append(arm.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(arm.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Arms",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] arm type."
                        + "<br/><i>Arm type is only used for character descriptions and subspecies identification.</i>"),
                "ARM_TYPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformArmCountDiv() {
        contentSB.setLength(0);

        for (int i = 1; i <= Arm.MAXIMUM_ROWS; i++) {
            if (BodyChanging.getTarget().getArmRows() == i) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='ARM_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Arm Rows Count",
                UtilText.parse(BodyChanging.getTarget(), "Change how many pairs of [npc.arms] [npc.name] has."
                        + "<br/><i>Arm count is used for character descriptions and subspecies identification, as well as determining if there are any free hands to use for sex actions.</i>"),
                "ARM_COUNT",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformLegChoiceDiv(List<AbstractRace> availableRaces, boolean bypassRestrictions) {
        contentSB.setLength(0);

        for (AbstractLegType leg : LegType.getAllLegTypes()) {
            if ((leg.isAvailableForSelfTransformMenu(BodyChanging.getTarget()) || bypassRestrictions) && leg.getRace() != null && availableRaces.contains(leg.getRace())) {
                if (BodyChanging.getTarget().getLegType().equals(leg)) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(leg.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(leg.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='LEG_").append(LegType.getIdFromLegType(leg)).append("' class='cosmetics-button'>").append("<span style='color:").append(leg.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(leg.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Legs",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] leg type."
                        + "<br/><i>Leg type is used for character descriptions and subspecies identification. Different leg types have different leg configurations available to them.</i>"),
                "LEG_TYPE",
                contentSB.toString(),
                true);

    }

    public static String getSelfTransformFootStructureChoiceDiv() {
        contentSB.setLength(0);

        for (FootStructure footStructure : FootStructure.values()) {
            if (BodyChanging.getTarget().getLegType().getFootType().getPermittedFootStructures(BodyChanging.getTarget().getLegConfiguration()).contains(footStructure)) {
                if (BodyChanging.getTarget().getFootStructure() == footStructure) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(footStructure.getName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='FOOT_STRUCTURE_").append(footStructure).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(footStructure.getName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Foot Structure",
                UtilText.parse(BodyChanging.getTarget(), "Change the structure of [npc.namePos] [npc.feet]."
                        + "<br/><i>Foot type is used for both character and sex descriptions, and also may limit foot-related clothing which can be worn.</i>"),
                "FOOT_STRUCTURE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformFaceChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractFaceType face : FaceType.getAllFaceTypes()) {
            if (face.getRace() != null && availableRaces.contains(face.getRace())) {
                if (BodyChanging.getTarget().getFaceType() == face) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(face.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(face.getTransformName())).append(face.getTags().contains(BodyPartTag.THERMAL_VISION) ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='FACE_").append(FaceType.getIdFromFaceType(face)).append("' class='cosmetics-button'>").append("<span style='color:").append(face.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(face.getTransformName())).append(face.getTags().contains(BodyPartTag.THERMAL_VISION) ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Face",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] face type."
                        + "<br/><i>Face type determines mouth and tongue types."
                        + " It also determines subspecies identification, and can grant thermal vision capabilities (marked by an asterisk).</i>"),
                "FACE_TYPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformBodyChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractTorsoType skin : TorsoType.getAllTorsoTypes()) {
            if (availableRaces.contains(skin.getRace())) {
                if (BodyChanging.getTarget().getTorsoType() == skin) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(skin.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(skin.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='TORSO_").append(TorsoType.getIdFromTorsoType(skin)).append("' class='cosmetics-button'>").append("<span style='color:").append(skin.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(skin.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Body",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] body type."
                        + "<br/><i>Body type influences what sort of material is covering [npc.namePos] body, and can range from skin or fur, to scales or feathers.</i>"),
                "BODY_TYPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformGenitalArrangementChoiceDiv() {
        contentSB.setLength(0);

        for (GenitalArrangement arrangement : GenitalArrangement.values()) {
//			if(arrangement!=GenitalArrangement.CLOACA_BEHIND) {
            if (BodyChanging.getTarget().getLegConfiguration().getAvailableGenitalConfigurations().contains(arrangement) || BodyChanging.getTarget().getGenitalArrangement() == arrangement) {
                if (BodyChanging.getTarget().getGenitalArrangement() == arrangement) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_SEX.toWebHexString()).append(";'>").append(Util.capitaliseSentence(arrangement.getName())).append("</span>").append("</div>");

                } else if (!Main.getProperties().hasValue(PropertyValue.bipedalCloaca) && BodyChanging.getTarget().getLegConfiguration() == LegConfiguration.BIPEDAL && arrangement != GenitalArrangement.NORMAL) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.BASE_GREY.getShades()[0]).append(";'>").append(Util.capitaliseSentence(arrangement.getName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='GENITAL_ARRANGEMENT_").append(arrangement).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.GENERIC_SEX.getShades()[0]).append(";'>").append(Util.capitaliseSentence(arrangement.getName())).append("</span>").append("</div>");
                }
            }
//			}
        }

        return applyWrapper("Genital Arrangement",
                UtilText.parse(BodyChanging.getTarget(),
                        "Change the [npc.namePos] genital configuration."
                                + "<br/><i>This determines where [npc.namePos] genitals and asshole are located. It is limited to '" + GenitalArrangement.NORMAL.getName() + "' for most leg configurations.</i>"
                                + (!Main.getProperties().hasValue(PropertyValue.bipedalCloaca)
                                ? " [style.italicsDisabled(Bipedal cloacas are disabled in your content options.)]"
                                : "")),
                "GENITAL_ARRANGEMENT",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformLegConfigurationChoiceDiv() {
        contentSB.setLength(0);

        for (LegConfiguration legConfig : LegConfiguration.values()) {
            if (BodyChanging.getTarget().getLegType().isLegConfigurationAvailable(legConfig)) {
                if (BodyChanging.getTarget().getLegConfiguration() == legConfig) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.RACE_BESTIAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(legConfig.getName())).append(!legConfig.isAbleToGrowTail() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='LEG_CONFIGURATION_").append(legConfig).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.RACE_BESTIAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(legConfig.getName())).append(!legConfig.isAbleToGrowTail() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Leg Configuration",
                UtilText.parse(BodyChanging.getTarget(), "Change what [npc.namePos] lower body is like."
                        + "<br/><i>Applying this change causes all body parts below the waist to be transformed! Some leg configurations (marked by an asterisk) will prevent [npc.namePos] from growing a tail!</i>"),
                "LEG_CONFIGURATION",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformEarChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractEarType ear : EarType.getAllEarTypes()) {
            if (availableRaces.contains(ear.getRace())) {
                if (BodyChanging.getTarget().getEarType().equals(ear)) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(ear.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(ear.getTransformName())).append(ear.isAbleToBeUsedAsHandlesInSex() ? "*" : "").append(ear.getTags().contains(BodyPartTag.ECHO_LOCATION) ? "+" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='EAR_").append(EarType.getIdFromEarType(ear)).append("' class='cosmetics-button'>").append("<span style='color:").append(ear.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(ear.getTransformName())).append(ear.isAbleToBeUsedAsHandlesInSex() ? "*" : "").append(ear.getTags().contains(BodyPartTag.ECHO_LOCATION) ? "+" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Ears",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] ear type."
                        + "<br/><i>Ear type helps to determine subspecies identification. Some are long enough to be pulled during sex (marked by an asterisk), or can grant echo-location (marked by a plus).</i>"),
                "EAR_TYPE",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformEyeChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractEyeType eye : EyeType.getAllEyeTypes()) {
            if (availableRaces.contains(eye.getRace())) {
                if (BodyChanging.getTarget().getEyeType() == eye) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(eye.getRace().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(eye.getTransformName())).append(eye.getTags().contains(BodyPartTag.NIGHT_VISION) ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='EYE_").append(EyeType.getIdFromEyeType(eye)).append("' class='cosmetics-button'>").append("<span style='color:").append(eye.getRace().getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(eye.getTransformName())).append(eye.getTags().contains(BodyPartTag.NIGHT_VISION) ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Eyes",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] eye type."
                        + "<br/><i>Eye type determines what iris and pupil shape characters spawn with, is used for subspecies identification, and can grant night vision capabilities (marked by an asterisk).</i>"),
                "EYE_TYPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformIrisChoiceDiv() {
        contentSB.setLength(0);

        for (EyeShape eyeShape : EyeShape.values()) {
            if (BodyChanging.getTarget().getIrisShape() == eyeShape) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(eyeShape.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='IRIS_SHAPE_").append(eyeShape).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(eyeShape.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Iris Shape",
                UtilText.parse(BodyChanging.getTarget(), "Change the shape of [npc.namePos] irises."
                        + "<br/><i>This is a purely cosmetic transformation.</i>"),
                "IRIS_SHAPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformPupilChoiceDiv() {
        contentSB.setLength(0);

        for (EyeShape eyeShape : EyeShape.values()) {
            if (BodyChanging.getTarget().getPupilShape() == eyeShape) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(eyeShape.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='PUPIL_SHAPE_").append(eyeShape).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(eyeShape.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Pupil Shape",
                UtilText.parse(BodyChanging.getTarget(), "Change the shape of [npc.namePos] pupils."
                        + "<br/><i>This is a purely cosmetic transformation.</i>"),
                "PUPIL_SHAPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformEyeCountDiv() {
        contentSB.setLength(0);

        for (int i = 1; i <= Eye.MAXIMUM_PAIRS; i++) {
            if (BodyChanging.getTarget().getEyePairs() == i) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='EYE_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Eye Pairs Count",
                UtilText.parse(BodyChanging.getTarget(), "Change how many pairs of [npc.eyes] [npc.name] [npc.has]."
                        + "<br/><i>This is a purely cosmetic transformation.</i>"),
                "EYE_COUNT",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformLipSizeDiv() {
        contentSB.setLength(0);

        for (LipSize lipSize : LipSize.values()) {
            if (BodyChanging.getTarget().getLipSize() == lipSize) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(lipSize.getName())).append(lipSize.isImpedesSpeech() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='LIP_SIZE_").append(lipSize).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(lipSize.getName())).append(lipSize.isImpedesSpeech() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Lip Size",
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] lips."
                        + "<br/><i>While mostly a cosmetic transformation, very large lip sizes (marked by an asterisk) will also cause [npc.name] to speak with a lisp.</i>"),
                "LIP_SIZE",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformThroatModifiersDiv() {
        contentSB.setLength(0);

        for (OrificeModifier orificeMod : OrificeModifier.values()) {
            if (BodyChanging.getTarget().hasFaceOrificeModifier(orificeMod)) {
                contentSB.append("<div  id='THROAT_MOD_").append(orificeMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='THROAT_MOD_").append(orificeMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Lip & Throat Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] lips and throat."
                        + "<br/><i>These modifiers affect descriptions when performing oral sex.</i>"),
                "THROAT_MODS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformThroatWetnessDiv() {
        contentSB.setLength(0);

        for (Wetness value : Wetness.values()) {
            if (BodyChanging.getTarget().getFaceWetness() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                if (BodyChanging.getTarget().getBodyMaterial().isOrificesAlwaysMaximumWetness()) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='THROAT_WETNESS_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Throat Wetness",
                UtilText.parse(BodyChanging.getTarget(), "Change the wetness of [npc.namePos] throat."
                        + "<br/><i>This affects pleasure in sex, as non-lubricated orifices negatively affect arousal gains.</i>"
                        + (BodyChanging.getTarget().getBodyMaterial().isOrificesAlwaysMaximumWetness()
                        ? "<br/>[style.italicsWetness8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(Wetness.SEVEN_DROOLING.getDescriptor()) + "'!)]"
                        : "")),
                "THROAT_WETNESS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformThroatCapacityDiv() {
        contentSB.setLength(0);

        for (Capacity value : Capacity.getCapacityListFromPreferences()) {
            if (BodyChanging.getTarget().getFaceCapacity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='THROAT_CAPACITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Throat Capacity",
                UtilText.parse(BodyChanging.getTarget(), "Change the capacity of [npc.namePos] throat."
                        + "<br/><i>This affects the size of penetrative objects that [npc.namePos] throat can take. If capacity is too small or too large for a penetrative object, arousal gains are affected.</i>"),
                "THROAT_CAPACITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformThroatDepthDiv() {
        contentSB.setLength(0);

        for (OrificeDepth value : OrificeDepth.values()) {
            if (BodyChanging.getTarget().getFaceDepth() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                if (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='THROAT_DEPTH_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Throat Depth",
                UtilText.parse(BodyChanging.getTarget(), "Change the depth of [npc.namePos] throat."
                        + "<br/><i>Depth of an orifice determines the length of a penetrating object that it can comfortably accommodate.</i>"
                        + (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()
                        ? "<br/>[style.italicsSize8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(OrificeDepth.SEVEN_FATHOMLESS.getDescriptor()) + "'!)]"
                        : "")),
                "THROAT_DEPTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformThroatElasticityDiv() {
        contentSB.setLength(0);

        for (OrificeElasticity value : OrificeElasticity.values()) {
            if (BodyChanging.getTarget().getFaceElasticity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='THROAT_ELASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Throat Elasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the elasticity of [npc.namePos] throat."
                        + "<br/><i>This determines how quickly [npc.namePos] throat will stretch out if a penetrating object is too thick for it."
                        + (Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? " Values marked with an asterisk increase the maximum uncomfortable depth of [npc.her] throat." : ""))
                        + "</i>",
                "THROAT_ELASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformThroatPlasticityDiv() {
        contentSB.setLength(0);

        for (OrificePlasticity value : OrificePlasticity.values()) {
            if (BodyChanging.getTarget().getFacePlasticity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='THROAT_PLASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Throat Plasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the plasticity of [npc.namePos] throat."
                        + "<br/><i>Plasticity of an orifice determines how quickly, and by how much, it regains its original tightness after being stretched out.</i>"),
                "THROAT_PLASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformTongueModifiersDiv() {
        contentSB.setLength(0);

        for (TongueModifier tongueMod : TongueModifier.values()) {
            if (BodyChanging.getTarget().hasTongueModifier(tongueMod)) {
                contentSB.append("<div  id='TONGUE_MOD_").append(tongueMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(tongueMod.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='TONGUE_MOD_").append(tongueMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(tongueMod.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Tongue Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] tongue."
                        + "<br/><i>Tongue modifiers affect descriptions when performing oral sex.</i>"),
                "TONGUE_MODS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformTongueSizeDiv() {
        contentSB.setLength(0);

        for (TongueLength tongueLength : TongueLength.values()) {
            if (BodyChanging.getTarget().getTongueLength() == tongueLength) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.toWebHexString()).append(";'>").append(Util.capitaliseSentence(tongueLength.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='TONGUE_LENGTH_").append(tongueLength).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_GENERIC.getShades()[0]).append(";'>").append(Util.capitaliseSentence(tongueLength.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Tongue Length",
                UtilText.parse(BodyChanging.getTarget(), "Change the length of [npc.namePos] tongue."
                        + "<br/><i>This affects descriptions when performing oral sex.</i>"),
                "TONGUE_LENGTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAssSizeDiv() {
        contentSB.setLength(0);

        for (AssSize as : AssSize.values()) {
            if (BodyChanging.getTarget().getAssSize() == as) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(as.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(as.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='ASS_SIZE_").append(as).append("' class='cosmetics-button'>").append("<span style='color:").append(as.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(as.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Ass Size",
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] ass."
                        + "<br/><i>This is a purely cosmetic change.</i>"),
                "ASS_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformHipSizeDiv() {
        contentSB.setLength(0);

        for (HipSize hs : HipSize.values()) {
            if (BodyChanging.getTarget().getHipSize() == hs) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(hs.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(hs.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='HIP_SIZE_").append(hs).append("' class='cosmetics-button'>").append("<span style='color:").append(hs.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(hs.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Hip Size",
                UtilText.parse(BodyChanging.getTarget(),
                        "Change the size of [npc.namePos] hips."
                                + (BodyChanging.getTarget().getLegConfiguration() == LegConfiguration.TAIL_LONG
                                ? "<br/>This affects the girth of [npc.her] serpent-tail."
                                : "<br/>If [npc.she] ever gains a serpent-tail lower-body, this will affect its girth.")),
                "HIP_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAnusWetnessDiv() {
        contentSB.setLength(0);

        for (Wetness value : Wetness.values()) {
            if (BodyChanging.getTarget().getAssWetness() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                if (BodyChanging.getTarget().getBodyMaterial().isOrificesAlwaysMaximumWetness()) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='ANUS_WETNESS_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Anus Wetness",
                UtilText.parse(BodyChanging.getTarget(), "Change the wetness of [npc.namePos] asshole."
                        + "<br/><i>This affects pleasure in sex, as non-lubricated orifices negatively affect arousal gains.</i>"
                        + (BodyChanging.getTarget().getBodyMaterial().isOrificesAlwaysMaximumWetness()
                        ? "<br/>[style.italicsWetness8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(Wetness.SEVEN_DROOLING.getDescriptor()) + "'!)]"
                        : "")),
                "ASSHOLE_WETNESS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAnusCapacityDiv() {
        contentSB.setLength(0);

        for (Capacity value : Capacity.getCapacityListFromPreferences()) {
            if (BodyChanging.getTarget().getAssCapacity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='ANUS_CAPACITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Anus Capacity",
                UtilText.parse(BodyChanging.getTarget(), "Change the capacity of [npc.namePos] asshole."
                        + "<br/><i>This affects the size of penetrative objects that [npc.namePos] asshole can take. If capacity is too small or too large for a penetrative object, arousal gains are affected.</i>"),
                "ASSHOLE_CAPACITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAnusDepthDiv() {
        contentSB.setLength(0);

        for (OrificeDepth value : OrificeDepth.values()) {
            if (BodyChanging.getTarget().getAssDepth() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                if (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='ANUS_DEPTH_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Anus Depth",
                UtilText.parse(BodyChanging.getTarget(), "Change the depth of [npc.namePos] asshole."
                        + "<br/><i>Depth of an orifice determines the length of a penetrating object that it can comfortably accommodate.</i>"
                        + (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()
                        ? "<br/>[style.italicsSize8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(OrificeDepth.SEVEN_FATHOMLESS.getDescriptor()) + "'!)]"
                        : "")),
                "ANUS_DEPTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAnusElasticityDiv() {
        contentSB.setLength(0);

        for (OrificeElasticity value : OrificeElasticity.values()) {
            if (BodyChanging.getTarget().getAssElasticity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='ANUS_ELASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Anus Elasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the elasticity of [npc.namePos] asshole."
                        + "<br/><i>This determines how quickly [npc.namePos] asshole will stretch out if a penetrating object is too thick for it."
                        + (Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? " Values marked with an asterisk increase the maximum uncomfortable depth of [npc.her] asshole." : ""))
                        + "</i>",
                "ASSHOLE_ELASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAnusPlasticityDiv() {
        contentSB.setLength(0);

        for (OrificePlasticity value : OrificePlasticity.values()) {
            if (BodyChanging.getTarget().getAssPlasticity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='ANUS_PLASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Anus Plasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the plasticity of [npc.namePos] asshole."
                        + "<br/><i>Plasticity of an orifice determines how quickly, and by how much, it regains its original tightness after being stretched out.</i>"),
                "ASSHOLE_PLASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAnusModifiersDiv() {
        contentSB.setLength(0);

        for (OrificeModifier orificeMod : OrificeModifier.values()) {
            if (BodyChanging.getTarget().hasAssOrificeModifier(orificeMod)) {
                contentSB.append("<div  id='ANUS_MOD_").append(orificeMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='ANUS_MOD_").append(orificeMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Anus Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] anus."
                        + "<br/><i>Orifice modifiers affect descriptions and some actions in sex. The modifiers which have more than just descriptive effects are marked by an asterisk.</i>"),
                "ANUS_MOD",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformBreastSizeDiv() {
        return applyFullVariableWrapper("Breast Size",
                BodyChanging.getTarget().isPlayer()
                        ? "Change the size of your breasts."
                        : UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] breasts."),
                "BREAST_SIZE",
                "1",
                "5",
                BodyChanging.getTarget().getBreastRawSizeValue()
                        + "<br/><i>" + Util.capitaliseSentence(BodyChanging.getTarget().getBreastSize().getCupSizeName()) + (BodyChanging.getTarget().getBreastSize() == CupSize.FLAT ? "" : "-cup") + "</i>",
                BodyChanging.getTarget().getBreastRawSizeValue() <= 0
                        || (BodyChanging.getTarget().getBreastRawSizeValue() <= CupSize.getMinimumCupSizeForEggIncubation().getMeasurement() && BodyChanging.getTarget().getIncubationLitter(SexAreaOrifice.NIPPLE) != null),
                BodyChanging.getTarget().getBreastRawSizeValue() >= CupSize.XXX_N.getMeasurement());
    }

    // This is to stop variable-length names from being generated every time a button is clicked (i.e. stop "crotch-tits", "crotch-breasts")
    private static String getCrotchBoobName(boolean plural) {
        if (BodyChanging.getTarget().getBreastCrotchShape() == BreastShape.UDDERS) {
            return plural ? "udders" : "udder";
        } else {
            return plural ? "crotch-boobs" : "crotch-boob";
        }
    }

    public static String getSelfTransformBreastCrotchSizeDiv() {
        return applyFullVariableWrapper(
                UtilText.parse(BodyChanging.getTarget(), Util.capitaliseSentence(getCrotchBoobName(false)) + " Size"),
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] " + getCrotchBoobName(true) + "."),
                "BREAST_CROTCH_SIZE",
                " cup",
                " cup",
                Util.capitaliseSentence(BodyChanging.getTarget().getBreastCrotchSize().getCupSizeName())
                        + "<br/>(" + BodyChanging.getTarget().getBreastCrotchRawSizeValue() + ")",
                BodyChanging.getTarget().getBreastCrotchRawSizeValue() <= 0
                        || (BodyChanging.getTarget().getBreastCrotchRawSizeValue() <= CupSize.getMinimumCupSizeForEggIncubation().getMeasurement() && BodyChanging.getTarget().getIncubationLitter(SexAreaOrifice.NIPPLE_CROTCH) != null),
                BodyChanging.getTarget().getBreastCrotchRawSizeValue() >= CupSize.XXX_N.getMeasurement());
    }

    public static String getSelfTransformBreastShapeDiv() {
        contentSB.setLength(0);

        for (BreastShape bs : BreastShape.values()) {
            if (!bs.isRestrictedToCrotchBoobs()) {
                if (BodyChanging.getTarget().getBreastShape() == bs) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(bs.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='BREAST_SHAPE_").append(bs).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(bs.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Breast Shape",
                UtilText.parse(BodyChanging.getTarget(), "Change the shape of [npc.namePos] breasts."
                        + "<br/><i>This is primarily a cosmetic change, but also affects some descriptions in sex.</i>"),
                "BREAST_SHAPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformBreastCrotchShapeDiv() {
        contentSB.setLength(0);

        for (BreastShape bs : BreastShape.values()) {
            if (BodyChanging.getTarget().getBreastCrotchShape() == bs) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(bs.getTransformName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='BREAST_CROTCH_SHAPE_").append(bs).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(bs.getTransformName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper(
                UtilText.parse(BodyChanging.getTarget(), Util.capitaliseSentence(getCrotchBoobName(false)) + " Shape"),
                UtilText.parse(BodyChanging.getTarget(), "Change the shape of [npc.namePos] " + getCrotchBoobName(true) + "."
                        + "<br/><i>This is primarily a cosmetic change, but also affects some descriptions in sex.</i>"),
                "BREAST_CROTCH_SHAPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformBreastRowsDiv() {
        contentSB.setLength(0);

        if (Main.getProperties().multiBreasts == 0
                || (Main.getProperties().multiBreasts == 1
                && BodyChanging.getTarget().getTorsoType() == TorsoType.HUMAN)) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("One")).append("</div>");
        } else {
            for (int i = 1; i <= Breast.MAXIMUM_BREAST_ROWS; i++) {
                if (BodyChanging.getTarget().getBreastRows() == i) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='BREAST_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Breast Pairs",
                UtilText.parse(BodyChanging.getTarget(), "Change how many pairs of breasts [npc.name] [npc.has]."
                        + "<br/><i>This is a mostly a cosmetic change, but is also taken into account when determining if there are any free nipples for use in sex.</i>"
                        + (Main.getProperties().multiBreasts == 0
                        ? "<br/>[style.italicsBad(Multi-Breasts are disabled in the content settings!)]"
                        : (Main.getProperties().multiBreasts == 1
                        && BodyChanging.getTarget().getTorsoType() == TorsoType.HUMAN)
                        ? "<br/>[style.italicsBad(Multi-Breasts are disabled for humans in the content settings!)]"
                        : "")),
                "BREAST_ROWS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformBreastCrotchRowsDiv() {
        contentSB.setLength(0);

        int minimum = 1;
        if (BodyChanging.getTarget().getBreastCrotchShape() == BreastShape.UDDERS) {
            minimum = 0;
        }

        for (int i = minimum; i <= BreastCrotch.MAXIMUM_BREAST_ROWS; i++) {
            String name = Util.capitaliseSentence(Util.intToString(i));
            if (i == 0) {
                name = "(Single)";
            }
            if (BodyChanging.getTarget().getBreastCrotchRows() == i) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(name).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='BREAST_CROTCH_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(name).append("</span>").append("</div>");
            }
        }

        return applyWrapper(
                UtilText.parse(BodyChanging.getTarget(), Util.capitaliseSentence(getCrotchBoobName(false)) + " Pairs"),
                UtilText.parse(BodyChanging.getTarget(), "Change how many pairs of " + getCrotchBoobName(false) + " [npc.name] [npc.has]."
                        + "<br/><i>This is a mostly a cosmetic change, but is also taken into account when determining if there are any free crotch-nipples for use in sex.</i>"),
                "BREAST_CROTCH_ROWS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleCountDiv() {
        contentSB.setLength(0);

        int minimum = 1;

        for (int i = minimum; i <= Breast.MAXIMUM_NIPPLES_PER_BREAST; i++) {
            if (BodyChanging.getTarget().getNippleCountPerBreast() == i) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='NIPPLE_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Nipple Count",
                UtilText.parse(BodyChanging.getTarget(), "Change the number of nipples [npc.name] has on each breast."
                        + "<br/><i>This is primarily a cosmetic change, but also affects descriptions in sex.</i>"),
                "NIPPLE_COUNT",
                contentSB.toString(), true);
    }

    public static String getSelfTransformNippleCrotchCountDiv() {
        contentSB.setLength(0);

        int minimum = 1;
        if (BodyChanging.getTarget().getBreastCrotchShape() == BreastShape.UDDERS && BodyChanging.getTarget().getBreastCrotchRows() == 0) {
            minimum = 2;
        }

        for (int i = minimum; i <= BreastCrotch.MAXIMUM_NIPPLES_PER_BREAST; i++) {
            if (BodyChanging.getTarget().getNippleCrotchCountPerBreast() == i) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='NIPPLE_CROTCH_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Nipple Count",
                UtilText.parse(BodyChanging.getTarget(), "Change the number of nipples [npc.name] [npc.has] on each " + getCrotchBoobName(false) + "."
                        + "<br/><i>This is primarily a cosmetic change, but also affects descriptions in sex.</i>"),
                "NIPPLE_CROTCH_COUNT",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleSizeDiv() {
        contentSB.setLength(0);

        for (NippleSize ns : NippleSize.values()) {
            if (BodyChanging.getTarget().getNippleSize() == ns) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='NIPPLE_SIZE_").append(ns).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Nipple Size",
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] nipples."
                        + "<br/><i>This is primarily a cosmetic change, but also affects descriptions in sex.</i>"),
                "NIPPLE_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleCrotchSizeDiv() {
        contentSB.setLength(0);

        for (NippleSize ns : NippleSize.values()) {
            if (BodyChanging.getTarget().getNippleCrotchSize() == ns) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='NIPPLE_CROTCH_SIZE_").append(ns).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Nipple Size",
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] [npc.crotchNipples]."
                        + "<br/><i>This is primarily a cosmetic change, but also affects descriptions in sex.</i>"),
                "NIPPLE_CROTCH_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAreolaeSizeDiv() {
        contentSB.setLength(0);

        for (AreolaeSize as : AreolaeSize.values()) {
            if (BodyChanging.getTarget().getAreolaeSize() == as) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(as.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='AREOLAE_SIZE_").append(as).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(as.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Areolae Size",
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] areolae."
                        + "<br/><i>This is primarily a cosmetic change, but also affects descriptions in sex.</i>"),
                "AREOLAE_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformAreolaeCrotchSizeDiv() {
        contentSB.setLength(0);

        for (AreolaeSize as : AreolaeSize.values()) {
            if (BodyChanging.getTarget().getAreolaeCrotchSize() == as) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(as.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='AREOLAE_CROTCH_SIZE_").append(as).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(as.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Areolae Size",
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] areolae around [npc.her] [npc.crotchNipples]."
                        + "<br/><i>This is primarily a cosmetic change, but also affects descriptions in sex.</i>"),
                "CROTCH_AREOLAE_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleShapeDiv() {
        contentSB.setLength(0);

        for (NippleShape ns : NippleShape.values()) {
            if (BodyChanging.isDebugMenu() || !ns.isAssociatedWithPenetrationContent() || Main.game.isNipplePenEnabled()) {
                if (BodyChanging.getTarget().getNippleShape() == ns) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_SHAPE_").append(ns).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipple Shape",
                UtilText.parse(BodyChanging.getTarget(), "Change the shape of [npc.namePos] nipples."
                        + "<br/><i>This is primarily a cosmetic change, but also affects descriptions and availability of some actions in sex.</i>"),
                "NIPPLE_SHAPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleCrotchShapeDiv() {
        contentSB.setLength(0);

        for (NippleShape ns : NippleShape.values()) {
            if (BodyChanging.isDebugMenu() || !ns.isAssociatedWithPenetrationContent() || Main.game.isNipplePenEnabled()) {
                if (BodyChanging.getTarget().getNippleCrotchShape() == ns) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_CROTCH_SHAPE_").append(ns).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Crotch-nipple Shape",
                UtilText.parse(BodyChanging.getTarget(), "Change the shape of [npc.namePos] [npc.crotchNipples]."
                        + "<br/><i>This is primarily a cosmetic change, but also affects descriptions and availability of some actions in sex.</i>"),
                "NIPPLE_CROTCH_SHAPE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleCapacityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (Capacity value : Capacity.getCapacityListFromPreferences()) {
                if (BodyChanging.getTarget().getNippleCapacity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_CAPACITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipple Capacity",
                UtilText.parse(BodyChanging.getTarget(), "Change the capacity of [npc.namePos] nipples."
                        + "<br/><i>Anything other than a value of zero qualifies [npc.namePos] nipples as being able to be penetrated during sex. (Subject to your 'nipple penetration' preference in the content options.)</i>"),
                "NIPPLE_CAPACITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleCrotchCapacityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (Capacity value : Capacity.getCapacityListFromPreferences()) {
                if (BodyChanging.getTarget().getNippleCrotchCapacity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_CROTCH_CAPACITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipple Capacity",
                UtilText.parse(BodyChanging.getTarget(), "Change the capacity of [npc.namePos] [npc.crotchNipples]."
                        + "<br/><i>Anything other than a value of zero qualifies [npc.namePos] nipples as being able to be penetrated during sex. (Subject to your 'nipple penetration' preference in the content options.)</i>"),
                "NIPPLE_CROTCH_CAPACITY",
                contentSB.toString(),
                true);
    }

    public static int getLactationUpperLimit() {
        if (Main.game.isInNewWorld()) {
            return Lactation.SEVEN_MONSTROUS_AMOUNT_POURING.getMaximumValue();
        } else {
            return 150;
        }
    }

    public static String getSelfTransformLactationDiv() {
        String title = "Lactation";
        String description = UtilText.parse(BodyChanging.getTarget(), "Change the maximum amount of milk [npc.name] can store in [npc.her] [npc.breasts]."
                + "<br/><i>Once drained, [npc.namePos] breasts fill with milk up to this value at a rate determined by [npc.her] milk regeneration value.</i>");
        String id = "MILK_PRODUCTION";
        if (!Main.game.isLactationContentEnabled()) {
            return applyWrapper(title, description, id,
                    "<div class='cosmetics-button disabled'>"
                            + Util.capitaliseSentence("Lactation Disabled")
                            + "</div>", true);
        } else {
            return applyVariableWrapperFluids(title,
                    description,
                    id,
                    Units.fluid(BodyChanging.getTarget().getBreastRawMilkStorageValue(), ValueType.PRECISE)
                            + "<br/><i>" + Util.capitaliseSentence(BodyChanging.getTarget().getBreastMilkStorage().getName()) + "</i>",
                    BodyChanging.getTarget().getBreastRawMilkStorageValue() <= 0,
                    BodyChanging.getTarget().getBreastRawMilkStorageValue() >= getLactationUpperLimit(),
                    FLUID_INCREMENT_SMALL,
                    FLUID_INCREMENT_AVERAGE,
                    FLUID_INCREMENT_LARGE,
                    FLUID_INCREMENT_HUGE);
        }
    }

    public static int getLactationCrotchUpperLimit() {
        return Lactation.SEVEN_MONSTROUS_AMOUNT_POURING.getMaximumValue();
    }

    public static String getSelfTransformLactationCrotchDiv() {
        String title = "Lactation";
        String description = UtilText.parse(BodyChanging.getTarget(), "Change the maximum amount of milk [npc.name] can store in [npc.her] [npc.crotchBoobs]."
                + "<br/><i>Once drained, [npc.namePos] [npc.crotchBoobs] fill with milk up to this value at a rate determined by [npc.her] crotch-milk regeneration value.</i>");
        String id = "MILK_CROTCH_PRODUCTION";
        if (!Main.game.isLactationContentEnabled()) {
            return applyWrapper(title, description, id,
                    "<div class='cosmetics-button disabled'>"
                            + Util.capitaliseSentence("Lactation Disabled")
                            + "</div>", true);
        } else {
            return applyVariableWrapperFluids(title,
                    description,
                    id,
                    Units.fluid(BodyChanging.getTarget().getBreastCrotchRawMilkStorageValue(), ValueType.PRECISE)
                            + "<br/><i>" + Util.capitaliseSentence(BodyChanging.getTarget().getBreastCrotchMilkStorage().getName()) + "</i>",
                    BodyChanging.getTarget().getBreastCrotchRawMilkStorageValue() <= 0,
                    BodyChanging.getTarget().getBreastCrotchRawMilkStorageValue() >= getLactationCrotchUpperLimit(),
                    FLUID_INCREMENT_SMALL,
                    FLUID_INCREMENT_AVERAGE,
                    FLUID_INCREMENT_LARGE,
                    FLUID_INCREMENT_HUGE);
        }
    }

    public static String getSelfTransformLactationRegenerationDiv() {
        String title = "Milk Regeneration Per Breast";
        String description = UtilText.parse(BodyChanging.getTarget(), "Alter the rate at which milk is produced in <b>each</b> of [npc.namePos] breasts. The more breasts [npc.sheHasFull], the more milk [npc.she] produces per second."
                + "<br/><i>Once drained, [npc.namePos] breasts fill with milk up to their maximum storage value at this rate.</i>");
        String id = "MILK_REGENERATION";
        if (!Main.game.isLactationContentEnabled()) {
            return applyWrapper(title, description, id,
                    "<div class='cosmetics-button disabled'>"
                            + Util.capitaliseSentence("Lactation Disabled")
                            + "</div>", true);
        } else {
            return applyVariableWrapperFluids(title,
                    description,
                    id,
                    Units.fluid(BodyChanging.getTarget().getBreastRawLactationRegenerationValue(), ValueType.PRECISE) + "/day"
                            + "<br/>(" + Units.fluid(BodyChanging.getTarget().getLactationRegenerationPerSecond(false) * 60, ValueType.PRECISE) + "/minute)"
                            + "<br/><i>" + Util.capitaliseSentence(BodyChanging.getTarget().getBreastLactationRegeneration().getName()) + "</i>",
                    BodyChanging.getTarget().getBreastRawLactationRegenerationValue() <= 0,
                    BodyChanging.getTarget().getBreastRawLactationRegenerationValue() >= FluidRegeneration.FOUR_VERY_RAPID.getMaximumRegenerationValuePerDay(),
                    FLUID_REGEN_INCREMENT_SMALL,
                    FLUID_REGEN_INCREMENT_AVERAGE,
                    FLUID_REGEN_INCREMENT_LARGE,
                    FLUID_REGEN_INCREMENT_HUGE);
        }
    }

    public static String getSelfTransformLactationCrotchRegenerationDiv() {
        String title = "Milk Regeneration Per Crotch-boob";
        String description = UtilText.parse(BodyChanging.getTarget(), "Alter the rate at which milk is produced in <b>each</b> of [npc.namePos] [npc.crotchBoobs]. The more [npc.crotchBoobs] [npc.sheHasFull], the more milk [npc.she] produces per second."
                + "<br/><i>Once drained, [npc.namePos] [npc.crotchBoobs] fill with milk up to their maximum storage value at this rate.</i>");
        String id = "MILK_CROTCH_REGENERATION";
        if (!Main.game.isLactationContentEnabled()) {
            return applyWrapper(title, description, id,
                    "<div class='cosmetics-button disabled'>"
                            + Util.capitaliseSentence("Lactation Disabled")
                            + "</div>", true);
        } else {
            return applyVariableWrapperFluids(title,
                    description,
                    id,
                    Units.fluid(BodyChanging.getTarget().getBreastCrotchRawLactationRegenerationValue(), ValueType.PRECISE) + "/day"
                            + "<br/>(" + Units.fluid(BodyChanging.getTarget().getCrotchLactationRegenerationPerSecond(false) * 60, ValueType.PRECISE) + "/minute)"
                            + "<br/><i>" + Util.capitaliseSentence(BodyChanging.getTarget().getBreastCrotchLactationRegeneration().getName()) + "</i>",
                    BodyChanging.getTarget().getBreastCrotchRawLactationRegenerationValue() <= 0,
                    BodyChanging.getTarget().getBreastCrotchRawLactationRegenerationValue() >= FluidRegeneration.FOUR_VERY_RAPID.getMaximumRegenerationValuePerDay(),
                    FLUID_REGEN_INCREMENT_SMALL,
                    FLUID_REGEN_INCREMENT_AVERAGE,
                    FLUID_REGEN_INCREMENT_LARGE,
                    FLUID_REGEN_INCREMENT_HUGE);
        }
    }

    public static String getSelfTransformLactationFlavourDiv() {
        contentSB.setLength(0);

        if (!Main.game.isLactationContentEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Lactation Disabled")).append("</div>");
        } else {
            for (FluidFlavour flavour : FluidFlavour.values()) {
                if (BodyChanging.getTarget().getMilkFlavour().equals(flavour)) {
                    contentSB.append("<div id='MILK_FLAVOUR_").append(flavour).append("' class='cosmetics-button active'>").append("<span style='color:").append(flavour.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(flavour.getName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='MILK_FLAVOUR_").append(flavour).append("' class='cosmetics-button'>").append("<span style='color:").append(flavour.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(flavour.getName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Milk Flavour",
                UtilText.parse(BodyChanging.getTarget(), "Change the flavour of [npc.namePos] milk."
                        + "<br/><i>This will affect descriptions both in and out of sex.</i>"),
                "MILK_FLAVOUR",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformLactationModifiersDiv() {
        contentSB.setLength(0);

        if (!Main.game.isLactationContentEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Lactation Disabled")).append("</div>");
        } else {
            for (FluidModifier modifier : FluidModifier.values()) {
                if (BodyChanging.getTarget().hasMilkModifier(modifier)) {
                    contentSB.append("<div id='MILK_MODIFIER_").append(modifier).append("' class='cosmetics-button active'>").append("<span style='color:").append(modifier.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(modifier.getName())).append(modifier.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='MILK_MODIFIER_").append(modifier).append("' class='cosmetics-button'>").append("<span style='color:").append(modifier.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(modifier.getName())).append(modifier.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Milk Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Add or remove modifiers to [npc.namePos] milk."
                        + "<br/><i>This will affect descriptions both in and out of sex. The modifiers with an asterisk next to their name have special in-game effects, such as the application of status effects when ingested.</i>"),
                "MILK_MODIFIER",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformLactationCrotchFlavourDiv() {
        contentSB.setLength(0);

        if (!Main.game.isLactationContentEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Lactation Disabled")).append("</div>");
        } else {
            for (FluidFlavour flavour : FluidFlavour.values()) {
                if (BodyChanging.getTarget().getMilkCrotchFlavour().equals(flavour)) {
                    contentSB.append("<div id='MILK_CROTCH_FLAVOUR_").append(flavour).append("' class='cosmetics-button active'>").append("<span style='color:").append(flavour.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(flavour.getName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='MILK_CROTCH_FLAVOUR_").append(flavour).append("' class='cosmetics-button'>").append("<span style='color:").append(flavour.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(flavour.getName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Milk Flavour",
                UtilText.parse(BodyChanging.getTarget(), "Change the flavour of [npc.namePos] [npc.crotchMilk]."
                        + "<br/><i>This will affect descriptions both in and out of sex.</i>"),
                "MILK_CROTCH_FLAVOUR",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformLactationCrotchModifiersDiv() {
        contentSB.setLength(0);

        if (!Main.game.isLactationContentEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Lactation Disabled")).append("</div>");
        } else {
            for (FluidModifier modifier : FluidModifier.values()) {
                if (BodyChanging.getTarget().hasMilkCrotchModifier(modifier)) {
                    contentSB.append("<div id='MILK_CROTCH_MODIFIER_").append(modifier).append("' class='cosmetics-button active'>").append("<span style='color:").append(modifier.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(modifier.getName())).append(modifier.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='MILK_CROTCH_MODIFIER_").append(modifier).append("' class='cosmetics-button'>").append("<span style='color:").append(modifier.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(modifier.getName())).append(modifier.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Milk Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Add or remove modifiers to [npc.namePos] [npc.crotchMilk]."
                        + "<br/><i>This will affect descriptions both in and out of sex. The modifiers with an asterisk next to their name have special in-game effects, such as the application of status effects when ingested.</i>"),
                "MILK_CROTCH_MODIFIER",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleDepthDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (OrificeDepth value : OrificeDepth.values()) {
                if (BodyChanging.getTarget().getNippleDepth() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    if (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()) {
                        contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                    } else {
                        contentSB.append("<div id='NIPPLE_DEPTH_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                    }
                }
            }
        }

        return applyWrapper("Nipple Depth",
                UtilText.parse(BodyChanging.getTarget(), "Change the depth of [npc.namePos] [npc.nipples]."
                        + "<br/><i>Depth of an orifice determines the length of a penetrating object that it can comfortably accommodate.</i>"
                        + (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()
                        ? "<br/>[style.italicsSize8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(OrificeDepth.SEVEN_FATHOMLESS.getDescriptor()) + "'!)]"
                        : "")),
                "NIPPLE_DEPTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleCrotchDepthDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (OrificeDepth value : OrificeDepth.values()) {
                if (BodyChanging.getTarget().getNippleCrotchDepth() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    if (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()) {
                        contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                    } else {
                        contentSB.append("<div id='NIPPLE_CROTCH_DEPTH_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                    }
                }
            }
        }

        return applyWrapper("Nipple Depth",
                UtilText.parse(BodyChanging.getTarget(), "Change the depth of [npc.namePos] [npc.crotchNipples]."
                        + "<br/><i>Depth of an orifice determines the length of a penetrating object that it can comfortably accommodate.</i>"
                        + (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()
                        ? "<br/>[style.italicsSize8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(OrificeDepth.SEVEN_FATHOMLESS.getDescriptor()) + "'!)]"
                        : "")),
                "NIPPLE_CROTCH_DEPTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleElasticityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (OrificeElasticity value : OrificeElasticity.values()) {
                if (BodyChanging.getTarget().getNippleElasticity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_ELASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipple Elasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the elasticity of [npc.namePos] nipples."
                        + "<br/><i>This determines how quickly [npc.namePos] nipples will stretch out if a penetrating object is too thick for them."
                        + (Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? " Values marked with an asterisk increase the maximum uncomfortable depth of [npc.her] nipples." : ""))
                        + "</i>",
                "NIPPLE_ELASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleCrotchElasticityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (OrificeElasticity value : OrificeElasticity.values()) {
                if (BodyChanging.getTarget().getNippleCrotchElasticity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_CROTCH_ELASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipple Elasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the elasticity of [npc.namePos] [npc.crotchNipples]."
                        + "<br/><i>This determines how quickly [npc.namePos] [npc.crotchNipples] will stretch out if a penetrating object is too thick for them."
                        + (Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? " Values marked with an asterisk increase the maximum uncomfortable depth of [npc.her] [npc.crotchNipples]." : ""))
                        + "</i>",
                "NIPPLE_CROTCH_ELASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNipplePlasticityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (OrificePlasticity value : OrificePlasticity.values()) {
                if (BodyChanging.getTarget().getNipplePlasticity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_PLASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipple Plasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the plasticity of [npc.namePos] nipples."
                        + "<br/><i>Plasticity of an orifice determines how quickly, and by how much, it regains its original tightness after being stretched out.</i>"),
                "NIPPLE_PLASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleCrotchPlasticityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (OrificePlasticity value : OrificePlasticity.values()) {
                if (BodyChanging.getTarget().getNippleCrotchPlasticity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_CROTCH_PLASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipple Plasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the plasticity of [npc.namePos] [npc.crotchNipples]."
                        + "<br/><i>Plasticity of an orifice determines how quickly, and by how much, it regains its original tightness after being stretched out.</i>"),
                "NIPPLE_CROTCH_PLASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleModifiersDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (OrificeModifier orificeMod : OrificeModifier.values()) {
                if (BodyChanging.getTarget().hasNippleOrificeModifier(orificeMod)) {
                    contentSB.append("<div id='NIPPLE_MOD_").append(orificeMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_MOD_").append(orificeMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipple Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] nipples."
                        + "<br/><i>Orifice modifiers affect descriptions and some actions in sex. The modifiers which have more than just descriptive effects are marked by an asterisk.</i>"),
                "NIPPLE_MODS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformNippleCrotchModifiersDiv() {
        contentSB.setLength(0);

        if (!Main.game.isNipplePenEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Nipple Pen Disabled")).append("</div>");
        } else {
            for (OrificeModifier orificeMod : OrificeModifier.values()) {
                if (BodyChanging.getTarget().hasNippleCrotchOrificeModifier(orificeMod)) {
                    contentSB.append("<div id='NIPPLE_CROTCH_MOD_").append(orificeMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='NIPPLE_CROTCH_MOD_").append(orificeMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Nipple Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] [npc.crotchNipples]."
                        + "<br/><i>Orifice modifiers affect descriptions and some actions in sex. The modifiers which have more than just descriptive effects are marked by an asterisk.</i>"),
                "NIPPLE_CROTCH_MODS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaChoiceDiv(List<AbstractRace> availableRaces) {
        contentSB.setLength(0);

        for (AbstractVaginaType vagina : VaginaType.getAllVaginaTypes()) {
            if (((vagina.getRace() != null && availableRaces.contains(vagina.getRace()))
                    || vagina == VaginaType.NONE)
                    && vagina != VaginaType.ONAHOLE) {

                Colour c = PresetColour.TEXT_GREY;

                if (vagina.getRace() != null) {
                    c = vagina.getRace().getColour();
                }


                if (BodyChanging.getTarget().getVaginaType() == vagina) {
                    contentSB.append("<div id='VAGINA_").append(VaginaType.getIdFromVaginaType(vagina)).append("' class='cosmetics-button active'>").append("<span style='color:").append(c.toWebHexString()).append(";'>").append(Util.capitaliseSentence(vagina.getTransformName())).append(vagina.isEggLayer() ? "*" : "").append("</span>").append("</div>");

                } else {
                    boolean cannotChoose = vagina == VaginaType.NONE
                            && (BodyChanging.getTarget().isPregnant() || BodyChanging.getTarget().hasStatusEffect(StatusEffect.PREGNANT_0) || BodyChanging.getTarget().hasIncubationLitter(SexAreaOrifice.VAGINA));
                    contentSB.append("<div id='VAGINA_").append(VaginaType.getIdFromVaginaType(vagina)).append("' class='cosmetics-button").append(cannotChoose ? " disabled" : "").append("'>").append("<span style='color:").append(cannotChoose ? PresetColour.TEXT_GREY.toWebHexString() : c.getShades()[0]).append(";'>").append(Util.capitaliseSentence(vagina.getTransformName())).append(vagina.isEggLayer() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Vagina",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] vagina type. Types with an asterisk by their names [style.colourEgg(lay eggs)] by default."
//					+ "<br/><i>Vagina type affects default vagina attributes and descriptions.</i>"
                        + "<br/>[style.italicsMinorBad(A character's vagina cannot be removed while incubating eggs or having a possibility of being pregnant!)]"),
                "VAGINA_TYPE",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformGirlcumFlavourDiv() {
        contentSB.setLength(0);

        for (FluidFlavour flavour : FluidFlavour.values()) {
            if (BodyChanging.getTarget().getGirlcumFlavour().equals(flavour)) {
                contentSB.append("<div id='GIRLCUM_FLAVOUR_").append(flavour).append("' class='cosmetics-button active'>").append("<span style='color:").append(flavour.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(flavour.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='GIRLCUM_FLAVOUR_").append(flavour).append("' class='cosmetics-button'>").append("<span style='color:").append(flavour.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(flavour.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Girlcum Flavour",
                UtilText.parse(BodyChanging.getTarget(), "Change the flavour of [npc.namePos] girlcum."
                        + "<br/><i>This will affect descriptions both in and out of sex.</i>"),
                "GIRLCUM_FLAVOUR",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformGirlcumModifiersDiv() {
        contentSB.setLength(0);

        for (FluidModifier modifier : FluidModifier.values()) {
            if (BodyChanging.getTarget().hasGirlcumModifier(modifier)) {
                contentSB.append("<div id='GIRLCUM_MODIFIER_").append(modifier).append("' class='cosmetics-button active'>").append("<span style='color:").append(modifier.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(modifier.getName())).append(modifier.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='GIRLCUM_MODIFIER_").append(modifier).append("' class='cosmetics-button'>").append("<span style='color:").append(modifier.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(modifier.getName())).append(modifier.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Girlcum Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Add or remove modifiers to [npc.namePos] girlcum."
                        + "<br/><i>This will affect descriptions both in and out of sex. The modifiers with an asterisk next to their name have special in-game effects, such as the application of status effects when ingested.</i>"),
                "GIRLCUM_MODIFIER",
                contentSB.toString(),
                false);
    }

    public static String getSelfTransformVaginaCapacityDiv() {
        contentSB.setLength(0);

        for (Capacity value : Capacity.getCapacityListFromPreferences()) {
            if (BodyChanging.getTarget().getVaginaCapacity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='VAGINA_CAPACITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Vagina Capacity",
                UtilText.parse(BodyChanging.getTarget(), "Change the capacity of [npc.namePos] vagina."
                        + "<br/><i>This affects the size of penetrative objects that [npc.namePos] pussy can take. If capacity is too small or too large for a penetrative object, arousal gains are affected.</i>"),
                "VAGINA_CAPACITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaSquirterDiv() {
        contentSB.setLength(0);

        if (BodyChanging.getTarget().isVaginaSquirter()) {
            contentSB.append("<div id='VAGINA_SQUIRTER_OFF' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.GENERIC_WETNESS_ONE.getShades()[0]).append(";'>Not squirter</span>").append("</div>").append("<div class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.GENERIC_WETNESS_EIGHT.toWebHexString()).append(";'>Squirter</span>").append("</div>");
        } else {
            contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_WETNESS_ONE.toWebHexString()).append(";'>Not squirter</span>").append("</div>").append("<div id='VAGINA_SQUIRTER_ON' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.GENERIC_WETNESS_EIGHT.getShades()[0]).append(";'>Squirter</span>").append("</div>");
        }


        return applyWrapper("Squirter",
                UtilText.parse(BodyChanging.getTarget(), "Set whether [npc.namePos] vagina squirts fluids when [npc.she] [npc.verb(orgasm)]."
                        + "<br/><i>If [npc.nameIsFull] a squirter, then when [npc.she] [npc.verb(orgasm)] [npc.she] will dirty clothes [npc.she] [npc.verb(squirt)] on."
                        + " Also, if someone is eating [npc.herHim] out, they will ingest [npc.her] fluids.</i>"),
                "VAGINA_SQUIRTER",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaHymenDiv() {
        contentSB.setLength(0);

        if (BodyChanging.getTarget().hasHymen()) {
            contentSB.append("<div id='VAGINA_HYMEN_OFF' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.GENERIC_MINOR_BAD.getShades()[0]).append(";'>Lost</span>").append("</div>").append("<div class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.GENERIC_MINOR_GOOD.toWebHexString()).append(";'>Intact</span>").append("</div>");
        } else {
            contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_MINOR_BAD.toWebHexString()).append(";'>Lost</span>").append("</div>").append("<div id='VAGINA_HYMEN_ON' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.GENERIC_MINOR_GOOD.getShades()[0]).append(";'>Intact</span>").append("</div>");
        }


        return applyWrapper("Hymen",
                UtilText.parse(BodyChanging.getTarget(), "Set whether [npc.namePos] hymen is intact or not."),
                "VAGINA_HYMEN",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaEggLayerDiv() {
        contentSB.setLength(0);

        boolean disabled = BodyChanging.getTarget().isPregnant();

        if (BodyChanging.getTarget().isVaginaEggLayer()) {
            contentSB.append("<div id='VAGINA_EGG_LAYER_OFF' class='cosmetics-button").append(disabled ? " disabled" : "").append("'>").append("<span style='color:").append(PresetColour.GENERIC_SEX.getShades()[0]).append(";'>Live birth</span>").append("</div>").append("<div class='cosmetics-button").append(disabled ? " disabled" : " active").append("'>").append("<span style='color:").append(PresetColour.EGG.toWebHexString()).append(";'>Egg-layer</span>").append("</div>");
        } else {
            contentSB.append("<div class='cosmetics-button").append(disabled ? " disabled" : " active").append("'>").append("<span style='color:").append(PresetColour.GENERIC_SEX.toWebHexString()).append(";'>Live birth</span>").append("</div>").append("<div id='VAGINA_EGG_LAYER_ON' class='cosmetics-button").append(disabled ? " disabled" : "").append("'>").append("<span style='color:").append(PresetColour.EGG.getShades()[0]).append(";'>Egg-layer</span>").append("</div>");
        }

        return applyWrapper("Birth Type",
                UtilText.parse(BodyChanging.getTarget(), "Set whether [npc.namePos] gives birth to live young or lays eggs. [style.italicsMinorBad(Cannot be changed while pregnant.)]"
                        + "<br/><i>Every time vagina type is changed, this value is reset to the vagina type's default birth type value.</i>"),
                "VAGINA_EGG_LAYER",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaWetnessDiv() {
        contentSB.setLength(0);

        for (Wetness value : Wetness.values()) {
            if (BodyChanging.getTarget().getVaginaWetness() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                if (BodyChanging.getTarget().getBodyMaterial().isOrificesAlwaysMaximumWetness()) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='VAGINA_WETNESS_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Vagina Wetness",
                UtilText.parse(BodyChanging.getTarget(), "Change the wetness of [npc.namePos] vagina."
                        + "<br/><i>This affects pleasure in sex, as non-lubricated orifices negatively affect arousal gains.</i>"
                        + (BodyChanging.getTarget().getBodyMaterial().isOrificesAlwaysMaximumWetness()
                        ? "<br/>[style.italicsWetness8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(Wetness.SEVEN_DROOLING.getDescriptor()) + "'!)]"
                        : "")),
                "VAGINA_WETNESS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaDepthDiv() {
        contentSB.setLength(0);

        for (OrificeDepth value : OrificeDepth.values()) {
            if (BodyChanging.getTarget().getVaginaDepth() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                if (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='VAGINA_DEPTH_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Vagina Depth",
                UtilText.parse(BodyChanging.getTarget(), "Change the depth of [npc.namePos] vagina."
                        + "<br/><i>Depth of an orifice determines the length of a penetrating object that it can comfortably accommodate.</i>"
                        + (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()
                        ? "<br/>[style.italicsSize8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(OrificeDepth.SEVEN_FATHOMLESS.getDescriptor()) + "'!)]"
                        : "")),
                "VAGINA_DEPTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaElasticityDiv() {
        contentSB.setLength(0);

        for (OrificeElasticity value : OrificeElasticity.values()) {
            if (BodyChanging.getTarget().getVaginaElasticity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='VAGINA_ELASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Vagina Elasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the elasticity of [npc.namePos] vagina."
                        + "<br/><i>This determines how quickly [npc.namePos] vagina will stretch out if a penetrating object is too thick for it."
                        + (Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? " Values marked with an asterisk increase the maximum uncomfortable depth of [npc.her] vagina." : ""))
                        + "</i>",
                "VAGINA_ELASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaPlasticityDiv() {
        contentSB.setLength(0);

        for (OrificePlasticity value : OrificePlasticity.values()) {
            if (BodyChanging.getTarget().getVaginaPlasticity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='VAGINA_PLASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Vagina Plasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the plasticity of [npc.namePos] vagina."
                        + "<br/><i>Plasticity of an orifice determines how quickly, and by how much, it regains its original tightness after being stretched out.</i>"),
                "VAGINA_PLASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformClitorisSizeDiv() {
        return applyFullVariableWrapper("Clitoris Length",
                UtilText.parse(BodyChanging.getTarget(), "Change the length of [npc.namePos] clitoris."
                        + " All sizes larger than '<i style='color:" + ClitorisSize.ONE_BIG.getColour().toWebHexString() + ";'>"
                        + Util.capitaliseSentence(ClitorisSize.ONE_BIG.getDescriptor()) + "</i>' enable [npc.her] clitoris to be used for penetrative actions in sex."
                        + " (Size will be marked by an asterisk when large enough to be used as a pseudo penis.)"),
                "CLITORIS_SIZE",
                Units.size(1),
                Units.size(5),
                Units.size(BodyChanging.getTarget().getVaginaRawClitorisSizeValue(), Units.ValueType.PRECISE, Units.UnitType.SHORT)
                        + "<br/><i style='color:" + BodyChanging.getTarget().getVaginaClitorisSize().getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(BodyChanging.getTarget().getVaginaClitorisSize().getDescriptor())
                        + (BodyChanging.getTarget().getVaginaClitorisSize().isPseudoPenisSize() ? "*" : "")
                        + "</i>"
                        + "<br/><i>Diameter: " + Units.size(BodyChanging.getTarget().getClitorisDiameter()) + "</i>",
                BodyChanging.getTarget().getVaginaRawClitorisSizeValue() <= 0,
                BodyChanging.getTarget().getVaginaRawClitorisSizeValue() >= ClitorisSize.SEVEN_STALLION.getMaximumValue());
    }

    public static String getSelfTransformClitorisGirthDiv() {
        contentSB.setLength(0);

        for (PenetrationGirth girth : PenetrationGirth.values()) {
            if (BodyChanging.getTarget().getClitorisGirth() == girth) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(girth.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(girth.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='CLITORIS_GIRTH_").append(girth).append("' class='cosmetics-button'>").append("<span style='color:").append(girth.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(girth.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Clitoris Girth",
                UtilText.parse(BodyChanging.getTarget(), "Change the girth of [npc.namePos] clitoris."
                        + "<br/><i>Clitoris girth has an impact in determining whether it is too thick for the orifice it is penetrating.</i>"),
                "CLITORIS_GIRTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformLabiaSizeDiv() {
        contentSB.setLength(0);

        for (LabiaSize size : LabiaSize.values()) {
            if (BodyChanging.getTarget().getVaginaLabiaSize() == size) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(size.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(size.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='LABIA_SIZE_").append(size).append("' class='cosmetics-button'>").append("<span style='color:").append(size.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(size.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Labia Size",
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] labia."
                        + "<br/><i>This is a purely cosmetic change, affecting descriptions in and out of sex.</i>"),
                "LABIA_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaModifiersDiv() {
        contentSB.setLength(0);

        for (OrificeModifier orificeMod : OrificeModifier.values()) {
            if (BodyChanging.getTarget().hasVaginaOrificeModifier(orificeMod)) {
                contentSB.append("<div id='VAGINA_MOD_").append(orificeMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='VAGINA_MOD_").append(orificeMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Vagina Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] vagina."
                        + "<br/><i>Orifice modifiers affect descriptions and some actions in sex. The modifiers which have more than just descriptive effects are marked by an asterisk.</i>"),
                "VAGINA_MODS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaUrethraCapacityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (Capacity value : Capacity.getCapacityListFromPreferences()) {
                if (BodyChanging.getTarget().getVaginaUrethraCapacity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='VAGINA_URETHRA_CAPACITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Urethra Capacity",
                UtilText.parse(BodyChanging.getTarget(), "Change the capacity of [npc.namePos] urethra."
                        + "<br/><i>Anything other than a value of zero qualifies [npc.namePos] vaginal urethra as being able to be penetrated during sex. (Subject to your 'urethra penetration' preference in the content options.)</i>"),
                "VAGINA_URETHRA_CAPACITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaUrethraDepthDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (OrificeDepth value : OrificeDepth.values()) {
                if (BodyChanging.getTarget().getVaginaUrethraDepth() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    if (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()) {
                        contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                    } else {
                        contentSB.append("<div id='VAGINA_URETHRA_DEPTH_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                    }
                }
            }
        }

        return applyWrapper("Urethra Depth",
                UtilText.parse(BodyChanging.getTarget(), "Change the depth of [npc.namePos] urethra."
                        + "<br/><i>Depth of an orifice determines the length of a penetrating object that it can comfortably accommodate.</i>"
                        + (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()
                        ? "<br/>[style.italicsSize8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(OrificeDepth.SEVEN_FATHOMLESS.getDescriptor()) + "'!)]"
                        : "")),
                "VAGINA_URETHRA_DEPTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaUrethraElasticityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (OrificeElasticity value : OrificeElasticity.values()) {
                if (BodyChanging.getTarget().getVaginaUrethraElasticity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='VAGINA_URETHRA_ELASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Urethra Elasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the elasticity of [npc.namePos] urethra."
                        + "<br/><i>This determines how quickly [npc.namePos] urethra will stretch out if a penetrating object is too thick for it."
                        + (Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? " Values marked with an asterisk increase the maximum uncomfortable depth of [npc.her] urethra." : ""))
                        + "</i>",
                "VAGINA_URETHRA_ELASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaUrethraPlasticityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (OrificePlasticity value : OrificePlasticity.values()) {
                if (BodyChanging.getTarget().getVaginaUrethraPlasticity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='VAGINA_URETHRA_PLASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Urethra Plasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the plasticity of [npc.namePos] urethra."
                        + "<br/><i>Plasticity of an orifice determines how quickly, and by how much, it regains its original tightness after being stretched out.</i>"),
                "VAGINA_URETHRA_PLASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformVaginaUrethraModifiersDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (OrificeModifier orificeMod : OrificeModifier.values()) {
                if (BodyChanging.getTarget().hasVaginaUrethraOrificeModifier(orificeMod)) {
                    contentSB.append("<div id='VAGINA_URETHRA_MOD_").append(orificeMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='VAGINA_URETHRA_MOD_").append(orificeMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Urethra Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] urethra."
                        + "<br/><i>Orifice modifiers affect descriptions and some actions in sex. The modifiers which have more than just descriptive effects are marked by an asterisk.</i>"),
                "VAGINA_URETHRA_MODS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformPenisChoiceDiv(List<AbstractRace> availableRaces, boolean halfWidth) {
        contentSB.setLength(0);

        for (AbstractPenisType penis : PenisType.getAllPenisTypes()) {
            if (((penis.getRace() != null && availableRaces.contains(penis.getRace()))
                    || penis == PenisType.NONE)
                    && penis != PenisType.DILDO) {

                Colour c = PresetColour.TEXT_GREY;

                if (penis.getRace() != null) {
                    c = penis.getRace().getColour();
                }

                if (BodyChanging.getTarget().getPenisType() == penis) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(c.toWebHexString()).append(";'>").append(Util.capitaliseSentence(penis.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='PENIS_").append(PenisType.getIdFromPenisType(penis)).append("' class='cosmetics-button'>").append("<span style='color:").append(c.getShades()[0]).append(";'>").append(Util.capitaliseSentence(penis.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Penis",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] penis type."
                        + "<br/><i>Penis type affects the modifiers and other attributes of the penis which characters spawn in with, as well as descriptions both in and out of sex.</i>"),
                "PENIS_TYPE",
                contentSB.toString(),
                halfWidth);
    }

    public static String getSelfTransformPenisSizeDiv() {
        return applyFullVariableWrapper("Penis Length",
                (BodyChanging.getTarget().isPlayer()
                        ? "Change the length of your penis."
                        : UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] penis.")),
                "PENIS_SIZE",
                Units.size(1),
                Units.size(5),
                Units.size(BodyChanging.getTarget().getPenisRawSizeValue(), Units.ValueType.PRECISE, Units.UnitType.SHORT)
                        + "<br/><i style='color:" + BodyChanging.getTarget().getPenisSize().getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(BodyChanging.getTarget().getPenisSize().getDescriptor()) + "</i>"
                        + "<br/><i>Diameter: " + Units.size(BodyChanging.getTarget().getPenisDiameter()) + "</i>",
                BodyChanging.getTarget().getPenisRawSizeValue() <= 0,
                BodyChanging.getTarget().getPenisRawSizeValue() >= PenisLength.SEVEN_STALLION.getMaximumValue());
    }

    public static String getSelfTransformPenisGirthDiv() {
        contentSB.setLength(0);

        for (PenetrationGirth girth : PenetrationGirth.values()) {
            if (BodyChanging.getTarget().getPenisGirth() == girth) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(girth.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(girth.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='PENIS_GIRTH_").append(girth).append("' class='cosmetics-button'>").append("<span style='color:").append(girth.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(girth.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Penis Girth",
                UtilText.parse(BodyChanging.getTarget(), "Change the girth of [npc.namePos] penis."
                        + "<br/><i>Penis girth has an impact in determining whether a penis is too large for the orifice it is penetrating.</i>"),
                "PENIS_GIRTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformTesticleSizeDiv() {
        contentSB.setLength(0);

        for (TesticleSize size : TesticleSize.values()) {
            if (BodyChanging.getTarget().getTesticleSize() == size) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(size.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(size.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='TESTICLE_SIZE_").append(size).append("' class='cosmetics-button'>").append("<span style='color:").append(size.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(size.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Testicle Size",
                UtilText.parse(BodyChanging.getTarget(), "Change the size of [npc.namePos] testicles."
                        + "<br/><i>Testicle size is a cosmetic transformation, and affects descriptions both in and out of sex.</i>"),
                "TESTICLE_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformTesticleCountDiv() {
        contentSB.setLength(0);

        for (int i = Testicle.MIN_TESTICLE_COUNT; i <= Testicle.MAX_TESTICLE_COUNT; i += 2) {
            if (BodyChanging.getTarget().getTesticleCount() == i) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='TESTICLE_COUNT_").append(i).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(Util.intToString(i))).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Testicle Count",
                UtilText.parse(BodyChanging.getTarget(), "Change how many testicles [npc.name] has."
                        + "<br/><i>Testicle count is a cosmetic transformation, and affects descriptions both in and out of sex.</i>"),
                "TESTICLE_COUNT",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformInternalTesticleDiv() {
        contentSB.setLength(0);

        if (BodyChanging.getTarget().isInternalTesticles()) {
            contentSB.append("<div id='TESTICLES_INTERNAL_OFF' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>External</span>").append("</div>").append("<div class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>Internal</span>").append("</div>");
        } else {
            contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>External</span>").append("</div>").append("<div id='TESTICLES_INTERNAL_ON' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>Internal</span>").append("</div>");
        }


        return applyWrapper("Internal Testicles",
                UtilText.parse(BodyChanging.getTarget(), "Set whether [npc.namePos] testicles are internal or not."
                        + "<br/><i>This affects descriptions and the availability of some actions in sex. (On futa characters, it is subject to your 'futanari testicles' content option.)</i>"),
                "INTERNAL_TESTICLES",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformUrethraCapacityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (Capacity value : Capacity.getCapacityListFromPreferences()) {
                if (BodyChanging.getTarget().getPenisCapacity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='URETHRA_CAPACITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Urethra Capacity",
                UtilText.parse(BodyChanging.getTarget(), "Change the capacity of [npc.namePos] urethra."
                        + "<br/><i>Anything other than a value of zero qualifies [npc.namePos] penile urethra as being able to be penetrated during sex. (Subject to your 'urethra penetration' preference in the content options.)</i>"),
                "PENIS_URETHRA_CAPACITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformUrethraDepthDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (OrificeDepth value : OrificeDepth.values()) {
                if (BodyChanging.getTarget().getUrethraDepth() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    if (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()) {
                        contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                    } else {
                        contentSB.append("<div id='URETHRA_DEPTH_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                    }
                }
            }
        }

        return applyWrapper("Urethra Depth",
                UtilText.parse(BodyChanging.getTarget(), "Change the depth of [npc.namePos] urethra."
                        + "<br/><i>Depth of an orifice determines the length of a penetrating object that it can comfortably accommodate.</i>"
                        + (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()
                        ? "<br/>[style.italicsSize8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(OrificeDepth.SEVEN_FATHOMLESS.getDescriptor()) + "'!)]"
                        : "")),
                "URETHRA_DEPTH",
                contentSB.toString(),
                true);
    }

    public static int getCumUpperLimit() {
        if (Main.game.isInNewWorld()) {
            return CumProduction.SEVEN_MONSTROUS.getMaximumValue();
        } else {
            return 30;
        }
    }

    public static String getSelfTransformCumProductionDiv() {
        return applyVariableWrapperFluids("Cum Storage",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] maximum cum storage."
                        + "<br/><i>Once drained, [npc.namePos] balls fill with cum up to this value at a rate determined by [npc.her] cum regeneration value.</i>"),
                "CUM_PRODUCTION",
                Util.capitaliseSentence(BodyChanging.getTarget().getPenisCumStorage().getName())
                        + "<br/>(" + Units.fluid(BodyChanging.getTarget().getPenisRawCumStorageValue(), ValueType.PRECISE) + ")",
                BodyChanging.getTarget().getPenisRawCumStorageValue() <= 0,
                BodyChanging.getTarget().getPenisRawCumStorageValue() >= getCumUpperLimit(),
                FLUID_INCREMENT_SMALL,
                FLUID_INCREMENT_AVERAGE,
                FLUID_INCREMENT_LARGE,
                FLUID_INCREMENT_HUGE);
    }

    public static String getSelfTransformCumRegenerationDiv() {
        return applyVariableWrapperFluids("Cum Regeneration",
                UtilText.parse(BodyChanging.getTarget(), "Alter the rate at which [npc.name] [npc.verb(produce)] cum."
                        + "<br/><i>Once drained, [npc.namePos] balls fill with cum up to their maximum storage value at this rate.</i>"),
                "CUM_REGENERATION",
                Units.fluid(BodyChanging.getTarget().getPenisRawCumProductionRegenerationValue(), ValueType.PRECISE) + "/day"
                        + "<br/>(" + Units.fluid(BodyChanging.getTarget().getCumRegenerationPerSecond() * 60, ValueType.PRECISE) + "/minute)"
                        + "<br/><i>" + Util.capitaliseSentence(BodyChanging.getTarget().getPenisCumProductionRegeneration().getName()) + "</i>",
                BodyChanging.getTarget().getPenisRawCumProductionRegenerationValue() <= 0,
                BodyChanging.getTarget().getPenisRawCumProductionRegenerationValue() >= FluidRegeneration.FOUR_VERY_RAPID.getMaximumRegenerationValuePerDay(),
                FLUID_REGEN_INCREMENT_SMALL,
                FLUID_REGEN_INCREMENT_AVERAGE,
                FLUID_REGEN_INCREMENT_LARGE,
                FLUID_REGEN_INCREMENT_HUGE);
    }

    public static String getSelfTransformCumExplusionDiv() {
        CumProduction expelledValue = CumProduction.getCumProductionFromInt(BodyChanging.getTarget().getPenisMaximumOrgasmCumQuantity());
        int extraSlotsDirtied = expelledValue.getAdditionalSlotsDirtiedUponOrgasm();
        return applyFullVariableWrapper("Cum Expulsion",
                UtilText.parse(BodyChanging.getTarget(), "Alter how much of [npc.namePos] stored cum is expelled at each orgasm."
                        + "<br/><i>If the amount of stored cum is less than or equal to " + Units.fluid(Testicle.MINIMUM_VALUE_FOR_ALL_CUM_TO_BE_EXPELLED) + ", 100% of cum left in [npc.namePos] balls will be expelled.</i>"),
                "CUM_EXPULSION",
                "1",
                "10",
                BodyChanging.getTarget().getPenisRawCumExpulsionValue() + "%"
                        + "<br/><i>" + Units.fluid(BodyChanging.getTarget().getPenisMaximumOrgasmCumQuantity()) + " - <span style='color:" + expelledValue.getColour().toWebHexString() + ";'>" + Util.capitaliseSentence(expelledValue.getName()) + "</span></i>",
//					+"<br/><i>"+Util.capitaliseSentence(BodyChanging.getTarget().getPenisCumExpulsion().getDescriptor())+"</i>",
                BodyChanging.getTarget().getPenisRawCumExpulsionValue() <= 5 || !Main.getProperties().hasValue(PropertyValue.cumRegenerationContent),
                BodyChanging.getTarget().getPenisRawCumExpulsionValue() >= 100 || !Main.getProperties().hasValue(PropertyValue.cumRegenerationContent),
                "<i>Dirties <b style='color:" + expelledValue.getColour().toWebHexString() + ";'>" + Util.intToString(extraSlotsDirtied) + "</b> extra inventory slot" + (extraSlotsDirtied == 1 ? "" : "s") + " during targeted orgasm.</i>");
    }

    public static String getSelfTransformCumFlavourDiv() {
        contentSB.setLength(0);

        for (FluidFlavour flavour : FluidFlavour.values()) {
            if (BodyChanging.getTarget().getCumFlavour().equals(flavour)) {
                contentSB.append("<div id='CUM_FLAVOUR_").append(flavour).append("' class='cosmetics-button active'>").append("<span style='color:").append(flavour.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(flavour.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='CUM_FLAVOUR_").append(flavour).append("' class='cosmetics-button'>").append("<span style='color:").append(flavour.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(flavour.getName())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Cum Flavour",
                UtilText.parse(BodyChanging.getTarget(), "Change the flavour of [npc.namePos] cum."
                        + "<br/><i>This will affect descriptions both in and out of sex.</i>"),
                "CUM_FLAVOUR",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformCumModifiersDiv() {
        contentSB.setLength(0);

        for (FluidModifier modifier : FluidModifier.values()) {
            if (BodyChanging.getTarget().hasCumModifier(modifier)) {
                contentSB.append("<div id='CUM_MODIFIER_").append(modifier).append("' class='cosmetics-button active'>").append("<span style='color:").append(modifier.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(modifier.getName())).append(modifier.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='CUM_MODIFIER_").append(modifier).append("' class='cosmetics-button'>").append("<span style='color:").append(modifier.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(modifier.getName())).append(modifier.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Cum Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Add or remove modifiers to [npc.namePos] cum."
                        + "<br/><i>This will affect descriptions both in and out of sex. The modifiers with an asterisk next to their name have special in-game effects, such as the application of status effects when ingested.</i>"),
                "CUM_MODIFIER",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformUrethraElasticityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (OrificeElasticity value : OrificeElasticity.values()) {
                if (BodyChanging.getTarget().getUrethraElasticity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='URETHRA_ELASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Urethra Elasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the elasticity of [npc.namePos] urethra."
                        + "<br/><i>This determines how quickly [npc.namePos] urethra will stretch out if a penetrating object is too thick for it."
                        + (Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? " Values marked with an asterisk increase the maximum uncomfortable depth of [npc.her] urethra." : ""))
                        + "</i>",
                "PENIS_URETHRA_ELASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformUrethraPlasticityDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (OrificePlasticity value : OrificePlasticity.values()) {
                if (BodyChanging.getTarget().getUrethraPlasticity() == value) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='URETHRA_PLASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Urethra Plasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the plasticity of [npc.namePos] urethra."
                        + "<br/><i>Plasticity of an orifice determines how quickly, and by how much, it regains its original tightness after being stretched out.</i>"),
                "PENIS_URETHRA_PLASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformPenisModifiersDiv() {
        contentSB.setLength(0);

        for (PenetrationModifier penMod : PenetrationModifier.values()) {
            if (BodyChanging.getTarget().hasPenisModifier(penMod)) {
                contentSB.append("<div id='PENIS_MOD_").append(penMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(penMod.getName())).append(penMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='PENIS_MOD_").append(penMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(penMod.getName())).append(penMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Penis Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] penis."
                        + "<br/><i>Penis modifiers affect descriptions and some actions in sex. The modifiers which have more than just descriptive effects are marked by an asterisk.</i>"),
                "PENIS_MODS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformUrethraModifiersDiv() {
        contentSB.setLength(0);

        if (!Main.game.isUrethraEnabled()) {
            contentSB.append("<div class='cosmetics-button disabled'>").append(Util.capitaliseSentence("Urethra Pen Disabled")).append("</div>");
        } else {
            for (OrificeModifier orificeMod : OrificeModifier.values()) {
                if (BodyChanging.getTarget().hasUrethraOrificeModifier(orificeMod)) {
                    contentSB.append("<div id='URETHRA_MOD_").append(orificeMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='URETHRA_MOD_").append(orificeMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Urethra Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] urethra."
                        + "<br/><i>Orifice modifiers affect descriptions and some actions in sex. The modifiers which have more than just descriptive effects are marked by an asterisk.</i>"),
                "PENIS_URETHRA_MODS",
                contentSB.toString(),
                true);
    }

    public static String getBodySizeChoiceDiv() {
        contentSB.setLength(0);

        for (BodySize bs : BodySize.values()) {
            if (BodyChanging.getTarget().getBodySize() == bs) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(BodyChanging.getTarget().getBodySize().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(bs.getName(false))).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='BODY_SIZE_").append(bs).append("' class='cosmetics-button'>").append("<span style='color:").append(bs.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(bs.getName(false))).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Body Size",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] body size."
                        + "<br/><i>This determines how much body fat [npc.namePos] [npc.has], and is a purely cosmetic transformation.</i>"),
                "BODY_SIZE",
                contentSB.toString(),
                true);
    }

    public static String getMuscleChoiceDiv() {
        contentSB.setLength(0);

        for (Muscle muscle : Muscle.values()) {
            if (BodyChanging.getTarget().getMuscle() == muscle) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(BodyChanging.getTarget().getMuscle().getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(muscle.getName(false))).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='MUSCLE_").append(muscle).append("' class='cosmetics-button'>").append("<span style='color:").append(muscle.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(muscle.getName(false))).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Muscle Definition",
                UtilText.parse(BodyChanging.getTarget(), "Change [npc.namePos] muscle definition." + (!Main.game.isInNewWorld() ? " This does not affect the physique attribute of your character." : "")
                        + "<br/><i>This determines how much muscle [npc.namePos] [npc.has], and is a purely cosmetic transformation.</i>"),
                "MUSCLE",
                contentSB.toString(),
                true);
    }

    public static String getLipSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Lip Size"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how large your lips are."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        for (LipSize ls : LipSize.values()) {
            if (!ls.isImpedesSpeech()) {
                if (BodyChanging.getTarget().getLipSize() == ls) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(ls.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(ls.getName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='LIP_SIZE_").append(ls).append("' class='cosmetics-button'>").append("<span style='color:").append(ls.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(ls.getName())).append("</span>").append("</div>");
                }
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static String getLipPuffynessDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Puffy Lips"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose whether your lips are extra puffy or not."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        if (BodyChanging.getTarget().hasFaceOrificeModifier(OrificeModifier.PUFFY)) {
            contentSB.append("<div id='LIP_PUFFY_OFF' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>Normal</span>").append("</div>").append("<div class='cosmetics-button active'>").append("[style.boldGood(Puffy)]").append("</div>");
        } else {
            contentSB.append("<div class='cosmetics-button active'>" + "[style.boldGood(Normal)]" + "</div>" + "<div id='LIP_PUFFY_ON' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>Puffy</span>").append("</div>");
        }

        contentSB.append(
                "</div>"
                        + "</div>");

        return contentSB.toString();
    }

    public static CupSize[] getBreastSizesAvailable() {
        if (BodyChanging.getTarget().hasPenis()) {
            return new CupSize[]{CupSize.FLAT, CupSize.TRAINING_AAA, CupSize.TRAINING_AA, CupSize.TRAINING_A};
        } else {
            return new CupSize[]{CupSize.AA, CupSize.A, CupSize.B, CupSize.C, CupSize.D, CupSize.DD, CupSize.E};
        }
    }

    public static String getBreastSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Breast Size"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how large your breasts are, in cup size."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        CupSize[] sizesAvailable = getBreastSizesAvailable();

        for (CupSize cs : sizesAvailable) {
            if (BodyChanging.getTarget().getBreastSize() == cs) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(cs.getCupSizeName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='BREAST_SIZE_").append(cs).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>").append(Util.capitaliseSentence(cs.getCupSizeName())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static String getBreastShapeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Breast Shape"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose the shape of your breasts."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        for (BreastShape bs : BreastShape.values()) {
            if (!bs.isRestrictedToCrotchBoobs()) {
                if (BodyChanging.getTarget().getBreastShape() == bs) {
                    contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(bs.getTransformName())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='BREAST_SHAPE_").append(bs).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>").append(Util.capitaliseSentence(bs.getTransformName())).append("</span>").append("</div>");
                }
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static NippleSize[] getNippleSizesAvailable() {
        if (BodyChanging.getTarget().hasPenis()) {
            return new NippleSize[]{NippleSize.ZERO_TINY, NippleSize.ONE_SMALL, NippleSize.TWO_BIG};
        } else {
            return new NippleSize[]{NippleSize.ZERO_TINY, NippleSize.ONE_SMALL, NippleSize.TWO_BIG, NippleSize.THREE_LARGE, NippleSize.FOUR_MASSIVE};
        }
    }

    public static String getNippleSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Nipple Size"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how large your nipples are."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        NippleSize[] sizesAvailable = getNippleSizesAvailable();

        for (NippleSize ns : sizesAvailable) {
            if (BodyChanging.getTarget().getNippleSize() == ns) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(ns.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='NIPPLE_SIZE_").append(ns).append("' class='cosmetics-button'>").append("<span style='color:").append(ns.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(ns.getName())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static AreolaeSize[] getAreolaeSizesAvailable() {
        if (BodyChanging.getTarget().hasPenis()) {
            return new AreolaeSize[]{AreolaeSize.ZERO_TINY, AreolaeSize.ONE_SMALL, AreolaeSize.TWO_BIG};
        } else {
            return new AreolaeSize[]{AreolaeSize.ZERO_TINY, AreolaeSize.ONE_SMALL, AreolaeSize.TWO_BIG, AreolaeSize.THREE_LARGE, AreolaeSize.FOUR_MASSIVE};
        }
    }

    public static String getAreolaeSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Areolae Size"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how large your areolae are."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        AreolaeSize[] sizesAvailable = getAreolaeSizesAvailable();

        for (AreolaeSize as : sizesAvailable) {
            if (BodyChanging.getTarget().getAreolaeSize() == as) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(as.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(as.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='AREOLAE_SIZE_").append(as).append("' class='cosmetics-button'>").append("<span style='color:").append(as.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(as.getName())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static String getNipplePuffynessDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Puffy Nipples"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose whether your nipples are extra puffy or not."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        if (BodyChanging.getTarget().hasNippleOrificeModifier(OrificeModifier.PUFFY)) {
            contentSB.append("<div id='NIPPLE_PUFFY_OFF' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>Normal</span>").append("</div>").append("<div class='cosmetics-button active'>").append("[style.boldGood(Puffy)]").append("</div>");
        } else {
            contentSB.append("<div class='cosmetics-button active'>" + "[style.boldGood(Normal)]" + "</div>" + "<div id='NIPPLE_PUFFY_ON' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>Puffy</span>").append("</div>");
        }

        contentSB.append(
                "</div>"
                        + "</div>");

        return contentSB.toString();
    }

    public static AssSize[] getAssSizesAvailable() {
        if (BodyChanging.getTarget().hasPenis()) {
            return new AssSize[]{AssSize.ZERO_FLAT, AssSize.ONE_TINY, AssSize.TWO_SMALL, AssSize.THREE_NORMAL, AssSize.FOUR_LARGE};
        } else {
            return AssSize.values();
        }
    }

    public static String getAssSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Ass Size"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how large your ass is."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        AssSize[] sizesAvailable = getAssSizesAvailable();

        for (AssSize as : sizesAvailable) {
            if (BodyChanging.getTarget().getAssSize() == as) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(as.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(as.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='ASS_SIZE_").append(as).append("' class='cosmetics-button'>").append("<span style='color:").append(as.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(as.getDescriptor())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static HipSize[] getHipSizesAvailable() {
        if (BodyChanging.getTarget().hasPenis()) {
            return new HipSize[]{HipSize.ZERO_NO_HIPS, HipSize.ONE_VERY_NARROW, HipSize.TWO_NARROW, HipSize.THREE_GIRLY};
        } else {
            return HipSize.values();
        }
    }

    public static String getHipSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Hip Size"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how large your hips are."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        HipSize[] sizesAvailable = getHipSizesAvailable();

        for (HipSize hs : sizesAvailable) {
            if (BodyChanging.getTarget().getHipSize() == hs) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(hs.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(hs.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='HIP_SIZE_").append(hs).append("' class='cosmetics-button'>").append("<span style='color:").append(hs.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(hs.getDescriptor())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static String getBleachedAnusDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Bleached Anus"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose whether your anus has been bleached to have the same skin tone as the rest of your body."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        if (BodyChanging.getTarget().isAssBleached()) {
            contentSB.append("<div id='BLEACHING_OFF' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>Normal</span>").append("</div>").append("<div class='cosmetics-button active'>").append("[style.boldGood(Bleached)]").append("</div>");
        } else {
            contentSB.append("<div class='cosmetics-button active'>" + "[style.boldGood(Normal)]" + "</div>" + "<div id='BLEACHING_ON' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>Bleached</span>").append("</div>");
        }

        contentSB.append(
                "</div>"
                        + "</div>");

        return contentSB.toString();
    }

    public static int[] getPenisSizesAvailable() {
        return new int[]{3, 5, 8, 10, 12, 15, 18, 20};
    }

    public static String getPenisSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Penis Length"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how long your penis is."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        int[] sizesAvailable = getPenisSizesAvailable();

        for (int size : sizesAvailable) {
            if (BodyChanging.getTarget().getPenisRawSizeValue() == size) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Units.size(size)).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='PENIS_SIZE_").append(size).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>").append(Units.size(size)).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static TesticleSize[] getTesticleSizesAvailable() {
        return new TesticleSize[]{TesticleSize.ZERO_VESTIGIAL, TesticleSize.ONE_TINY, TesticleSize.TWO_AVERAGE, TesticleSize.THREE_LARGE};
    }

    public static String getTesticleSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Testicle Size"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how large your testicles are."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        TesticleSize[] sizesAvailable = getTesticleSizesAvailable();

        for (TesticleSize size : sizesAvailable) {
            if (BodyChanging.getTarget().getTesticleSize() == size) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(size.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='TESTICLE_SIZE_").append(size).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.GENERIC_GOOD.getShades()[0]).append(";'>").append(Util.capitaliseSentence(size.getDescriptor())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static String getVaginaCapacityDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Vagina Capacity"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Select the capacity of your vagina. A higher capacity means that you'll be able to take larger insertions easier, but if it's too loose, it won't be very pleasurable for partners with small cocks."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        for (Capacity value : Capacity.getCapacityListFromPreferences()) {
            if (BodyChanging.getTarget().getVaginaCapacity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='VAGINA_CAPACITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static String getSelfTransformClitorisModifiersDiv() {
        contentSB.setLength(0);

        for (PenetrationModifier penMod : PenetrationModifier.values()) {
            if (BodyChanging.getTarget().hasClitorisModifier(penMod)) {
                contentSB.append("<div id='CLITORIS_MOD_").append(penMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(penMod.getName())).append(penMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='CLITORIS_MOD_").append(penMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(penMod.getName())).append(penMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Clitoris Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] clitoris."
                        + "<br/><i>Clitoris modifiers affect descriptions and some actions in sex. The modifiers which have more than just descriptive effects are marked by an asterisk.</i>"),
                "CLITORIS_MODS",
                contentSB.toString(),
                true);
    }

    public static ClitorisSize[] getClitorisSizesAvailable() {
        return new ClitorisSize[]{ClitorisSize.ZERO_AVERAGE, ClitorisSize.ONE_BIG};
    }

    public static String getClitorisSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Clitoris Length"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how long your clitoris is."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        ClitorisSize[] sizesAvailable = getClitorisSizesAvailable();

        for (ClitorisSize size : sizesAvailable) {
            if (BodyChanging.getTarget().getVaginaClitorisSize() == size) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(size.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(size.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='CLITORIS_SIZE_").append(size).append("' class='cosmetics-button'>").append("<span style='color:").append(size.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(size.getDescriptor())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static String getLabiaSizeDiv() {
        contentSB.setLength(0);

        contentSB.append(
                "<div class='container-full-width'>"
                        + "<div class='cosmetics-inner-container left'>"
                        + "<h5 style='text-align:center;'>"
                        + "Labia Size"
                        + "</h5>"
                        + "<p style='text-align:center;'>"
                        + "Choose how large your labia are."
                        + "</p>"
                        + "</div>"
                        + "<div class='cosmetics-inner-container right'>");

        for (LabiaSize size : LabiaSize.values()) {
            if (BodyChanging.getTarget().getVaginaLabiaSize() == size) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(size.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(size.getName())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='LABIA_SIZE_").append(size).append("' class='cosmetics-button'>").append("<span style='color:").append(size.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(size.getName())).append("</span>").append("</div>");
            }
        }

        contentSB.append("</div></div>");

        return contentSB.toString();
    }

    public static String getSelfTransformSpinneretModifiersDiv() {
        contentSB.setLength(0);

        for (OrificeModifier orificeMod : OrificeModifier.values()) {
            if (BodyChanging.getTarget().hasSpinneretOrificeModifier(orificeMod)) {
                contentSB.append("<div id='SPINNERET_MOD_").append(orificeMod).append("' class='cosmetics-button active'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.toWebHexString()).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='SPINNERET_MOD_").append(orificeMod).append("' class='cosmetics-button'>").append("<span style='color:").append(PresetColour.TRANSFORMATION_SEXUAL.getShades()[0]).append(";'>").append(Util.capitaliseSentence(orificeMod.getName())).append(orificeMod.isSpecialEffects() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Spinneret Modifiers",
                UtilText.parse(BodyChanging.getTarget(), "Change the modifiers for [npc.namePos] spinneret."
                        + "<br/><i>Orifice modifiers affect descriptions and some actions in sex. The modifiers which have more than just descriptive effects are marked by an asterisk.</i>"),
                "SPINNERET_MODS",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformSpinneretCapacityDiv() {
        contentSB.setLength(0);

        for (Capacity value : Capacity.getCapacityListFromPreferences()) {
            if (BodyChanging.getTarget().getSpinneretCapacity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='SPINNERET_CAPACITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Spinneret Capacity",
                UtilText.parse(BodyChanging.getTarget(), "Change the capacity of [npc.namePos] spinneret."
                        + "<br/><i>This affects the size of penetrative objects that [npc.namePos] spinneret can take. If capacity is too small or too large for a penetrative object, arousal gains are affected.</i>"),
                "SPINNERET_CAPACITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformSpinneretWetnessDiv() {
        contentSB.setLength(0);

        for (Wetness value : Wetness.values()) {
            if (BodyChanging.getTarget().getSpinneretWetness() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                if (BodyChanging.getTarget().getBodyMaterial().isOrificesAlwaysMaximumWetness()) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='SPINNERET_WETNESS_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Spinneret Wetness",
                UtilText.parse(BodyChanging.getTarget(), "Change the wetness of [npc.namePos] spinneret."
                        + "<br/><i>This affects pleasure in sex, as non-lubricated orifices negatively affect arousal gains.</i>"
                        + (BodyChanging.getTarget().getBodyMaterial().isOrificesAlwaysMaximumWetness()
                        ? "<br/>[style.italicsWetness8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(Wetness.SEVEN_DROOLING.getDescriptor()) + "'!)]"
                        : "")),
                "SPINNERET_WETNESS",
                contentSB.toString(),
                true);
    }


    // ---------------------- Kate's Shop: ---------------------- //

    public static String getSelfTransformSpinneretDepthDiv() {
        contentSB.setLength(0);

        for (OrificeDepth value : OrificeDepth.values()) {
            if (BodyChanging.getTarget().getSpinneretDepth() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                if (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

                } else {
                    contentSB.append("<div id='SPINNERET_DEPTH_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
                }
            }
        }

        return applyWrapper("Spinneret Depth",
                UtilText.parse(BodyChanging.getTarget(), "Change the depth of [npc.namePos] spinneret."
                        + "<br/><i>Depth of an orifice determines the length of a penetrating object that it can comfortably accommodate.</i>"
                        + (!BodyChanging.getTarget().getBodyMaterial().isOrificesLimitedDepth()
                        ? "<br/>[style.italicsSize8(As [npc.namePos] body is made out of [npc.bodyMaterial], [npc.her] orifices can never be less than '" + Util.capitaliseSentence(OrificeDepth.SEVEN_FATHOMLESS.getDescriptor()) + "'!)]"
                        : "")),
                "SPINNERET_DEPTH",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformSpinneretElasticityDiv() {
        contentSB.setLength(0);

        for (OrificeElasticity value : OrificeElasticity.values()) {
            if (BodyChanging.getTarget().getSpinneretElasticity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='SPINNERET_ELASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append(value.isExtendingUncomfortableDepth() && Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? "*" : "").append("</span>").append("</div>");
            }
        }

        return applyWrapper("Spinneret Elasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the elasticity of [npc.namePos] spinneret."
                        + "<br/><i>This determines how quickly [npc.namePos] spinneret will stretch out if a penetrating object is too thick for it."
                        + (Main.game.isPenetrationLimitationsEnabled() && Main.game.isElasticityAffectDepthEnabled() ? " Values marked with an asterisk increase the maximum uncomfortable depth of [npc.her] spinneret." : ""))
                        + "</i>",
                "SPINNERET_ELASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getSelfTransformSpinneretPlasticityDiv() {
        contentSB.setLength(0);

        for (OrificePlasticity value : OrificePlasticity.values()) {
            if (BodyChanging.getTarget().getSpinneretPlasticity() == value) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(value.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='SPINNERET_PLASTICITY_").append(value).append("' class='cosmetics-button'>").append("<span style='color:").append(value.getColour().getShades()[0]).append(";'>").append(Util.capitaliseSentence(value.getDescriptor())).append("</span>").append("</div>");
            }
        }

        return applyWrapper("Spinneret Plasticity",
                UtilText.parse(BodyChanging.getTarget(), "Change the plasticity of [npc.namePos] spinneret."
                        + "<br/><i>Plasticity of an orifice determines how quickly, and by how much, it regains its original tightness after being stretched out.</i>"),
                "SPINNERET_PLASTICITY",
                contentSB.toString(),
                true);
    }

    public static String getKatesDivHairLengths(boolean withCost, String title, String description) {
        contentSB.setLength(0);

        boolean noCost = !withCost;


        for (HairLength hairLength : HairLength.values()) {
            if (BodyChanging.getTarget().getHairLength() == hairLength) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(hairLength.getColour().toWebHexString()).append(";'>").append(Util.capitaliseSentence(hairLength.getDescriptor())).append(hairLength.isSuitableForPulling() ? "*" : "").append("</span>").append("</div>");

            } else {
                contentSB.append("<div id='HAIR_LENGTH_").append(hairLength).append("' class='cosmetics-button'>").append(Main.game.getPlayer().getMoney() >= SuccubisSecrets.BASE_HAIR_LENGTH_COST || noCost
                        ? "<span style='color:" + hairLength.getColour().getShades()[0] + ";'>" + Util.capitaliseSentence(hairLength.getDescriptor()) + (hairLength.isSuitableForPulling() ? "*" : "") + "</span>"
                        : "[style.colourDisabled(" + Util.capitaliseSentence(hairLength.getDescriptor()) + (hairLength.isSuitableForPulling() ? "*" : "") + ")]").append("</div>");
            }
        }

        return applyWrapper("Hair Length"
                        + (noCost
                        ? ""
                        : " " + (Main.game.getPlayer().getMoney() >= SuccubisSecrets.BASE_HAIR_LENGTH_COST
                        ? UtilText.formatAsMoney(SuccubisSecrets.BASE_HAIR_LENGTH_COST, "b")
                        : UtilText.formatAsMoney(SuccubisSecrets.BASE_HAIR_LENGTH_COST, "b", PresetColour.GENERIC_BAD))),
                UtilText.parse(BodyChanging.getTarget(), "Change the length of [npc.namePos] [npc.hair(true)]."
                        + "<br/><i>Hair of a sufficient length (marked by an asterisk) can be pulled in some sex actions.</i>"),
                "HAIR_LENGTH",
                contentSB.toString(),
                false);
    }

    public static String getKatesDivHairStyles(boolean withCost, String title, String description) {
        contentSB.setLength(0);

        boolean noCost = !withCost;
        for (HairStyle hairStyle : HairStyle.values()) {
            if (BodyChanging.getTarget().getHairStyle() == hairStyle) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(hairStyle.getName(BodyChanging.getTarget()))).append("</span>").append("</div>");
            } else {
                if (BodyChanging.getTarget().getHairRawLengthValue() >= hairStyle.getMinimumLengthRequired()) {
                    contentSB.append("<div id='HAIR_STYLE_").append(hairStyle).append("' class='cosmetics-button'>").append(Main.game.getPlayer().getMoney() >= SuccubisSecrets.BASE_HAIR_STYLE_COST || noCost
                            ? "<span style='color:" + PresetColour.TRANSFORMATION_GENERIC.getShades()[0] + ";'>" + Util.capitaliseSentence(hairStyle.getName(BodyChanging.getTarget())) + "</span>"
                            : "[style.colourDisabled(" + Util.capitaliseSentence(hairStyle.getName(BodyChanging.getTarget())) + ")]").append("</div>");
                } else {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "[style.colourDisabled(").append(Util.capitaliseSentence(hairStyle.getName(BodyChanging.getTarget()))).append(")]").append("</div>");
                }
            }
        }

        return applyWrapper("Hair Style"
                        + (noCost
                        ? ""
                        : " " + (Main.game.getPlayer().getMoney() >= SuccubisSecrets.BASE_HAIR_STYLE_COST
                        ? UtilText.formatAsMoney(SuccubisSecrets.BASE_HAIR_STYLE_COST, "b")
                        : UtilText.formatAsMoney(SuccubisSecrets.BASE_HAIR_STYLE_COST, "b", PresetColour.GENERIC_BAD))),
                UtilText.parse(BodyChanging.getTarget(), description)
                        + "<br/><i>'" + Util.capitaliseSentence(HairStyle.TWIN_TAILS.getName(BodyChanging.getTarget()))
                        + "' and '" + Util.capitaliseSentence(HairStyle.TWIN_BRAIDS.getName(BodyChanging.getTarget())) + "' can be used as handles in some sex actions.</i>",
                "HAIR_STYLE",
                contentSB.toString(),
                false);

    }

    public static String getKatesDivAssHair(boolean withCost, String title, String description) {
        return getKatesDivGenericBodyHair(withCost,
                title,
                description
                        + (BodyChanging.getTarget().isAssHairAvailable()
                        ? ""
                        : "<br/><i>Due to [npc.namePos] anus type, [npc.she] cannot grow any ass hair!</i>"),
                BodyChanging.getTarget().getAssHair(),
                "ASS_HAIR_",
                !BodyChanging.getTarget().isAssHairAvailable());
    }

    public static String getKatesDivUnderarmHair(boolean withCost, String title, String description) {
        return getKatesDivGenericBodyHair(withCost,
                title,
                description
                        + (BodyChanging.getTarget().isUnderarmHairAvailable()
                        ? ""
                        : "<br/><i>Due to [npc.namePos] arm type, [npc.she] cannot grow any underam hair!</i>"),
                BodyChanging.getTarget().getUnderarmHair(),
                "UNDERARM_HAIR_",
                !BodyChanging.getTarget().isUnderarmHairAvailable());
    }

    public static String getKatesDivFacialHair(boolean withCost, String title, String description) {
        return getKatesDivGenericBodyHair(withCost,
                title,
                description
                        + (BodyChanging.getTarget().isFeminine() && !Main.game.isFemaleFacialHairEnabled()
                        ? "<br/><i>Due to the fact that [npc.nameIsFull] feminine, [npc.she] cannot grow a beard!</i>"
                        : (BodyChanging.getTarget().isUnderarmHairAvailable()
                        ? ""
                        : "<br/><i>Due to [npc.namePos] face type, [npc.she] cannot grow a beard!</i>")),
                BodyChanging.getTarget().getFacialHair(),
                "FACIAL_HAIR_",
                !BodyChanging.getTarget().isFacialHairAvailable() || (BodyChanging.getTarget().isFeminine() && !Main.game.isFemaleFacialHairEnabled()));
    }

    public static String getKatesDivPubicHair(boolean withCost, String title, String description) {
        return getKatesDivGenericBodyHair(withCost,
                title,
                description
                        + (BodyChanging.getTarget().hasPenisIgnoreDildo() && !BodyChanging.getTarget().getPenisType().isPubicHairAllowed()
                        ? "<br/><i>Due to [npc.namePos] penis type, [npc.she] cannot grow any pubic hair!</i>"
                        : (BodyChanging.getTarget().hasVagina() && !BodyChanging.getTarget().getVaginaType().isPubicHairAllowed()
                        ? "<br/><i>Due to [npc.namePos] vagina type, [npc.she] cannot grow any pubic hair!</i>"
                        : (BodyChanging.getTarget().hasPenisIgnoreDildo() || BodyChanging.getTarget().hasVagina()
                        ? ""
                        : "<br/><i>Due to the fact that [npc.she] [npc.verb(lack)] genitalia, [npc.name] cannot grow any pubic hair!</i>"))),
                BodyChanging.getTarget().getPubicHair(),
                "PUBIC_HAIR_",
                !BodyChanging.getTarget().isPubicHairAvailable());
    }

    private static String getKatesDivGenericBodyHair(boolean withCost, String title, String description, BodyHair activeHair, String id, boolean blockAllButNoneOptions) {
        contentSB.setLength(0);

        boolean noCost = !withCost;
        for (BodyHair bodyHair : BodyHair.values()) {
            if (activeHair == bodyHair) {
                contentSB.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(bodyHair.getName())).append("</span>").append("</div>");
            } else {
                if (blockAllButNoneOptions) {
                    contentSB.append("<div class='cosmetics-button disabled'>" + "[style.colourDisabled(").append(Util.capitaliseSentence(bodyHair.getName())).append(")]").append("</div>");
                } else {
                    contentSB.append("<div id='").append(id).append(bodyHair).append("' class='cosmetics-button'>").append(Main.game.getPlayer().getMoney() >= SuccubisSecrets.BASE_BODY_HAIR_COST || noCost
                            ? "<span style='color:" + PresetColour.TRANSFORMATION_GENERIC.getShades()[0] + ";'>" + Util.capitaliseSentence(bodyHair.getName()) + "</span>"
                            : "[style.colourDisabled(" + Util.capitaliseSentence(bodyHair.getName()) + ")]").append("</div>");
                }
            }
        }

        return applyWrapper(title
                        + (noCost
                        ? ""
                        : " " + (Main.game.getPlayer().getMoney() >= SuccubisSecrets.BASE_BODY_HAIR_COST
                        ? UtilText.formatAsMoney(SuccubisSecrets.BASE_BODY_HAIR_COST, "b")
                        : UtilText.formatAsMoney(SuccubisSecrets.BASE_BODY_HAIR_COST, "b", PresetColour.GENERIC_BAD))),
                UtilText.parse(BodyChanging.getTarget(), description),
                id,
                contentSB.toString(),
                false);
    }

    public static String getKatesDivGenericBodyHairDisabled(String title, String description, String disabledDescription) {
        contentSB.setLength(0);

        contentSB.append("<div class='container-full-width'>" + "<div class='cosmetics-inner-container left'>" + "<h5 style='text-align:center;'>").append(title).append("</h5>").append("<p style='text-align:center;'>").append(description).append("</p>").append("</div>").append("<div class='cosmetics-inner-container right'>").append("<p style='text-align:center; color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>").append(disabledDescription).append("</p>").append("</div>").append("</div>");

        return contentSB.toString();
    }

    public static String getKatesDivAnalBleaching() {
        contentSB.setLength(0);

        boolean noCost = !Main.game.isInNewWorld();

        contentSB.append("<div class='container-full-width'>");

        contentSB.append("<div class='container-full-width' style='text-align:center; width:100%;padding:0;margin:0;'>" + "<b>Anal bleaching</b>").append(noCost
                ? ""
                : " " + (Main.game.getPlayer().getMoney() >= SuccubisSecrets.BASE_ANAL_BLEACHING_COST
                ? UtilText.formatAsMoney(SuccubisSecrets.BASE_ANAL_BLEACHING_COST, "b")
                : UtilText.formatAsMoney(SuccubisSecrets.BASE_ANAL_BLEACHING_COST, "b", PresetColour.GENERIC_BAD))).append("</div>");

        contentSB.append(
                getInformationDiv(
                        "ANAL_BLEACHING",
                        new TooltipInformationEventListener().setInformation("Anal bleaching", "Anal bleaching is the process of lightening the colour of the skin around the anus so as to make it blend in with the surrounding area."),
                        false));

        contentSB.append("<div class='container-full-width' style='text-align:center; width:100%;padding:0;margin:0;'>");
        if (BodyChanging.getTarget().isAssBleached()) {
            contentSB.append(
                    "<div id='BLEACHING_OFF' class='cosmetics-button'>"
                            + "[style.colourDisabled(Normal)]"
                            + "</div>"
                            + "<div class='cosmetics-button active'>"
                            + "[style.boldArcane(Bleached)]"
                            + "</div>");
        } else {
            contentSB.append("<div class='cosmetics-button active'>" + "Normal" + "</div>" + "<div id='BLEACHING_ON' class='cosmetics-button'>").append(Main.game.getPlayer().getMoney() >= SuccubisSecrets.BASE_ANAL_BLEACHING_COST
                    ? "<span style='color:" + PresetColour.GENERIC_ARCANE.getShades()[0] + ";'>Bleached</span>"
                    : "[style.colourDisabled(Bleached)]").append("</div>");
        }

        contentSB.append("</div>");

        contentSB.append("</div>");

        return contentSB.toString();
    }

    public static Map<AbstractBodyCoveringType, Covering> getCoveringsToBeApplied() {
        return coveringsToBeApplied;
    }

    public static void resetCoveringsToBeApplied() {
        coveringsToBeApplied = new HashMap<>();
    }

    public static String getKatesDivCoveringsNew(boolean withCost, AbstractRace race, AbstractBodyCoveringType coveringType, String title, String description, boolean withSecondary, boolean withGlow) {
        return getKatesDivCoveringsNew(withCost, race, coveringType, title, description, withSecondary, withGlow, true);
    }

    public static String getKatesDivCoveringsNew(boolean withCost, AbstractRace race, AbstractBodyCoveringType coveringType, String title, String description, boolean withSecondary, boolean withGlow, boolean withDyeAndExtraPatterns) {
        Main.game.getDialogueFlags().setFlag(DialogueFlagValue.coveringChangeListenersRequired, true);

        boolean disabledButton = !coveringsToBeApplied.containsKey(coveringType) || coveringsToBeApplied.get(coveringType).equals(BodyChanging.getTarget().getCovering(coveringType));

        StringBuilder sb = new StringBuilder();

        Covering activeCovering = !coveringsToBeApplied.containsKey(coveringType)
                ? BodyChanging.getTarget().getCovering(coveringType)
                : coveringsToBeApplied.get(coveringType);

        List<CoveringPattern> availablePatterns = new ArrayList<>(withDyeAndExtraPatterns
                ? coveringType.getAllPatterns().keySet()
                : coveringType.getNaturalPatterns().keySet());

        String rainbow = "";

        List<Colour> availablePrimaryColours = new ArrayList<>(withDyeAndExtraPatterns
                ? coveringType.getAllPrimaryColours()
                : coveringType.getNaturalColoursPrimary());
        Collections.sort(availablePrimaryColours, (c1, c2) -> c2.isMetallic() ? (c1.isMetallic() ? 0 : -1) : (c1.isMetallic() ? 1 : 0));

        List<Colour> availableSecondaryColours = new ArrayList<>(withDyeAndExtraPatterns
                ? coveringType.getAllSecondaryColours()
                : coveringType.getNaturalColoursSecondary());
        Collections.sort(availableSecondaryColours, (c1, c2) -> c2.isMetallic() ? (c1.isMetallic() ? 0 : -1) : (c1.isMetallic() ? 1 : 0));

        String border = "border: 1px solid " + PresetColour.BACKGROUND.toWebHexString() + ";";

        boolean secondaryDisabled = activeCovering.getPattern() == CoveringPattern.NONE
                || activeCovering.getPattern() == CoveringPattern.EYE_IRISES
                || activeCovering.getPattern() == CoveringPattern.EYE_PUPILS
                || activeCovering.getPattern() == CoveringPattern.EYE_SCLERA
                || !withSecondary;

        sb.append("<div class='container-full-width' style='text-align:center;'>");

        sb.append("<div class='container-full-width' style='padding:0; margin:0; text-align:center;'>");
        sb.append("<b>").append(Util.capitaliseSentence(title)).append("</b>");
        if (race != Race.NONE) {
            sb.append(" - <b style='color:").append(race.getColour().toWebHexString()).append("'>").append(Util.capitaliseSentence(race.getName(BodyChanging.getTarget().getBody(), BodyChanging.getTarget().isFeral()))).append("</b>");
        }
        sb.append("</div>");
        sb.append("<div class='container-full-width' style='padding:0; margin:0; text-align:center;'>");
        sb.append(Util.capitaliseSentence(BodyChanging.getTarget().getCovering(coveringType).getFullDescription(BodyChanging.getTarget(), true)));
        sb.append("</div>");

        // Pattern and Modifiers section on left:
        sb.append("<div class='container-full-width' style='width:33.3%; padding:0; margin:0; text-align:center;'>");

        // Covering Pattern:
        sb.append("<div class='container-full-width' style='width:100%; padding:0; margin:0; text-align:center; ").append(border).append("'>");
        sb.append("<p style='padding:0;margin:0;text-align:center;'>Pattern:</p>");
        for (CoveringPattern pattern : availablePatterns) {
            if (activeCovering.getPattern() == pattern) {
                sb.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(pattern.getName())).append("</span>").append("</div>");
            } else {
                sb.append("<div id='").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("_PATTERN_").append(pattern).append("' class='cosmetics-button'>").append(Main.game.getPlayer().getMoney() >= SuccubisSecrets.getBodyCoveringTypeCost(coveringType) || !withCost
                        ? "<span style='color:" + PresetColour.TRANSFORMATION_GENERIC.getShades()[0] + ";'>" + Util.capitaliseSentence(pattern.getName()) + "</span>"
                        : "[style.colourDisabled(" + Util.capitaliseSentence(pattern.getName()) + ")]").append("</div>");
            }
        }
        sb.append("</div>");

        // Covering Modifiers:
        sb.append("<div class='container-full-width' style='width:100%; padding:0; margin:0; text-align:center; ").append(border).append("'>");
        sb.append("<p style='padding:0;margin:0;text-align:center;'>Modifiers:</p>");
        if (activeCovering.getType().getNaturalModifiers().size() + activeCovering.getType().getExtraModifiers().size() > 1) {
            sb.append("<div class='container-full-width'>");
            for (CoveringModifier mod : activeCovering.getType().getNaturalModifiers()) {
                if (activeCovering.getModifier() == mod) {
                    sb.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(mod.getName())).append("</span>").append("</div>");
                } else {
                    sb.append("<div id='").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("_MODIFIER_").append(mod).append("' class='cosmetics-button'>").append(Main.game.getPlayer().getMoney() >= SuccubisSecrets.getBodyCoveringTypeCost(coveringType) || !withCost
                            ? "<span style='color:" + PresetColour.TRANSFORMATION_GENERIC.getShades()[0] + ";'>" + Util.capitaliseSentence(mod.getName()) + "</span>"
                            : "[style.colourDisabled(" + Util.capitaliseSentence(mod.getName()) + ")]").append("</div>");
                }
            }
            for (CoveringModifier mod : activeCovering.getType().getExtraModifiers()) {
                if (activeCovering.getModifier() == mod) {
                    sb.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.GENERIC_GOOD.toWebHexString()).append(";'>").append(Util.capitaliseSentence(mod.getName())).append("</span>").append("</div>");
                } else {
                    sb.append("<div id='").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("_MODIFIER_").append(mod).append("' class='cosmetics-button'>").append(Main.game.getPlayer().getMoney() >= SuccubisSecrets.getBodyCoveringTypeCost(coveringType) || !withCost
                            ? "<span style='color:" + PresetColour.TRANSFORMATION_GENERIC.getShades()[0] + ";'>" + Util.capitaliseSentence(mod.getName()) + "</span>"
                            : "[style.colourDisabled(" + Util.capitaliseSentence(mod.getName()) + ")]").append("</div>");
                }
            }
            sb.append("</div>");

        } else {
            sb.append("<p style='padding:0;margin:0;text-align:center;'>[style.italicsDisabled(None Available)]</p>");
        }
        sb.append("</div>");

        sb.append("</div>");

        // Primary and secondary colours:
        sb.append("<div class='container-full-width' style='width:66.6%; padding:0; margin:0; text-align:center;'>");

        // Primary:
        sb.append("<div class='container-full-width' style='width:100%; padding:0; margin:0; text-align:center; ").append(border).append("'>");
        sb.append("<div class='container-full-width' style='width:100%; padding:0; margin:0; text-align:center; background:transparent;'>");
        sb.append("Primary Colour");
        sb.append(" | <span style='color:").append(activeCovering.getPrimaryColour().toWebHexString()).append(";").append(activeCovering.isPrimaryGlowing()
                ? "text-shadow: 0px 0px 4px " + activeCovering.getPrimaryColour().getShades()[4] + ";"
                : "").append("'>").append(Util.capitaliseSentence(activeCovering.getPrimaryColour().getName())).append("</span>");
        sb.append("</div>");
        for (Colour c : availablePrimaryColours) {
            if (c.getRainbowColours() != null) {
                rainbow = c.getRainbowDiv(5);
            } else {
                rainbow = "";
            }
            sb.append("<div class='normal-button").append(activeCovering.getPrimaryColour() == c ? " selected" : "").append("' id='").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("_PRIMARY_").append(c.getId()).append("'").append(" style='width:auto; margin-right:4px;").append(activeCovering.getPrimaryColour() == c ? " background-color:" + PresetColour.BASE_GREEN.getShades()[4] + ";" : "").append("'>").append(c.isMetallic()
                    ? "<div class='phone-item-colour' style='background: repeating-linear-gradient(135deg, " + c.toWebHexString() + ", " + c.getShades()[4] + " 10px);"
                    : (c.getRainbowColours() != null
                    ? "<div class='phone-item-colour' style='background: " + rainbow
                    : "<div class='phone-item-colour' style='background-color:" + (c.getCoveringIconColour()) + ";")).append(c == PresetColour.COVERING_NONE
                    ? " color:" + PresetColour.BASE_RED.toWebHexString() + ";'>X"
                    : "'>").append("</div>").append("</div>");
        }

        if (activeCovering.getPrimaryColour() != PresetColour.COVERING_NONE && withGlow) { // Glow:
            if (activeCovering.isPrimaryGlowing()) {
                sb.append("<div class='normal-button active' id='").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("_PRIMARY_GLOW_OFF' style='width:50%; margin:1% 25%; padding:0; text-align:center;'>").append("[style.boldArcane(Arcane Glow)]").append("</div>");
            } else {
                sb.append("<div id='").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("_PRIMARY_GLOW_ON' class='normal-button' style='width:50%; margin:1% 25%; padding:0; text-align:center;'>").append("<span style='color:").append(PresetColour.GENERIC_ARCANE.getShades()[0]).append(";'>Arcane Glow</span>").append("</div>");
            }
        }
        sb.append("</div>");

        // Secondary:
        sb.append("<div class='container-full-width' style='width:100%; padding:0; margin:0; text-align:center; ").append(border).append("'>");
        sb.append("<div class='container-full-width' style='width:100%; padding:0; margin:0; text-align:center; background:transparent;'>");
        sb.append("Secondary Colour");
        if (!secondaryDisabled) {
            sb.append(" | <span style='color:").append(activeCovering.getSecondaryColour().toWebHexString()).append(";").append(activeCovering.isSecondaryGlowing()
                    ? "text-shadow: 0px 0px 4px " + activeCovering.getSecondaryColour().getShades()[4] + ";"
                    : "").append("'>").append(Util.capitaliseSentence(activeCovering.getSecondaryColour().getName())).append("</span>");
        }
        sb.append("</div>");
        if (secondaryDisabled) {
            sb.append("<p style='padding:0;margin:0;text-align:center;'>[style.italicsDisabled(None Available)]</p>");

        } else {
            for (Colour c : availableSecondaryColours) {
                if (c.getRainbowColours() != null) {
                    rainbow = c.getRainbowDiv(5);
                } else {
                    rainbow = "";
                }
                sb.append("<div class='normal-button").append(activeCovering.getSecondaryColour() == c ? " selected" : "").append("' id='").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("_SECONDARY_").append(c.getId()).append("'").append(" style='width:auto; margin-right:4px;").append(activeCovering.getSecondaryColour() == c ? " background-color:" + PresetColour.BASE_GREEN.getShades()[4] + ";" : "").append("'>").append(c.isMetallic()
                        ? "<div class='phone-item-colour' style='background: repeating-linear-gradient(135deg, " + c.toWebHexString() + ", " + c.getShades()[4] + " 10px);"
                        : (c.getRainbowColours() != null
                        ? "<div class='phone-item-colour' style='background: " + rainbow
                        : "<div class='phone-item-colour' style='background-color:" + (c.getCoveringIconColour()) + ";")).append(c == PresetColour.COVERING_NONE
                        ? " color:" + PresetColour.BASE_RED.toWebHexString() + ";'>X"
                        : "'>").append("</div>").append("</div>");
            }
        }

        if (activeCovering.getSecondaryColour() != PresetColour.COVERING_NONE && withGlow && !secondaryDisabled) { // Glow:
            if (activeCovering.isSecondaryGlowing()) {
                sb.append("<div class='normal-button active' id='").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("_SECONDARY_GLOW_OFF' style='width:50%; margin:1% 25%; padding:0; text-align:center;'>").append("[style.boldArcane(Arcane Glow)]").append("</div>");
            } else {
                sb.append("<div id='").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("_SECONDARY_GLOW_ON' class='normal-button' style='width:50%; margin:1% 25%; padding:0; text-align:center;'>").append("<span style='color:").append(PresetColour.GENERIC_ARCANE.getShades()[0]).append(";'>Arcane Glow</span>").append("</div>");
            }
        }
        sb.append("</div>");

        sb.append("</div>");

        if (coveringType == BodyCoveringType.MAKEUP_LIPSTICK && Main.getProperties().hasValue(PropertyValue.lipstickMarkingContent)) {
            boolean heavyLipstick = BodyChanging.getTarget().isHeavyMakeup(BodyCoveringType.MAKEUP_LIPSTICK);
            sb.append("<div class='container-full-width' style='width:100%; padding:0; margin:0; text-align:center; ").append(border).append("'>");
            sb.append("<div class='container-full-width' style='width:60%; padding:0; margin:0; text-align:center;'>");
            sb.append(UtilText.parse(BodyChanging.getTarget(),
                    "By applying a heavy layer of lipstick, [npc.name] will leave marks on any part [npc.she] [npc.verb(kiss)]!"
                            + "<br/>[style.italics(Heavy lipstick needs to be re-applied after a sex scene in which it's used.)]"));
            sb.append("</div>");
            sb.append("<div class='container-full-width' style='width:40%; padding:0; margin:0; text-align:center;'>");
            if (!heavyLipstick) {
                sb.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.BASE_PINK_LIGHT.toWebHexString()).append(";'>Normal</span>").append("</div>");
            } else {
                sb.append("<div id='MAKEUP_LIPSTICK_HEAVY_OFF' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.BASE_PINK_LIGHT.getShades()[0]).append(";'>Normal</span>").append("</div>");
            }
            if (heavyLipstick) {
                sb.append("<div class='cosmetics-button active'>" + "<span style='color:").append(PresetColour.BASE_PINK_DEEP.toWebHexString()).append(";'>Heavy</span>").append("</div>");
            } else {
                sb.append("<div id='MAKEUP_LIPSTICK_HEAVY_ON' class='cosmetics-button'>" + "<span style='color:").append(PresetColour.BASE_PINK_DEEP.getShades()[0]).append(";'>Heavy</span>").append("</div>");
            }
            sb.append("</div>");
            sb.append("</div>");
        }

        // Reset/Apply changes buttons:
        sb.append("<div class='container-full-width' style='width:100%; padding:0; margin:0; text-align:center;'>");
        sb.append("<div class='container-full-width' style='width:50%; padding:0; margin:0; text-align:center;'>");
        if (disabledButton) {
            sb.append("<div class='normal-button disabled' style='width:80%; margin:2% auto; padding:0; text-align:center; bottom:0;'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>Reset Changes</span>").append("</div>");
        } else {
            sb.append("<div class='normal-button' style='width:80%; margin:2% auto; padding:0; text-align:center; bottom:0;' id='RESET_COVERING_").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("'>").append("[style.colourMinorBad(Reset Changes)]").append("</div>");
        }
        sb.append("</div>");
        sb.append("<div class='container-full-width' style='width:50%; padding:0; margin:0; text-align:center;'>");
        if (disabledButton) {
            sb.append("<div class='normal-button disabled' style='width:80%; margin:2% auto; padding:0; text-align:center; bottom:0;'>" + "<span style='color:").append(PresetColour.TEXT_GREY.toWebHexString()).append(";'>Apply Changes").append(withCost
                    ? " (" + UtilText.formatAsMoneyUncoloured(SuccubisSecrets.getBodyCoveringTypeCost(coveringType), "span") + ")"
                    : "").append("</span>").append("</div>");
        } else {
            sb.append("<div class='normal-button' style='width:80%; margin:2% auto; padding:0; text-align:center; bottom:0;' id='APPLY_COVERING_").append(BodyCoveringType.getIdFromBodyCoveringType(coveringType)).append("'>").append("[style.colourMinorGood(Apply Changes)]").append(withCost
                    ? " ("
                    + (Main.game.getPlayer().getMoney() >= SuccubisSecrets.getBodyCoveringTypeCost(coveringType)
                    ? UtilText.formatAsMoney(SuccubisSecrets.getBodyCoveringTypeCost(coveringType), "span")
                    : UtilText.formatAsMoney(SuccubisSecrets.getBodyCoveringTypeCost(coveringType), "span", PresetColour.GENERIC_BAD)) + ")"
                    : "").append("</div>");
        }
        sb.append("</div>");
        sb.append("</div>");

        sb.append("</div>");

        return sb.toString();
    }

    public static String getKatesDivPiercings(boolean noCost) {
        contentSB.setLength(0);

        boolean isPierced = false;
        boolean canPierce = true;

        String title = "";
        String description = "";
        int i = 0;

        for (PiercingType piercingType : PiercingType.values()) {
            title = Util.capitaliseSentence(piercingType.getName()) + " Piercing";
            description = piercingType.getDescription();

            switch (piercingType) {
                case EAR:
                    isPierced = BodyChanging.getTarget().isPiercedEar();
                    break;
                case LIP:
                    isPierced = BodyChanging.getTarget().isPiercedLip();
                    break;
                case NAVEL:
                    isPierced = BodyChanging.getTarget().isPiercedNavel();
                    break;
                case NOSE:
                    isPierced = BodyChanging.getTarget().isPiercedNose();
                    break;
                case TONGUE:
                    isPierced = BodyChanging.getTarget().isPiercedTongue();
                    break;

                case NIPPLE:
                    isPierced = BodyChanging.getTarget().isPiercedNipple();
                    break;
                case PENIS:
                    canPierce = BodyChanging.getTarget().hasPenisIgnoreDildo();
                    isPierced = BodyChanging.getTarget().isPiercedPenis();
                    break;
                case VAGINA:
                    canPierce = BodyChanging.getTarget().hasVagina();
                    isPierced = BodyChanging.getTarget().isPiercedVagina();
                    break;
            }

            contentSB.append("<div class='container-full-width' style='padding:4px;margin:0;width:100%;background:").append((i % 2 == 0 ? PresetColour.BACKGROUND_ALT : PresetColour.BACKGROUND).toWebHexString()).append(";'>");

            if (noCost) {
                contentSB.append("<div class='container-half-width' style='padding:0;margin:0;width:50%;background:transparent;text-align:center;'>");
                if (canPierce) {
                    contentSB.append(title);
                } else {
                    contentSB.append("[style.colourDisabled(").append(title).append(")]");
                }
                contentSB.append("</div>");

            } else {
                contentSB.append("<div class='container-half-width' style='padding:0;margin:0 5% 0 0;width:35%;background:transparent;text-align:right;'>");
                if (canPierce) {
                    contentSB.append(title);
                } else {
                    contentSB.append("[style.colourDisabled(").append(title).append(")]");
                }
                contentSB.append("</div>");
                contentSB.append("<div class='container-half-width' style='padding:0;margin:0;width:10%;background:transparent;text-align:left;'>");
                if (Main.game.getPlayer().getMoney() >= SuccubisSecrets.getPiercingCost(piercingType)) {
                    contentSB.append(UtilText.formatAsMoney(SuccubisSecrets.getPiercingCost(piercingType), "b"));
                } else {
                    contentSB.append(UtilText.formatAsMoney(SuccubisSecrets.getPiercingCost(piercingType), "b", PresetColour.GENERIC_BAD));
                }
                contentSB.append("</div>");
            }

            contentSB.append(
                    getInformationDiv(
                            "PIERCING_INFO_" + piercingType,
                            new TooltipInformationEventListener().setInformation(title, description),
                            true));

            contentSB.append("<div class='container-half-width' style='padding:0;margin:0;width:50%;background:transparent;'>");

            if (isPierced) {
                contentSB.append("<div id='").append(piercingType).append("_PIERCE_REMOVE' class='cosmetics-button'>").append("[style.colourDisabled(Unpierced)]").append("</div>");

                contentSB.append(
                        "<div class='cosmetics-button active'>"
                                + "[style.boldArcane(Pierced)]"
                                + "</div>");
            } else {
                contentSB.append(
                        "<div class='cosmetics-button active'>"
                                + "Unpierced"
                                + "</div>");

                if (canPierce) {
                    contentSB.append("<div id='").append(piercingType).append("_PIERCE' class='cosmetics-button'>").append(Main.game.getPlayer().getMoney() >= SuccubisSecrets.getPiercingCost(piercingType) || noCost
                            ? "<span style='color:" + PresetColour.GENERIC_ARCANE.getShades()[0] + ";'>Pierced</span>"
                            : "[style.colourDisabled(Pierced)]").append("</div>");

                } else {
                    contentSB.append(
                            "<div class='cosmetics-button disabled'>"
                                    + "[style.colourDisabled(Pierced)]"
                                    + "</div>");
                }
            }
            contentSB.append("</div>");

            contentSB.append("</div>");

            i++;
        }


        return contentSB.toString();
    }

    public static String getKatesDivTattoos() {
        contentSB.setLength(0);

        contentSB.append("<div class='container-full-width'>");
//				+ "<h5 style='width:100%; text-align:center;'>Main Areas</h5>");

        for (InventorySlot invSlot : RenderingEngine.mainInventorySlots) {
            contentSB.append(getTattooDiv(invSlot));
        }

//		contentSB.append("</div>");
//
//		contentSB.append("<div class='container-full-width'>"
//				+ "<h5 style='width:100%; text-align:center;'>Extra Areas</h5>");

        for (InventorySlot invSlot : RenderingEngine.secondaryInventorySlots) {
            contentSB.append(getTattooDiv(invSlot));
        }

        contentSB.append("</div>");

        return contentSB.toString();
    }

    private static String getTattooDiv(InventorySlot invSlot) {
        Tattoo tattooInSlot = BodyChanging.getTarget().getTattooInSlot(invSlot);
        boolean disabled = false;

        switch (invSlot) {
            case HORNS:
                if (BodyChanging.getTarget().getHornType().equals(HornType.NONE)) {
                    disabled = true;
                }
                break;
            case PENIS:
                if (!BodyChanging.getTarget().hasPenisIgnoreDildo()) {
                    disabled = true;
                }
                break;
            case TAIL:
                if (BodyChanging.getTarget().getTailType() == TailType.NONE) {
                    disabled = true;
                }
                break;
            case VAGINA:
                if (!BodyChanging.getTarget().hasVagina()) {
                    disabled = true;
                }
                break;
            case WINGS:
                if (BodyChanging.getTarget().getWingType() == WingType.NONE) {
                    disabled = true;
                }
                break;
            default:
                break;
        }

        return "<div class='container-half-width inner' style='width:23%;margin:1%;'>"
                + "<div class='container-full-width inner' style='width:100%; margin:0; text-align:center;'>" + Util.capitaliseSentence(invSlot.getTattooSlotName()) + "</div>"
                + (disabled
                ? "<div class='inventory-item-slot disabled' style='width:48%;margin:0 1%'></div>"
                : "<div class='modifier-icon' style='width:48%;margin:0 1%'>"
                + (tattooInSlot == null
                ? "<div class='modifier-icon-content'></div>"
                : "<div class='modifier-icon-content' style='background-color:" + tattooInSlot.getRarity().getBackgroundColour().toWebHexString() + ";'>" + tattooInSlot.getSVGImage(BodyChanging.getTarget()) + "</div>")
                + "<div class='overlay no-pointer' id='TATTOO_INFO_" + invSlot + "'></div>"
                + "</div>")

                + "<div class='container-half-width inner' style='width:48%;margin:1%;'>"
                + "<div style='float:left; width:98%; margin:0 1%; padding:0;'>"
                + "<div class='normal-button" + (disabled ? " disabled" : "") + "' " + (!disabled ? "id='TATTOO_ADD_REMOVE_" + invSlot + "'" : "") + " style='width:100%;'>"
                + (tattooInSlot == null
                ? "Add"
                : (SuccubisSecrets.invSlotTattooToRemove == invSlot || !Main.getProperties().hasValue(PropertyValue.tattooRemovalConfirmations) ? "[style.colourBad(Remove)]" : "Remove"))
                + "</div>"
                + "</div>"
                + (Main.game.isInNewWorld()
                ? "<div style='float:left; width:98%; margin:0 1%; padding:0;'>"
                + "<div class='normal-button" + (disabled || tattooInSlot == null ? " disabled" : "") + "' " + (!disabled && tattooInSlot != null ? "id='TATTOO_ENCHANT_" + invSlot + "'" : "") + " style='width:100%;'>Enchant</div>"
                + "</div>"
                : "")
                + "</div>"
                + "</div>";
    }

    public static void resetTattooVariables(InventorySlot slot) {
        tattooInventorySlot = slot;

        tattoo = new Tattoo(
                "innoxia_symbol_tribal",
                PresetColour.CLOTHING_GREY,
                null,
                null,
                false,
                new TattooWriting(
                        "",
                        PresetColour.BASE_GREY,
                        false),
                new TattooCounter(
                        TattooCounterType.NONE,
                        TattooCountType.NUMBERS,
                        PresetColour.BASE_GREY,
                        false));
    }

    public static void resetTattooColours() {
        if (!tattoo.getType().getAvailablePrimaryColours().contains(tattoo.getPrimaryColour())) {
            tattoo.setPrimaryColour(tattoo.getType().getAvailablePrimaryColours().get(0));
        }

        if (!tattoo.getType().getAvailableSecondaryColours().contains(tattoo.getSecondaryColour())) {
            if (tattoo.getType().getAvailableSecondaryColours().isEmpty()) {
                tattoo.setSecondaryColour(null);
            } else {
                tattoo.setSecondaryColour(tattoo.getType().getAvailableSecondaryColours().get(0));
            }
        }

        if (!tattoo.getType().getAvailableTertiaryColours().contains(tattoo.getTertiaryColour())) {
            if (tattoo.getType().getAvailableTertiaryColours().isEmpty()) {
                tattoo.setTertiaryColour(null);
            } else {
                tattoo.setTertiaryColour(tattoo.getType().getAvailableTertiaryColours().get(0));
            }
        }

        tattoo.setGlowing(false);
    }

    public static String getKatesDivTattoosAdd() {
        contentSB.setLength(0);

        // Type:

        contentSB.append("<div class='container-full-width'>");
        contentSB.append("<div class='container-full-width' style='width:75%; margin:0; position:relative; text-align:center;'>");
        contentSB.append("<h5 style='width:100%; text-align:center;'>Select Type</h5>");

        for (AbstractTattooType type : TattooType.getConditionalTattooTypes(BodyChanging.getTarget())) {
            if (type.getSlotAvailability().contains(tattooInventorySlot)) {
                contentSB.append("<div style='width:18%; margin:1%; padding:0; display:inline-block;'>" + "<div class='normal-button").append(tattoo.getType() == type ? " selected" : "").append("' id='TATTOO_TYPE_").append(type.getId()).append("'").append(" style='width:100%; margin:0; color:").append((tattoo.getType() == type ? PresetColour.GENERIC_GOOD : PresetColour.TEXT_HALF_GREY).toWebHexString()).append(";'>").append(Util.capitaliseSentence(type.getName())).append("</div>").append("</div>");

            } else {
                contentSB.append("<div style='width:18%; margin:1%; padding:0; display:inline-block;'>" + "<div class='normal-button disabled' id='TATTOO_TYPE_").append(type.getId()).append("'").append(" style='width:100%; margin:0;'>").append(Util.capitaliseSentence(type.getName())).append("</div>").append("</div>");
            }
        }
        contentSB.append("</div>"
                + "<div class='container-full-width' style='width:25%; margin:0;'>");

        contentSB.append("<div class='modifier-icon' style='float:left; width:100%; margin:0; text-align:center;'>" + "<div class='modifier-icon-content'>").append(tattoo.getSVGImage(BodyChanging.getTarget())).append("</div>").append("<div class='overlay no-pointer' id='NEW_TATTOO_INFO'></div>").append("</div>");

        contentSB.append("</div>");
        contentSB.append("</div>");

        // Colours:

        contentSB.append("<div class='container-full-width'>"
                + "<h5 style='width:100%; text-align:center;'>Select Colours</h5>");

        // Primary:
        contentSB.append("<div class='container-full-width' style='width:33.3%; margin:0;'>");
        for (Colour c : tattoo.getType().getAvailablePrimaryColours()) {
            contentSB.append("<div class='normal-button").append(tattoo.getPrimaryColour() == c ? " selected" : "").append("' id='TATTOO_COLOUR_PRIMARY_").append(c.getId()).append("'").append(" style='width:auto; margin-right:4px;").append(tattoo.getPrimaryColour() == c ? " background-color:" + PresetColour.BASE_GREEN.getShades()[4] + ";" : "").append("'>").append("<div class='phone-item-colour' style='background-color:").append(c.toWebHexString()).append(";").append(c == PresetColour.COVERING_NONE ? " color:" + PresetColour.BASE_RED.toWebHexString() + ";'>X" : "'>").append("</div>").append("</div>");
        }
        contentSB.append("</div>");

        // Secondary:
        contentSB.append("<div class='container-full-width' style='width:33.3%; margin:0;'>");
        if (tattoo.getType().getAvailableSecondaryColours().isEmpty()) {
            contentSB.append(
                    "<p style='text-align:center;'>[style.italicsDisabled(No secondary colours available...)]</p>");

        } else {
            for (Colour c : tattoo.getType().getAvailableSecondaryColours()) {
                contentSB.append("<div class='normal-button").append(tattoo.getSecondaryColour() == c ? " selected" : "").append("' id='TATTOO_COLOUR_SECONDARY_").append(c.getId()).append("'").append(" style='width:auto; margin-right:4px;").append(tattoo.getSecondaryColour() == c ? " background-color:" + PresetColour.BASE_GREEN.getShades()[4] + ";" : "").append("'>").append("<div class='phone-item-colour' style='background-color:").append(c.toWebHexString()).append(";").append(c == PresetColour.COVERING_NONE ? " color:" + PresetColour.BASE_RED.toWebHexString() + ";'>X" : "'>").append("</div>").append("</div>");
            }
        }
        contentSB.append("</div>");

        // Tertiary:
        contentSB.append("<div class='container-full-width' style='width:33.3%; margin:0;'>");
        if (tattoo.getType().getAvailableTertiaryColours().isEmpty()) {
            contentSB.append(
                    "<p style='text-align:center;'>[style.italicsDisabled(No tertiary colours available...)]</p>");

        } else {
            for (Colour c : tattoo.getType().getAvailableTertiaryColours()) {
                contentSB.append("<div class='normal-button").append(tattoo.getTertiaryColour() == c ? " selected" : "").append("' id='TATTOO_COLOUR_TERTIARY_").append(c.getId()).append("'").append(" style='width:auto; margin-right:4px;").append(tattoo.getTertiaryColour() == c ? " background-color:" + PresetColour.BASE_GREEN.getShades()[4] + ";" : "").append("'>").append("<div class='phone-item-colour' style='background-color:").append(c.toWebHexString()).append(";").append(c == PresetColour.COVERING_NONE ? " color:" + PresetColour.BASE_RED.toWebHexString() + ";'>X" : "'>").append("</div>").append("</div>");
            }
        }
        contentSB.append("</div>");

        if (Main.game.isInNewWorld()) {
            contentSB.append("<div class='container-full-width'>");
            if (tattoo.getType().equals(TattooType.getTattooTypeFromId("innoxia_misc_none"))) {
                contentSB.append(
                        "<div class='normal-button disabled' style='width:20%; margin:2% 40%; padding:0; text-align:center;'>"
                                + "Glow"
                                + "</div>");

            } else if (tattoo.isGlowing()) {
                contentSB.append(
                        "<div class='normal-button active' id='TATTOO_GLOW' style='width:20%; margin:2% 40%; padding:0; text-align:center;'>"
                                + "[style.boldArcane(Arcane Glow)]"
                                + "</div>");
            } else {
                contentSB.append("<div id='TATTOO_GLOW' class='normal-button' style='width:20%; margin:2% 40%; padding:0; text-align:center;'>" + "<span style='color:").append(PresetColour.GENERIC_ARCANE.getShades()[0]).append(";'>Arcane Glow</span>").append("</div>");
            }
            contentSB.append("</div>");
        }

        contentSB.append("</div>");

        // Writing:

        contentSB.append("<div class='container-full-width'>"
                + "<h5 style='width:100%; text-align:center;'>Select Writing</h5>");

        contentSB.append("<div class='container-full-width' style='width:66.6%; margin:0; position:relative; text-align:center;'>" + "<form style='padding:0; margin:0 0 4px 0; text-align:center;'>" + "<input type='text' id='tattoo_name' value='").append(UtilText.parseForHTMLDisplay(tattoo.getWriting().getText())).append("' style='padding:0;margin:0;width:80%;'>"// "+(tattoo.getWriting().getStyle()==TattooWritingStyle.NONE?"disabled":"")+">"
        ).append("</form>").append("<p style='width:100%; text-align:center;'>Writing Style</p>");
        for (TattooWritingStyle style : TattooWritingStyle.values()) {
            contentSB.append("<div style='width:18%; margin:0 1%; padding:0; display:inline-block;'>" + "<div class='normal-button").append(tattoo.getWriting().getStyles().contains(style) ? " selected" : "").append("' id='TATTOO_WRITING_STYLE_").append(style.toString()).append("'").append(" style='width:100%; margin:0; color:").append((tattoo.getWriting().getStyles().contains(style) ? PresetColour.GENERIC_GOOD : PresetColour.TEXT_HALF_GREY).toWebHexString()).append(";'>").append(Util.capitaliseSentence(style.getName())).append("</div>").append("</div>");
        }
        contentSB.append("<div class='container-full-width'>" + "Output: ").append(tattoo.getFormattedWritingOutput()).append("</div>");
        contentSB.append("</div>");

        contentSB.append("<div class='container-full-width' style='width:33.3%; margin:0;'>");
        for (Colour c : TattooWriting.getAvailableColours()) {
            contentSB.append("<div class='normal-button").append(tattoo.getWriting().getColour() == c ? " selected" : "").append("' id='TATTOO_WRITING_COLOUR_").append(c.getId()).append("'").append(" style='width:auto; margin-right:4px;").append(tattoo.getWriting().getColour() == c ? " background-color:" + PresetColour.BASE_GREEN.getShades()[4] + ";" : "").append("'>").append("<div class='phone-item-colour' style='background-color:").append(c.toWebHexString()).append(";").append(c == PresetColour.COVERING_NONE ? " color:" + PresetColour.BASE_RED.toWebHexString() + ";'>X" : "'>").append("</div>").append("</div>");
        }
        if (Main.game.isInNewWorld()) {
            contentSB.append("<br/>");
            if (tattoo.getWriting().isGlow()) {
                contentSB.append(
                        "<div class='normal-button selected' id='TATTOO_WRITING_GLOW' style='width:50%; margin:2% 25%; padding:0; text-align:center;'>"
                                + "[style.boldArcane(Arcane Glow)]"
                                + "</div>");
            } else {
                contentSB.append("<div id='TATTOO_WRITING_GLOW' class='normal-button' style='width:50%; margin:2% 25%; padding:0; text-align:center;'>" + "<span style='color:").append(PresetColour.GENERIC_ARCANE.getShades()[0]).append(";'>Arcane Glow</span>").append("</div>");
            }
        }
        contentSB.append("</div>");
        contentSB.append("</div>");

        // Counter:
        if (Main.game.isInNewWorld()) {
            contentSB.append("<div class='container-full-width'>"
                    + "<h5 style='width:100%; text-align:center;'>Select Counter</h5>");

            contentSB.append("<div class='container-full-width' style='width:66.6%; margin:0;'>");
            contentSB.append("<div class='container-full-width' style='position:relative; text-align:center;'>");
            contentSB.append("<p style='width:100%; text-align:center;'>Counter Type</p>");
            for (TattooCounterType counterType : TattooCounterType.values()) {
                contentSB.append("<div style='width:48%; margin:1%; padding:0; display:inline-block;'>" + "<div class='normal-button").append(tattoo.getCounter().getType() == counterType ? " selected" : "").append("' id='TATTOO_COUNTER_TYPE_").append(counterType.toString()).append("'").append(" style='width:100%; margin:0; color:").append((tattoo.getCounter().getType() == counterType ? PresetColour.GENERIC_GOOD : PresetColour.TEXT_HALF_GREY).toWebHexString()).append(";'>").append(Util.capitaliseSentence(counterType.getName())).append("</div>").append("</div>");
            }
            contentSB.append("</div>");
            contentSB.append("</div>");

            contentSB.append("<div class='container-full-width' style='width:33.3%; margin:0;'>");
            for (Colour c : TattooCounter.getAvailableColours()) {
                contentSB.append("<div class='normal-button").append(tattoo.getCounter().getColour() == c ? " selected" : "").append("' id='TATTOO_COUNTER_COLOUR_").append(c.getId()).append("'").append(" style='width:auto; margin-right:4px;").append(tattoo.getCounter().getColour() == c ? " background-color:" + PresetColour.BASE_GREEN.getShades()[4] + ";" : "").append("'>").append("<div class='phone-item-colour' style='background-color:").append(c.toWebHexString()).append(";").append(c == PresetColour.COVERING_NONE ? " color:" + PresetColour.BASE_RED.toWebHexString() + ";'>X" : "'>").append("</div>").append("</div>");
            }
            contentSB.append("<br/>");
            if (tattoo.getCounter().isGlow()) {
                contentSB.append(
                        "<div class='normal-button selected' id='TATTOO_COUNTER_GLOW' style='width:50%; margin:2% 25%; padding:0; text-align:center;'>"
                                + "[style.boldArcane(Arcane Glow)]"
                                + "</div>");
            } else {
                contentSB.append("<div id='TATTOO_COUNTER_GLOW' class='normal-button' style='width:50%; margin:2% 25%; padding:0; text-align:center;'>" + "<span style='color:").append(PresetColour.GENERIC_ARCANE.getShades()[0]).append(";'>Arcane Glow</span>").append("</div>");
            }

            contentSB.append("<div class='container-full-width' style='position:relative; text-align:center;'>");
            contentSB.append("<p style='width:100%; text-align:center;'>Counter Style</p>");
            for (TattooCountType countType : TattooCountType.values()) {
                contentSB.append("<div style='width:98%; margin:1%; padding:0; display:inline-block;'>" + "<div class='normal-button").append(tattoo.getCounter().getCountType() == countType ? " selected" : "").append("' id='TATTOO_COUNT_TYPE_").append(countType.toString()).append("'").append(" style='width:100%; margin:0; color:").append((tattoo.getCounter().getCountType() == countType ? PresetColour.GENERIC_GOOD : PresetColour.TEXT_HALF_GREY).toWebHexString()).append(";'>").append(Util.capitaliseSentence(countType.getName())).append("</div>").append("</div>");
            }
            contentSB.append("</div>");

            contentSB.append("<div class='container-full-width'>" + "Output: ").append(tattoo.getFormattedCounterOutput(BodyChanging.getTarget())).append("</div>");
            contentSB.append("</div>");

            contentSB.append("</div>");
        }

        contentSB.append("<p id='hiddenPField' style='display:none;'></p>");

        return contentSB.toString();
    }

}
