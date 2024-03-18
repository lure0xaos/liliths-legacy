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
import com.lilithslegacy.game.character.body.valueEnums.ClitorisSize;
import com.lilithslegacy.game.character.body.valueEnums.CupSize;
import com.lilithslegacy.game.character.body.valueEnums.FluidFlavour;
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
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.places.dominion.cityHall.CityHallDemographics;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.inventory.item.AbstractItem;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexPace;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.game.sex.managers.dominion.vanessa.SMVanessaOral;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlotSitting;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.3.4
 * @since 0.3.2
 */
public class Vanessa extends NPC {

    public Vanessa() {
        this(false);
    }

    public Vanessa(boolean isImported) {
        super(isImported, new NameTriplet("Vanessa"), "Cunningham",
                "Ms. Cunningham, as she prefers to be called, is the sole person responsible for all of the affairs of Dominion's 'Bureau of Demographics'."
                        + " Although getting on years, she's aged remarkably well, and has a refined, elegant beauty about her.",
                55, Month.SEPTEMBER, 26,
                10, Gender.F_V_B_FEMALE, Subspecies.FOX_MORPH, RaceStage.PARTIAL,
                new CharacterInventory(10), WorldType.CITY_HALL, PlaceType.CITY_HALL_ARCHIVES, true);

        if (!isImported) {
            this.setPlayerKnowsName(false);

            this.setAttribute(Attribute.MAJOR_CORRUPTION, 15);
        }
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.2.6")) {
            this.setPlayerKnowsName(false);
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.5.1")) {
            this.setPersonalityTraits(
                    PersonalityTrait.KIND);
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.6")) {
            this.resetPerksMap(true);
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.4.1.1")) {
            this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_GREY), false);
            this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_FOX_FUR, PresetColour.COVERING_GREY), false);
        }
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.setAttribute(Attribute.MAJOR_CORRUPTION, 15);

        PerkManager.initialisePerks(this,
                Util.newArrayListOfValues(),
                Util.newHashMapOfValues(
                        new Value<>(PerkCategory.PHYSICAL, 1),
                        new Value<>(PerkCategory.LUST, 5),
                        new Value<>(PerkCategory.ARCANE, 0)));
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Persona:

        if (setPersona) {
            this.setPersonalityTraits(
                    PersonalityTrait.KIND);

            this.setSexualOrientation(SexualOrientation.AMBIPHILIC);

            this.setHistory(Occupation.NPC_OFFICE_WORKER);

            this.addFetish(Fetish.FETISH_FOOT_GIVING);
            this.addFetish(Fetish.FETISH_ORAL_RECEIVING);

            this.setFetishDesire(Fetish.FETISH_SUBMISSIVE, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_VAGINAL_RECEIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_ANAL_RECEIVING, FetishDesire.ONE_DISLIKE);
            this.setFetishDesire(Fetish.FETISH_MASOCHIST, FetishDesire.ZERO_HATE);
        }


        // Body:

        // Core:
        this.setHeight(177);
        this.setFemininity(90);
        this.setMuscle(Muscle.TWO_TONED.getMedianValue());
        this.setBodySize(BodySize.ONE_SLENDER.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_HUMAN, PresetColour.EYE_BLUE));
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_FOX_MORPH, PresetColour.EYE_BLUE));
        this.setSkinCovering(new Covering(BodyCoveringType.FOX_FUR, PresetColour.COVERING_GREY), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_LIGHT), true);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HUMAN, PresetColour.COVERING_GREY), true);
        this.setHairCovering(new Covering(BodyCoveringType.HAIR_FOX_FUR, PresetColour.COVERING_GREY), true);
        this.setHairLength(HairLength.FOUR_MID_BACK.getMedianValue());
        this.setHairStyle(HairStyle.BRAIDED);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_GREY), false);
        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_FOX_FUR, PresetColour.COVERING_GREY), false);
        this.setUnderarmHair(BodyHair.ZERO_NONE);
        this.setAssHair(BodyHair.ZERO_NONE);
        this.setPubicHair(BodyHair.FOUR_NATURAL);
        this.setFacialHair(BodyHair.ZERO_NONE);

        this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_CLEAR));
        this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, PresetColour.COVERING_CLEAR));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
        this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_RED_LIGHT));
        this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
//		this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PINK));

        // Face:
        this.setFaceVirgin(false);
        this.setLipSize(LipSize.TWO_FULL);
        this.setFaceCapacity(Capacity.THREE_SLIGHTLY_LOOSE, true);
        // Throat settings and modifiers
        this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        this.setNippleVirgin(true);
        this.setBreastSize(CupSize.E.getMeasurement());
        this.setBreastShape(BreastShape.ROUND);
        this.setNippleSize(NippleSize.THREE_LARGE.getValue());
        this.setAreolaeSize(AreolaeSize.THREE_LARGE.getValue());
