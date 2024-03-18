package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.valueEnums.LegConfiguration;
import com.lilithslegacy.game.character.fetishes.AbstractFetish;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.fetishes.FetishDesire;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.npc.NPCGenerationFlag;
import com.lilithslegacy.game.character.persona.Name;
import com.lilithslegacy.game.character.persona.Occupation;
import com.lilithslegacy.game.character.persona.PersonalityTrait;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.character.race.RacialBody;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.combat.CombatBehaviour;
import com.lilithslegacy.game.combat.moves.AbstractCombatMove;
import com.lilithslegacy.game.combat.moves.CombatMoveType;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.npcDialogue.dominion.EnforcerAlleywayDialogue;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.inventory.item.AbstractItem;
import com.lilithslegacy.game.inventory.outfit.OutfitType;
import com.lilithslegacy.game.sex.sexActions.dominion.EnforcerPatrolSA;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * A randomly-generated Patrol Enforcer. Spawns with the 'patrol' outfit (ATHLETIC).
 *
 * @author Innoxia
 * @version 0.3.8.3
 * @since 0.3.6.2
 */
public class EnforcerPatrol extends NPC {

    public EnforcerPatrol() {
        this(Occupation.NPC_ENFORCER_PATROL_CONSTABLE, Gender.getGenderFromUserPreferences(false, false), false);
    }

    public EnforcerPatrol(boolean isImported) {
        this(Occupation.NPC_ENFORCER_PATROL_CONSTABLE, Gender.F_V_B_FEMALE, isImported);
    }

    public EnforcerPatrol(Occupation occupation, Gender gender) {
        this(occupation, gender, false);
    }

    public EnforcerPatrol(Occupation occupation, Gender gender, boolean isImported, NPCGenerationFlag... generationFlags) {
        super(isImported, null, null, "",
                Util.random.nextInt(28) + 18, Util.randomItemFrom(Month.values()), 1 + Util.random.nextInt(25),
                5, gender, null, null,
                new CharacterInventory(10), WorldType.ENFORCER_HQ, PlaceType.ENFORCER_HQ_CELLS_OFFICE, false,
                generationFlags);

        if (!isImported) {
            setLevel(Util.random.nextInt(5) + 3);

            Map<AbstractSubspecies, Integer> availableRaces = new HashMap<>();
            for (AbstractSubspecies s : Subspecies.getAllSubspecies()) {
                if (s.getSubspeciesOverridePriority() > 0) { // Do not spawn demonic races, elementals, or youko
                    continue;
                }
                if (Subspecies.getWorldSpecies(WorldType.DOMINION, null, false, Subspecies.SLIME).containsKey(s)) {
                    AbstractSubspecies.addToSubspeciesMap((int) (5000 * Subspecies.getWorldSpecies(WorldType.DOMINION, null, false, Subspecies.SLIME).get(s).getChanceMultiplier()), gender, s, availableRaces);

                } else if (Subspecies.getWorldSpecies(WorldType.SUBMISSION, null, false, Subspecies.SLIME).containsKey(s)) { // Add Submission races at only 20% of the chance of Dominion races
                    AbstractSubspecies.addToSubspeciesMap((int) (1000 * Subspecies.getWorldSpecies(WorldType.SUBMISSION, null, false, Subspecies.SLIME).get(s).getChanceMultiplier()), gender, s, availableRaces);
                }
            }

            this.setBodyFromSubspeciesPreference(gender, availableRaces, true, false);

            if (Math.random() < Main.getProperties().halfDemonSpawnRate / 100f) { // Half-demon spawn rate
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

            this.setHistory(occupation);

            Main.game.getCharacterUtils().addFetishes(this, Fetish.FETISH_CROSS_DRESSER, Fetish.FETISH_EXHIBITIONIST); // Do not allow cross-dressing or exhibitionist, as otherwise it will mess with uniforms.

            List<AbstractFetish> fetishesForNonNegative = Util.newArrayListOfValues(
                    Fetish.FETISH_ANAL_GIVING,
                    Fetish.FETISH_ORAL_RECEIVING,
                    Fetish.FETISH_VAGINAL_GIVING,
                    Fetish.FETISH_VAGINAL_RECEIVING,
                    Fetish.FETISH_PENIS_GIVING,
                    Fetish.FETISH_PENIS_RECEIVING,
                    Fetish.FETISH_DOMINANT);
            for (AbstractFetish fetish : fetishesForNonNegative) {
                if (this.getFetishDesire(fetish).isNegative()) {
                    this.setFetishDesire(fetish, FetishDesire.TWO_NEUTRAL);
                }
            }

            Main.game.getCharacterUtils().randomiseBody(this, true);

            resetInventory(true);
            inventory.setMoney(10 + Util.random.nextInt(getLevel() * 10) + 1);
            Main.game.getCharacterUtils().generateItemsInInventory(this, true, true, false);
            this.addClothing(Main.game.getItemGen().generateClothing("innoxia_penis_condom", PresetColour.CLOTHING_PURPLE_DARK, false), 5, false, false);

            if (!Arrays.asList(generationFlags).contains(NPCGenerationFlag.NO_CLOTHING_EQUIP)) {
                this.equipClothing(EquipClothingSetting.getAllClothingSettings());
            }
            Main.game.getCharacterUtils().applyMakeup(this, true);
            Main.game.getCharacterUtils().applyTattoos(this, true);

            initPerkTreeAndBackgroundPerks(); // Set starting perks based on the character's race

            this.removePersonalityTrait(PersonalityTrait.MUTE);

            this.setEssenceCount(100);

            this.setLocation(Main.game.getPlayer(), false); // Move to player location

            for (AbstractCombatMove move : new ArrayList<>(this.getEquippedMoves())) {
                if (move.getType() == CombatMoveType.TEASE) {
                    this.unequipMove(move.getIdentifier());
                }
            }

            initHealthAndManaToMax();
        }
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.9.9")) {
            AbstractClothing vest = this.getClothingInSlot(InventorySlot.TORSO_OVER);
            if (vest != null) {
                this.unequipClothingIntoVoid(vest, true, this);
                AbstractClothing newVest = Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_stpvest", false);
                newVest.setSticker("name_plate", "enforcer");
                this.equipClothingFromNowhere(newVest, true, this);
            }
        }
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Not needed
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        this.unequipAllClothingIntoVoid(true, true);

        Main.game.getCharacterUtils().equipClothingFromOutfitType(this, OutfitType.ATHLETIC, settings);

        // Do not wear gas masks:
        AbstractClothing mouthClothing = this.getClothingInSlot(InventorySlot.MOUTH);
        if (mouthClothing != null && mouthClothing.getClothingType().equals(ClothingType.getClothingTypeFromId("dsg_eep_tacequipset_gmask"))) {
            this.unequipClothingIntoVoid(mouthClothing, true, this);
        }
    }

