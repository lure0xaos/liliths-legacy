package com.lilithslegacy.game.character.npc.fields;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.types.HornType;
import com.lilithslegacy.game.character.body.types.WingType;
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
import com.lilithslegacy.game.character.body.valueEnums.HornLength;
import com.lilithslegacy.game.character.body.valueEnums.LabiaSize;
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
import com.lilithslegacy.game.combat.DamageType;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.ItemTag;
import com.lilithslegacy.game.inventory.SetBonus;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.AbstractClothingType;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.inventory.enchanting.ItemEffect;
import com.lilithslegacy.game.inventory.enchanting.ItemEffectType;
import com.lilithslegacy.game.inventory.enchanting.TFModifier;
import com.lilithslegacy.game.inventory.enchanting.TFPotency;
import com.lilithslegacy.game.inventory.item.ItemType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.1
 * @since 0.4.1
 */
public class Yui extends NPC {

    public Yui() {
        this(false);
    }

    public Yui(boolean isImported) {
        super(isImported, new NameTriplet("Yui"), "Lyxiasmartu",
                "Yui owns and operates the stall 'Yui's Toys' in Elis's Farmer's Market."
                        + " Despite her small size and thin frame, she has an extremely dominant personality, and delights in sadistically abusing her customers.",
                25, Month.DECEMBER, 22,
                25,
                Gender.F_V_B_FEMALE, Subspecies.getSubspeciesFromId("charisma_spider_subspecies_spider"), RaceStage.GREATER,
                new CharacterInventory(10),
                WorldType.getWorldTypeFromId("innoxia_fields_elis_market"), PlaceType.getPlaceTypeFromId("innoxia_fields_elis_market_toys"),
                true);

        this.setGenericName("grinning jorogumo");

        if (!isImported) {
            this.setPlayerKnowsName(false);
            this.dailyUpdate();
        }
    }


    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_DIRTY_MINDED);

        PerkManager.initialisePerks(this,
                Util.newArrayListOfValues(Perk.CLOTHING_ENCHANTER),
                Util.newHashMapOfValues(
                        new Value<>(PerkCategory.PHYSICAL, 0),
                        new Value<>(PerkCategory.LUST, 1),
                        new Value<>(PerkCategory.ARCANE, 0)));
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Persona:

        if (setPersona) {
            this.setPersonalityTraits(
                    PersonalityTrait.CONFIDENT,
                    PersonalityTrait.CYNICAL,
                    PersonalityTrait.SELFISH,
                    PersonalityTrait.LEWD);

            this.setSexualOrientation(SexualOrientation.AMBIPHILIC);

            this.setHistory(Occupation.NPC_STORE_OWNER);

            this.addFetish(Fetish.FETISH_DOMINANT);
            this.addFetish(Fetish.FETISH_BONDAGE_APPLIER);
            this.addFetish(Fetish.FETISH_DENIAL);

            this.setFetishDesire(Fetish.FETISH_SADIST, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_EXHIBITIONIST, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_ARMPIT_RECEIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_FOOT_GIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_ORAL_RECEIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_NON_CON_DOM, FetishDesire.THREE_LIKE);

            this.setFetishDesire(Fetish.FETISH_SUBMISSIVE, FetishDesire.ZERO_HATE);
            this.setFetishDesire(Fetish.FETISH_NON_CON_SUB, FetishDesire.ZERO_HATE);
        }

        // Body:
        this.setBody(Main.game.getCharacterUtils().generateHalfDemonBody(this, Gender.F_V_B_FEMALE, Subspecies.getSubspeciesFromId("charisma_spider_subspecies_spider"), true, RaceStage.GREATER), false);
        this.setArmRows(3);
        this.setWingType(WingType.NONE);
        this.setHornType(HornType.CURVED);
        this.setHornLength(HornLength.ONE_SMALL.getMedianValue());

        // Core:
        this.setHeight(150);
        this.setFemininity(80);
        this.setMuscle(Muscle.ONE_LIGHTLY_MUSCLED.getMedianValue());
        this.setBodySize(BodySize.ONE_SLENDER.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_DEMON_COMMON, CoveringPattern.EYE_IRISES, PresetColour.EYE_CRIMSON, true, PresetColour.EYE_CRIMSON, true));
        this.setSkinCovering(new Covering(BodyCoveringType.getBodyCoveringTypeFromId("charisma_spider_chitinSmooth"), CoveringPattern.STRIPED, CoveringModifier.SMOOTH, PresetColour.COVERING_BLACK, false, PresetColour.COVERING_YELLOW, false), true);
        this.setSkinCovering(new Covering(BodyCoveringType.DEMON_COMMON, PresetColour.SKIN_EBONY), true);

        this.setSkinCovering(new Covering(BodyCoveringType.HORN, PresetColour.COVERING_BLACK), false);

        this.setHairCovering(new Covering(BodyCoveringType.getBodyCoveringTypeFromId("charisma_spider_hair"), CoveringPattern.OMBRE, CoveringModifier.SILKEN, PresetColour.COVERING_BLACK, false, PresetColour.COVERING_RED_DARK, false), true);
        this.setHairLength(HairLength.THREE_SHOULDER_LENGTH.getMedianValue());
        this.setHairStyle(HairStyle.BUN);

        this.setHairCovering(new Covering(BodyCoveringType.getBodyCoveringTypeFromId("charisma_spider_body_hair"), PresetColour.COVERING_BLACK), false);
        this.setUnderarmHair(BodyHair.ZERO_NONE);
        this.setAssHair(BodyHair.ZERO_NONE);
        this.setPubicHair(BodyHair.ZERO_NONE);
        this.setFacialHair(BodyHair.ZERO_NONE);

        this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_RED_DARK));
        this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, PresetColour.COVERING_RED_DARK));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
