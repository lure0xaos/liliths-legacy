package com.lilithslegacy.game.character.npc.fields;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.types.HornType;
import com.lilithslegacy.game.character.body.types.TailType;
import com.lilithslegacy.game.character.body.types.WingType;
import com.lilithslegacy.game.character.body.valueEnums.BodyHair;
import com.lilithslegacy.game.character.body.valueEnums.BodySize;
import com.lilithslegacy.game.character.body.valueEnums.HairLength;
import com.lilithslegacy.game.character.body.valueEnums.HairStyle;
import com.lilithslegacy.game.character.body.valueEnums.Muscle;
import com.lilithslegacy.game.character.body.valueEnums.PenetrationGirth;
import com.lilithslegacy.game.character.body.valueEnums.TesticleSize;
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
import com.lilithslegacy.game.combat.DamageType;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;
import com.lilithslegacy.game.inventory.enchanting.ItemEffectType;
import com.lilithslegacy.game.inventory.enchanting.TFModifier;
import com.lilithslegacy.game.inventory.enchanting.TFPotency;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.9
 * @since 0.4.9
 */
public class Sleip extends NPC {

    public Sleip() {
        this(false);
    }

    public Sleip(boolean isImported) {
        super(isImported, new NameTriplet("Sleip"), "Loviennemartu",
                "Sleip is one of two nightmare bodyguards who protect Angelixx at all times.",
                28, Month.AUGUST, 28,
                20, Gender.M_P_MALE, Subspecies.HORSE_MORPH, RaceStage.GREATER,
                new CharacterInventory(30),
                WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL,
                true);
        if (!isImported) {
            this.setPlayerKnowsName(false);
            this.setGenericName("furious nightmare");
        }
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_DIRTY_MINDED);
        this.addSpecialPerk(Perk.SPECIAL_HEALTH_FANATIC);
        this.addSpecialPerk(Perk.SPECIAL_MELEE_EXPERT);

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
                    PersonalityTrait.SELFISH,
                    PersonalityTrait.BRAVE,
                    PersonalityTrait.CONFIDENT,
                    PersonalityTrait.LEWD);

            this.setSexualOrientation(SexualOrientation.GYNEPHILIC);

            this.setHistory(Occupation.NPC_GANG_BODY_GUARD);

            this.addFetish(Fetish.FETISH_DOMINANT);
            this.addFetish(Fetish.FETISH_NON_CON_DOM);
            this.addFetish(Fetish.FETISH_DEFLOWERING);

            this.setFetishDesire(Fetish.FETISH_INCEST, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_ORAL_RECEIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_SADIST, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_BREASTS_OTHERS, FetishDesire.THREE_LIKE);

            this.setFetishDesire(Fetish.FETISH_ANAL_RECEIVING, FetishDesire.ONE_DISLIKE);

            this.setFetishDesire(Fetish.FETISH_BONDAGE_VICTIM, FetishDesire.ZERO_HATE);
            this.setFetishDesire(Fetish.FETISH_SUBMISSIVE, FetishDesire.ZERO_HATE);
            this.setFetishDesire(Fetish.FETISH_PENIS_RECEIVING, FetishDesire.ZERO_HATE);
        }


        // Body:
        this.setBody(Main.game.getCharacterUtils().generateHalfDemonBody(this, Gender.M_P_MALE, Subspecies.HORSE_MORPH, true, RaceStage.GREATER), false);
        this.setWingType(WingType.NONE);
        this.setHornType(HornType.NONE);
        this.setTailType(TailType.HORSE_MORPH);

        // Core:
        this.setHeight(205);
        this.setFemininity(0);
        this.setMuscle(Muscle.FOUR_RIPPED.getMedianValue());
        this.setBodySize(BodySize.THREE_LARGE.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_DEMON_COMMON, PresetColour.EYE_RED));
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_HORSE_MORPH, PresetColour.EYE_RED));
        this.setSkinCovering(new Covering(BodyCoveringType.HORSE_HAIR, PresetColour.COVERING_BLACK), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_EBONY), true);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HORSE_HAIR, PresetColour.COVERING_JET_BLACK), true);
        this.setHairLength(HairLength.TWO_SHORT);
        this.setHairStyle(HairStyle.LOOSE);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_JET_BLACK), false);
        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HORSE_HAIR, PresetColour.COVERING_JET_BLACK), false);
        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_DEMON, PresetColour.COVERING_JET_BLACK), false);
        this.setUnderarmHair(BodyHair.FOUR_NATURAL);
        this.setAssHair(BodyHair.FOUR_NATURAL);
        this.setPubicHair(BodyHair.FOUR_NATURAL);
        this.setFacialHair(BodyHair.ZERO_NONE);

