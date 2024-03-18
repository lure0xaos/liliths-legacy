package com.lilithslegacy.game.sex.managers.dominion.cultist;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPositionUnique;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;

import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.4
 * @since 0.1.69
 */
public class SMCultistKneeling extends SexManagerDefault {

    public SMCultistKneeling(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPositionUnique.KNEELING_ORAL_CULTIST,
                dominants,
                submissives);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
        return false;
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        if (!Main.sex.isDom(character) && character.isPlayer()) {
            return SexControl.ONGOING_ONLY;
        }
        return super.getSexControl(character);
    }
}
