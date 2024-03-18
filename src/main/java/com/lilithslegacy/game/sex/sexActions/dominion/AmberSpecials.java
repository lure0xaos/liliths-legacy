package com.lilithslegacy.game.sex.sexActions.dominion;

import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.sex.ArousalIncrease;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.sexActions.SexAction;
import com.lilithslegacy.game.sex.sexActions.SexActionPriority;
import com.lilithslegacy.game.sex.sexActions.SexActionType;
import com.lilithslegacy.main.Main;

/**
 * @author Innoxia
 * @version 0.3.4.5
 * @since 0.1.95
 */
public class AmberSpecials {

    public static final SexAction PARTNER_STOP_SEX = new SexAction(
            SexActionType.SPECIAL,
            ArousalIncrease.ONE_MINIMUM,
            ArousalIncrease.ONE_MINIMUM,
            CorruptionLevel.ZERO_PURE,
            null,
            SexParticipantType.SELF) {

        @Override
        public String getActionTitle() {
            return "Finished";
        }

        @Override
        public String getActionDescription() {
            return "";
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return Main.sex.getInitialSexManager().isPartnerWantingToStopSex(Main.sex.getCharacterPerformingAction())
                    && Main.sex.isDom(Main.sex.getCharacterPerformingAction())
                    && !Main.sex.getCharacterPerformingAction().isPlayer();
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.HIGH;
        }

        @Override
        public String getDescription() {
            return "With a satisfied sigh, [npc.name] pulls back, growling,"
                    + " [npc.speech(Good bitch! I think you've learned your lesson!)]";
        }

        @Override
        public boolean endsSex() {
            return true;
        }
    };
}
