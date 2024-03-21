package com.lilithslegacy.game.sex.managers.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.SexPositionUnique;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.game.sex.sexActions.SexActionInterface;
import com.lilithslegacy.game.sex.sexActions.dominion.RalphOral;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.1.6?
 */
public class SexManagerRalphDiscount extends SexManagerDefault {

    public SexManagerRalphDiscount(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(false,
                SexPositionUnique.UNDER_DESK_RALPH,
                dominants,
                submissives);
    }

    @Override
    public SexActionInterface getPartnerSexAction(NPC partner, SexActionInterface sexActionPlayer) {
        if (Main.sex.getAvailableSexActionsPartner().contains(RalphOral.PARTNER_PENETRATES)) {
            return RalphOral.PARTNER_PENETRATES;
        } else if (Main.sex.getAvailableSexActionsPartner().contains(RalphOral.PARTNER_PENETRATES_ANUS)) {
            return RalphOral.PARTNER_PENETRATES_ANUS;
        }

        return super.getPartnerSexAction(partner, sexActionPlayer);
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
        return false;
    }

    @Override
    public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing) {
        return false;
    }

    @Override
    public List<AbstractSexPosition> getAllowedSexPositions() {
        return Util.newArrayListOfValues(
                SexPositionUnique.UNDER_DESK_RALPH);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

}
