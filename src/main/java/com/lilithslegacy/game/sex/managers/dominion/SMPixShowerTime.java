package com.lilithslegacy.game.sex.managers.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.SexPositionUnique;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.utils.Util;

import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.8.8
 * @since 0.1.69.9
 */
public class SMPixShowerTime extends SexManagerDefault {

    public SMPixShowerTime(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPositionUnique.SHOWER_TIME_PIX,
                dominants,
                submissives);
    }

    @Override
    public boolean isWashingScene() {
        return true;
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
        return false;
    }

    @Override
    public boolean isItemUseAvailable() {
        return false;
    }

    @Override
    public boolean isCharacterStartNaked(GameCharacter character) {
        return true;
    }

    @Override
    public List<AbstractSexPosition> getAllowedSexPositions() {
        return Util.newArrayListOfValues(
                SexPositionUnique.SHOWER_TIME_PIX);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

}
