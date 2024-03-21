package com.lilithslegacy.game.sex.sexActions.submission;

import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.body.types.FluidType;
import com.lilithslegacy.game.character.effects.Addiction;
import com.lilithslegacy.game.character.npc.submission.Roxy;
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.enchanting.ItemEffectType;
import com.lilithslegacy.game.sex.ArousalIncrease;
import com.lilithslegacy.game.sex.SexFlags;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.sexActions.SexAction;
import com.lilithslegacy.game.sex.sexActions.SexActionLimitation;
import com.lilithslegacy.game.sex.sexActions.SexActionPriority;
import com.lilithslegacy.game.sex.sexActions.SexActionType;
import com.lilithslegacy.main.Main;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.2.6
 */
public class SARoxySpecials {

    public static final SexAction ROXY_FACE_SITTING_ORGASM = new SexAction(
            SexActionType.ORGASM,
            ArousalIncrease.FIVE_EXTREME,
            ArousalIncrease.FIVE_EXTREME,
            CorruptionLevel.ZERO_PURE,
            null,
            SexParticipantType.NORMAL) {

        @Override
        public String getActionTitle() {
            return "Orgasm";
        }

        @Override
        public String getActionDescription() {
            return "You've reached your climax, and can't hold back your orgasm any longer.";
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return !Main.sex.getCharacterPerformingAction().isPlayer();
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public String getDescription() {
            Addiction ratGCumAdd = Main.game.getPlayer().getAddiction(FluidType.GIRL_CUM_RAT_MORPH);

            if (ratGCumAdd != null && ratGCumAdd.getProviderIDs().contains(Main.game.getNpc(Roxy.class).getId())) {
                return UtilText.parseFromXMLFile("characters/submission/roxy", "ROXY_FACE_SITTING_ORGASM_ALREADY_ADDICTED");

            } else {
                return UtilText.parseFromXMLFile("characters/submission/roxy", "ROXY_FACE_SITTING_ORGASM_NEW_ADDICTION");
            }
        }

        @Override
        public void applyEffects() {
            Main.game.getDialogueFlags().setFlag(DialogueFlagValue.roxyAddicted, true);
        }

        @Override
        public boolean endsSex() {
            return true;
        }
    };

    public static final SexAction BLOW_SMOKE = new SexAction(
            SexActionType.ONGOING,
            ArousalIncrease.TWO_LOW,
            ArousalIncrease.TWO_LOW,
            CorruptionLevel.ZERO_PURE,
            null,
            SexParticipantType.NORMAL) {

        @Override
        public boolean isBaseRequirementsMet() {
            return !Main.sex.getCharacterPerformingAction().isPlayer();
        }

        @Override
        public String getActionTitle() {
            return "Smoke";
        }

        @Override
        public String getActionDescription() {
            return "";
        }

        @Override
        public String getDescription() {
            return UtilText.parseFromXMLFile("characters/submission/roxy", "BLOW_SMOKE");
        }

        @Override
        public void applyEffects() {
            ItemEffectType.CIGARETTE.applyEffect(null, null, null, 0, Main.game.getNpc(Roxy.class), Main.game.getNpc(Roxy.class), null);
        }
    };

    public static final SexAction FACE_SITTING_TEASE = new SexAction(
            SexActionType.ONGOING,
            ArousalIncrease.TWO_LOW,
            ArousalIncrease.TWO_LOW,
            CorruptionLevel.ZERO_PURE,
            null,
            SexParticipantType.NORMAL) {

        @Override
        public boolean isBaseRequirementsMet() {
            return !Main.sex.getCharacterPerformingAction().isPlayer();
        }

        @Override
        public String getActionTitle() {
            return "Face-sitting tease";
        }

        @Override
        public String getActionDescription() {
            return "";
        }

        @Override
        public String getDescription() {
            UtilText.nodeContentSB.setLength(0);
            if (Math.random() < 0.5f) {
                UtilText.nodeContentSB.append(UtilText.parseFromXMLFile("characters/submission/roxy", "FACE_SITTING_TEASE_START"));
                UtilText.nodeContentSB.append(UtilText.parseFromXMLFile("characters/submission/roxy", "FACE_SITTING_TEASE_END"));
            } else {
                UtilText.nodeContentSB.append(UtilText.parseFromXMLFile("characters/submission/roxy", "FACE_SITTING_ADDICTION_TEASE_START"));
                UtilText.nodeContentSB.append(UtilText.parseFromXMLFile("characters/submission/roxy", "FACE_SITTING_ADDICTION_TEASE_END"));
            }
            return UtilText.nodeContentSB.toString();
        }
    };

    public static final SexAction CUSTOMER_INTERRUPTION = new SexAction(
            SexActionType.ONGOING,
            ArousalIncrease.ONE_MINIMUM,
            ArousalIncrease.ONE_MINIMUM,
            CorruptionLevel.ZERO_PURE,
            null,
            SexParticipantType.NORMAL) {
        @Override
        public SexActionLimitation getLimitation() {
            return SexActionLimitation.NPC_ONLY;
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return (!SexFlags.genericFlags.containsKey("roxyInterruptedTurn") || Main.sex.getTurn() - SexFlags.genericFlags.get("roxyInterruptedTurn") > 10) // Make this very infrequent
                    && Main.sex.getTurn() > 4
                    && Main.game.getPlayer().getArousal() < 75
                    && Main.game.getNpc(Roxy.class).getArousal() < 75
                    && Math.random() < 0.25f;
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public String getActionTitle() {
            return "Customer interruption";
        }

        @Override
        public String getActionDescription() {
            return "";
        }

        @Override
        public String getDescription() {
            return UtilText.parseFromXMLFile("characters/submission/roxy", "CUSTOMER_INTERRUPTION");
        }

        @Override
        public void applyEffects() {
            SexFlags.genericFlags.put("roxyInterruptedTurn", Main.sex.getTurn());
        }
    };
}
