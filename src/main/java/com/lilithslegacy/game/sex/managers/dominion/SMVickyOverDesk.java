package com.lilithslegacy.game.sex.managers.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.utils.Util;

import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.8.6
 * @since 0.1.97
 */
public class SMVickyOverDesk extends SexManagerDefault {

    public SMVickyOverDesk(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.OVER_DESK,
                dominants,
                submissives);
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
        return false;
    }

    @Override
    public List<AbstractSexPosition> getAllowedSexPositions() {
        return Util.newArrayListOfValues(
                SexPosition.OVER_DESK);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return false;
    }
}
