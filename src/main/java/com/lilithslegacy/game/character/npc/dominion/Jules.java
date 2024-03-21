package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.valueEnums.AreolaeSize;
import com.lilithslegacy.game.character.body.valueEnums.AssSize;
import com.lilithslegacy.game.character.body.valueEnums.BodyHair;
import com.lilithslegacy.game.character.body.valueEnums.BodySize;
import com.lilithslegacy.game.character.body.valueEnums.BreastShape;
import com.lilithslegacy.game.character.body.valueEnums.Capacity;
import com.lilithslegacy.game.character.body.valueEnums.CoveringPattern;
import com.lilithslegacy.game.character.body.valueEnums.CupSize;
import com.lilithslegacy.game.character.body.valueEnums.HairStyle;
import com.lilithslegacy.game.character.body.valueEnums.HipSize;
import com.lilithslegacy.game.character.body.valueEnums.LipSize;
import com.lilithslegacy.game.character.body.valueEnums.Muscle;
import com.lilithslegacy.game.character.body.valueEnums.NippleSize;
import com.lilithslegacy.game.character.body.valueEnums.OrificeElasticity;
import com.lilithslegacy.game.character.body.valueEnums.OrificePlasticity;
import com.lilithslegacy.game.character.body.valueEnums.PenetrationGirth;
import com.lilithslegacy.game.character.body.valueEnums.TesticleSize;
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
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.game.sex.managers.dominion.SMJulesCockSucking;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.9
 * @since 0.2.8
 */
public class Jules extends NPC {

    public Jules() {
        this(false);
    }

    public Jules(boolean isImported) {
        super(isImported, new NameTriplet("Jules", "Jules", "Julia"), "Kamau",
                "Jules is the zebra-boy bouncer for the nightclub 'The Watering Hole'.",
                33, Month.FEBRUARY, 21,
                10,
                Gender.M_P_MALE,
                Subspecies.HORSE_MORPH_ZEBRA,
                RaceStage.GREATER,
                new CharacterInventory(10),
                WorldType.NIGHTLIFE_CLUB,
                PlaceType.WATERING_HOLE_ENTRANCE,
                true);

        if (!isImported) {
            this.setAttribute(Attribute.MAJOR_CORRUPTION, 35);
        }

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
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.5.6")) {
            this.equipClothing();
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.6")) {
            this.resetPerksMap(true);
        }
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_SLUT);

        PerkManager.initialisePerks(this,
                Util.newArrayListOfValues(Perk.UNARMED_DAMAGE),
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

            this.setHistory(Occupation.NPC_BOUNCER);

            this.addFetish(Fetish.FETISH_DOMINANT);
            this.addFetish(Fetish.FETISH_ORAL_RECEIVING);

            this.setFetishDesire(Fetish.FETISH_VAGINAL_GIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_SUBMISSIVE, FetishDesire.ONE_DISLIKE);
        }

        // Body:

        // Core:
        this.setHeight(190);
        this.setFemininity(5);
        this.setMuscle(Muscle.THREE_MUSCULAR.getMedianValue());
        this.setBodySize(BodySize.THREE_LARGE.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_HORSE_MORPH, PresetColour.EYE_BROWN));
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_EBONY), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HORSE_HAIR, CoveringPattern.STRIPED, PresetColour.COVERING_BLACK, false, PresetColour.COVERING_WHITE, false), true);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HUMAN, CoveringPattern.STRIPED, PresetColour.COVERING_BLACK, false, PresetColour.COVERING_WHITE, false), false);
        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HORSE_HAIR, CoveringPattern.STRIPED, PresetColour.COVERING_BLACK, false, PresetColour.COVERING_WHITE, false), false);
        this.setHairLength(7);
        this.setHairStyle(HairStyle.MOHAWK);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_BLACK), false);
        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HORSE_HAIR, PresetColour.COVERING_BLACK), false);
        this.setUnderarmHair(BodyHair.FOUR_NATURAL);
        this.setAssHair(BodyHair.FOUR_NATURAL);
        this.setPubicHair(BodyHair.FOUR_NATURAL);
        this.setFacialHair(BodyHair.ZERO_NONE);

