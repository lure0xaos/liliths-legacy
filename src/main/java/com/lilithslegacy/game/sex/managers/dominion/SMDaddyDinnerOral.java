package com.lilithslegacy.game.sex.managers.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.dominion.Daddy;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.3.10
 * @since 0.3.3.10
 */
public class SMDaddyDinnerOral extends SexManagerDefault {

    public SMDaddyDinnerOral(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.SITTING,
                dominants,
                submissives);
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        Map<GameCharacter, List<CoverableArea>> map = new HashMap<>();
        map.put(Main.game.getNpc(Daddy.class),
                Util.newArrayListOfValues(CoverableArea.PENIS));
        return map;
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        if (!Main.sex.isDom(character)) {
            return SexControl.ONGOING_ONLY;
        }
        return super.getSexControl(character);
    }

    @Override
    public boolean isPublicSex() {
        return false;
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isAbleToEquipSexClothing(GameCharacter equippingCharacter, GameCharacter targetedCharacter, AbstractClothing clothingToEquip) {
        return clothingToEquip.isCondom();
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
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
        return (partner instanceof Daddy) && Main.sex.getNumberOfOrgasms(partner) >= 1;
    }

    @Override
    public boolean isAppendStartingExposedDescriptions(GameCharacter character) {
        return false;
    }
}
