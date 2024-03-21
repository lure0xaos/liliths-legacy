package com.lilithslegacy.game.character.npc.fields;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.types.EarType;
import com.lilithslegacy.game.character.body.types.EyeType;
import com.lilithslegacy.game.character.body.types.HairType;
import com.lilithslegacy.game.character.body.valueEnums.AssSize;
import com.lilithslegacy.game.character.body.valueEnums.BodyHair;
import com.lilithslegacy.game.character.body.valueEnums.BodySize;
import com.lilithslegacy.game.character.body.valueEnums.HairLength;
import com.lilithslegacy.game.character.body.valueEnums.HairStyle;
import com.lilithslegacy.game.character.body.valueEnums.HipSize;
import com.lilithslegacy.game.character.body.valueEnums.LipSize;
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
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;
import com.lilithslegacy.game.inventory.enchanting.ItemEffectType;
import com.lilithslegacy.game.inventory.enchanting.TFModifier;
import com.lilithslegacy.game.inventory.enchanting.TFPotency;
import com.lilithslegacy.game.sex.GenericSexFlag;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.4
 * @since 0.4.1
 */
public class Kheiron extends NPC {

    public Kheiron() {
        this(false);
    }

    public Kheiron(boolean isImported) {
        super(isImported, new NameTriplet("Kheiron"), "Grigori",
                "The owner of the tavern 'The Centaur's Sword' in Elis, this huge, muscular centaur is known throughout town as being remarkably kind and wise.",
                45, Month.OCTOBER, 11,
                25, Gender.M_P_MALE, Subspecies.CENTAUR, RaceStage.PARTIAL_FULL,
                new CharacterInventory(10),
                WorldType.getWorldTypeFromId("innoxia_fields_elis_tavern_taur"), PlaceType.getPlaceTypeFromId("innoxia_fields_elis_tavern_taur_bar"),
                true);

        this.setGenericName("muscular centaur");

        if (!isImported) {
            this.setPlayerKnowsName(false);
        }
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);

        if (Main.isVersionOlderThan(Game.loadingVersion, "0.4.3.9")) {
            this.setFaceVirgin(false);
            this.setAssVirgin(false);
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.4.6.1")) {
            this.setStartingBody(true);
        }
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_HEALTH_FANATIC);
        this.addSpecialPerk(Perk.SPECIAL_MARTIAL_BACKGROUND);
        this.addSpecialPerk(Perk.SPECIAL_MELEE_EXPERT);

        PerkManager.initialisePerks(this,
                Util.newArrayListOfValues(Perk.MELEE_DAMAGE),
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
                    PersonalityTrait.CONFIDENT,
                    PersonalityTrait.KIND,
                    PersonalityTrait.BRAVE);

            this.setSexualOrientation(SexualOrientation.AMBIPHILIC);

            this.setHistory(Occupation.NPC_TAVERN_OWNER);

            this.addFetish(Fetish.FETISH_PENIS_GIVING);

            this.setFetishDesire(Fetish.FETISH_VAGINAL_GIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_IMPREGNATION, FetishDesire.THREE_LIKE);

            this.setFetishDesire(Fetish.FETISH_ANAL_RECEIVING, FetishDesire.ZERO_HATE);
            this.setFetishDesire(Fetish.FETISH_NON_CON_DOM, FetishDesire.ZERO_HATE);
        }


        // Body:
        setBody(Gender.M_P_MALE, Subspecies.CENTAUR, RaceStage.PARTIAL_FULL, false);

        // Core:
        this.setHeight(230);
        this.setFemininity(0);
        this.setMuscle(100);
        this.setBodySize(BodySize.FOUR_HUGE.getMedianValue());
        // Parts changes:
        this.setHairType(HairType.HUMAN);
        this.setEarType(EarType.HUMAN);
        this.setEyeType(EyeType.HUMAN);

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_HORSE_MORPH, PresetColour.EYE_HAZEL));
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_HUMAN, PresetColour.EYE_HAZEL));
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_OLIVE), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HORSE_HAIR, PresetColour.COVERING_BLACK), true);

        this.setSkinCovering(new Covering(BodyCoveringType.ANUS, PresetColour.SKIN_DARK), false);
        this.setSkinCovering(new Covering(BodyCoveringType.PENIS, PresetColour.SKIN_DARK), false);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HUMAN, PresetColour.COVERING_BLACK), true);
        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HORSE_HAIR, PresetColour.COVERING_BLACK), true);
        this.setHairLength(HairLength.TWO_SHORT.getMedianValue());
        this.setHairStyle(HairStyle.STRAIGHT);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_BLACK), false);
        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HORSE_HAIR, PresetColour.COVERING_BLACK), false);
        this.setUnderarmHair(BodyHair.FOUR_NATURAL);
        this.setAssHair(BodyHair.ZERO_NONE);
        this.setPubicHair(BodyHair.ZERO_NONE);
        this.setFacialHair(BodyHair.SIX_BUSHY);

