package com.lilithslegacy.game.sex.managers.fields;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.submission.DarkSiren;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.*;
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
 * @version 0.4.1
 * @since 0.4.1
 */
public class SMMeraxisMasturbation extends SexManagerDefault {

    public SMMeraxisMasturbation(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.STANDING,
                dominants,
                submissives);
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        Map<GameCharacter, List<CoverableArea>> map = new HashMap<>();
        map.put(Main.game.getNpc(DarkSiren.class), Util.newArrayListOfValues(CoverableArea.NIPPLES));
        return map;
    }

    @Override
    public boolean isExposeAtStartOfSexMapRemoval(GameCharacter character) {
        return character.equals(Main.game.getNpc(DarkSiren.class));
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        return SexControl.SELF;
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
        return true;
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
        return Main.sex.getNumberOfOrgasms(Main.game.getPlayer()) >= 1;
    }

    @Override
    public SexType getForeplayPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (!character.isPlayer()) {
            return new SexType(SexParticipantType.SELF, SexAreaPenetration.FINGER, SexAreaOrifice.BREAST);
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