//		this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_CLEAR));
//		this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
//		this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PINK_LIGHT));

        // Face:
        this.setEyePairs(4);
        this.setFaceVirgin(false);
        this.setLipSize(LipSize.TWO_FULL);
        this.setFaceCapacity(Capacity.THREE_SLIGHTLY_LOOSE, true);
        // Throat settings and modifiers
        this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        this.setNippleVirgin(true);
        this.setBreastSize(CupSize.B.getMeasurement());
        this.setBreastShape(BreastShape.PERKY);
        this.setNippleSize(NippleSize.THREE_LARGE);
        this.setAreolaeSize(AreolaeSize.THREE_LARGE);
        // Nipple settings and modifiers

        // Ass:
        this.setAssVirgin(false);
        this.setAssBleached(false);
        this.setAssSize(AssSize.THREE_NORMAL);
        this.setHipSize(HipSize.THREE_GIRLY);
        this.setAssCapacity(Capacity.TWO_TIGHT, true);
        this.setAssWetness(Wetness.THREE_WET);
        this.setAssElasticity(OrificeElasticity.SIX_SUPPLE.getValue());
        this.setAssPlasticity(OrificePlasticity.ONE_SPRINGY.getValue());
        // Anus modifiers

        // Penis:
        this.setPenisVirgin(false);
        this.setPenisGirth(PenetrationGirth.THREE_AVERAGE);
        this.setPenisSize(18);
        this.setTesticleSize(TesticleSize.TWO_AVERAGE);
        this.setPenisCumStorage(150);
        this.setPenisCumExpulsion(75);
        this.fillCumToMaxStorage();
        this.setTesticleCount(2);

        // Vagina:
        this.setVaginaVirgin(false);
        this.setVaginaClitorisSize(ClitorisSize.ZERO_AVERAGE);
        this.setVaginaLabiaSize(LabiaSize.ZERO_TINY);
        this.setVaginaSquirter(true);
        this.setVaginaCapacity(Capacity.ZERO_IMPENETRABLE, true);
        this.setVaginaWetness(Wetness.SEVEN_DROOLING);
        this.setVaginaElasticity(OrificeElasticity.SEVEN_ELASTIC.getValue());
        this.setVaginaPlasticity(OrificePlasticity.ZERO_RUBBERY.getValue());

        // Feet:
        // Foot shape
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.unequipAllClothingIntoVoid(true, true);

        this.setMoney(5000);

        this.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon("innoxia_bdsm_riding_crop", DamageType.FIRE));

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("norin_hair_accessories_hair_sticks", PresetColour.CLOTHING_BLACK_JET, false), true, this);

        AbstractClothing necklace = Main.game.getItemGen().generateClothing("innoxia_neck_demonstone_necklace", PresetColour.CLOTHING_BLACK_JET, PresetColour.CLOTHING_GOLD, null, false);
        for (int i = 0; i < 10; i++) {
            necklace.addEffect(new ItemEffect(ItemEffectType.CLOTHING, TFModifier.CLOTHING_ATTRIBUTE, TFModifier.CRITICAL_DAMAGE, TFPotency.MAJOR_BOOST, 0));
        }
        this.equipClothingFromNowhere(necklace, true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("sage_latex_stockings", PresetColour.CLOTHING_BLACK_JET, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("sage_latex_panties", PresetColour.CLOTHING_BLACK_JET, PresetColour.CLOTHING_GOLD, null, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_goth_boots_fem", PresetColour.CLOTHING_BLACK_JET, PresetColour.CLOTHING_GOLD, PresetColour.CLOTHING_BLACK_JET, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("sage_latex_bra", PresetColour.CLOTHING_BLACK_JET, PresetColour.CLOTHING_GOLD, null, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("sage_latex_croptop", PresetColour.CLOTHING_BLACK_JET, PresetColour.CLOTHING_GOLD, null, false), true, this);

        this.setPiercedEar(true);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_ear_ball_studs", PresetColour.CLOTHING_GOLD, false), true, this);
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getSpeechColour() {
        return "#ff799b";
    }

    @Override
    public void dailyUpdate() {
        clearNonEquippedInventory(false);

        List<AbstractClothingType> clothingTypesToSell = new ArrayList<>();

        for (AbstractClothingType clothing : ClothingType.getAllClothing()) {
            if ((clothing.getClothingSet() == SetBonus.getSetBonusFromId("innoxia_bdsm") || clothing.getClothingSet() == SetBonus.getSetBonusFromId("sage_ltxset") || clothing.getDefaultItemTags().contains(ItemTag.SOLD_BY_FINCH))
                    && (!clothing.getDefaultItemTags().contains(ItemTag.SILLY_MODE) || Main.game.isSillyMode())) {
                clothingTypesToSell.add(clothing);
            }
        }

        // Limit number of clothing types to 80% inventory size:
        while (clothingTypesToSell.size() >= this.getMaximumInventorySpace() * 0.8) {
            clothingTypesToSell.remove(Util.random.nextInt(clothingTypesToSell.size()));
        }

        for (AbstractClothingType type : clothingTypesToSell) {
            this.addClothing(Main.game.getItemGen().generateClothing(type, false), false);
        }

        for (int i = 0; i < 3; i++) {
            this.addWeapon(Main.game.getItemGen().generateWeapon("innoxia_bdsm_riding_crop"), false);
        }

        this.addItem(Main.game.getItemGen().generateItem(ItemType.DYE_BRUSH), 10, false, false);
    }

    @Override
    public void turnUpdate() {
        if (!Main.game.getCharactersPresent().contains(this)) {
            if (Main.game.isHourBetween(8, 19)) {
                this.returnToHome(); // Stall in farmer's market

            } else {
                this.setLocation(WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);
            }
        }
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
        this.cleanAllDirtySlots(true);
        this.cleanAllClothing(false, false);
    }

    @Override
    public boolean isAbleToBeImpregnated() {
        return false;
    }

    @Override
    public String getTraderDescription() {
        return UtilText.parseFromXMLFile("places/fields/elis/market", "TOYS_TRANSACTION_START");
    }

    @Override
    public boolean isTrader() {
        return true;
    }

    @Override
    public boolean willBuy(AbstractCoreItem item) {
        return false;
    }

}
