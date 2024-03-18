package com.lilithslegacy.game.sex.managers.submission;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.submission.Roxy;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.2.6
 */
public class SMRoxyPussyLicker extends SexManagerDefault {

    public SMRoxyPussyLicker(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.LYING_DOWN,
                dominants,
                submissives);
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        Map<GameCharacter, List<CoverableArea>> map = new HashMap<>();
        map.put(Main.game.getNpc(Roxy.class), Util.newArrayListOfValues(CoverableArea.VAGINA));
        return map;
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        if (character.isPlayer()) {
            return SexControl.ONGOING_ONLY;
        }
        return super.getSexControl(character);
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
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
        return Main.sex.getNumberOfOrgasms(Main.game.getNpc(Roxy.class)) >= 1;
    }

    @Override
    public SexType getForeplayPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (!character.isPlayer()) {
            return new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.TONGUE);
        }
        return super.getForeplayPreference(character, targetedCharacter);
    }

    @Override
    public SexType getMainSexPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (!character.isPlayer()) {
            return getForeplayPreference(character, targetedCharacter);
        }
        return super.getMainSexPreference(character, targetedCharacter);
    }

    @Override
    public List<CoverableArea> getAdditionalAreasToExposeDuringSex(GameCharacter performer, GameCharacter target) {
        return new ArrayList<>();
    }
}