//        this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_RED));
//        this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, PresetColour.COVERING_RED));
//        this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
//        this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_RED));
//        this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
//        this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PURPLE));

        // Face:
        this.setFaceVirgin(true);
        this.setLipSize(LipSize.ONE_AVERAGE);
        this.setFaceCapacity(Capacity.ZERO_IMPENETRABLE, true);
        // Throat settings and modifiers
        this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        this.setNippleVirgin(true);
        this.setBreastSize(CupSize.FLAT.getMeasurement());
        this.setBreastShape(BreastShape.ROUND);
        this.setNippleSize(NippleSize.ZERO_TINY);
        this.setAreolaeSize(AreolaeSize.ZERO_TINY);
        // Nipple settings and modifiers

        // Ass:
        this.setAssVirgin(true);
        this.setAssBleached(false);
        this.setAssSize(AssSize.TWO_SMALL);
        this.setHipSize(HipSize.TWO_NARROW);
        this.setAssCapacity(Capacity.ZERO_IMPENETRABLE, true);
        this.setAssWetness(Wetness.ZERO_DRY);
        this.setAssElasticity(OrificeElasticity.ONE_RIGID.getValue());
        this.setAssPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());
        // Anus modifiers

        // Penis:
        this.setPenisVirgin(false);
        this.setPenisGirth(PenetrationGirth.FOUR_GIRTHY);
        this.setPenisSize(25);
        this.setTesticleSize(TesticleSize.THREE_LARGE);
        this.setPenisCumStorage(40);
        this.fillCumToMaxStorage();
        // Leave cum as normal value

        // Vagina:
        // No vagina

        // Feet:
        // Foot shape
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.unequipAllClothingIntoVoid(true, true);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_crotchless_briefs", PresetColour.CLOTHING_BLACK, false), true, this);
//        this.equipClothingFromNowhere(Main.game.getItemGeneration().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_BLACK, false), true, this);
//        this.equipClothingFromNowhere(Main.game.getItemGeneration().generateClothing("innoxia_foot_work_boots", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_jeans", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_short_sleeved_shirt", PresetColour.CLOTHING_WHITE, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_MENS_WATCH, PresetColour.CLOTHING_SILVER, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_eye_aviators", PresetColour.CLOTHING_BLACK_STEEL, false), true, this);

    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public void endSex() {
        setPendingClothingDressing(true);
        Main.game.getPlayer().setNearestLocation(WorldType.NIGHTLIFE_CLUB, PlaceType.WATERING_HOLE_MAIN_AREA, false);
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

    // Sex:
    @Override
    public boolean getSexBehaviourDeniesRequests(GameCharacter requestingCharacter, SexType sexTypeRequest) {
        if (Main.game.isInSex() && Main.sex.getInitialSexManager() instanceof SMJulesCockSucking) {
            return true;
        }
        return super.getSexBehaviourDeniesRequests(requestingCharacter, sexTypeRequest);
    }
//
//    @Override
//    public SexType getForeplayPreference(GameCharacter target) {
//        if(Main.sex.getSexPositionSlot(Main.game.getNpc(Jules.class))==SexSlotStanding.STANDING_DOMINANT && this.hasPenis()) {
//            return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH);
//        }
//
//        return super.getForeplayPreference(target);
//    }
//
//    @Override
//    public SexType getMainSexPreference(GameCharacter target) {
//        if(Main.sex.getSexPositionSlot(Main.game.getNpc(Jules.class))==SexSlotStanding.STANDING_DOMINANT && this.hasPenis()) {
//            return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH);
//        }
//
//        return super.getMainSexPreference(target);
//    }
}
