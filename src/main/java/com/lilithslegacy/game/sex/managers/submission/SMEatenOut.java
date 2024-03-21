package com.lilithslegacy.game.sex.managers.submission;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.3.5.5
 */
public class SMEatenOut extends SexManagerDefault {

    public SMEatenOut(AbstractSexPosition position, Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(position,
                dominants,
                submissives);
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        Map<GameCharacter, List<CoverableArea>> map = new HashMap<>();

        for (GameCharacter dom : this.getDominants().keySet()) {
            map.put(dom, Util.newArrayListOfValues(CoverableArea.MOUTH));
        }

        for (GameCharacter sub : this.getSubmissives().keySet()) {
            map.put(sub, Util.newArrayListOfValues(CoverableArea.VAGINA));
        }

        return map;
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
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
    public SexType getForeplayPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (Main.sex.isDom(character)) {
            return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.TONGUE, SexAreaOrifice.VAGINA);
        }
        return character.getForeplayPreference(targetedCharacter);
    }

    @Override
    public SexType getMainSexPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (Main.sex.isDom(character)) {
            return character.getForeplayPreference(targetedCharacter);
        }
        return character.getMainSexPreference(targetedCharacter);
    }
}
