package com.lilithslegacy.game.sex.managers.universal;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.2.5
 */
public class SMMasturbation extends SexManagerDefault {

    public SMMasturbation(Map<GameCharacter, SexSlot> dominants) {
        super(true,
                SexPosition.MASTURBATION,
                dominants,
                new HashMap<>());
    }

    @Override
    public List<AbstractSexPosition> getAllowedSexPositions() {
        return Util.newArrayListOfValues(
                SexPosition.MASTURBATION);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

}