//		this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, PresetColour.COVERING_PURPLE));
//		this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_PURPLE));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_BLACK));
//		this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_PURPLE));
//		this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
//		this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PURPLE));

        // Face:
        this.setFaceVirgin(false);
        this.setLipSize(LipSize.ONE_AVERAGE);
//		this.setFaceCapacity(Capacity.FIVE_ROOMY, true);
        // Throat settings and modifiers
//		this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        this.setNippleVirgin(true);
//		this.setBreastSize(CupSize.E.getMeasurement());
//		this.setBreastShape(BreastShape.ROUND);
//		this.setNippleSize(NippleSize.THREE_LARGE);
//		this.setAreolaeSize(AreolaeSize.THREE_LARGE);
        // Nipple settings and modifiers

        // Ass:
        this.setAssVirgin(false);
        this.setAssBleached(false);
        this.setAssSize(AssSize.THREE_NORMAL);
        this.setHipSize(HipSize.TWO_NARROW);
//		this.setAssCapacity(Capacity.FIVE_ROOMY, true);
//		this.setAssWetness(Wetness.FIVE_SLOPPY);
        // Horse-like modifiers:
//		this.addAssOrificeModifier(OrificeModifier.PUFFY);
//		this.addAssOrificeModifier(OrificeModifier.RIBBED);
//		this.addAssOrificeModifier(OrificeModifier.MUSCLE_CONTROL);

        // Penis:
        this.setPenisVirgin(false);
        this.setPenisGirth(PenetrationGirth.FIVE_THICK);
        this.setPenisSize(50);
        this.setTesticleSize(TesticleSize.FOUR_HUGE);
        this.setPenisCumStorage(500);
        this.setPenisCumExpulsion(85);
        this.fillCumToMaxStorage();
        this.setTesticleCount(2);
        // Horse-like modifiers:
//		this.clearPenisModifiers();
//		this.addPenisModifier(PenetrationModifier.FLARED);
//		this.addPenisModifier(PenetrationModifier.VEINY);
//		this.addPenisModifier(PenetrationModifier.SHEATHED);
//		this.addCumModifier(FluidModifier.MUSKY);

        // Vagina:
