package com.lilithslegacy.game.sex.sexActions.submission;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.submission.FortressAlphaLeader;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.inventory.clothing.DisplacementType;
import com.lilithslegacy.game.sex.*;
import com.lilithslegacy.game.sex.positions.slots.SexSlotStanding;
import com.lilithslegacy.game.sex.sexActions.SexAction;
import com.lilithslegacy.game.sex.sexActions.SexActionPriority;
import com.lilithslegacy.game.sex.sexActions.SexActionType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Innoxia
 * @version 0.3.4
 * @since 0.2.11
 */
public class FortressAlphaLeaderSA {

    public static boolean isBothTargetsUsed() {
        try {
            return Main.sex.getSexTypeCount(Main.sex.getCharacterPerformingAction(), Main.sex.getCharacterInPosition(SexSlotStanding.PERFORMING_ORAL), new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH)) > 0
                    && Main.sex.getSexTypeCount(Main.sex.getCharacterPerformingAction(), Main.sex.getCharacterInPosition(SexSlotStanding.PERFORMING_ORAL_TWO), new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH)) > 0;
        } catch (Exception ex) {
            return true;
        }
    }

    public static GameCharacter getBlowjobTarget() {
        return Main.sex.getCharactersHavingOngoingActionWith(Main.game.getNpc(FortressAlphaLeader.class), SexAreaPenetration.PENIS).isEmpty()
                ? null
                : Main.sex.getCharactersHavingOngoingActionWith(Main.game.getNpc(FortressAlphaLeader.class), SexAreaPenetration.PENIS).get(0);
    }

    private static GameCharacter getOtherTarget() {
        try {
            if (getBlowjobTarget() == null) {
                if (Main.sex.getSexTypeCount(Main.sex.getCharacterPerformingAction(), Main.sex.getCharacterInPosition(SexSlotStanding.PERFORMING_ORAL), new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH)) > 0) {
                    return Main.sex.getCharacterInPosition(SexSlotStanding.PERFORMING_ORAL_TWO);
                } else {
                    return Main.sex.getCharacterInPosition(SexSlotStanding.PERFORMING_ORAL);
                }
            }
            return Main.sex.getSexPositionSlot(getBlowjobTarget()) == SexSlotStanding.PERFORMING_ORAL
                    ? Main.sex.getCharacterInPosition(SexSlotStanding.PERFORMING_ORAL_TWO)
                    : Main.sex.getCharacterInPosition(SexSlotStanding.PERFORMING_ORAL);
        } catch (Exception ex) {
            return null;
        }
    }

    public static final SexAction PARTNER_ROUND_TWO_ONGOING = new SexAction(
            SexActionType.ONGOING,
            ArousalIncrease.ONE_MINIMUM,
            ArousalIncrease.ONE_MINIMUM,
            CorruptionLevel.ZERO_PURE,
            Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH)),
            SexParticipantType.NORMAL) {

        @Override
        public String getActionTitle() {
            return "Round two";
        }

        @Override
        public String getActionDescription() {
            return "Tell [npc2.name] that you aren't finished yet!";
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return getOtherTarget() == null
                    && (Main.sex.getLastUsedSexAction(Main.game.getNpc(FortressAlphaLeader.class)).getActionType() == SexActionType.ORGASM
                    || Main.sex.getLastUsedSexAction(Main.game.getNpc(FortressAlphaLeader.class)).getActionType() == SexActionType.PREPARE_FOR_PARTNER_ORGASM)
                    && !isBothTargetsUsed()
                    && Main.sex.getNumberOfOrgasms(Main.sex.getCharacterPerformingAction()) == 1
                    && Main.sex.getCharacterPerformingAction().equals(Main.game.getNpc(FortressAlphaLeader.class));
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public String getDescription() {
            return "Grabbing the back of [npc2.namePos] head, [npc.name] roughly slams [npc.her] hips forwards and hilts [npc.her] [npc.cock+] fully down [npc2.her] throat."
                    + " Letting out a deep groan as [npc.she] feels [npc2.namePos] lips bump up against the base of [npc.her] cock, [npc.she] growls,"
                    + " [npc.speechNoEffects(I'm not finished yet, bitch! Hope you're ready for round two!)]";
        }
    };

    public static final SexAction PARTNER_ROUND_TWO_ONGOING_SWITCH = new SexAction(
            SexActionType.ONGOING,
            ArousalIncrease.ONE_MINIMUM,
            ArousalIncrease.ONE_MINIMUM,
            CorruptionLevel.ZERO_PURE,
            Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH)),
            SexParticipantType.NORMAL) {

        @Override
        public String getActionTitle() {
            return "Round two (switch)";
        }

        @Override
        public String getActionDescription() {
            return UtilText.parse(getOtherTarget(), "Switch to fucking [npc.namePos] face!");
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return getOtherTarget() != null
                    && (Main.sex.getLastUsedSexAction(Main.game.getNpc(FortressAlphaLeader.class)).getActionType() == SexActionType.ORGASM
                    || Main.sex.getLastUsedSexAction(Main.game.getNpc(FortressAlphaLeader.class)).getActionType() == SexActionType.PREPARE_FOR_PARTNER_ORGASM)
                    && !isBothTargetsUsed()
                    && Main.sex.getNumberOfOrgasms(Main.sex.getCharacterPerformingAction()) == 1
                    && Main.sex.getCharacterPerformingAction().equals(Main.game.getNpc(FortressAlphaLeader.class));
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public String getDescription() {
            return UtilText.parse(Util.newArrayListOfValues(Main.sex.getCharacterPerformingAction(), Main.sex.getCharacterTargetedForSexAction(this), getOtherTarget()),
                    "Grabbing the sides of [npc2.namePos] head, [npc.name] pushes [npc2.herHim] back, sliding [npc.her] [npc.cock+] out from [npc2.her] mouth and leaving a thick trail of cummy saliva drooling from [npc2.her] [npc2.lips].<br/>"
                            + "Focusing [npc.her] attention on [npc3.name], [npc.name] pushes the cum-coated tip of [npc.her] [npc.cock+] up against [npc3.her] [npc3.lips+],"
                            + " before roughly slamming [npc.her] hips forwards and hilting it fully down [npc3.her] throat."
                            + " Letting out a deep groan as [npc.she] feels [npc3.namePos] [npc3.lips] bump up against the base of [npc.her] cock, [npc.she] growls,"
                            + " [npc.speechNoEffects(It's your turn, bitch! Hope you're ready for my second load!)]");
        }

        @Override
        public void applyEffects() {
            GameCharacter otherTarget = getOtherTarget();

            Map<AbstractClothing, DisplacementType> clothingTouched = otherTarget.displaceClothingForAccess(CoverableArea.MOUTH, null);
            for (Entry<AbstractClothing, DisplacementType> e : clothingTouched.entrySet()) {
                if (e.getValue() == DisplacementType.REMOVE_OR_EQUIP) {
                    Main.game.getPlayerCell().getInventory().addClothing(e.getKey());
                }
            }

            Main.sex.stopAllOngoingActions(otherTarget, SexAreaOrifice.MOUTH, otherTarget, false);

            Main.sex.stopOngoingAction(
                    Main.sex.getCharacterPerformingAction(),
                    SexAreaPenetration.PENIS,
                    getBlowjobTarget(),
                    SexAreaOrifice.MOUTH);

            Main.sex.stopOngoingAction(
                    otherTarget,
                    SexAreaPenetration.TONGUE,
                    Main.sex.getCharacterPerformingAction(),
                    SexAreaOrifice.VAGINA);

            Main.sex.applyOngoingAction(
                    Main.sex.getCharacterPerformingAction(),
                    SexAreaPenetration.PENIS,
                    otherTarget,
                    SexAreaOrifice.MOUTH,
                    true);
        }
    };

    public static final SexAction PARTNER_ROUND_TWO_START = new SexAction(
            SexActionType.START_ONGOING,
            ArousalIncrease.ONE_MINIMUM,
            ArousalIncrease.ONE_MINIMUM,
            CorruptionLevel.ZERO_PURE,
            Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH)),
            SexParticipantType.NORMAL) {

        @Override
        public String getActionTitle() {
            return "Round two";
        }

        @Override
        public String getActionDescription() {
            return "Tell [npc2.name] that you aren't finished yet!";
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return getOtherTarget() == null
                    && (Main.sex.getLastUsedSexAction(Main.game.getNpc(FortressAlphaLeader.class)).getActionType() == SexActionType.ORGASM
                    || Main.sex.getLastUsedSexAction(Main.game.getNpc(FortressAlphaLeader.class)).getActionType() == SexActionType.PREPARE_FOR_PARTNER_ORGASM)
                    && !isBothTargetsUsed()
                    && Main.sex.getNumberOfOrgasms(Main.sex.getCharacterPerformingAction()) == 1
                    && Main.sex.getCharacterPerformingAction().equals(Main.game.getNpc(FortressAlphaLeader.class));
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public String getDescription() {
            return "Grabbing the back of [npc2.namePos] head, [npc.name] pushes the cum-coated tip of [npc.her] [npc.cock+] up against [npc2.namePos] lips,"
                    + " before roughly slamming [npc.her] hips forwards and hilting it fully down [npc2.her] throat."
                    + " Letting out a deep groan as [npc.she] feels [npc2.namePos] lips bump up against the base of [npc.her] cock, [npc.she] growls,"
                    + " [npc.speechNoEffects(I'm not finished yet, bitch! Hope you're ready for round two!)]";
        }
    };

    public static final SexAction PARTNER_ROUND_TWO_START_SWITCH = new SexAction(
            SexActionType.START_ONGOING,
            ArousalIncrease.ONE_MINIMUM,
            ArousalIncrease.ONE_MINIMUM,
            CorruptionLevel.ZERO_PURE,
            Util.newHashMapOfValues(new Value<>(SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH)),
            SexParticipantType.NORMAL) {

        @Override
        public String getActionTitle() {
            return "Round two (switch)";
        }

        @Override
        public String getActionDescription() {
            return UtilText.parse(getOtherTarget(), "Switch to fucking [npc.namePos] face!");
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return getOtherTarget() != null
                    && (Main.sex.getLastUsedSexAction(Main.game.getNpc(FortressAlphaLeader.class)).getActionType() == SexActionType.ORGASM
                    || Main.sex.getLastUsedSexAction(Main.game.getNpc(FortressAlphaLeader.class)).getActionType() == SexActionType.PREPARE_FOR_PARTNER_ORGASM)
                    && !isBothTargetsUsed()
                    && Main.sex.getNumberOfOrgasms(Main.sex.getCharacterPerformingAction()) == 1
                    && Main.sex.getCharacterPerformingAction().equals(Main.game.getNpc(FortressAlphaLeader.class));
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public String getDescription() {
            return UtilText.parse(Util.newArrayListOfValues(Main.sex.getCharacterPerformingAction(), Main.sex.getCharacterTargetedForSexAction(this), getOtherTarget()),
                    "Focusing [npc.her] attention on [npc3.name], [npc.name] pushes the cum-coated tip of [npc.her] [npc.cock+] up against [npc3.her] [npc3.lips+],"
                            + " before roughly slamming [npc.her] hips forwards and hilting it fully down [npc3.her] throat."
                            + " Letting out a deep groan as [npc.she] feels [npc3.namePos] [npc3.lips] bump up against the base of [npc.her] cock, [npc.she] growls,"
                            + " [npc.speechNoEffects(It's your turn, bitch! Hope you're ready for my second load!)]");
        }

        @Override
        public void applyEffects() {
            GameCharacter otherTarget = getOtherTarget();

            Map<AbstractClothing, DisplacementType> clothingTouched = otherTarget.displaceClothingForAccess(CoverableArea.MOUTH, null);
            for (Entry<AbstractClothing, DisplacementType> e : clothingTouched.entrySet()) {
                if (e.getValue() == DisplacementType.REMOVE_OR_EQUIP) {
                    Main.game.getPlayerCell().getInventory().addClothing(e.getKey());
                }
            }

            Main.sex.stopAllOngoingActions(otherTarget, SexAreaOrifice.MOUTH, otherTarget, false);

            Main.sex.stopOngoingAction(
                    Main.sex.getCharacterPerformingAction(),
                    SexAreaPenetration.PENIS,
                    getBlowjobTarget(),
                    SexAreaOrifice.MOUTH);

            Main.sex.stopOngoingAction(
                    otherTarget,
                    SexAreaPenetration.TONGUE,
                    Main.sex.getCharacterPerformingAction(),
                    SexAreaOrifice.VAGINA);

            Main.sex.applyOngoingAction(
                    Main.sex.getCharacterPerformingAction(),
                    SexAreaPenetration.PENIS,
                    otherTarget,
                    SexAreaOrifice.MOUTH,
                    true);
        }
    };


}