//		this.addNippleOrificeModifier(OrificeModifier.PUFFY);
//		this.setBreastLactationRegeneration(FluidRegeneration.TWO_FAST.getMedianRegenerationValuePerDay());
//		this.setBreastMilkStorage(500);
//		this.setBreastStoredMilk(500);

        // Ass:
        this.setAssVirgin(false);
        this.setAssBleached(false);
        this.setAssSize(AssSize.THREE_NORMAL.getValue());
        this.setHipSize(HipSize.FOUR_WOMANLY.getValue());
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
        this.setVaginaLabiaSize(LabiaSize.THREE_LARGE);
        this.setVaginaSquirter(true);
        this.setVaginaCapacity(Capacity.FOUR_LOOSE, true);
        this.setVaginaWetness(Wetness.THREE_WET);
        this.setVaginaElasticity(OrificeElasticity.THREE_FLEXIBLE.getValue());
        this.setVaginaPlasticity(OrificePlasticity.FIVE_YIELDING.getValue());
        this.setGirlcumFlavour(FluidFlavour.HONEY);

        // Feet:
//		this.setFootStructure(FootStructure.PLANTIGRADE);
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {

        this.unequipAllClothingIntoVoid(true, true);

        inventory.setMoney(10 + Util.random.nextInt(getLevel() * 10) + 1);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_lacy_panties", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_lacy_plunge_bra", PresetColour.CLOTHING_BLACK, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_pencil_skirt", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_feminine_short_sleeve_shirt", PresetColour.CLOTHING_PINK_LIGHT, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torsoOver_open_front_cardigan", PresetColour.CLOTHING_GREY, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_pantyhose", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_flats", PresetColour.CLOTHING_BLACK, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_eye_half_moon_glasses", PresetColour.CLOTHING_STEEL, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.WRIST_WOMENS_WATCH, PresetColour.CLOTHING_BLACK, false), true, this);

        this.setPiercedEar(true);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_ear_pearl_studs", PresetColour.CLOTHING_WHITE, PresetColour.CLOTHING_GOLD, null, false), true, this);

    }

    @Override
    public String getArtworkFolderName() {
        if (this.isVisiblyPregnant()) {
            return "VanessaPregnant";
        }
        return "Vanessa";
    }

    @Override
    public String getSpeechColour() {
        return "#E7CAE6";
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getGenericName() {
        return "Ms. Cunningham";
    }

    @Override
    public void dailyUpdate() {
        Main.game.getDialogueFlags().setFlag(DialogueFlagValue.vanessaDailyHelped, false);
        Main.game.getDialogueFlags().setFlag(DialogueFlagValue.vanessaDailyMassage, false);
    }

    // Sex:

    @Override
    public SexType getForeplayPreference(GameCharacter target) {
        if (Main.sex.getSexPositionSlot(Main.game.getNpc(Vanessa.class)) == SexSlotSitting.SITTING) {
            return new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.TONGUE);
        }
        if (Main.sex.getSexPositionSlot(Main.game.getNpc(Vanessa.class)) == SexSlotSitting.PERFORMING_ORAL) {
            return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.TONGUE, SexAreaOrifice.VAGINA);
        }
        if (Main.sex.getPosition() == SexPosition.OVER_DESK) {
            return new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.PENIS);
        }

        return super.getForeplayPreference(target);
    }

    @Override
    public SexType getMainSexPreference(GameCharacter target) {
        return getForeplayPreference(target);
    }

    @Override
    public SexPace getSexPaceSubPreference(GameCharacter character) {
        return SexPace.SUB_EAGER;
    }

    @Override
    public SexPace getSexPaceDomPreference() {
        return SexPace.DOM_GENTLE;
    }

    @Override
    public void endSex() {
        if (!(Main.sex.getSexManager() instanceof SMVanessaOral) || !Main.sex.isDom(Main.game.getNpc(Vanessa.class))) {
            Main.game.getNpc(Vanessa.class).cleanAllDirtySlots(true);
            Main.game.getNpc(Vanessa.class).equipClothing(Util.newArrayListOfValues(EquipClothingSetting.REPLACE_CLOTHING, EquipClothingSetting.ADD_ACCESSORIES));
        }
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
        return CityHallDemographics.CITY_HALL_DEMOGRAPHICS_ENTRANCE;
    }

    @Override
    public Value<Boolean, String> getItemUseEffects(AbstractItem item, GameCharacter itemOwner, GameCharacter user, GameCharacter target) {
        if (user.isPlayer() && !target.isPlayer()) {
            if (item.isTypeOneOf("innoxia_pills_fertility", "innoxia_pills_broodmother")) {
                String useDesc = itemOwner.useItem(item, target, false, true);
                return new Value<>(true,
                        "<p>"
                                + "Producing a " + item.getName(false, false) + " from your inventory, you pop it out of its plastic wrapper before offering it to [vanessa.name]."
                                + " Flashing you a knowing look, the mature fox-girl takes the little " + item.getColour(0).getName() + " pill, before teasing, [vanessa.speechNoEffects(~Mmm!~ You really want to knock me up that badly, huh?)]"
                                + "</p>"
                                + "<p>"
                                + "Before you can reply, [vanessa.name] pops the pill into her mouth and swallows it down."
                                + " Playfully biting her lip, she breathlessly moans, [vanessa.speechNoEffects(Come on then... Make me a mommy...)]"
                                + "</p>"
                                + useDesc);
            }
        }
        return super.getItemUseEffects(item, itemOwner, user, target);
    }
}