package com.lilithslegacy.game.character.npc.fields;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.lilithslegacy.rendering.Pattern;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.types.HornType;
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
import com.lilithslegacy.game.character.body.valueEnums.Femininity;
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
import com.lilithslegacy.game.inventory.ItemTag;
import com.lilithslegacy.game.inventory.Rarity;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.AbstractClothingType;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.2
 * @since 0.4.2
 */
public class Monica extends NPC {

    private final List<AbstractClothing> commonFemaleClothing;
    private final List<AbstractClothing> commonFemaleUnderwear;
    private final List<AbstractClothing> commonFemaleAccessories;
    private final List<AbstractClothing> commonMaleClothing;
    private final List<AbstractClothing> commonMaleLingerie;
    private final List<AbstractClothing> commonMaleAccessories;
    private final List<AbstractClothing> commonAndrogynousClothing;
    private final List<AbstractClothing> commonAndrogynousLingerie;
    private final List<AbstractClothing> commonAndrogynousAccessories;

    public Monica() {
        this(false);
    }

    public Monica(boolean isImported) {
        super(isImported, new NameTriplet("Monica"), "Webster",
                "Monica is the namesake and owner of the shop, 'The Fashionable Moo', which is located in Elis's shopping precinct, 'Wall's End'.",
                27, Month.FEBRUARY, 28,
                15,
                Gender.F_V_B_FEMALE, Subspecies.COW_MORPH, RaceStage.PARTIAL,
                new CharacterInventory(10),
                WorldType.getWorldTypeFromId("innoxia_fields_elis_shops"), PlaceType.getPlaceTypeFromId("innoxia_fields_elis_shops_clothing"),
                true);

        this.setGenericName("busty cow-girl");

        commonFemaleClothing = new ArrayList<>();
        commonFemaleUnderwear = new ArrayList<>();
        commonFemaleAccessories = new ArrayList<>();
        commonMaleClothing = new ArrayList<>();
        commonMaleLingerie = new ArrayList<>();
        commonMaleAccessories = new ArrayList<>();
        commonAndrogynousClothing = new ArrayList<>();
        commonAndrogynousLingerie = new ArrayList<>();
        commonAndrogynousAccessories = new ArrayList<>();

        if (!isImported) {
            this.setPlayerKnowsName(false);
            dailyUpdate();
        }
    }

    private Map<String, List<AbstractClothing>> getAllClothingListsMap() {
        return Util.newHashMapOfValues(
                new Value<>("commonFemaleClothing", commonFemaleClothing),
                new Value<>("commonFemaleUnderwear", commonFemaleUnderwear),
                new Value<>("commonFemaleAccessories", commonFemaleAccessories),
                new Value<>("commonMaleClothing", commonMaleClothing),
                new Value<>("commonMaleLingerie", commonMaleLingerie),
                new Value<>("commonMaleAccessories", commonMaleAccessories),
                new Value<>("commonAndrogynousClothing", commonAndrogynousClothing),
                new Value<>("commonAndrogynousLingerie", commonAndrogynousLingerie),
                new Value<>("commonAndrogynousAccessories", commonAndrogynousAccessories));
    }

    public void switchToFemaleClothing() {
        switchClothingInventory(commonFemaleClothing);
    }

    public void switchToFemaleUnderwear() {
        switchClothingInventory(commonFemaleUnderwear);
    }

    public void switchToFemaleAccessories() {
        switchClothingInventory(commonFemaleAccessories);
    }

    public void switchToMaleClothing() {
        switchClothingInventory(commonMaleClothing);
    }

    public void switchToAndrogynousClothing() {
        switchClothingInventory(commonAndrogynousClothing);
    }

    public void switchToMaleLingerie() {
        switchClothingInventory(commonMaleLingerie);
    }

    public void switchToMaleAccessories() {
        switchClothingInventory(commonMaleAccessories);
    }

    public void switchToAndrogynousLingerie() {
        switchClothingInventory(commonAndrogynousLingerie);
    }

