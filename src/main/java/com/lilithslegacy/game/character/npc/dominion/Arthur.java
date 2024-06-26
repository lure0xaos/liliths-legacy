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
import com.lilithslegacy.game.character.persona.SexualOrientation;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.Vector2i;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.GenericPlace;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.2.11
 * @since 0.1.95
 */
public class Arthur extends NPC {

    public Arthur() {
        this(false);
    }

    public Arthur(boolean isImported) {
        super(isImported, new NameTriplet("Arthur"), "Fairbanks",
                "With messy brown hair, pale skin, and a thin frame, the Arthur of this world looks exactly the same as the one you've always known."
                        + " Just as he was in your world, this Arthur used to be a colleague of Lilaya's, before Lilaya kicked him out."
                        + "<br/>"
                        + "Arthur is recognised as one of, if not the best arcane researcher in all of Dominion."
                        + " Despite this, however, he's unable to harness the arcane himself.",
                41, Month.DECEMBER, 9,
                10,
                Gender.M_P_MALE,
                Subspecies.HUMAN, RaceStage.HUMAN, new CharacterInventory(10),
                WorldType.ZARANIX_HOUSE_FIRST_FLOOR, PlaceType.ZARANIX_FF_OFFICE, true);

    }

    @Override
    public String getDescription() {
        return "With messy brown hair, pale skin, and a thin frame, the Arthur of this world looks exactly the same as the one you've always known."
                + " Just as he was in your world, this Arthur used to be a colleague of Lilaya's, before Lilaya kicked him out."
                + "<br/>"
                + "Arthur is recognised as one of, if not the best arcane researcher in all of Dominion."
                + " Despite this, however, he's unable to harness the arcane himself.";
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);

        if (Main.isVersionOlderThan(Game.loadingVersion, "0.2.10.5")) {
            resetBodyAfterVersion_2_10_5();
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.2.12")) {
            equipClothing(EquipClothingSetting.getAllClothingSettings());
        }
        this.setSurname("Fairbanks");
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.3.6")) {
            this.resetPerksMap(true);
        }
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_ARCANE_ALLERGY);

        PerkManager.initialisePerks(this,
                Util.newArrayListOfValues(),
                Util.newHashMapOfValues(
                        new Value<>(PerkCategory.PHYSICAL, 1),
                        new Value<>(PerkCategory.LUST, 0),
                        new Value<>(PerkCategory.ARCANE, 5)));
    }

    @Override
    public void setStartingBody(boolean setPersona) {

        // Persona:

        if (setPersona) {
            this.setSexualOrientation(SexualOrientation.GYNEPHILIC);

            this.setHistory(Occupation.NPC_ARCANE_RESEARCHER);

            this.setFetishDesire(Fetish.FETISH_ORAL_RECEIVING, FetishDesire.THREE_LIKE);
            this.addFetish(Fetish.FETISH_BREASTS_OTHERS);
            this.addFetish(Fetish.FETISH_LACTATION_OTHERS);
        }

        // Body:

        // Core:
        this.setHeight(183);
        this.setFemininity(25);
        this.setMuscle(Muscle.ONE_LIGHTLY_MUSCLED.getMedianValue());
        this.setBodySize(BodySize.ONE_SLENDER.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_HUMAN, PresetColour.EYE_BROWN));
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_PALE), true);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HUMAN, PresetColour.COVERING_BROWN_DARK), false);
        this.setHairLength(HairLength.TWO_SHORT.getMedianValue());
        this.setHairStyle(HairStyle.MESSY);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_BROWN_DARK), false);
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
        this.setBreastShape(BreastShape.WIDE);
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
        this.setPenisGirth(PenetrationGirth.THREE_AVERAGE);
        this.setPenisSize(15);
        this.setTesticleSize(TesticleSize.TWO_AVERAGE);
        // Leave cum as normal value

        // Vagina:
        // No vagina

        // Feet:
        // Foot shape
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.unequipAllClothingIntoVoid(true, true);

        this.setMoney(0);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_boxers", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_socks", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torso_short_sleeved_shirt", PresetColour.CLOTHING_WHITE, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_scientist_lab_coat", PresetColour.CLOTHING_WHITE, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_trousers", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_scientist_safety_goggles", false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_mens_smart_shoes", PresetColour.CLOTHING_BLACK, false), true, this);

    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public void changeFurryLevel() {
    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }

    // Misc.:

    public void generateNewTile() {
        if (Main.game.getWorlds().get(WorldType.DOMINION).getCell(PlaceType.DOMINION_DEMON_HOME_ARTHUR) == null) {
            Vector2i towerLoc = new Vector2i(Main.game.getWorlds().get(WorldType.DOMINION).getCell(PlaceType.DOMINION_LILITHS_TOWER).getLocation());
            towerLoc.setX(towerLoc.getX() - 2);
            towerLoc.setY(towerLoc.getY() - 1);
            if (!Main.game.getWorlds().get(WorldType.DOMINION).getCell(towerLoc).getPlace().getPlaceType().equals(PlaceType.DOMINION_DEMON_HOME)) {
                towerLoc = new Vector2i(Main.game.getWorlds().get(WorldType.DOMINION).getRandomCell(PlaceType.DOMINION_DEMON_HOME).getLocation());
            }
            Main.game.getWorlds().get(WorldType.DOMINION).getCell(towerLoc).setPlace(new GenericPlace(PlaceType.DOMINION_DEMON_HOME_ARTHUR), false);
        }
    }

}
