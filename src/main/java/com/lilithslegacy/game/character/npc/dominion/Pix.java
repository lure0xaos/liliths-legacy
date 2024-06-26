package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.valueEnums.AreolaeSize;
import com.lilithslegacy.game.character.body.valueEnums.AssSize;
import com.lilithslegacy.game.character.body.valueEnums.BodyHair;
import com.lilithslegacy.game.character.body.valueEnums.BodySize;
import com.lilithslegacy.game.character.body.valueEnums.BreastShape;
import com.lilithslegacy.game.character.body.valueEnums.Capacity;
import com.lilithslegacy.game.character.body.valueEnums.ClitorisSize;
import com.lilithslegacy.game.character.body.valueEnums.CoveringModifier;
import com.lilithslegacy.game.character.body.valueEnums.CoveringPattern;
import com.lilithslegacy.game.character.body.valueEnums.CupSize;
import com.lilithslegacy.game.character.body.valueEnums.HairLength;
import com.lilithslegacy.game.character.body.valueEnums.HairStyle;
import com.lilithslegacy.game.character.body.valueEnums.HipSize;
import com.lilithslegacy.game.character.body.valueEnums.LabiaSize;
import com.lilithslegacy.game.character.body.valueEnums.LipSize;
import com.lilithslegacy.game.character.body.valueEnums.Muscle;
import com.lilithslegacy.game.character.body.valueEnums.NippleSize;
import com.lilithslegacy.game.character.body.valueEnums.OrificeElasticity;
import com.lilithslegacy.game.character.body.valueEnums.OrificePlasticity;
import com.lilithslegacy.game.character.body.valueEnums.TongueLength;
import com.lilithslegacy.game.character.body.valueEnums.Wetness;
import com.lilithslegacy.game.character.effects.Perk;
import com.lilithslegacy.game.character.effects.PerkCategory;
import com.lilithslegacy.game.character.effects.PerkManager;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.fetishes.FetishDesire;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.NameTriplet;
import com.lilithslegacy.game.character.persona.Occupation;
import com.lilithslegacy.game.character.persona.PersonalityTrait;
import com.lilithslegacy.game.character.persona.SexualOrientation;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.7.8
 * @since 0.1.6?
 */
public class Pix extends NPC {

    private static String PERK_PROGRESS_ID = "gym_perk_progress";

    public Pix() {
        this(false);
    }

    public Pix(boolean isImported) {
        super(isImported, new NameTriplet("Pix"), "Fear",
                "An extremely energetic border collie-girl, who is the owner and manager of the Shopping Arcade's gym; 'Pix's Playground'.",
                29, Month.FEBRUARY, 21,
                10, Gender.F_V_B_FEMALE, Subspecies.DOG_MORPH_BORDER_COLLIE, RaceStage.GREATER,
                new CharacterInventory(10),
                WorldType.getWorldTypeFromId("innoxia_dominion_shopping_arcade_gym"), PlaceType.getPlaceTypeFromId("innoxia_dominion_shopping_arcade_gym_reception"),
                true);

    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);

        if (Main.isVersionOlderThan(Game.loadingVersion, "0.2.10.5")) {
            resetBodyAfterVersion_2_10_5();
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.5.1")) {
            this.setPersonalityTraits(
                    PersonalityTrait.CONFIDENT);
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.6")) {
            this.resetPerksMap(true);
        }
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_HEALTH_FANATIC);
        this.addSpecialPerk(Perk.SPECIAL_DIRTY_MINDED);

        PerkManager.initialisePerks(this,
                Util.newArrayListOfValues(),
                Util.newHashMapOfValues(
                        new Value<>(PerkCategory.PHYSICAL, 1),
                        new Value<>(PerkCategory.LUST, 0),
                        new Value<>(PerkCategory.ARCANE, 0)));
    }


    @Override
    public void setStartingBody(boolean setPersona) {

        // Persona:

        if (setPersona) {
            this.setPersonalityTraits(
                    PersonalityTrait.CONFIDENT);

            this.setSexualOrientation(SexualOrientation.AMBIPHILIC);

            this.setHistory(Occupation.NPC_GYM_OWNER);

            this.addFetish(Fetish.FETISH_DENIAL);
            this.addFetish(Fetish.FETISH_DOMINANT);

            this.setFetishDesire(Fetish.FETISH_VAGINAL_RECEIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_PREGNANCY, FetishDesire.ONE_DISLIKE);
            this.setFetishDesire(Fetish.FETISH_ANAL_RECEIVING, FetishDesire.ZERO_HATE);
        }

        // Body:

        // Core:
        this.setHeight(175);
        this.setFemininity(75);
        this.setMuscle(Muscle.FOUR_RIPPED.getMedianValue());
        this.setBodySize(BodySize.TWO_AVERAGE.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_DOG_MORPH, PresetColour.EYE_GREEN));
        this.setSkinCovering(new Covering(BodyCoveringType.CANINE_FUR, CoveringPattern.MARKED, CoveringModifier.FLUFFY, PresetColour.COVERING_BLACK, false, PresetColour.COVERING_WHITE, false), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_OLIVE), true);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_CANINE_FUR, PresetColour.COVERING_BROWN_DARK), true);
        this.setHairLength(HairLength.THREE_SHOULDER_LENGTH.getMedianValue());
        this.setHairStyle(HairStyle.PONYTAIL);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_BROWN_DARK), false);
        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_CANINE_FUR, PresetColour.COVERING_BROWN_DARK), false);
        this.setUnderarmHair(BodyHair.ZERO_NONE);
        this.setAssHair(BodyHair.ZERO_NONE);
        this.setPubicHair(BodyHair.ZERO_NONE);
        this.setFacialHair(BodyHair.ZERO_NONE);

