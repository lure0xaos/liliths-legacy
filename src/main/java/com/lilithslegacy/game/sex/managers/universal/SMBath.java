package com.lilithslegacy.game.sex.managers.universal;

import com.lilithslegacy.game.PropertyValue;
import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.LubricationType;
import com.lilithslegacy.game.sex.SexAreaInterface;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.game.sex.positions.slots.SexSlotTag;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Innoxia
 * @version 0.4.1
 * @since 0.3.8.8
 */
public class SMBath extends SexManagerDefault {


    public SMBath(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        this(SexPosition.LYING_DOWN, dominants, submissives);
    }

    /**
     * @param startingPosition Need to be either SexPosition.LYING_DOWN, SexPosition.SITTING, or SexPosition.ALL_FOURS.
     */
    public SMBath(AbstractSexPosition startingPosition, Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(startingPosition,
                dominants,
                submissives);
        if (startingPosition != SexPosition.LYING_DOWN
                && startingPosition != SexPosition.SITTING
                && startingPosition != SexPosition.ALL_FOURS) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String getSexTitle() {
        return (!Main.sex.isConsensual() && Main.getProperties().hasValue(PropertyValue.nonConContent) ? "Non-consensual " : "")
                + (Main.sex.isPublicSex() ? "Public " : "")
                + (Main.sex.getAllParticipants(false).size() > 1 ? "Sex: " : "Masturbation: ")
                + " Bath";
    }

    @Override
    public boolean isSlotAvailable(GameCharacter character, SexSlot slot) {
        return !slot.hasTag(SexSlotTag.FACE_SITTING)
                && !slot.hasTag(SexSlotTag.FACE_SITTING_REVERSE)
                && !slot.hasTag(SexSlotTag.LAP_PILLOW)
                && !slot.hasTag(SexSlotTag.MATING_PRESS)
                && !slot.hasTag(SexSlotTag.SIXTY_NINE)
                && !slot.hasTag(SexSlotTag.MISSIONARY_ORAL)
//				&& !slot.hasTag(SexSlotTag.SITTING_BETWEEN_LEGS)
                && !slot.hasTag(SexSlotTag.SITTING_PERFORMING_ORAL)
//				&& !slot.hasTag(SexSlotTag.SITTING_TAUR_PRESENTING_ORAL)
                ;
    }

    @Override
    public List<AbstractSexPosition> getAllowedSexPositions() {

        return Util.newArrayListOfValues(
                SexPosition.LYING_DOWN,
                SexPosition.SITTING,
                SexPosition.ALL_FOURS);
    }

    @Override
    public boolean isCharacterStartNaked(GameCharacter character) {
        return true;
    }

    @Override
    public boolean isWashingScene() {
        return true;
    }

    @Override
    public Map<GameCharacter, Map<SexAreaInterface, Map<GameCharacter, Set<LubricationType>>>> getStartingWetAreas() {
        Map<GameCharacter, Map<SexAreaInterface, Map<GameCharacter, Set<LubricationType>>>> map = new HashMap<>();

        List<GameCharacter> allCharacters = new ArrayList<>(this.getDominants().keySet());
        allCharacters.addAll(this.getSubmissives().keySet());

        for (GameCharacter character : allCharacters) {
            map.put(character, new HashMap<>());
            for (SexAreaPenetration penetration : SexAreaPenetration.values()) {
                map.get(character).put(penetration, Util.newHashMapOfValues(new Value<>(null, Util.newHashSetOfValues(LubricationType.WATER))));
            }
            for (SexAreaOrifice orifice : SexAreaOrifice.values()) {
                if (!orifice.isInternalOrifice()
                        || orifice == SexAreaOrifice.NIPPLE
                        || orifice == SexAreaOrifice.NIPPLE_CROTCH) {
                    map.get(character).put(orifice, Util.newHashMapOfValues(new Value<>(null, Util.newHashSetOfValues(LubricationType.WATER))));
                }
            }
        }

        return map;
    }
}
