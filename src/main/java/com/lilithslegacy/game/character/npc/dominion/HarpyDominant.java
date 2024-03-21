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
import com.lilithslegacy.game.character.body.valueEnums.BodySize;
import com.lilithslegacy.game.character.body.valueEnums.BreastShape;
import com.lilithslegacy.game.character.body.valueEnums.Capacity;
import com.lilithslegacy.game.character.body.valueEnums.ClitorisSize;
import com.lilithslegacy.game.character.body.valueEnums.CupSize;
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
import com.lilithslegacy.game.character.effects.Perk;
import com.lilithslegacy.game.character.effects.PerkCategory;
import com.lilithslegacy.game.character.effects.PerkManager;
import com.lilithslegacy.game.character.fetishes.Fetish;
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
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.DialogueManager;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.places.dominion.harpyNests.HarpyNestDominant;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.inventory.item.AbstractItem;
import com.lilithslegacy.game.inventory.item.ItemType;
import com.lilithslegacy.game.sex.SexPace;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.2.11
 * @since 0.1.8
 */
public class HarpyDominant extends NPC {

    public HarpyDominant() {
        this(false);
    }

    public HarpyDominant(boolean isImported) {
        super(isImported, new NameTriplet("Diana"), "Zima",
                "One of the more notable harpy matriarchs, Diana is the leader of a flock of harpies."
                        + " As cruel as harpies come, Diana rules her flock with an iron fist, harshly punishing any harpies that try to challenge her dominance.",
                33, Month.OCTOBER, 2,
                7, Gender.F_V_B_FEMALE, Subspecies.HARPY, RaceStage.LESSER,
                new CharacterInventory(30), WorldType.HARPY_NEST, PlaceType.HARPY_NESTS_HARPY_NEST_RED, true);

    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);

        if (Main.isVersionOlderThan(Game.loadingVersion, "0.2.10.5")) {
            resetBodyAfterVersion_2_10_5();
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.5.1")) {
            this.setPersonalityTraits(
                    PersonalityTrait.SELFISH);
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.6")) {
            this.resetPerksMap(true);
        }
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.20")) {
            this.setSkinCovering(new Covering(BodyCoveringType.HARPY_SKIN, PresetColour.SKIN_RED_DARK), false);
        }
    }

    @Override
    public void setupPerks(boolean autoSelectPerks) {
        this.addSpecialPerk(Perk.SPECIAL_DIRTY_MINDED);

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
                    PersonalityTrait.SELFISH);

            this.setSexualOrientation(SexualOrientation.AMBIPHILIC);

            this.setHistory(Occupation.NPC_HARPY_MATRIARCH);

            this.addFetish(Fetish.FETISH_DOMINANT);
            this.addFetish(Fetish.FETISH_SADIST);
        }

        // Body:

        // Core:
        this.setHeight(170);
        this.setFemininity(95);
        this.setMuscle(Muscle.FOUR_RIPPED.getMedianValue());
        this.setBodySize(BodySize.ZERO_SKINNY.getMedianValue());

        // Coverings:
        this.setEyeCovering(new Covering(BodyCoveringType.EYE_HARPY, PresetColour.EYE_RED));
        this.setSkinCovering(new Covering(BodyCoveringType.FEATHERS, PresetColour.COVERING_RED), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_OLIVE), true);
        this.setSkinCovering(new Covering(BodyCoveringType.HARPY_SKIN, PresetColour.SKIN_RED_DARK), false);

        this.setHairCovering(new Covering(BodyCoveringType.HAIR_HARPY, PresetColour.COVERING_BLACK), true);
        this.setHairLength(HairLength.THREE_SHOULDER_LENGTH);
        this.setHairStyle(HairStyle.LOOSE);

        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HUMAN, PresetColour.COVERING_BLACK), false);
        this.setHairCovering(new Covering(BodyCoveringType.BODY_HAIR_HARPY, PresetColour.COVERING_BLACK), false);
        this.setUnderarmHair(BodyHair.ZERO_NONE);
        this.setAssHair(BodyHair.ZERO_NONE);
        this.setPubicHair(BodyHair.ZERO_NONE);
        this.setFacialHair(BodyHair.ZERO_NONE);

