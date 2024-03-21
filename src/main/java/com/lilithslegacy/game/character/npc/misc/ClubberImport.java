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
import com.lilithslegacy.game.character.persona.PersonalityTrait;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.dialogue.places.dominion.nightlife.NightlifeDistrict;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Innoxia
 * @version 0.4.5.7
 * @since 0.4.5.7
 */
public class ClubberImport extends NPC {

    public ClubberImport() {
        this(false);
    }

    public ClubberImport(boolean isImported) {
        super(isImported, new NameTriplet("Clubber"), "", "-",
                18, Month.JUNE, 10,
                1, Gender.F_V_B_FEMALE, Subspecies.HUMAN, RaceStage.HUMAN,
                new CharacterInventory(0), WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, false);
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
        this.setLastTimeEncountered(DEFAULT_TIME_START_VALUE);

        if (!this.getId().endsWith("ClubberImport")) {
            this.setId(Main.game.getNextNPCId(ClubberImport.class));
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

    public void applyNewlyImportedClubberVariables() {
        this.endPregnancy(false);

        this.washAllOrifices(true);
        this.calculateStatusEffects(0);
        this.cleanAllDirtySlots(true);
        this.cleanAllClothing(true, false);

        this.clearAffectionMap();

        this.getSlavesOwned().clear();

        this.setPlayerKnowsName(true);

        if (NightlifeDistrict.isSearchingForASub()) {
            this.removePersonalityTrait(PersonalityTrait.CONFIDENT);
        } else {
            this.addPersonalityTrait(PersonalityTrait.CONFIDENT);
        }

        this.setLocation(WorldType.EMPTY, PlaceType.GENERIC_CLUB_HOLDING_CELL, true);
    }

    @Override
    public String getDescription() {
        return UtilText.parse(this, "[npc.Name] is a resident of Dominion, who you met in one of the clubs in the city's Nightlife district.");
    }

    @Override
    public boolean isAbleToBeImpregnated() {
        return true;
    }

    @Override
    public boolean isUnique() {
        return false;
    }

//    @Override
//    public Vector2i getLocation() {
//        if(this.getWorldLocation()==WorldType.NIGHTLIFE_CLUB
//                && Main.game.getPlayer().getWorldLocation()==WorldType.NIGHTLIFE_CLUB) {
//            return Main.game.getPlayer().getLocation();
//        }
//        return location;
//    }

    @Override
    public void changeFurryLevel() {
    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }

}
