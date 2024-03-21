package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.PropertyValue;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.valueEnums.AreolaeSize;
import com.lilithslegacy.game.character.body.valueEnums.AssSize;
import com.lilithslegacy.game.character.body.valueEnums.BodyHair;
import com.lilithslegacy.game.character.body.valueEnums.BreastShape;
import com.lilithslegacy.game.character.body.valueEnums.Capacity;
import com.lilithslegacy.game.character.body.valueEnums.CoveringModifier;
import com.lilithslegacy.game.character.body.valueEnums.CoveringPattern;
import com.lilithslegacy.game.character.body.valueEnums.CupSize;
import com.lilithslegacy.game.character.body.valueEnums.FootStructure;
import com.lilithslegacy.game.character.body.valueEnums.HairLength;
import com.lilithslegacy.game.character.body.valueEnums.HairStyle;
import com.lilithslegacy.game.character.body.valueEnums.HipSize;
import com.lilithslegacy.game.character.body.valueEnums.LipSize;
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
import com.lilithslegacy.game.character.quests.Quest;
import com.lilithslegacy.game.character.quests.QuestLine;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.sex.SexPace;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author DSG (character creator), Innoxia
 * @version 0.3.9.4
 * @since 0.3.9.4
 */
public class Wes extends NPC {

    public Wes() {
        this(false);
    }