//		this.setHandNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS, PresetColour.COVERING_RED));
//		this.setFootNailPolish(new Covering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET, PresetColour.COVERING_RED));
//		this.setBlusher(new Covering(BodyCoveringType.MAKEUP_BLUSHER, PresetColour.COVERING_RED));
        this.setLipstick(new Covering(BodyCoveringType.MAKEUP_LIPSTICK, PresetColour.COVERING_RED_DARK));
        this.setEyeLiner(new Covering(BodyCoveringType.MAKEUP_EYE_LINER, PresetColour.COVERING_BLACK));
        this.setEyeShadow(new Covering(BodyCoveringType.MAKEUP_EYE_SHADOW, PresetColour.COVERING_RED_DARK));

        // Face:
        this.setFaceVirgin(false);
        this.setLipSize(LipSize.ONE_AVERAGE);
        this.setFaceCapacity(Capacity.TWO_TIGHT, true);
        // Throat settings and modifiers
        this.setTongueLength(TongueLength.ZERO_NORMAL.getMedianValue());
        // Tongue modifiers

        // Chest:
        this.setNippleVirgin(true);
        this.setBreastSize(CupSize.B.getMeasurement());
        this.setBreastShape(BreastShape.POINTY);
        this.setNippleSize(NippleSize.ONE_SMALL);
        this.setAreolaeSize(AreolaeSize.ONE_SMALL);
        // Nipple settings and modifiers

        // Ass:
        this.setAssVirgin(true);
        this.setAssBleached(false);
        this.setAssSize(AssSize.THREE_NORMAL);
        this.setHipSize(HipSize.FOUR_WOMANLY);
        this.setAssCapacity(Capacity.TWO_TIGHT, true);
        this.setAssWetness(Wetness.ZERO_DRY);
        this.setAssElasticity(OrificeElasticity.TWO_FIRM.getValue());
        this.setAssPlasticity(OrificePlasticity.FOUR_ACCOMMODATING.getValue());
        // Anus modifiers

        // Penis:
        // No penis

        // Vagina:
        this.setVaginaVirgin(false);
        this.setVaginaClitorisSize(ClitorisSize.ZERO_AVERAGE);
        this.setVaginaLabiaSize(LabiaSize.ONE_SMALL);
        this.setVaginaSquirter(false);
        this.setVaginaCapacity(Capacity.TWO_TIGHT, true);
        this.setVaginaWetness(Wetness.THREE_WET);
        this.setVaginaElasticity(OrificeElasticity.FOUR_LIMBER.getValue());
        this.setVaginaPlasticity(OrificePlasticity.THREE_RESILIENT.getValue());

        // Feet:
        // Foot shape
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {

        this.unequipAllClothingIntoVoid(true, true);

        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_thong", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_plunge_bra", PresetColour.CLOTHING_BLACK, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.TORSO_PLUNGE_DRESS, PresetColour.CLOTHING_BLACK, false), true, this);

        this.setPiercedEar(true);
        this.setPiercedLip(true);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_ear_ring", PresetColour.CLOTHING_SILVER, false), true, this);
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_lip_double_ring", PresetColour.CLOTHING_SILVER, false), true, this);

    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getSpeechColour() {
        if (Main.getProperties().hasValue(PropertyValue.lightTheme)) {
            return "#C13350";

        } else {
            return "#D7567D";
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
    public DialogueNode getEncounterDialogue() {
        return null;
    }

    @Override
    public SexPace getSexPaceSubPreference(GameCharacter character) {
        return SexPace.SUB_EAGER;
    }

    // Combat

    public int getEscapeChance() {
        return 0;
    }

    @Override
    public Response endCombat(boolean applyEffects, boolean victory) {
        if (victory) {
            return new Response("", "", HarpyNestDominant.HARPY_NEST_DOMINANT_FIGHT_BEAT_DOMINANT) {
                @Override
                public void effects() {
                    Main.game.getDialogueFlags().values.add(DialogueFlagValue.dominantPacified);
                    Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().addItem(Main.game.getItemGen().generateItem(ItemType.HARPY_MATRIARCH_DOMINANT_PERFUME), false, true));

                    if (Main.game.getPlayer().getQuest(QuestLine.SIDE_HARPY_PACIFICATION) == Quest.HARPY_PACIFICATION_ONE) {
                        Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE_HARPY_PACIFICATION, Quest.HARPY_PACIFICATION_TWO));

                    } else if (Main.game.getPlayer().getQuest(QuestLine.SIDE_HARPY_PACIFICATION) == Quest.HARPY_PACIFICATION_TWO) {
                        Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE_HARPY_PACIFICATION, Quest.HARPY_PACIFICATION_THREE));

                    } else if (Main.game.getPlayer().getQuest(QuestLine.SIDE_HARPY_PACIFICATION) == Quest.HARPY_PACIFICATION_THREE) {
                        Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().setQuestProgress(QuestLine.SIDE_HARPY_PACIFICATION, Quest.HARPY_PACIFICATION_REWARD));
                    }
                }
            };
        } else {
            return new Response("", "", DialogueManager.getDialogueFromId("innoxia_places_dominion_harpy_nests_dominant_combat_lost_matriarch"));
        }
    }

    @Override
    public Value<Boolean, String> getItemUseEffects(AbstractItem item, GameCharacter itemOwner, GameCharacter user, GameCharacter target) {
        // You really shouldn't be able to alter this character's fetishes...
        if (user.isPlayer() && !target.isPlayer() && (item.getItemType().equals(ItemType.FETISH_UNREFINED) || item.getItemType().equals(ItemType.FETISH_REFINED))) {
            return new Value<>(false,
                    "<p>"
                            + "You try to give [npc.name] your " + item.getName() + ", but [npc.she] takes one look at it and laughs,"
                            + " [npc.speechNoEffects(You pathetic little thing! Do you seriously expect me to drink some potion of yours?! Idiot!)]"
                            + "</p>");
        }

        return super.getItemUseEffects(item, itemOwner, user, target);
    }

    public void applyBadEndClothing(GameCharacter target, boolean applyPiercings) {
        target.unequipAllClothingIntoVoid(true, true);

        if (target.hasVagina()) {
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_groin_lacy_thong", PresetColour.CLOTHING_BLACK, false), true, target);
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_strapless_bra", PresetColour.CLOTHING_BLACK, false), true, target);

            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.TORSO_CORSET_DRESS, PresetColour.CLOTHING_BLACK, false), true, target);

        } else {
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_bdsm_ballgag", PresetColour.CLOTHING_RED_DARK, PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_STEEL, false), true, target);

            AbstractClothing choker = Main.game.getItemGen().generateClothing("innoxia_bdsm_choker", PresetColour.CLOTHING_PURPLE_VERY_DARK, PresetColour.CLOTHING_STEEL, null, false);
            choker.setSticker("top_txt", "obedient");
            choker.setSticker("btm_txt", "toy");
            target.equipClothingFromNowhere(choker, true, target);

//			target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("sage_latex_croptop", PresetColour.CLOTHING_PURPLE_VERY_DARK, PresetColour.CLOTHING_STEEL, null, false), true, target);
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_nipple_tape_crosses", PresetColour.CLOTHING_PURPLE_VERY_DARK, false), true, target);
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("sage_latex_corset", PresetColour.CLOTHING_PURPLE_VERY_DARK, PresetColour.CLOTHING_STEEL, null, false), true, target);
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("sage_latex_stockings_open", PresetColour.CLOTHING_PURPLE_VERY_DARK, false), true, target);
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_bdsm_wrist_bracelets", PresetColour.CLOTHING_PURPLE_VERY_DARK, false), true, target);
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_bdsm_wrist_bracelets", PresetColour.CLOTHING_PURPLE_VERY_DARK, false), InventorySlot.ANKLE, true, target);
        }

        if (applyPiercings) {
            target.setPiercedEar(true);
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_ear_ball_studs", PresetColour.CLOTHING_STEEL, false), true, target);
            target.setPiercedNose(true);
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_nose_ring", PresetColour.CLOTHING_STEEL, false), true, target);
            target.setPiercedLip(true);
            target.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_piercing_lip_double_ring", PresetColour.CLOTHING_STEEL, false), true, target);
        }
    }
}
