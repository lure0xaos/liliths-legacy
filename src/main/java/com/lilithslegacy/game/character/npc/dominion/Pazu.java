package com.lilithslegacy.game.character.npc.dominion;

import java.time.Month;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.lilithslegacy.game.PropertyValue;
import com.lilithslegacy.game.character.CharacterImportSetting;
import com.lilithslegacy.game.character.EquipClothingSetting;
import com.lilithslegacy.game.character.attributes.Attribute;
import com.lilithslegacy.game.character.body.coverings.BodyCoveringType;
import com.lilithslegacy.game.character.body.coverings.Covering;
import com.lilithslegacy.game.character.body.valueEnums.CupSize;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.gender.Gender;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.persona.NameTriplet;
import com.lilithslegacy.game.character.persona.SexualOrientation;
import com.lilithslegacy.game.character.race.RaceStage;
import com.lilithslegacy.game.character.race.Subspecies;
import com.lilithslegacy.game.dialogue.DialogueNode;
import com.lilithslegacy.game.inventory.CharacterInventory;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;
import com.lilithslegacy.world.places.PlaceType;

/**
 * @author Kumiko
 * @version 0.2.11
 * @since 0.1.79
 */
public class Pazu extends NPC {

    public Pazu() {
        this(false);
    }

    public Pazu(boolean isImported) {
        super(isImported, new NameTriplet("Pazu"), "Pazu", //TODO
                "Pazu is a harpy matriarch, and a particularly gorgeous one at that. She is new to the job and needs your help in whipping her flock into shape.",
                /* TODO (Once quest advances)
                 *  Pazu is a harpy matriarch, and a particularly gorgeous one at that. Despite this, he is actually a male harpy, a fact that he keeps hidden from everyone else for obvious reasons.
                 *  He has a friendly relationship with you, so you can visit his nest at any time*
                 * TODO (Once lover)
                 *  Pazu is a beautiful male harpy, and also your boyfriend. Despite being an ex-matriarch, he can act rather shy and bashful, and is still rather naïve.
                 *  He adores with all his heart, but due to this, he's not keen on sharing you with anybody else.
                 * TODO ( If he opens his candy shop and you're not his lover)
                 *  Pazu is a beautiful male harpy, and the owner of a candy shop. He used to be a harpy matriarch, but left the oppressing nests in search of a simpler life.
                 *  (if he opens the shop and is still your lover, his description is the same but with, "He also owns a candy shop in the shopping arcade." at the end)
                 */
                25, Month.JUNE, 1, //TODO
                1, Gender.M_P_MALE, Subspecies.HARPY, RaceStage.LESSER,
                new CharacterInventory(1), WorldType.EMPTY, PlaceType.GENERIC_HOLDING_CELL, true);

        if (!isImported) {
            this.setSexualOrientation(SexualOrientation.AMBIPHILIC);

            this.setEyeCovering(new Covering(BodyCoveringType.EYE_HARPY, PresetColour.EYE_PINK));
            this.setHairCovering(new Covering(BodyCoveringType.HAIR_HARPY, PresetColour.COVERING_LILAC), true);
            this.setSkinCovering(new Covering(BodyCoveringType.FEATHERS, PresetColour.COVERING_LILAC), true);
            this.setSkinCovering(new Covering(BodyCoveringType.HUMAN, PresetColour.SKIN_LIGHT), true);

            this.setAssVirgin(true);
            this.setFaceVirgin(true);

            this.setBreastSize(CupSize.FLAT.getMeasurement());

            this.setPenisSize(18);

            this.setHeight(185);

            this.setFemininity(80);

            this.setAttribute(Attribute.MAJOR_PHYSIQUE, 4);
            this.setAttribute(Attribute.MAJOR_ARCANE, 45);
            this.setAttribute(Attribute.MAJOR_CORRUPTION, 5);

            this.addFetish(Fetish.FETISH_ORAL_RECEIVING);
            this.addFetish(Fetish.FETISH_ORAL_GIVING);

            this.equipClothingFromNowhere(Main.game.getItemGen().generateClothing("innoxia_leg_shorts", PresetColour.CLOTHING_WHITE, false), true, this);
        }
    }

    @Override
    public void loadFromXML(Element parentElement, Document doc, CharacterImportSetting... settings) {
        loadNPCVariablesFromXML(this, null, parentElement, doc, settings);
    }

    @Override
    public void setStartingBody(boolean setPersona) {
        // TODO
    }

    @Override
    public void equipClothing(List<EquipClothingSetting> settings) {
        // TODO
    }

    @Override
    public boolean isUnique() {
        return true;
    }

    @Override
    public String getSpeechColour() {
        if (Main.getProperties().hasValue(PropertyValue.lightTheme)) {
            return "#7000FA";
        } else {
            return "#C18FFF";
        }
    }

    @Override
    public void changeFurryLevel() {
    }

    @Override
    public DialogueNode getEncounterDialogue() {
        return null;
    }


}
