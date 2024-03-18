package com.lilithslegacy.game.sex.positions;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.SexActionInteractions;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util.Value;

import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.1
 * @since 0.3.1
 */
public abstract class VariableInteractions {

    private static GameCharacter characterForPositionTesting;

    public static void setCharacterForPositionTesting(GameCharacter characterForPositionTesting) {
        VariableInteractions.characterForPositionTesting = characterForPositionTesting;
    }

    protected static GameCharacter getCharacter(SexSlot slot) {
        GameCharacter performer = Main.sex.getCharacterInPosition(slot);
        if (performer == null) {
            performer = characterForPositionTesting == null ? Main.game.getPlayer() : characterForPositionTesting;
        }
        return performer;
    }

    public abstract Value<SexSlot, Map<SexSlot, SexActionInteractions>> getSexActionInteractions(SexSlot performerSlot, SexSlot targetSlot);

}
