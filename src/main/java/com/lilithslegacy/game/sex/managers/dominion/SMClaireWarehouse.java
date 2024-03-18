package com.lilithslegacy.game.sex.managers.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.5.1
 * @since 0.3.5
 */
public class SMClaireWarehouse extends SexManagerDefault {

    public SMClaireWarehouse(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.AGAINST_WALL,
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
                SexPosition.AGAINST_WALL);
    }

    @Override
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
        return Main.sex.isSatisfiedFromOrgasms(Main.game.getPlayer(), true) && Main.sex.isSatisfiedFromOrgasms(partner, true);
    }
}
