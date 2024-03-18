package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.valueEnums.AreolaeSize;
import com.lilithslegacy.game.character.body.valueEnums.AssSize;
import com.lilithslegacy.game.character.body.valueEnums.BodyHair;
import com.lilithslegacy.game.character.body.valueEnums.BodySize;
import com.lilithslegacy.game.character.body.valueEnums.BreastShape;
import com.lilithslegacy.game.character.body.valueEnums.Capacity;
import com.lilithslegacy.game.character.body.valueEnums.CupSize;
import com.lilithslegacy.game.character.body.valueEnums.HairLength;
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
import com.lilithslegacy.game.combat.CombatBehaviour;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.places.dominion.slaverAlley.SlaverAlleyDialogue;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.inventory.weapon.WeaponType;
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
 * @version 0.3.7
 * @since 0.3.6.2
 */
public class Sean extends NPC {

    public Sean() {
        this(false);
    }

    public Sean(boolean isImported) {
        super(isImported, new NameTriplet("Sean"), "Hughes",
                "",
                28, Month.MAY, 3,
                15, Gender.M_P_MALE,
                Subspecies.RABBIT_MORPH, RaceStage.GREATER, new CharacterInventory(10), WorldType.SLAVER_ALLEY, PlaceType.SLAVER_ALLEY_PUBLIC_STOCKS, true);

        if (!isImported) {
            this.setPlayerKnowsName(false);
            this.setGenericName("rabbit-boy Enforcer");
        }
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);

        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.9.7")) {
            this.setPenisCumStorage(300);
            this.setPenisCumExpulsion(75);
            equipClothing(EquipClothingSetting.getAllClothingSettings());
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.4.8.4")) {
            this.setFetishDesire(Fetish.FETISH_VAGINAL_GIVING, FetishDesire.THREE_LIKE);
        }
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_MEGA_SLUT);

        PerkManager.initialisePerks(this,
                Util.newArrayListOfValues(
                        Perk.RANGED_DAMAGE,
                        Perk.FETISH_SEEDER),
                Util.newHashMapOfValues(
                        new Value<>(PerkCategory.PHYSICAL, 3),
                        new Value<>(PerkCategory.LUST, 1),
                        new Value<>(PerkCategory.ARCANE, 0)));
    }

    @Override
    public void resetDefaultMoves() {
        this.clearEquippedMoves();
        equipMove("strike");
        equipMove("offhand-strike");
        equipMove("twin-strike");
        equipMove("block");
        this.equipAllSpellMoves();
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Persona:
        if (setPersona) {
            this.setPersonalityTraits(
                    PersonalityTrait.CONFIDENT,
                    PersonalityTrait.BRAVE);

            this.setSexualOrientation(SexualOrientation.GYNEPHILIC);

            this.setHistory(Occupation.NPC_ENFORCER_PATROL_CONSTABLE);

            this.addFetish(Fetish.FETISH_IMPREGNATION);
            this.setFetishDesire(Fetish.FETISH_VAGINAL_GIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_DOMINANT, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_EXHIBITIONIST, FetishDesire.THREE_LIKE);
        }

        // Body:

        // Core:
        this.setHeight(178);
        this.setFemininity(30);
        this.setMuscle(Muscle.THREE_MUSCULAR.getMedianValue());
        this.setBodySize(BodySize.TWO_AVERAGE.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_RABBIT, PresetColour.EYE_BLUE_LIGHT));
        this.setSkinCovering(new Covering(BodyCoveringType.RABBIT_FUR, PresetColour.COVERING_SANDY), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_LIGHT), true);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_RABBIT_FUR, PresetColour.COVERING_SANDY), true);
        this.setHairLength(HairLength.ZERO_BALD);
        this.setHairStyle(HairStyle.LOOSE);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_RABBIT_FUR, PresetColour.COVERING_SANDY), false);
        this.setUnderarmHair(BodyHair.FOUR_NATURAL);
        this.setAssHair(BodyHair.FOUR_NATURAL);
        this.setPubicHair(BodyHair.FOUR_NATURAL);
        this.setFacialHair(BodyHair.ZERO_NONE);

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
        this.setBreastShape(BreastShape.PERKY);
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
        this.setPenisSize(20);
        this.setTesticleSize(TesticleSize.THREE_LARGE);
        this.setPenisCumStorage(300);
        this.setPenisCumExpulsion(75);
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.unequipAllClothingIntoVoid(true, true);
        this.clearNonEquippedInventory(false);
        this.unequipMainWeaponIntoVoid(0, false);
        this.unequipOffhandWeaponIntoVoid(0, false);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_boxers", PresetColour.CLOTHING_BLACK, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_enfslacks", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_utilbelt", PresetColour.CLOTHING_BLACK, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_ssldshirt", PresetColour.CLOTHING_BLUE, false), true, this);

        AbstractClothing vest = Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_stpvest", PresetColour.CLOTHING_BLACK, false);
        vest.setSticker("name_plate", "sean");
        this.equipClothingFromNowhere(vest, true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("dsg_eep_tacequipset_cboots", PresetColour.CLOTHING_BLACK, false), true, this);

        AbstractClothing beret = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfberet", PresetColour.CLOTHING_BLUE, false);
        beret.setSticker("flash", "flash_patrol_dominion");
        this.equipClothingFromNowhere(beret, true, this);


        if (settings.contains(EquipClothingSetting.ADD_ACCESSORIES)) {
            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_MENS_WATCH, PresetColour.CLOTHING_STEEL, false), true, this);
        }

        if (settings.contains(EquipClothingSetting.ADD_WEAPONS)) {
            this.setEssenceCount(100);
            this.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon(WeaponType.getWeaponTypeFromId("dsg_eep_pbweap_pbpistol")));
            this.equipOffhandWeaponFromNowhere(Main.game.getItemGen().generateWeapon(WeaponType.getWeaponTypeFromId("dsg_eep_pbweap_pbpistol")));
        }
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getDescription() {
        return UtilText.parse(this, "Constable [npc.surname] is a member of the Enforcer's Frontline Patrol division, and has been tasked with watching over the slaves locked into the stocks in Slaver Alley.");
    }

    @Override
    public void endSex() {
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
    public SexType getForeplayPreference(GameCharacter target) {
        if (target.isAbleToAccessCoverableArea(CoverableArea.VAGINA, true) && target.hasVagina()) {
            return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.FINGER, SexAreaOrifice.VAGINA);
        }

        return super.getForeplayPreference(target);
    }

    @Override
    public SexType getMainSexPreference(GameCharacter target) {
        if (target.isAbleToAccessCoverableArea(CoverableArea.VAGINA, true) && target.hasVagina()) {
            return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.VAGINA);
        }

        return super.getMainSexPreference(target);
    }

    // Combat:

    @Override
    public CombatBehaviour getCombatBehaviour() {
        return CombatBehaviour.ATTACK;
    }

    @Override
    public Response endCombat(boolean applyEffects, boolean victory) {
        equipClothing(EquipClothingSetting.getAllClothingSettings());
        this.setEssenceCount(200); // Make sure he doesn't run out of ammo for subsequent fights
        if (victory) {
            return new Response("", "", SlaverAlleyDialogue.PUBLIC_STOCKS_COMPLAIN_CHALLENGE_VICTORY);
        } else {
            return new Response("", "", SlaverAlleyDialogue.PUBLIC_STOCKS_COMPLAIN_CHALLENGE_DEFEAT);
        }
    }

    @Override
    public int getEscapeChance() {
        return 0;
    }

    public int getLootMoney() {
        return 0;
    }

    public List<AbstractCoreItem> getLootItems() {
        return null;
    }

}
