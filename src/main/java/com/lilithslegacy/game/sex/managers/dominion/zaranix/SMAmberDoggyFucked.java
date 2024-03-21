package com.lilithslegacy.game.sex.managers.dominion.zaranix;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;

import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.4
 * @since 0.1.95
 */
public class SMAmberDoggyFucked extends SexManagerDefault {

    public SMAmberDoggyFucked(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.ALL_FOURS,
                dominants,
                submissives);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

//    @Override
//    public boolean isPositionChangingAllowed(GameCharacter character) {
//        return false;
//    }
}
