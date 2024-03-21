package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.npc.NPCGenerationFlag;
import com.lilithslegacy.game.character.npc.submission.Claire;
import com.lilithslegacy.game.character.persona.Name;
import com.lilithslegacy.game.character.persona.Occupation;
import com.lilithslegacy.game.character.persona.SexualOrientation;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.combat.CombatBehaviour;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.places.dominion.EnforcerWarehouse;
import com.lilithslegacy.game.dialogue.responses.Response;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.ClothingType;
import com.lilithslegacy.game.inventory.outfit.OutfitType;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlotStocks;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.3.5
 * @since 0.3.5
 */
public class EnforcerWarehouseGuard extends NPC {

    public EnforcerWarehouseGuard() {
        this(Occupation.NPC_ENFORCER_SWORD_SERGEANT, Subspecies.WOLF_MORPH, RaceStage.GREATER, Gender.getGenderFromUserPreferences(false, false), false);
    }

    public EnforcerWarehouseGuard(boolean isImported) {
        this(Occupation.NPC_ENFORCER_SWORD_SERGEANT, Subspecies.WOLF_MORPH, RaceStage.GREATER, Gender.F_V_B_FEMALE, isImported);
    }

    public EnforcerWarehouseGuard(Occupation occupation, AbstractSubspecies subspecies, RaceStage raceStage, Gender gender, boolean isImported, NPCGenerationFlag... generationFlags) {
        super(isImported, null, null, "",
                Util.random.nextInt(28) + 18, Util.randomItemFrom(Month.values()), 1 + Util.random.nextInt(25),
                5, gender, subspecies, raceStage,
                new CharacterInventory(10), WorldType.ENFORCER_WAREHOUSE, PlaceType.ENFORCER_WAREHOUSE_ENFORCER_GUARD_POST, false,
                generationFlags);

        if (!isImported) {
            setLevel(Util.random.nextInt(6) + 5);

            setSexualOrientation(SexualOrientation.AMBIPHILIC);

            setName(Name.getRandomTriplet(this.getSubspecies()));

            this.setPlayerKnowsName(false);

            this.setHistory(occupation);

            Main.game.getCharacterUtils().addFetishes(this, Fetish.FETISH_CROSS_DRESSER, Fetish.FETISH_EXHIBITIONIST); // Do not allow cross-dressing or exhibitionist, as otherwise it will mess with uniforms.

            this.addFetish(Fetish.FETISH_SADIST);

            Main.game.getCharacterUtils().randomiseBody(this, true);

            resetInventory(true);
            inventory.setMoney(10 + Util.random.nextInt(getLevel() * 10) + 1);
            Main.game.getCharacterUtils().generateItemsInInventory(this, true, true, false);

            if (!Arrays.asList(generationFlags).contains(NPCGenerationFlag.NO_CLOTHING_EQUIP)) {
                this.equipClothing(EquipClothingSetting.getAllClothingSettings());
            }
            Main.game.getCharacterUtils().applyMakeup(this, true);
            Main.game.getCharacterUtils().applyTattoos(this, true);

            initPerkTreeAndBackgroundPerks(); // Set starting perks based on the character's race

            this.setCombatBehaviour(CombatBehaviour.ATTACK);

            this.setEssenceCount(100);

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
        Main.game.getCharacterUtils().equipClothingFromOutfitType(this, OutfitType.ATHLETIC, settings);

        // Do not wear gas masks in the warehouse:
        AbstractClothing mouthClothing = this.getClothingInSlot(InventorySlot.MOUTH);
        if (mouthClothing != null && mouthClothing.getClothingType().equals(ClothingType.getClothingTypeFromId("dsg_eep_tacequipset_gmask"))) {
            this.unequipClothingIntoVoid(mouthClothing, true, this);
        }
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public String getDescription() {
        return UtilText.parse(this, "One of the SWORD Enforcers tasked with guarding [npc.his] division's warehouse, [npc.name] is more than prepared to use an unreasonable amount of force to detain anyone [npc.she] catches...");
    }

    @Override
    public void endSex() {
    }

    @Override
    public boolean isClothingStealable() {
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

    // Combat:
    @Override
    public int getEscapeChance() {
        return 0;
    }

    @Override
    public Response endCombat(boolean applyEffects, boolean victory) {
        if (Main.game.getPlayer().getCell().getPlace().getPlaceType() == PlaceType.ENFORCER_WAREHOUSE_ENFORCER_GUARD_POST) {
            if (victory) {
                return new Response("Victory", "The Enforcer slumps to the floor before you, defeated.", EnforcerWarehouse.AFTER_GUARD_COMBAT_VICTORY) {
                    @Override
                    public void effects() {
                        Main.game.getDialogueFlags().warehouseDefeatedIDs.add(EnforcerWarehouseGuard.this.getId());
                        Main.game.getNpc(Claire.class).setLocation(Main.game.getPlayer(), false);
                    }
                };

            } else {
                return new Response("Defeated...", "You collapse to the floor, completely defeated.", EnforcerWarehouse.AFTER_GUARD_COMBAT_DEFEAT);
            }

        } else { // Main entrance:
            if (victory) {
                return new Response("Victory", "The Enforcers slump to the floor before you, defeated.", EnforcerWarehouse.AFTER_ENTRANCE_VICTORY);

            } else {
                return new Response("Defeated...", "You collapse to the floor, completely defeated.", EnforcerWarehouse.AFTER_ENTRANCE_DEFEAT);
            }
        }
    }

    // Sex:

    @Override
    public SexType getForeplayPreference(GameCharacter target) {
        if (Main.sex.getSexManager().getPosition() == SexPosition.STOCKS) {
            if (Main.sex.getSexPositionSlot(this) == SexSlotStocks.BEHIND_STOCKS) {
                if (this.hasPenis()) {
                    if (target.isAbleToAccessCoverableArea(CoverableArea.VAGINA, true) && target.hasVagina()) {
                        return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.VAGINA);
                    } else {
                        return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.ANUS);
                    }
                } else {
                    if (target.isAbleToAccessCoverableArea(CoverableArea.VAGINA, true) && target.hasVagina()) {
                        return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.FINGER, SexAreaOrifice.VAGINA);
                    }
                }

            } else {
                if (this.hasPenis()) {
                    if (target.isAbleToAccessCoverableArea(CoverableArea.MOUTH, true)) {
                        return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH);
                    }
                } else if (this.hasVagina()) {
                    if (target.isAbleToAccessCoverableArea(CoverableArea.MOUTH, true)) {
                        return new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.TONGUE);
                    }
                }
            }
        }

        return super.getForeplayPreference(target);
    }

    @Override
    public SexType getMainSexPreference(GameCharacter target) {
        if (Main.sex.getSexManager().getPosition() == SexPosition.STOCKS) {
            return getForeplayPreference(target);
        }

        return super.getMainSexPreference(target);
    }
}
