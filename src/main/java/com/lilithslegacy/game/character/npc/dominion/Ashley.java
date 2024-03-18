package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.List;
import java.util.Map.Entry;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.AffectionLevel;
import com.lilithslegacy.game.character.attributes.ObedienceLevel;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.valueEnums.BodySize;
import com.lilithslegacy.game.character.body.valueEnums.Muscle;
import com.lilithslegacy.game.character.effects.Perk;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.NameTriplet;
import com.lilithslegacy.game.character.persona.PersonalityTrait;
import com.lilithslegacy.game.character.persona.SexualOrientation;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.item.AbstractItemType;
import com.lilithslegacy.game.inventory.item.ItemType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Kumiko, Innoxia
 * @version 0.2.11
 * @since 0.1.99
 */
public class Ashley extends NPC {

    private final AbstractItemType[] itemsForSale = new AbstractItemType[]{
            ItemType.GIFT_CHOCOLATES,
            ItemType.GIFT_PERFUME,
            ItemType.GIFT_ROSE_BOUQUET,
            ItemType.GIFT_TEDDY_BEAR};

    public Ashley() {
        this(false);
    }

    public Ashley(boolean isImported) {
        super(isImported, new NameTriplet("Ashley"), "Minett", //TODO
                "Ashley is the owner of the shop 'Dream Lover', and is seemingly also its only working staff."
                        + " They are very stand-offish and loathe helping out their customers, to the point where they'd rather stare at the walls instead of offering any help.",
                200, Month.AUGUST, 14,//TODO
                10,
                Gender.N_P_TRAP,
                Subspecies.ANGEL,
                RaceStage.GREATER,
                new CharacterInventory(10),
                WorldType.SHOPPING_ARCADE,
                PlaceType.SHOPPING_ARCADE_ASHLEYS_SHOP,
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
                    PersonalityTrait.PRUDE);
        }
    }

    @Override
    public void setStartingBody(boolean setPersona) {

        this.setRaceConcealed(true);

        // Persona:

        if (setPersona) {
            this.setPersonalityTraits(
                    PersonalityTrait.PRUDE);

            this.setSexualOrientation(SexualOrientation.AMBIPHILIC);
        }
//		this.setHistory(Occupation.NPC_PROSTITUTE);

//		this.addFetish(Fetish.FETISH_MASOCHIST);


        // Body:

        // Core:
        this.setAgeAppearanceDifferenceToAppearAsAge(25);
        this.setHeight(186);
        this.setFemininity(50);
        this.setMuscle(Muscle.TWO_TONED.getMedianValue());
        this.setBodySize(BodySize.ONE_SLENDER.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_ANGEL, PresetColour.EYE_BLUE));
        this.setSkinCovering(new Covering(BodyCoveringType.ANGEL, PresetColour.SKIN_LIGHT), true);

//		this.setHairCovering(new Covering(BodyCoveringType.HAIR_HUMAN, PresetColour.COVERING_BLONDE), false);
//		this.setHairLength(HairLength.FOUR_MID_BACK.getMedianValue());
//		this.setHairStyle(HairStyle.WAVY);

//		this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_BLACK), false);
//		this.setUnderarmHair(BodyHair.ZERO_NONE);
//		this.setAssHair(BodyHair.ZERO_NONE);
//		this.setPubicHair(BodyHair.ZERO_NONE);
//		this.setFacialHair(BodyHair.ZERO_NONE);

//		this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_RED));
//		this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, PresetColour.COVERING_RED));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
//		this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_RED));
//		this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
//		this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PURPLE));

        // Face:
//		this.setFaceVirgin(false);
//		this.setLipSize(LipSize.THREE_PLUMP);
//		this.setFaceCapacity(Capacity.SEVEN_GAPING, true);
        // Throat settings and modifiers
//		this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
//		this.setNippleVirgin(true);
//		this.setBreastSize(CupSize.DD.getMeasurement());
//		this.setBreastShape(BreastShape.ROUND);
//		this.setNippleSize(NippleSize.TWO_BIG);
//		this.setAreolaeSize(AreolaeSize.TWO_BIG);
        // Nipple settings and modifiers

        // Ass:
