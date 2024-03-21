package com.lilithslegacy.game.sex.managers.universal;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;

import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.4
 * @since 0.1.69
 */
public class SMAllFours extends SexManagerDefault {

    public SMAllFours(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.ALL_FOURS,
                dominants,
                submissives);
    }

}