//		this.setVaginaVirgin(false);
//		this.setVaginaClitorisSize(ClitorisSize.ZERO_AVERAGE);
//		this.setVaginaLabiaSize(LabiaSize.TWO_AVERAGE);
//		this.setVaginaSquirter(true);
//		this.setVaginaCapacity(Capacity.TWO_TIGHT, true);
//		this.setVaginaWetness(Wetness.FOUR_SLIMY);
//		this.setVaginaElasticity(OrificeElasticity.SEVEN_ELASTIC.getValue());
//		this.setVaginaPlasticity(OrificePlasticity.ONE_SPRINGY.getValue());

        // Feet:
        // Foot shape
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.unequipAllClothingIntoVoid(true, true);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_short_sleeved_shirt", PresetColour.CLOTHING_GREEN_DRAB, false), true, this);
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getSpeechColour() {
        if (Main.game.isLightTheme()) {
            return "#2b4a8a";
        }
        return "#6a87c3";
    }

    @Override
    public void changeFurryLevel() {
    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }

    @Override
    public void dailyUpdate() {
    }

    @Override
    public void turnUpdate() {
        if (!Main.game.getCharactersPresent().contains(this) && this.isPlayerKnowsName()) { // Only move away after player met him
            if (Main.game.isHourBetween(8, 24)) {
                this.returnToHome(); // Tavern bar
            } else {
                this.setLocation(WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);
            }
        }
    }

    @Override
    public void endSex() {
    }

    @Override
    public boolean isAbleToBeImpregnated() {
        return false;
    }

    public void applyGolixCollar(boolean equip) {
        if (equip) {
            AbstractClothing collar = Main.game.getItemGen().generateClothing("innoxia_bdsm_choker", PresetColour.CLOTHING_DESATURATED_BROWN_DARK, PresetColour.CLOTHING_BRASS, null, false);
            collar.setSticker("top_txt", "good");
            collar.setSticker("btm_txt", "horsie");
            collar.setName("Kheiron's Good Horsie Collar");

            collar.clearEffects();

            if (Main.game.isAnalContentEnabled()) {
                collar.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.TF_MOD_FETISH_BODY_PART, TFModifier.TF_MOD_FETISH_ANAL_RECEIVING, TFPotency.MAJOR_BOOST, 0));
            } else {
                collar.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.TF_MOD_FETISH_BODY_PART, TFModifier.TF_MOD_FETISH_ORAL_RECEIVING, TFPotency.MAJOR_BOOST, 0));
            }
            collar.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.TF_MOD_FETISH_BODY_PART, TFModifier.TF_MOD_FETISH_PENIS_RECEIVING, TFPotency.MAJOR_BOOST, 0));

            collar.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.TF_MOD_FETISH_BEHAVIOUR, TFModifier.TF_MOD_FETISH_SUBMISSIVE, TFPotency.MAJOR_BOOST, 0));
            collar.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.TF_MOD_FETISH_BEHAVIOUR, TFModifier.TF_MOD_FETISH_MASOCHIST, TFPotency.MAJOR_BOOST, 0));
            collar.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.TF_MOD_FETISH_BEHAVIOUR, TFModifier.TF_MOD_FETISH_SIZE_QUEEN, TFPotency.MAJOR_BOOST, 0));

            this.equipClothingFromNowhere(collar, true, this);

        } else {
            if (this.getClothingInSlot(InventorySlot.NECK) != null) {
                this.unequipClothingIntoVoid(this.getClothingInSlot(InventorySlot.NECK), true, this);
            }
        }
    }

    public void applyGenericGolixSex(boolean golixIsDom) {
        SexType st = new SexType(SexParticipantType.NORMAL, SexAreaOrifice.MOUTH, SexAreaPenetration.PENIS);
        if (Main.game.isAnalContentEnabled()) {
            st = new SexType(SexParticipantType.NORMAL, SexAreaOrifice.ANUS, SexAreaPenetration.PENIS);
        }
        if (golixIsDom) {
            this.calculateGenericSexEffects(false, true, Main.game.getNpc(Oglix.class).getElemental(), st, GenericSexFlag.NO_DESCRIPTION_NEEDED, GenericSexFlag.PREVENT_LEVEL_DRAIN);

        } else {
            Main.game.getNpc(Oglix.class).getElemental().calculateGenericSexEffects(false, true, this, st, GenericSexFlag.NO_DESCRIPTION_NEEDED, GenericSexFlag.PREVENT_LEVEL_DRAIN);
        }
    }

}
