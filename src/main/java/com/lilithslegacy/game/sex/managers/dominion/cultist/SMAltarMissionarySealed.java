package com.lilithslegacy.game.sex.managers.dominion.cultist;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPositionUnique;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;

import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.1.88
 */
public class SMAltarMissionarySealed extends SexManagerDefault {

    public SMAltarMissionarySealed(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(false,
                SexPositionUnique.MISSIONARY_ALTAR_SEALED_CULTIST,
                dominants,
                submissives);
    }

    @Override
    public boolean isAbleToRemoveSelfClothing(GameCharacter character) {
        return !Main.sex.isCharacterImmobilised(character);
    }

    @Override
    public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing) {
        return !Main.sex.isCharacterImmobilised(character);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
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