//		this.setAssVirgin(false);
//		this.setAssBleached(true);
//		this.setAssSize(AssSize.FOUR_LARGE);
//		this.setHipSize(HipSize.FOUR_WOMANLY);
//		this.setAssCapacity(Capacity.TWO_TIGHT, true);
//		this.setAssWetness(Wetness.ZERO_DRY);
//		this.setAssElasticity(OrificeElasticity.SIX_SUPPLE.getValue());
//		this.setAssPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());
        // Anus modifiers

        // Penis:
        // No penis

        // Vagina:
//		this.setVaginaVirgin(false);
//		this.setVaginaClitorisSize(ClitorisSize.ZERO_AVERAGE);
//		this.setVaginaLabiaSize(LabiaSize.THREE_LARGE);
//		this.setVaginaSquirter(false);
//		this.setVaginaCapacity(Capacity.SEVEN_GAPING, true);
//		this.setVaginaWetness(Wetness.FIVE_SLOPPY);
//		this.setVaginaElasticity(OrificeElasticity.SEVEN_ELASTIC.getValue());
//		this.setVaginaPlasticity(OrificePlasticity.SEVEN_MOULDABLE.getValue());

        // Feet:
        // Foot shape
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {

        this.unequipAllClothingIntoVoid(true, true);

        // No weapons

        // No tattoos or scars

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_torsoOver_hooded_cloak", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_SILVER, null, false), true, this);

    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getCharacterInformationScreen(boolean includePerkTree) {
        infoScreenSB.setLength(0);

        infoScreenSB.append("<h4>Background</h4>" + "<p>").append(this.getDescription()).append("</p>").append("<br/>").append("<h4>Relationships</h4>").append("<p>").append("[style.boldAffection(Affection:)]<br/>").append(AffectionLevel.getDescription(this, Main.game.getPlayer(), true));

        for (Entry<String, Float> entry : this.getAffectionMap().entrySet()) {
            try {
                GameCharacter target = Main.game.getNPCById(entry.getKey());
                if (!target.isPlayer()) {
                    infoScreenSB.append("<br/>").append(AffectionLevel.getDescription(this, target, true));
                }
            } catch (Exception e) {
                Util.logGetNpcByIdError("Ashley.getCharacterInformationScreen()", entry.getKey());
            }
        }

        infoScreenSB.append("<br/><br/>" + "[style.boldObedience(Obedience:)]<br/>").append(UtilText.parse(this, "[npc.Name] [style.boldGood(is not a slave)].")).append("<br/>").append(ObedienceLevel.getDescription(this, ObedienceLevel.getObedienceLevelFromValue(this.getObedienceValue()), true, true)).append("</p>").append("<br/>").append("<h4>Appearance</h4>").append("<p>").append(Main.game.getPlayer().hasTraitActivated(Perk.OBSERVANT)
                ? "Despite the fact that you're highly observant, there's no giveaway whatsoever which would hint as to what Ashley's gender is."
                : "You have no idea what Ashley's gender is.").append(" Standing at full height, they measure 6'1\" (186cm).").append("</p>").append("<p>").append("The hood of their cloak is pulled up, completely obscuring their facial features.").append(" All that you can make out from the darkness of the hood is the sheen of a pair of deep blue eyes.").append("</p>").append("<p>").append("Ashley's cloak covers the entirety of their body, leaving you completely unable to see any part of their body.").append(" You have no idea what race they are, much less the state of their breasts and genitals.").append("</p>");

        return infoScreenSB.toString();
    }

    @Override
    public void dailyUpdate() {
        clearNonEquippedInventory(false);

        for (AbstractItemType item : itemsForSale) {
            for (int i = 0; i < 3 + (Util.random.nextInt(6)); i++) {
                this.addItem(Main.game.getItemGen().generateItem(item), false);
            }
        }

        this.addWeapon(Main.game.getItemGen().generateWeapon("innoxia_cleaning_feather_duster"), false);
    }

    @Override
    public void turnUpdate() {
        if (!Main.game.getCharactersPresent().contains(this)) {
            if (Main.game.isExtendedWorkTime()) {
                this.returnToHome();
            } else {
                this.setLocation(WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);
            }
        }
    }

    @Override
    public boolean willBuy(AbstractCoreItem item) {
        return false;
    }

    @Override
    public void changeFurryLevel() {
    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }

    @Override
    public boolean isTrader() {
        return true;
    }

}