//		this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_CLEAR));
//		this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, PresetColour.COVERING_CLEAR));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
//		this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_RED_LIGHT));
//		this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
//		this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PINK));

        // Face:
        this.setFaceVirgin(false);
        this.setLipSize(LipSize.ONE_AVERAGE);
        this.setFaceCapacity(Capacity.THREE_SLIGHTLY_LOOSE, true);
        // Throat settings and modifiers
        this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        this.setBreastRows(3);
        this.setNippleVirgin(true);
        this.setBreastSize(CupSize.B.getMeasurement());
        this.setBreastShape(BreastShape.POINTY);
        this.setNippleSize(NippleSize.TWO_BIG.getValue());
        this.setAreolaeSize(AreolaeSize.TWO_BIG.getValue());
        // Nipple settings and modifiers

        // Ass:
        this.setAssVirgin(true);
        this.setAssBleached(false);
        this.setAssSize(AssSize.THREE_NORMAL);
        this.setHipSize(HipSize.THREE_GIRLY);
        this.setAssCapacity(Capacity.TWO_TIGHT, true);
        this.setAssWetness(Wetness.ZERO_DRY);
        this.setAssElasticity(OrificeElasticity.FOUR_LIMBER.getValue());
        this.setAssPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());
        // Anus modifiers

        // Penis:
        // No penis

        // Vagina:
        this.setVaginaVirgin(false);
        this.setVaginaClitorisSize(ClitorisSize.ZERO_AVERAGE);
        this.setVaginaLabiaSize(LabiaSize.ZERO_TINY);
        this.setVaginaSquirter(true);
        this.setVaginaCapacity(Capacity.FOUR_LOOSE, true);
        this.setVaginaWetness(Wetness.FOUR_SLIMY);
        this.setVaginaElasticity(OrificeElasticity.THREE_FLEXIBLE.getValue());
        this.setVaginaPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());

        // Feet:
//		this.setFootStructure(FootStructure.PLANTIGRADE);
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {

        this.unequipAllClothingIntoVoid(true, true);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_boyshorts", PresetColour.CLOTHING_WHITE, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_sports_bra", PresetColour.CLOTHING_GREEN_LIME, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_sport_shorts", PresetColour.CLOTHING_BLUE_LIGHT, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_WHITE, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_trainers", PresetColour.CLOTHING_PINK_LIGHT, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_head_sweatband", PresetColour.CLOTHING_RED, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_WRISTBANDS, PresetColour.CLOTHING_RED, false), true, this);

    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public boolean isAbleToBeImpregnated() {
        return true;
    }

    @Override
    public void changeFurryLevel() {
    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }

    @Override
    public void turnUpdate() {
        if (Main.game.getPlayer().getWorldLocation() != WorldType.getWorldTypeFromId("innoxia_dominion_shopping_arcade_gym")
                && Main.game.getDialogueFlags().hasFlag("innoxia_pix_had_tour")
                && !Main.game.getCharactersPresent().contains(this)) {
            if (Main.game.isExtendedWorkTime()) {
                this.setLocation(WorldType.getWorldTypeFromId("innoxia_dominion_shopping_arcade_gym"), PlaceType.getPlaceTypeFromId("innoxia_dominion_shopping_arcade_gym_reception"), true);
            } else {
                this.setLocation(WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);
            }
        }
    }


    // Methods for her special workout in the gym:

    private int getWorkoutIncrement() {
        return 10
                + (Main.game.getDialogueFlags().hasFlag("innoxia_pix_workout_weights_saved_energy") ? 0 : 5)
                + (Main.game.getDialogueFlags().hasFlag("innoxia_pix_workout_cardio_saved_energy") ? 0 : 5);
    }

    public void incrementPerkProgress() {
        int increment = getWorkoutIncrement();

        if (!Main.game.getDialogueFlags().hasSavedLong(PERK_PROGRESS_ID) || Main.game.getDialogueFlags().getSavedLong(PERK_PROGRESS_ID) < 0) {
            Main.game.getDialogueFlags().setSavedLong(PERK_PROGRESS_ID, 0);
        }
        Main.game.getDialogueFlags().incrementSavedLong(PERK_PROGRESS_ID, increment);
    }

    public String getPerkProgressString() {
        int increment = getWorkoutIncrement();

        if (!Main.game.getPlayer().hasPerkAnywhereInTree(Perk.PIX_TRAINING) && Main.game.getDialogueFlags().getSavedLong(PERK_PROGRESS_ID) < 100) {
            UtilText.addSpecialParsingString(String.valueOf(increment), true);
            UtilText.addSpecialParsingString(String.valueOf(Main.game.getDialogueFlags().getSavedLong(PERK_PROGRESS_ID)), false);
            return UtilText.parseFromXMLFile("places/dominion/shoppingArcade/pixsPlayground", "PERK_PROGRESS");
        }
        return "";
    }

}