    public void switchToAndrogynousAccessories() {
        switchClothingInventory(commonAndrogynousAccessories);
    }

    private void switchClothingInventory(List<AbstractClothing> clothingToSwitchTo) {
        Collections.shuffle(clothingToSwitchTo);
        this.clearNonEquippedInventory(false);

        for (AbstractClothing c : clothingToSwitchTo) {
            if (this.isInventoryFull()) {
                break;
            }
            this.addClothing(c, false);
        }
    }

    @Override
    public Element saveAsXML(Element parentElement, Document doc) {
        Element properties = super.saveAsXML(parentElement, doc);

        for (Entry<String, List<AbstractClothing>> entry : getAllClothingListsMap().entrySet()) {
            Element clothingElement = doc.createElement(entry.getKey());
            properties.appendChild(clothingElement);
            for (AbstractClothing c : entry.getValue()) {
                try {
                    c.saveAsXML(clothingElement, doc);
                } catch (Exception ex) {
                }
            }
        }
        return properties;
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
        for (Entry<String, List<AbstractClothing>> entry : this.getAllClothingListsMap().entrySet()) {
            Element npcSpecificElement = (Element) parentElement.getElementsByTagName(entry.getKey()).item(0);
            if (npcSpecificElement != null) {
                entry.getValue().clear();

                NodeList nodeList = npcSpecificElement.getElementsByTagName("clothing");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Element e = (Element) nodeList.item(i);
                    try {
                        AbstractClothing c = AbstractClothing.loadFromXML(e, doc);
                        if (c != null) {
                            entry.getValue().add(c);
                        } else {
                            System.getLogger("").log(System.Logger.Level.ERROR, "Warning: loaded clothing is null in Monica's loadFromXML() method!");
                        }
                    } catch (Exception ex) {
                    }
                }
            }
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.4.2")) {
            this.setStartingBody(true);
            this.equipClothing();
        }
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_DIRTY_MINDED);

        PerkManager.initialisePerks(this,
                Util.newArrayListOfValues(),
                Util.newHashMapOfValues(
                        new Value<>(PerkCategory.PHYSICAL, 1),
                        new Value<>(PerkCategory.LUST, 3),
                        new Value<>(PerkCategory.ARCANE, 0)));
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Persona:

        if (setPersona) {
            this.setPersonalityTraits(
                    PersonalityTrait.KIND,
                    PersonalityTrait.CONFIDENT);

            this.setSexualOrientation(SexualOrientation.AMBIPHILIC);

            this.setHistory(Occupation.NPC_STORE_OWNER);

            this.clearFetishDesires();
            this.clearFetishes();

            this.addFetish(Fetish.FETISH_LACTATION_SELF);
            this.addFetish(Fetish.FETISH_BREASTS_SELF);

            this.setFetishDesire(Fetish.FETISH_SUBMISSIVE, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_ORAL_RECEIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_STRUTTER, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_MASTURBATION, FetishDesire.THREE_LIKE);
        }

        // Body:

        // Core:
        this.setHeight(158);
        this.setFemininity(85);
        this.setMuscle(Muscle.ZERO_SOFT.getMedianValue());
        this.setBodySize(BodySize.TWO_AVERAGE.getMedianValue());
        this.setHornType(HornType.STRAIGHT);
        this.setHornLength(HornLength.ZERO_TINY.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_COW_MORPH, PresetColour.EYE_HAZEL));
        this.setSkinCovering(new Covering(BodyCoveringType.BOVINE_FUR, CoveringPattern.SPOTTED, PresetColour.COVERING_WHITE, false, PresetColour.COVERING_BLACK, false), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_LIGHT), true);
        this.setSkinCovering(new Covering(BodyCoveringType.NIPPLES, PresetColour.SKIN_TANNED), false);
        this.setSkinCovering(new Covering(BodyCoveringType.VAGINA, PresetColour.SKIN_TANNED), false);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_BOVINE_FUR, CoveringPattern.HIGHLIGHTS, PresetColour.COVERING_BLACK, false, PresetColour.COVERING_WHITE, false), true);
        this.setHairLength(HairLength.THREE_SHOULDER_LENGTH.getMedianValue());
        this.setHairStyle(HairStyle.WAVY);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_BLACK), false);
        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_BOVINE_FUR, PresetColour.COVERING_BLACK), false);
        this.setUnderarmHair(BodyHair.ZERO_NONE);
        this.setAssHair(BodyHair.FOUR_NATURAL);
        this.setPubicHair(BodyHair.FOUR_NATURAL);
        this.setFacialHair(BodyHair.ZERO_NONE);

        this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, CoveringPattern.NONE, CoveringModifier.METALLIC, PresetColour.COVERING_SILVER, false, PresetColour.COVERING_SILVER, false));
        this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, CoveringPattern.NONE, CoveringModifier.METALLIC, PresetColour.COVERING_SILVER, false, PresetColour.COVERING_SILVER, false));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
        this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_RED_LIGHT));
        this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
        this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_PURPLE_LIGHT));

        // Face:
        this.setFaceVirgin(false);
        this.setLipSize(LipSize.TWO_FULL);
        this.setFaceCapacity(Capacity.THREE_SLIGHTLY_LOOSE, true);
        // Throat settings and modifiers
        this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        this.setNippleVirgin(true);
        this.setBreastSize(CupSize.JJ.getMeasurement());
        this.setBreastShape(BreastShape.ROUND);
        this.setNippleSize(NippleSize.FOUR_MASSIVE);
        this.setAreolaeSize(AreolaeSize.FOUR_MASSIVE);

        this.setBreastMilkStorage(4000);
        this.fillMilkToMaxStorage();
        this.setBreastLactationRegeneration(10_000);

        // Ass:
        this.setAssVirgin(true);
        this.setAssBleached(false);
        this.setAssSize(AssSize.FIVE_HUGE);
        this.setHipSize(HipSize.FIVE_VERY_WIDE);
        this.setAssCapacity(Capacity.TWO_TIGHT, true);
        this.setAssWetness(Wetness.ZERO_DRY);
        this.setAssElasticity(OrificeElasticity.TWO_FIRM.getValue());
        this.setAssPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());
        // Anus modifiers

        // Penis:

        // Vagina:
        this.setVaginaVirgin(false);
        this.setVaginaClitorisSize(ClitorisSize.ZERO_AVERAGE);
        this.setVaginaLabiaSize(LabiaSize.THREE_LARGE);
        this.setVaginaSquirter(false);
        this.setVaginaCapacity(Capacity.TWO_TIGHT, true);
        this.setVaginaWetness(Wetness.THREE_WET);
        this.setVaginaElasticity(OrificeElasticity.THREE_FLEXIBLE.getValue());
        this.setVaginaPlasticity(OrificePlasticity.FOUR_ACCOMMODATING.getValue());

        // Feet:
        // Foot shape
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.unequipAllClothingIntoVoid(true, true);

        this.setMoney(5000);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_head_headband", PresetColour.CLOTHING_YELLOW, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_neck_velvet_choker", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_SILVER, null, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_lacy_panties", PresetColour.CLOTHING_ORANGE, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_nursing_bra", PresetColour.CLOTHING_ORANGE, false), true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_sock_pantyhose", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("norin_tail_ribbon_tail_ribbon", PresetColour.CLOTHING_YELLOW, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_foot_flats", PresetColour.CLOTHING_BLACK, false), true, this);

        AbstractClothing dress = Main.game.getItemGen().generateClothing("phlarx_dresses_vintage_dress", PresetColour.CLOTHING_YELLOW, PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_SILVER, false);
        dress.setPattern(Pattern.getPatternIdByName("irbynx_cow_patterned"));
        dress.setPatternColours(Util.newArrayListOfValues(PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_WHITE));
        this.equipClothingFromNowhere(dress, true, this);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_wrist_thin_bangles", PresetColour.CLOTHING_BRONZE, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_finger_wrap_ring", PresetColour.CLOTHING_BRONZE, false), true, this);

        this.setPiercedEar(true);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("norin_sunflower_piercings_piercing_ear_sunflower", PresetColour.CLOTHING_ORANGE_BRIGHT, PresetColour.CLOTHING_BRONZE, PresetColour.CLOTHING_BROWN_DARK, false), true, this);
        this.setPiercedNose(true);
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getSpeechColour() {
        return "#ffb8a3";
    }

    @Override
    public void dailyUpdate() {
        clearNonEquippedInventory(false);

        commonFemaleClothing.clear();
        commonFemaleUnderwear.clear();
        commonFemaleAccessories.clear();

        commonMaleClothing.clear();
        commonMaleLingerie.clear();
        commonMaleAccessories.clear();

        commonAndrogynousClothing.clear();
        commonAndrogynousLingerie.clear();
        commonAndrogynousAccessories.clear();

        for (AbstractClothingType clothing : ClothingType.getAllClothing()) {
            try {
                if (clothing != null
                        && (clothing.getDefaultItemTags().contains(ItemTag.SOLD_BY_NYAN) || clothing.getDefaultItemTags().contains(ItemTag.SOLD_BY_MONICA))
                        && (!clothing.getDefaultItemTags().contains(ItemTag.SILLY_MODE) || Main.game.isSillyMode())) {
                    AbstractClothing generatedClothing = Main.game.getItemGen().generateClothing(clothing, false);

                    for (int i = 0; i < 2 + Util.random.nextInt(5); i++) {
                        if (clothing.getRarity() == Rarity.COMMON) {
                            if (clothing.getFemininityRestriction() == Femininity.FEMININE) {
                                if (ClothingType.getCoreClothingSlots().contains(clothing.getEquipSlots().get(0))) {
                                    commonFemaleClothing.add(generatedClothing);

                                } else if (ClothingType.getLingerieSlots().contains(clothing.getEquipSlots().get(0))) {
                                    commonFemaleUnderwear.add(generatedClothing);

                                } else {
                                    commonFemaleAccessories.add(generatedClothing);
                                }

                            } else if (clothing.getFemininityRestriction() == Femininity.MASCULINE) {
                                if (ClothingType.getCoreClothingSlots().contains(clothing.getEquipSlots().get(0))) {
                                    commonMaleClothing.add(generatedClothing);

                                } else if (ClothingType.getLingerieSlots().contains(clothing.getEquipSlots().get(0))) {
                                    commonMaleLingerie.add(generatedClothing);

                                } else {
                                    commonMaleAccessories.add(generatedClothing);
                                }

                            } else {
                                if (ClothingType.getCoreClothingSlots().contains(clothing.getEquipSlots().get(0))) {
                                    commonAndrogynousClothing.add(generatedClothing);

                                } else if (ClothingType.getLingerieSlots().contains(clothing.getEquipSlots().get(0))) {
                                    commonAndrogynousLingerie.add(generatedClothing);

                                } else {
                                    commonAndrogynousAccessories.add(generatedClothing);
                                }
                            }
                        }
                    }
                }

            } catch (Exception ex) {
                System.getLogger("").log(System.Logger.Level.ERROR, ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void turnUpdate() {
        if (!Main.game.getCharactersPresent().contains(this)) {
            if (Main.game.isHourBetween(9, 17) && Main.game.getDayOfWeek() != DayOfWeek.SUNDAY) {
                this.returnToHome();

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
        this.equipClothing();
    }

    @Override
    public boolean isAbleToBeImpregnated() {
        return true;
    }

    @Override
    public String getTraderDescription() {
        return UtilText.parseFromXMLFile("places/fields/elis/shops/clothing", "CLOTHING_TRANSACTION_START");
    }
}