//		this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_RED));
//		this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, PresetColour.COVERING_RED));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
//		this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_RED));
//		this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
//		this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PURPLE));

        // Face:
        this.setFaceVirgin(true);
        // Leave as default:
//		this.setLipSize(LipSize.ONE_AVERAGE);
//		this.setFaceCapacity(Capacity.ZERO_IMPENETRABLE, true);
        // Throat settings and modifiers
//		this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        // Leave as default:
//		this.setNippleVirgin(true);
//		this.setBreastSize(CupSize.FLAT.getMeasurement());
//		this.setBreastShape(BreastShape.ROUND);
//		this.setNippleSize(NippleSize.ZERO_TINY);
//		this.setAreolaeSize(AreolaeSize.ZERO_TINY);
        // Nipple settings and modifiers

        // Ass:
        this.setAssVirgin(true);
        this.setAssBleached(false);
        // Leave as default:
//		this.setAssSize(AssSize.TWO_SMALL);
//		this.setHipSize(HipSize.TWO_NARROW);
//		this.setAssCapacity(Capacity.ZERO_IMPENETRABLE, true);
//		this.setAssWetness(Wetness.ZERO_DRY);
//		this.setAssElasticity(OrificeElasticity.ONE_RIGID.getValue());
//		this.setAssPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());
        // Anus modifiers

        // Penis:
        this.setPenisVirgin(false);
        this.setPenisGirth(PenetrationGirth.FIVE_THICK);
        this.setPenisSize(34);
        this.setTesticleSize(TesticleSize.FOUR_HUGE);
        this.setPenisCumStorage(400);
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

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_jockstrap", PresetColour.CLOTHING_BLUE_NAVY, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_jeans", PresetColour.CLOTHING_BLUE_NAVY, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_hips_leather_belt", PresetColour.CLOTHING_DESATURATED_BROWN_DARK, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_tshirt", PresetColour.CLOTHING_DESATURATED_BROWN_DARK, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_finger_meander_ring", PresetColour.CLOTHING_STEEL, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_MENS_WATCH, PresetColour.CLOTHING_STEEL, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_stpvest", false), true, this);


        AbstractClothing necklace = Main.game.getItemGen().generateClothing("innoxia_neck_horseshoe_necklace", PresetColour.CLOTHING_RED_DARK, PresetColour.CLOTHING_STEEL, null, false);
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.RESISTANCE_LUST, TFPotency.MAJOR_DRAIN, 0));
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.RESISTANCE_LUST, TFPotency.MAJOR_DRAIN, 0));
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.RESISTANCE_LUST, TFPotency.MAJOR_DRAIN, 0));
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.RESISTANCE_LUST, TFPotency.MINOR_DRAIN, 0));

        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_MAJOR_ATTRIBUTE, TFModifier.STRENGTH, TFPotency.MAJOR_BOOST, 0));
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_MAJOR_ATTRIBUTE, TFModifier.STRENGTH, TFPotency.MAJOR_BOOST, 0));
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_MAJOR_ATTRIBUTE, TFModifier.STRENGTH, TFPotency.MAJOR_BOOST, 0));
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_MAJOR_ATTRIBUTE, TFModifier.STRENGTH, TFPotency.MINOR_BOOST, 0));

        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.DAMAGE_PHYSICAL, TFPotency.MAJOR_BOOST, 0));
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.DAMAGE_PHYSICAL, TFPotency.MAJOR_BOOST, 0));
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.DAMAGE_PHYSICAL, TFPotency.MAJOR_BOOST, 0));
        necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.DAMAGE_PHYSICAL, TFPotency.MINOR_BOOST, 0));
        this.equipClothingFromNowhere(necklace, true, this);
        necklace.setName("Angelixx's Gift");

        this.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon("innoxia_bat_metal", DamageType.PHYSICAL));

        this.setPiercedEar(true);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_ear_ball_studs", PresetColour.CLOTHING_STEEL, false), true, this);
        this.setPiercedNose(true);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_cattle_piercing_nose_ring", PresetColour.CLOTHING_STEEL, false), true, this);
        this.setPiercedNipples(true);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_basic_barbell_pair", PresetColour.CLOTHING_STEEL, false), true, this);
        this.setPiercedPenis(true);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_penis_ring", PresetColour.CLOTHING_STEEL, false), true, this);
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
    public void endSex() {
    }

}
