package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.body.valueEnums.BodySize;
import com.lilithslegacy.game.character.body.valueEnums.Muscle;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.fetishes.FetishDesire;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.Name;
import com.lilithslegacy.game.character.persona.Occupation;
import com.lilithslegacy.game.character.persona.SexualOrientation;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.Colour;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.3.7.7
 * @since 0.3.7.7
 */
public class DominionExpressCentaur extends NPC {

    public DominionExpressCentaur() {
        this(Gender.getGenderFromUserPreferences(false, false), PresetColour.CLOTHING_STEEL, false);
    }

    public DominionExpressCentaur(boolean isImported) {
        this(Gender.F_V_B_FEMALE, PresetColour.CLOTHING_STEEL, isImported);
    }

    public DominionExpressCentaur(Gender gender, Colour collarColour) {
        this(gender, collarColour, false);
    }

    public DominionExpressCentaur(Gender gender, Colour collarColour, boolean isImported) {
        super(isImported,
                null, null, "",
                Util.random.nextInt(28) + 18, Util.randomItemFrom(Month.values()), 1 + Util.random.nextInt(25),
                3,
                null, null, null,
                new CharacterInventory(10), WorldType.DOMINION_EXPRESS, PlaceType.DOMINION_EXPRESS_STABLES, false);

        if (!isImported) {
            setLevel(8 + Util.random.nextInt(5)); // 8-12

            // RACE & NAME:

            this.setBodyFromSubspeciesPreference(gender, Util.newHashMapOfValues(new Value<>(Subspecies.CENTAUR, 1)), true, false);

            if (Math.random() < 0.4f) {
                setSexualOrientation(SexualOrientation.AMBIPHILIC);
            } else {
                setSexualOrientation(SexualOrientation.GYNEPHILIC);
            }

            List<String> names;
            if (this.isFeminine()) {
                names = Util.newArrayListOfValues("horny centauress", "lustful centauress", "desperate centauress");
            } else {
                names = Util.newArrayListOfValues("horny centaur", "lustful centaur", "desperate centaur");
            }
            this.setGenericName(Util.randomItemFrom(names));
            setName(Name.getRandomTriplet(this.getSubspecies()));
            this.setPlayerKnowsName(false);

            // PERSONALITY & BACKGROUND:

            Main.game.getCharacterUtils().setHistoryAndPersonality(this, false);

            this.setHistory(Occupation.NPC_SLAVE);

            // ADDING FETISHES:

            this.clearFetishes();
            this.clearFetishDesires();

            this.setAssVirgin(false);
            this.setPenisVirgin(false);
            this.setFaceVirgin(false);

            this.addFetish(Fetish.FETISH_ANAL_GIVING);
            this.addFetish(Fetish.FETISH_ORAL_RECEIVING);

            this.setFetishDesire(Fetish.FETISH_DOMINANT, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_PENIS_GIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_CUM_STUD, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_ANAL_RECEIVING, FetishDesire.THREE_LIKE);
            this.setFetishDesire(Fetish.FETISH_BREASTS_OTHERS, FetishDesire.THREE_LIKE);

            this.setFetishDesire(Fetish.FETISH_VAGINAL_GIVING, FetishDesire.ZERO_HATE);
            this.setFetishDesire(Fetish.FETISH_IMPREGNATION, FetishDesire.ZERO_HATE);

            double rnd = Math.random();
            if (rnd < 0.05f) {
                this.addFetish(Fetish.FETISH_SUBMISSIVE);
            } else if (rnd < 0.15f) {
                this.addFetish(Fetish.FETISH_SADIST);
            }

            // BODY RANDOMISATION:

            Main.game.getCharacterUtils().randomiseBody(this, true);

            this.setMuscle(Muscle.FOUR_RIPPED.getMedianValue());
            this.setBodySize(BodySize.THREE_LARGE.getMedianValue());

            this.setHeight(215 + Util.random.nextInt(26));


            // INVENTORY:

            resetInventory(true);
            inventory.setMoney(10 + Util.random.nextInt(getLevel() * 10) + 1);

            this.equipClothing(EquipClothingSetting.getAllClothingSettings());
            if (collarColour == null) {
                collarColour = PresetColour.CLOTHING_GOLD;
                rnd = Math.random();
                if (rnd < 0.3f) {
                    collarColour = PresetColour.CLOTHING_STEEL;
                } else if (rnd < 0.8f) {
                    collarColour = PresetColour.CLOTHING_BRONZE;
                } else if (rnd < 0.95f) {
                    collarColour = PresetColour.CLOTHING_SILVER;
                }
            }
            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing(ClothingType.getClothingTypeFromId("innoxia_bdsm_metal_collar"), collarColour, false), true, this);

//			Main.game.getCharacterUtils().applyMakeup(this, true);

            // Set starting attributes based on the character's race
            initPerkTreeAndBackgroundPerks();
            this.setStartingCombatMoves();
            loadImages();

            initHealthAndManaToMax();
        }
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.8.2")) {
            this.setHistory(Occupation.NPC_SLAVE);
        }
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Not needed
    }

    @Override
    public String getDescription() {
        return UtilText.parse(this, "[npc.Name] is a slave at Dominion Express, and works hard every day to transport goods all over the city.");
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.unequipAllClothingIntoVoid(true, true);

        if (this.isFeminine()) {
            if (Math.random() < 0.5f) {
                this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_tube_top", false), true, this);
            } else {
                this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_chest_sports_bra", false), true, this);
            }
        }
    }

    @Override
    public boolean isUnique() {
        return false;
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
    public void hourlyUpdate(int hour) {
        if ((hour >= 6 && hour < 22) // extended work time
                && !Main.game.getCharactersPresent().contains(this)
                && Math.random() < 0.8f) {
            this.setLocation(WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL);
        } else {
            this.returnToHome();
        }
    }

    @Override
    public String getDirtyTalk() {
        if (this.getLocationPlace().getPlaceType() == PlaceType.DOMINION_EXPRESS_OFFICE_STABLE) {
            if (Main.sex.getForeplayPreference(this, Main.game.getPlayer()).getTargetedSexArea() == SexAreaOrifice.MOUTH) {
                return UtilText.parseFromXMLFile("characters/dominion/dominionExpressCentaur", "OFFICE_DIRTY_TALK_ORAL", this);

            } else if (Main.sex.getForeplayPreference(this, Main.game.getPlayer()).getTargetedSexArea() == SexAreaPenetration.TONGUE) {
                return UtilText.parseFromXMLFile("characters/dominion/dominionExpressCentaur", "OFFICE_DIRTY_TALK_ANILINGUS", this);

            } else if (Main.sex.getForeplayPreference(this, Main.game.getPlayer()).getTargetedSexArea() == SexAreaOrifice.ANUS) {
                return UtilText.parseFromXMLFile("characters/dominion/dominionExpressCentaur", "OFFICE_DIRTY_TALK_ANAL", this);
            }
        }

        return super.getDirtyTalk();
    }
}
