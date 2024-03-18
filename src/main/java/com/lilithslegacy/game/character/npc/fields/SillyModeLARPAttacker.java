package com.lilithslegacy.game.character.npc.fields;

import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.valueEnums.LegConfiguration;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.npc.NPCGenerationFlag;
import com.lilithslegacy.game.character.persona.Name;
import com.lilithslegacy.game.character.persona.Occupation;
import com.lilithslegacy.game.character.persona.PersonalityTrait;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.character.race.RacialBody;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.character.race.SubspeciesSpawnRarity;
import com.lilithslegacy.game.combat.moves.AbstractCombatMove;
import com.lilithslegacy.game.combat.moves.CombatMoveType;
import com.lilithslegacy.game.dialogue.DialogueManager;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.companions.SlaveDialogue;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.outfit.OutfitType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * Cribbed from ElisAlleyAttacker
 *
 * @author DSG
 * @version 0.4.8.6
 * @since 0.4.8.6
 */

public class SillyModeLARPAttacker extends NPC {

    public SillyModeLARPAttacker() {
        this(Gender.getGenderFromUserPreferences(false, false), false);
    }

    public SillyModeLARPAttacker(Gender gender) {
        this(gender, false);
    }

    public SillyModeLARPAttacker(boolean isImported) {
        this(Gender.M_P_MALE, isImported);
    }

    /**
     * You must manually place this NPC in a location after creation!
     */
    public SillyModeLARPAttacker(Gender gender, boolean isImported, NPCGenerationFlag... generationFlags) {
        super(isImported, null, null, "",
                Util.random.nextInt(28) + 18, Util.randomItemFrom(Month.values()), 1 + Util.random.nextInt(25),
                3,
                null, null, null,
                new CharacterInventory(10),
                WorldType.getWorldTypeFromId("dsg_fields_elis_eisek_sillymode_dungeon"),
                PlaceType.getPlaceTypeFromId("dsg_fields_elis_eisek_sillymode_dungeon_passage"),
                false,
                generationFlags);

        if (!isImported) {
            // Set random level from 1 to 5:
            setLevel(Util.random.nextInt(6) + 1);

            // Race, name, personality/fetishes:

            Map<AbstractSubspecies, Integer> availableRaces = new HashMap<>();
            for (AbstractSubspecies s : Subspecies.getAllSubspecies()) {
                if (s.getSubspeciesOverridePriority() > 0) { // Do not spawn demonic races, elementals, or youko
                    continue;
                }
                Map<AbstractSubspecies, SubspeciesSpawnRarity> subMap = Subspecies.getWorldSpecies(WorldType.getWorldTypeFromId("innoxia_fields_elis_town"), PlaceType.getPlaceTypeFromId("innoxia_fields_elis_town_alley"), false);
                if (subMap.containsKey(s)) {
                    AbstractSubspecies.addToSubspeciesMap((int) (10000 * subMap.get(s).getChanceMultiplier()), gender, s, availableRaces);
                }
            }

            this.setBodyFromSubspeciesPreference(gender, availableRaces, true, true);

            if (Math.random() < Main.getProperties().halfDemonSpawnRate / 100f && this.getSubspecies() != Subspecies.SLIME) { // Don't convert slimes, as their getFleshSubspecies() can be of any subspecies
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

            setSexualOrientation(RacialBody.valueOfRace(this.getRace()).getSexualOrientation(gender));

            setName(Name.getRandomTriplet(this.getSubspecies()));
            this.setPlayerKnowsName(false);

            //NEET gang represent!
            this.setHistory(Occupation.NPC_UNEMPLOYED);
            if (Math.random() < 0.25f) {
                this.addPersonalityTrait(PersonalityTrait.SLOVENLY);
            }

            Main.game.getCharacterUtils().addFetishes(this);

            Main.game.getCharacterUtils().randomiseBody(this, true);

            //An appropriately dorky physique
            this.setMuscle(Util.random.nextInt(40));

            // Inventory:
            resetInventory(true);
            inventory.setMoney(10 + Util.random.nextInt(getLevel() * 10) + 1);
            Main.game.getCharacterUtils().generateItemsInInventory(this, true, true, true);

            if (!Arrays.asList(generationFlags).contains(NPCGenerationFlag.NO_CLOTHING_EQUIP)) {
                this.equipClothing(EquipClothingSetting.getAllClothingSettings());
            }

            // Set starting perks based on the character's race
            initPerkTreeAndBackgroundPerks();
            this.setStartingCombatMoves();
            loadImages();

            initHealthAndManaToMax();
        }

        this.setEnslavementDialogue(SlaveDialogue.DEFAULT_ENSLAVEMENT_DIALOGUE, true);
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
    }

    // Do not add tease...just...don't do it
    @Override
    public void setStartingCombatMoves() {
        this.clearEquippedMoves();
        this.equipMove("strike");
        this.equipMove("twin-strike");
    }

    // Ace continues to be a lifesaver here
    @Override
    public float getMoveWeight(AbstractCombatMove move, List<GameCharacter> enemies, List<GameCharacter> allies) {
        if (move.getType() == CombatMoveType.TEASE || move.getType() == CombatMoveType.DEFEND) {
            return 0;
        } else {
            return super.getMoveWeight(move, enemies, allies);
        }
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.incrementMoney((int) (this.getInventory().getNonEquippedValue() * 1f));
        this.clearNonEquippedInventory(false);
        Main.game.getCharacterUtils().generateItemsInInventory(this, true, true, true);

        //Start out with a base labourer outfit
        Main.game.getCharacterUtils().equipClothingFromOutfitType(this, OutfitType.JOB_LABOUR, settings);

        //Equip cardboardium armor and weapon
        if (this.getClothingInSlot(InventorySlot.TORSO_OVER) != null) {
            this.unequipClothingIntoVoid(InventorySlot.TORSO_OVER, true, this);
        }
        this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("dsg_sm_cboard_cbarmor", false), InventorySlot.TORSO_OVER, true, this);
        this.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon("dsg_sm_cboard_cbsword"));
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public String getDescription() {
        return (UtilText.parse(this, "[npc.Name] is a resident of Elis who has an affinity for live-action roleplay."));
    }

    @Override
    public boolean isClothingStealable() {
        return true;
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
        return DialogueManager.getDialogueFromId("dsg_encounters_fields_elis_eisek_sillymode_dungeon_combat");
    }

    @Override
    public void applyEscapeCombatEffects() {
    }

    @Override
    public Response endCombat(boolean applyEffects, boolean victory) {
        if (victory) {
            return new Response("", "", DialogueManager.getDialogueFromId("dsg_encounters_fields_elis_eisek_sillymode_dungeon_combat_won"));
        } else {
            return new Response("", "", DialogueManager.getDialogueFromId("dsg_encounters_fields_elis_eisek_sillymode_dungeon_combat_lost"));
        }
    }

    @Override
    public boolean isLootingPlayerAfterCombat() {
        return false; // This is not high-stakes LARPing
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Here to make Java happy
    }

}
