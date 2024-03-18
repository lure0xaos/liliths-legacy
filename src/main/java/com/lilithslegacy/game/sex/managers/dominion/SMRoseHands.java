package com.lilithslegacy.game.sex.managers.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.SexPositionUnique;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.utils.Util;

import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.1.7?
 */
public class SMRoseHands extends SexManagerDefault {

    public SMRoseHands(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(false,
                SexPositionUnique.HANDS_ROSE,
                dominants,
                submissives);
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
        return true;
    }

    @Override
    public boolean isItemUseAvailable() {
        return false;
    }

    @Override
    public boolean isAbleToRemoveSelfClothing(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing) {
        return false;
    }

    @Override
    public List<AbstractSexPosition> getAllowedSexPositions() {
        return Util.newArrayListOfValues(
                SexPositionUnique.HANDS_ROSE);
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

}
