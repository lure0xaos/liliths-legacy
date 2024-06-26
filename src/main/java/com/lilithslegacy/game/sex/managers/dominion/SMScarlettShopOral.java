package com.lilithslegacy.game.sex.managers.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.dominion.Scarlett;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.*;
import com.lilithslegacy.game.sex.managers.OrgasmBehaviour;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlotStanding;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.7.1
 * @since 0.3.7.1
 */
public class SMScarlettShopOral extends SexManagerDefault {

    public SMScarlettShopOral() {
        super(SexPosition.STANDING,
                Util.newHashMapOfValues(new Value<>(Main.game.getNpc(Scarlett.class), SexSlotStanding.STANDING_DOMINANT)),
                Util.newHashMapOfValues(new Value<>(Main.game.getPlayer(), SexSlotStanding.PERFORMING_ORAL)));
    }

    @Override
    public boolean isPublicSex() {
        return false;
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        Map<GameCharacter, List<CoverableArea>> map = new HashMap<>();
        map.put(Main.game.getNpc(Scarlett.class), Util.newArrayListOfValues(CoverableArea.VAGINA));
        map.put(Main.game.getPlayer(), Util.newArrayListOfValues(CoverableArea.MOUTH));
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
        return Main.sex.getNumberOfOrgasms(Main.game.getNpc(Scarlett.class)) >= 1;
    }

    @Override
    public SexType getForeplayPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (!character.isPlayer()) {
            if (character.hasPenis()) {
                return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH);
            } else {
                return new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.TONGUE);
            }
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

    @Override
    public OrgasmBehaviour getCharacterOrgasmBehaviour(GameCharacter character) {
        return OrgasmBehaviour.CREAMPIE;
    }
}
