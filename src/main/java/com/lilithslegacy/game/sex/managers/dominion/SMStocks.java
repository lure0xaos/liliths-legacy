package com.lilithslegacy.game.sex.managers.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.game.sex.positions.slots.SexSlotStocks;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.4
 * @since 0.1.95
 */
public class SMStocks extends SexManagerDefault {

    public SMStocks(boolean vaginalAllowed, boolean analAllowed, boolean oralAllowed, Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.STOCKS,
                dominants,
                submissives);

        for (GameCharacter character : submissives.keySet()) {
            getAreasBannedMap().put(character, new ArrayList<>());
        }

        if (!vaginalAllowed) {
            for (GameCharacter character : submissives.keySet()) {
                getAreasBannedMap().get(character).add(SexAreaOrifice.VAGINA);
            }
        }

        if (!analAllowed) {
            for (GameCharacter character : submissives.keySet()) {
                getAreasBannedMap().get(character).add(SexAreaOrifice.ANUS);
            }
        }

        if (!oralAllowed) {
            for (GameCharacter character : submissives.keySet()) {
                getAreasBannedMap().get(character).add(SexAreaOrifice.MOUTH);
            }
        }
    }

    @Override
    public boolean isAbleToRemoveSelfClothing(GameCharacter character) {
        return getDominants().containsKey(character);
    }

    @Override
    public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing) {
        return getDominants().containsKey(character);
    }

    @Override
    public List<AbstractSexPosition> getAllowedSexPositions() {
        return Util.newArrayListOfValues(
                SexPosition.STOCKS);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        if (Main.sex.getSexPositionSlot(character) == SexSlotStocks.LOCKED_IN_STOCKS
                || Main.sex.getSexPositionSlot(character) == SexSlotStocks.LOCKED_IN_STOCKS_TWO
                || Main.sex.getSexPositionSlot(character) == SexSlotStocks.LOCKED_IN_STOCKS_THREE
                || Main.sex.getSexPositionSlot(character) == SexSlotStocks.LOCKED_IN_STOCKS_FOUR) {
            return SexControl.NONE;
        }
        return super.getSexControl(character);
    }
}