    public Wes(boolean isImported) {
        super(isImported, new NameTriplet("Wesley"), "Wormwood",
                "",
                38, Month.MARCH, 28,
                20,
                Gender.M_P_MALE, Subspecies.FOX_MORPH, RaceStage.GREATER,
                new CharacterInventory(10),
                WorldType.ENFORCER_HQ, PlaceType.ENFORCER_HQ_REQUISITIONS,
                true);

        if (!isImported) {
            this.setPlayerKnowsName(false);
            this.setGenericName("mysterious Enforcer");
        }
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.9.7")) {
            equipClothing(EquipClothingSetting.getAllClothingSettings());
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.9.8")) {
            this.setHistory(Occupation.NPC_ENFORCER_SWORD_CHIEF_INSPECTOR);
        }
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_MARTIAL_BACKGROUND);

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
            this.setPersonalityTraits(PersonalityTrait.CYNICAL);

            this.setSexualOrientation(SexualOrientation.GYNEPHILIC);

            this.setHistory(Occupation.NPC_ENFORCER_SWORD_CHIEF_INSPECTOR);

            this.setFetishDesire(Fetish.FETISH_SADIST, FetishDesire.ONE_DISLIKE);
        }

        // Body:
        this.setFootStructure(FootStructure.DIGITIGRADE);

        // Core:
        this.setHeight(173);
        this.setFemininity(20);
        this.setMuscle(70);
        this.setBodySize(30);

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_FOX_MORPH, PresetColour.EYE_GREEN));
        this.setSkinCovering(new Covering(BodyCoveringType.FOX_FUR, CoveringPattern.MARKED, CoveringModifier.FLUFFY, PresetColour.COVERING_ORANGE, false, PresetColour.COVERING_WHITE, false), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_LIGHT), true);
        this.setSkinCovering(new Covering(BodyCoveringType.PENIS, PresetColour.SKIN_RED), false);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_FOX_FUR, PresetColour.COVERING_BROWN), true);
        this.setHairLength(HairLength.ZERO_BALD);
        this.setHairStyle(HairStyle.SLICKED_BACK);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_FOX_FUR, PresetColour.COVERING_WHITE), false);
        this.setUnderarmHair(BodyHair.FOUR_NATURAL);
        this.setAssHair(BodyHair.TWO_MANICURED);
        this.setPubicHair(BodyHair.FOUR_NATURAL);
        this.setFacialHair(BodyHair.ZERO_NONE);

        // Face:
        this.setFaceVirgin(true);
        this.setLipSize(LipSize.ONE_AVERAGE);
        this.setFaceCapacity(Capacity.ZERO_IMPENETRABLE, true);
        this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());

        // Chest:
        this.setNippleVirgin(true);
        this.setBreastSize(CupSize.FLAT.getMeasurement());
        this.setBreastShape(BreastShape.POINTY);
        this.setNippleSize(NippleSize.ZERO_TINY);
        this.setAreolaeSize(AreolaeSize.ZERO_TINY);

        // Ass:
        this.setAssVirgin(true);
        this.setAssBleached(false);
        this.setAssSize(AssSize.TWO_SMALL);
        this.setHipSize(HipSize.TWO_NARROW);
        this.setAssCapacity(Capacity.ZERO_IMPENETRABLE, true);
        this.setAssWetness(Wetness.ZERO_DRY);
        this.setAssElasticity(OrificeElasticity.ONE_RIGID.getValue());
        this.setAssPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());

        // Penis:
        this.setPenisVirgin(false);
        this.setPenisGirth(PenetrationGirth.THREE_AVERAGE);
        this.setPenisSize(17);
        this.setTesticleSize(TesticleSize.TWO_AVERAGE);
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.unequipAllClothingIntoVoid(true, true);

        if (isSlave()) {
            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_bdsm_metal_collar", PresetColour.CLOTHING_BRASS, false), true, this);

            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_boxers", PresetColour.CLOTHING_BLACK, false), true, this);

        } else {
            this.setEssenceCount(100);
            this.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon("dsg_eep_pbweap_pbpistol"));

            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_boxers", PresetColour.CLOTHING_BLACK, false), true, this);
            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_BLACK, false), true, this);

            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_lsldshirt", PresetColour.CLOTHING_GREY, PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_GOLD, false), true, this);
            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdslacks", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_GOLD, PresetColour.CLOTHING_GREY, false), true, this);

            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_neck_tie", PresetColour.CLOTHING_BLACK, false), true, this);

            AbstractClothing jacket = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_GREY, PresetColour.CLOTHING_GOLD, false);
            if (Main.game.getPlayer().hasQuestInLine(QuestLine.SIDE_WES, Quest.WES_3_WES)) {
                jacket.setSticker("collar", "tab_su");
                jacket.setSticker("name", "name_wesley");
                jacket.setSticker("ribbon", "ribbon_wes");
            } else {
                jacket.setSticker("collar", "tab_ci");
                jacket.setSticker("name", "name_wesley");
                jacket.setSticker("ribbon", "ribbon_wes");
            }
            this.equipClothingFromNowhere(jacket, true, this);


            AbstractClothing beret = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfberet", PresetColour.CLOTHING_GREY, PresetColour.CLOTHING_BLACK, null, false);
            beret.setSticker("flash", "flash_sword");
            this.equipClothingFromNowhere(beret, true, this);

            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdbelt", PresetColour.CLOTHING_DESATURATED_BROWN, PresetColour.CLOTHING_DESATURATED_BROWN, PresetColour.CLOTHING_GOLD, false), true, this);

            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_mens_smart_shoes", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_BLACK, false), true, this);

            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_MENS_WATCH, PresetColour.CLOTHING_SILVER, false), true, this);
        }
    }

    public void applyDisguise() {
        this.unequipAllClothingIntoVoid(true, true);

        this.setEssenceCount(100);
        this.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon("dsg_eep_pbweap_pbpistol"));

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_boxers", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_GREY_LIGHT, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torsoOver_hoodie", PresetColour.CLOTHING_GREY, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_tshirt", PresetColour.CLOTHING_GREEN_DARK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_jeans", PresetColour.CLOTHING_BLUE_GREY, PresetColour.CLOTHING_BRASS, null, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_trainers", PresetColour.CLOTHING_DESATURATED_BROWN, PresetColour.CLOTHING_GREY, PresetColour.CLOTHING_GREY_DARK, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_eye_glasses", PresetColour.CLOTHING_BLACK_STEEL, PresetColour.CLOTHING_BLACK_STEEL, PresetColour.CLOTHING_BLACK_JET, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_GREY_LIGHT, false), true, this);
    }

    @Override
    public String getArtworkFolderName() {
        return this.getNameIgnoresPlayerKnowledge();
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getDescription() {
        if (Main.game.getPlayer().isQuestProgressLessThan(QuestLine.SIDE_WES, Quest.WES_1)) {
            return "This mysterious fox-boy approached you regarding some matter which couldn't be discussed in public.";

        } else if (this.isSlave()) {
            return "Wes was once the Junior Quartermaster for all of the Enforcers in Central Dominion. Thanks to you, he is now a plaything of his former commanding officer, Elle.";

        } else if (Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE_WES)) {
            return "With your help, Wes is now the Senior Quartermaster for all of the Enforcers in Central Dominion. Although he seems to enjoy his promotion, owning Elle has nonetheless left him a bit frazzled.";

        } else {
            return "Wes is the Junior Quartermaster for all of the Enforcers in Central Dominion. He lacks the usual shiftiness foxes are stereotyped for and instead projects a sort of folksy naiveté.";
        }
    }

    @Override
    public boolean isAbleToBeImpregnated() {
        return true;
    }

    @Override
    public String getSpeechColour() {
        if (Main.getProperties().hasValue(PropertyValue.lightTheme)) {
            return "#ef9424";
        }
        return "#ffbb7f";
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
        if (!Main.game.getCharactersPresent().contains(this)) {
            if (Main.game.isWorkTime()) {
                this.setLocation(WorldType.ENFORCER_HQ, PlaceType.ENFORCER_HQ_REQUISITIONS, true);

            } else {
                this.setLocation(WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);
            }
        }
    }

    @Override
    public void dailyUpdate() {
        if (Main.game.getPlayer().hasQuestInLine(QuestLine.SIDE_WES, Quest.WES_3_WES)) {
            clearNonEquippedInventory(false);

            // Weapons:
            this.addWeapon(Main.game.getItemGen().generateWeapon("dsg_eep_enbaton_enbaton"), 5, false, false);
            this.addWeapon(Main.game.getItemGen().generateWeapon("dsg_eep_pbweap_pbpistol"), 5, false, false);
            this.addWeapon(Main.game.getItemGen().generateWeapon("dsg_eep_pbweap_pbrifle"), 5, false, false);
            this.addWeapon(Main.game.getItemGen().generateWeapon("dsg_eep_taser_taser"), 5, false, false);

            // Clothing:
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_bglasses", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_servequipset_milsweatervest_crew", PresetColour.CLOTHING_GREY, false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_servequipset_milsweater_crew", PresetColour.CLOTHING_GREY, false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_servequipset_milsweater_vee", PresetColour.CLOTHING_GREY, false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_cbtshirt", PresetColour.CLOTHING_GREY, PresetColour.CLOTHING_BLACK, null, false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_sslcbtshirt", PresetColour.CLOTHING_GREY, PresetColour.CLOTHING_BLACK, null, false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_utilbelt", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_battlebelt", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_bgoggles", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_cboots", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_chelmet", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_gmask", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_nvgoggles", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_telbowpads", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_tkneepads", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_hndcuffs_hndcuffs", false), 5, false, false);

            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_stpvest", false), 5, false, false);
            this.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_pltcarrier", false), 5, false, false);
        }
    }

    @Override
    public String getTraderDescription() {
        return UtilText.parseFromXMLFile("characters/dominion/wes", "REQUISITIONS_TRADE_DIALOGUE");
    }

    @Override
    public boolean isTrader() {
        return true;
    }

    @Override
    public boolean willBuy(AbstractCoreItem item) {
        return false;
    }

    @Override
    public float getSellModifier(AbstractCoreItem item) {
        return 1.5f;
    }

    @Override
    public SexPace getSexPaceSubPreference(GameCharacter character) {
        return SexPace.SUB_NORMAL;
    }

    @Override
    public SexPace getSexPaceDomPreference() {
        return SexPace.DOM_NORMAL;
    }
}
