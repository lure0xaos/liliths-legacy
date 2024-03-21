package com.lilithslegacy.game.character.npc.misc;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.NameTriplet;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.2.10
 * @since 0.1.79
 */
public class GenericAndrogynousNPC extends NPC {

    public GenericAndrogynousNPC() {
        this(false);
    }

    public GenericAndrogynousNPC(boolean isImported) {
        super(isImported, new NameTriplet("Someone"), null, "Generic androgynous.",
                25, Month.JUNE, 15,
                1, Gender.N_P_V_HERMAPHRODITE, Subspecies.HUMAN, RaceStage.HUMAN,
                new CharacterInventory(0), WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, true);

        this.setFemininity(50);
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);

        this.setName(new NameTriplet("Someone"));
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Not needed
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        // Not needed
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


}
