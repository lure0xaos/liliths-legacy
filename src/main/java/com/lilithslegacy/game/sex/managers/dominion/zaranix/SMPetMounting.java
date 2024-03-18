package com.lilithslegacy.game.sex.managers.dominion.zaranix;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPositionUnique;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;

import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.4
 * @since 0.2.3
 */
public class SMPetMounting extends SexManagerDefault {

    public SMPetMounting(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPositionUnique.PET_MOUNTING,
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
    public boolean isPartnerUsingForeplayActions() {
        return false;
    }

    @Override
    public boolean isPublicSex() {
        return true;
    }

    @Override
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
        return Main.sex.getNumberOfOrgasms(partner) >= 1;
    }

}