    /**
     * Equips this Enforcer with a baton in the primary slot, and the weapon as defined by offhandWeaponID in the offhand slot.
     */
    public void setWeapons(String offhandWeaponID) {
        this.unequipAllWeaponsIntoVoid(false);
        this.equipMainWeaponFromNowhere(Main.game.getItemGen().generateWeapon("dsg_eep_enbaton_enbaton"));
        this.equipOffhandWeaponFromNowhere(Main.game.getItemGen().generateWeapon(offhandWeaponID));
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public String getDescription() {
        return UtilText.parse(this, "A member of the Enforcers' Frontline Patrol division, [npc.name] is expected to carry out a wide variety of day-to-day policing duties.");
    }

    @Override
    public String getGenericName() {
        if (this.getHistory() == Occupation.NPC_ENFORCER_PATROL_CONSTABLE) {
            return UtilText.parse(this, "Constable [npc.surname]");
        } else if (this.getHistory() == Occupation.NPC_ENFORCER_PATROL_SERGEANT) {
            return UtilText.parse(this, "Sergeant [npc.surname]");
        } else if (this.getHistory() == Occupation.NPC_ENFORCER_PATROL_INSPECTOR) {
            return UtilText.parse(this, "Inspector [npc.surname]");
        }
        return UtilText.parse(this, "Enforcer");
    }

    @Override
    public void endSex() {
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
        return null;
    }

    // Combat:

    @Override
    public CombatBehaviour getCombatBehaviour() {
        return CombatBehaviour.ATTACK; // Enforcers just use attacks
    }

    @Override
    public void applyEscapeCombatEffects() {
        EnforcerAlleywayDialogue.banishEnforcers(false);
    }

    @Override
    public Response endCombat(boolean applyEffects, boolean victory) {
        if (victory) {
            return new Response("", "", EnforcerAlleywayDialogue.AFTER_COMBAT_VICTORY);
        } else {
            return new Response("", "", EnforcerAlleywayDialogue.AFTER_COMBAT_DEFEAT);
        }
    }

    // Sex:

    @Override
    public List<Class<?>> getUniqueSexClasses() {
        return Util.newArrayListOfValues(EnforcerPatrolSA.class);
    }

    @Override
    public Value<AbstractItem, String> getSexItemToUse(GameCharacter partner) {
        return null; // Do not want Enforcers using items during sex
    }

//	@Override
//	public Value<AbstractClothing, String> getSexClothingToSelfEquip(GameCharacter partner, boolean inQuickSex) {
//		if(Main.game.isInSex() && (inQuickSex || !Main.sex.getInitialSexManager().isPartnerWantingToStopSex(this))) {
//			if(this.hasPenisIgnoreDildo() && this.getClothingInSlot(InventorySlot.PENIS)==null) {
//				AbstractClothing condom = null;
//				for(AbstractClothing clothing : this.getAllClothingInInventory().keySet()) {
//					if(clothing.isCondom()) {
//						condom = clothing;
//						break;
//					}
//				}
//				if(condom!=null && this.isAbleToEquip(condom, inQuickSex, this)) {
//					return new Value<>(condom, UtilText.parse(this, "[npc.Name] grabs a "+condom.getName()+" from out of [npc.her] inventory..."));
//				}
//			}
//		}
//		return null;
//	}
}
