package com.lilithslegacy.game.sex.managers.universal;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;

import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.3.5.5
 */
public class SMOverDesk extends SexManagerDefault {

    public SMOverDesk(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.OVER_DESK,
                dominants,
                submissives);
    }


}
