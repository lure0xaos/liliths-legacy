package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.Game;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.NameTriplet;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.companions.SlaveDialogue;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.game.inventory.InventorySlot;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util.Value;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.3.7
 * @since 0.1.83
 */
public class TestNPC extends NPC {

    public TestNPC() {
        this(false);
    }

    public TestNPC(boolean isImported) {
        super(isImported, new NameTriplet("TestNPC"), "Scriven",
                "Test NPC.",
                28, Month.JUNE, 1,
                1, Gender.F_V_B_FEMALE, Subspecies.CAT_MORPH, RaceStage.PARTIAL_FULL,
                new CharacterInventory(10), WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, true);

        if (!isImported) {
        }

        this.setEnslavementDialogue(SlaveDialogue.DEFAULT_ENSLAVEMENT_DIALOGUE, true);
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
        if (Main.isVersionOlderThan(Game.loadingVersion, "0.3.5.1")) {
        }
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        if (setPersona) {
            this.setPersonalityTraits();
        }
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
    }


    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public void changeFurryLevel() {
    }

    @Override
    public boolean isClothingStealable() {
        return true;
    }

    @Override
    public Value<Boolean, String> isInventoryEquipAllowed(AbstractClothing clothing, InventorySlot slotToEquipTo) {
        return new Value<>(true, "");
    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }

    @Override
    public void endSex() {
        if (!isSlave()) {
            setPendingClothingDressing(true);
        }
    }
}
