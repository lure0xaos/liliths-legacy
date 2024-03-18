package com.lilithslegacy.game.character.npc.fields;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.valueEnums.LegConfiguration;
import com.lilithslegacy.game.character.fetishes.AbstractFetish;
import com.lilithslegacy.game.character.fetishes.FetishDesire;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.Name;
import com.lilithslegacy.game.character.persona.PersonalityTrait;
import com.lilithslegacy.game.character.persona.SexualOrientation;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.outfit.OutfitType;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.AbstractPlaceType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.2
 * @since 0.4.2
 */
public class EvelyxSexualPartner extends NPC {

    public EvelyxSexualPartner() {
        this(Gender.getGenderFromUserPreferences(false, true), false);
    }

    public EvelyxSexualPartner(boolean isImported) {
        this(Gender.getGenderFromUserPreferences(false, true), isImported);
    }

    public EvelyxSexualPartner(Gender gender, boolean isImported) {
        super(isImported, null, null, "",
                25, Util.randomItemFrom(Month.values()), 1 + Util.random.nextInt(25),
                10,
                null, null, null,
                new CharacterInventory(10),
                WorldType.getWorldTypeFromId("innoxia_fields_dairyFarm"), PlaceType.getPlaceTypeFromId("innoxia_fields_dairyFarm_exit"),
                false);

        if (!isImported) {
            this.setLocation(worldLocation, location, false);

            setLevel(Util.random.nextInt(5) + 5);

            // RACE & NAME:

            Map<AbstractSubspecies, Integer> availableRaces = new HashMap<>();
            AbstractPlaceType placeType = Main.game.getPlayer().getLocationPlace().getPlaceType();
            for (AbstractSubspecies s : Subspecies.getAllSubspecies()) {
                if (s.getSubspeciesOverridePriority() > 0) { // Do not spawn demonic races, elementals, or youko
                    continue;
                }
                if (Subspecies.getWorldSpecies(WorldType.WORLD_MAP, placeType, false).containsKey(s)) {
                    AbstractSubspecies.addToSubspeciesMap((int) (10000 * Subspecies.getWorldSpecies(WorldType.WORLD_MAP, placeType, false).get(s).getChanceMultiplier()), gender, s, availableRaces);
                }
            }

            this.setBodyFromSubspeciesPreference(gender, availableRaces, true, true);

            if (Math.random() < Main.getProperties().halfDemonSpawnRate / 100f && this.getSubspecies() != Subspecies.SLIME) { // Don't convert slimes, as their getFleshSubspecies() can be of any non-Fields subspecies
                this.setBody(Main.game.getCharacterUtils().generateHalfDemonBody(this, gender, this.getBody().getFleshSubspecies(), true), true);
            }

            if (Math.random() < Main.getProperties().taurSpawnRate / 100f
                    && this.getLegConfiguration() != LegConfiguration.QUADRUPEDAL) { // Do not reset this character's taur body if they spawned as a taur (as otherwise subspecies-specific settings get overridden by global taur settings)
                // Check for race's leg type as taur, otherwise NPCs which spawn with human legs won't be affected by taur conversion rate:
                if (this.getRace().getRacialBody().getLegType().isLegConfigurationAvailable(LegConfiguration.QUADRUPEDAL)) {
                    this.setLegType(this.getRace().getRacialBody().getLegType());
                    Main.game.getCharacterUtils().applyTaurConversion(this);
                }
            }

            setSexualOrientation(SexualOrientation.AMBIPHILIC);

            setName(Name.getRandomTriplet(this.getSubspecies()));
            this.setPlayerKnowsName(false);
            setDescription(UtilText.parse(this,
                    "[npc.Name] is a resident of the Foloi Fields, who travels to Evelyx's Dairy to pay for sex with one of the farm's cows."));

            // PERSONALITY & BACKGROUND:

            Main.game.getCharacterUtils().setHistoryAndPersonality(this, false);
            this.removePersonalityTrait(PersonalityTrait.MUTE); // Don't want mutes in cow sex as they have dialogue

            // ADDING FETISHES:

            Main.game.getCharacterUtils().addFetishes(this);

            // Remove negative fetish desires as otherwise they might conflict with the sex type
            for (AbstractFetish fetish : new ArrayList<>(this.getFetishes(false))) {
                if (this.getFetishDesire(fetish).isNegative()) {
                    this.setFetishDesire(fetish, FetishDesire.TWO_NEUTRAL);
                }
            }

            // BODY RANDOMISATION:

            Main.game.getCharacterUtils().randomiseBody(this, true);

            // INVENTORY:

            resetInventory(true);
            inventory.setMoney(10 + Util.random.nextInt(getLevel() * 10) + 1);

            this.equipClothing(EquipClothingSetting.getAllClothingSettings());
            Main.game.getCharacterUtils().applyMakeup(this, true);
            Main.game.getCharacterUtils().applyTattoos(this, true);

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
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Not needed
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        Main.game.getCharacterUtils().equipClothingFromOutfitType(this, OutfitType.CASUAL, settings);
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public boolean isAbleToBeImpregnated() {
        return false;
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
        return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.VAGINA);
    }

    @Override
    public SexType getMainSexPreference(GameCharacter target) {
        return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.VAGINA);
    }

}
