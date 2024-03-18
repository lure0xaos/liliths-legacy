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
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.4
 */
public class LodgerImport extends NPC {

    public LodgerImport() {
        this(false);
    }

    public LodgerImport(boolean isImported) {
        super(isImported, new NameTriplet("Lodger"), "", "-",
                18, Month.JUNE, 10,
                1, Gender.F_V_B_FEMALE, Subspecies.HUMAN, RaceStage.HUMAN,
                new CharacterInventory(0), WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
        this.setLastTimeEncountered(DEFAULT_TIME_START_VALUE);

        if (!this.getId().endsWith("LodgerImport")) {
            this.setId(Main.game.getNextNPCId(LodgerImport.class));
        }
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // Not needed
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        // Not needed
    }

    public void applyNewlyImportedLodgerVariables() {
        this.endPregnancy(false);

        this.washAllOrifices(true);
        this.calculateStatusEffects(0);
        this.cleanAllDirtySlots(true);
        this.cleanAllClothing(true, false);

        this.clearAffectionMap();

        this.getSlavesOwned().clear();

        this.setPlayerKnowsName(true);

        this.setLocation(WorldType.CITY_HALL, PlaceType.CITY_HALL_WAITING_AREA, true);
    }

    @Override
    public String getDescription() {
        return UtilText.parse(this, "You first met [npc.name] in Dominion's city hall, where [npc.she] was waiting for someone to offer [npc.herHim] lodgings...");
    }

    @Override
    public boolean isAbleToBeImpregnated() {
        return true;
    }

    @Override
    public boolean isUnique() {
        return false;
    }

    @Override
    public void changeFurryLevel() {
    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }

}
