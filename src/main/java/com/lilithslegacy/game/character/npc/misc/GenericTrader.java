package com.lilithslegacy.game.character.npc.misc;

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
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.NameTriplet;
import com.lilithslegacy.game.character.race.AbstractSubspecies;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.inventory.AbstractCoreItem;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.1
 * @since 0.4.1
 */
public class GenericTrader extends NPC {

    public GenericTrader() {
        this(false);
    }

    public GenericTrader(boolean isImported) {
        super(isImported, new NameTriplet("Someone"), null, "A trader.",
                25, Month.JUNE, 15,
                1, Gender.N_P_V_HERMAPHRODITE, Subspecies.HUMAN, RaceStage.HUMAN,
                new CharacterInventory(0), WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);

        this.setFemininity(50);
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        this.setRaceConcealed(true);
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        // Not needed
    }

    @Override
    public Map<InventorySlot, List<AbstractClothing>> getInventorySlotsConcealed(GameCharacter characterViewing) {
        Map<InventorySlot, List<AbstractClothing>> concealed = new HashMap<>();

        for (InventorySlot slot : InventorySlot.values()) {
            concealed.put(slot, new ArrayList<>());
        }

        return concealed;
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

    // Trade:

    @Override
    public boolean willBuy(AbstractCoreItem item) {
        return false;
    }

    @Override
    public boolean isTrader() {
        return true;
    }

    /**
     * Resets this character's body and completely clears inventory.
     */
    public void initTrader(Gender gender, AbstractSubspecies subspecies, RaceStage stage) {
        this.setBody(gender, subspecies, stage, false);
        this.unequipAllClothingIntoVoid(true, true);
        clearNonEquippedInventory(false);
    }

}
