package com.lilithslegacy.game.sex.managers.submission;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.quests.Quest;
import com.lilithslegacy.game.character.quests.QuestLine;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.game.sex.SexPace;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;

import java.util.Map;

/**
 * @author Innoxia
 * @version 0.4.1
 * @since 0.3
 */
public class SMLyssiethSex extends SexManagerDefault {

    public SMLyssiethSex(AbstractSexPosition position, Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(position,
                dominants,
                submissives);
    }

    @Override
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
        if (!Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_2_D_MEETING_A_LILIN)) {
            return false; // Lyssieth orgasm ends sex, so this is fine
        }
        return super.isPartnerWantingToStopSex(partner);
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
        return Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_2_D_MEETING_A_LILIN);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return true;
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return true;
    }

    @Override
    public SexPace getForcedSexPace(GameCharacter character) {
        if (!character.isPlayer()) {
            if (Main.game.getPlayer().getRace() == Race.HUMAN) {
                if (Main.sex.isDom(character)) {
                    return SexPace.DOM_NORMAL;
                } else {
                    return SexPace.SUB_EAGER;
                }

            } else {
                if (Main.sex.isDom(character)) {
                    return SexPace.DOM_NORMAL;
                } else {
                    return SexPace.SUB_NORMAL;
                }
            }
        }
        return null;
    }
}
