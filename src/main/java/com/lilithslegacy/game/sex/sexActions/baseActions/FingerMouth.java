package com.lilithslegacy.game.sex.sexActions.baseActions;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.body.valueEnums.HairLength;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.sex.*;
import com.lilithslegacy.game.sex.sexActions.SexAction;
import com.lilithslegacy.game.sex.sexActions.SexActionType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.List;

/**
 * @author Innoxia
 * @version 0.3.4
 * @since 0.3.2
 */
public class FingerMouth {

    // The world isn't ready for finger sucking just yet

//    public static final SexAction FINGER_MOUTH_PENETRATION = new SexAction(
//            SexActionType.START_ONGOING,
//            ArousalIncrease.ZERO_NONE,
//            ArousalIncrease.ONE_MINIMUM,
//            CorruptionLevel.ZERO_PURE,
//            Util.newHashMapOfValues(new Value<>(SexAreaPenetration.FINGER, SexAreaOrifice.MOUTH)),
//            SexParticipantType.NORMAL) {
//		@Override
//		public String getActionTitle() {
//			return "Finger sucking";
//		}
//
//		@Override
//		public String getActionDescription() {
//			return "Push your [npc.fingers] into [npc2.namePos] mouth.";
//		}
//
//		@Override
//		public String getDescription() {
//			return "Lifting [npc.her] [npc.hand] up to [npc2.namePos] mouth, [npc.name] [npc.verb(slide)] [npc.her] [npc.fingers] past [npc2.her] [npc2.lips+], forcing [npc2.herHim] to start lewdly sucking on [npc.her] intruding digits.";
//		}
//	};
//
//	public static final SexAction FINGER_MOUTH_STOP_PENETRATION = new SexAction(
//			SexActionType.STOP_ONGOING,
//			ArousalIncrease.ONE_MINIMUM,
//			ArousalIncrease.ONE_MINIMUM,
//			CorruptionLevel.ZERO_PURE,
//			Util.newHashMapOfValues(new Value<>(SexAreaPenetration.FINGER, SexAreaOrifice.MOUTH)),
//			SexParticipantType.NORMAL) {
//		@Override
//		public String getActionTitle() {
//			return "Stop finger sucking";
//		}
//
//		@Override
//		public String getActionDescription() {
//			return "Pull your [npc.fingers] out of [npc2.namePos] mouth.";
//		}
//
//		@Override
//		public String getDescription() {
//			return "With a little sigh, [npc.name] [npc.verb(slide)] [npc.her] saliva-coated [npc.fingers] out of [npc2.namePos] mouth.";
//		}
//	};


    public static final SexAction PARTNER_ASSIST_BLOWJOB = new SexAction(
            SexActionType.ONGOING,
            ArousalIncrease.ONE_MINIMUM,
            ArousalIncrease.THREE_NORMAL,
            CorruptionLevel.ONE_VANILLA,
            null,
            SexParticipantType.NORMAL) {

        @Override
        public void applyEffects() {
            Main.sex.setPrimaryOngoingCharacter(Main.sex.getCharacterTargetedForSexAction(this), getBlowjobReceiver(), SexAreaPenetration.PENIS);
        }

        private GameCharacter getBlowjobReceiver() {
            List<GameCharacter> characters = Main.sex.getCharactersHavingOngoingActionWith(Main.sex.getCharacterTargetedForSexAction(this), SexAreaOrifice.MOUTH);
            if (characters.isEmpty()) {
                return null;
            }
            return Main.sex.getCharactersHavingOngoingActionWith(Main.sex.getCharacterTargetedForSexAction(this), SexAreaOrifice.MOUTH).get(0);
        }

        @Override
        public boolean isBaseRequirementsMet() {
            boolean mouthFinger = false;
            boolean mouthFingerReversed = false;
            try {
                mouthFinger = Main.sex.getPosition().getSlotTargets().get(Main.sex.getSexPositionSlot(Main.sex.getCharacterPerformingAction())).get(Main.sex.getSexPositionSlot(Main.sex.getCharacterTargetedForSexAction(this)))
                        .getInteractions().get(SexAreaPenetration.FINGER).contains(SexAreaOrifice.MOUTH);
            } catch (Exception ex) {
                // No available finger-mouth actions, so can't reach face
            }
            try {
                mouthFingerReversed = Main.sex.getPosition().getSlotTargets().get(Main.sex.getSexPositionSlot(Main.sex.getCharacterTargetedForSexAction(this))).get(Main.sex.getSexPositionSlot(Main.sex.getCharacterPerformingAction()))
                        .getInteractions().get(SexAreaOrifice.MOUTH).contains(SexAreaPenetration.FINGER);
            } catch (Exception ex) {
                // No available finger-mouth actions, so can't reach face
            }

            boolean available = getBlowjobReceiver() != null
                    && getBlowjobReceiver() != Main.sex.getCharacterPerformingAction()
                    && (mouthFinger || mouthFingerReversed)
                    && Main.sex.getFirstOngoingSexAreaPenetration(Main.sex.getCharacterTargetedForSexAction(this), SexAreaOrifice.MOUTH) == SexAreaPenetration.PENIS
                    && Main.sex.getSexPace(Main.sex.getCharacterPerformingAction()) != SexPace.SUB_RESISTING;

//			System.out.println(Main.sex.getCharacterPerformingAction().getName()+" "+Main.sex.getCharacterTargetedForSexAction(this).getName()+" "+available);

            return available;
        }

        @Override
        public String getActionTitle() {
            return "Assist blowjob";
        }

        @Override
        public String getActionDescription() {
            return "Assist [npc2.namePos] efforts in giving " + (UtilText.parse(getBlowjobReceiver(), "[npc.name]")) + " a blowjob.";
        }

        @Override
        public String getDescription() {
            if (Main.sex.getSexPace(Main.sex.getCharacterPerformingAction()) == SexPace.DOM_ROUGH) {
                return UtilText.parse(
                        Util.newArrayListOfValues(
                                Main.sex.getCharacterPerformingAction(),
                                Main.sex.getCharacterTargetedForSexAction(this),
                                getBlowjobReceiver()),
                        UtilText.returnStringAtRandom(
                                (Main.sex.getCharacterTargetedForSexAction(this).hasHair() && Main.sex.getCharacterTargetedForSexAction(this).getHairRawLengthValue() > HairLength.THREE_SHOULDER_LENGTH.getMaximumValue()
                                        ? "Reaching up and roughly taking a fistful of [npc2.namePos] [npc2.hair+], [npc.name] [npc.verb(force)] [npc2.her] head down into [npc3.namePos] groin while ordering [npc2.herHim] to choke on [npc3.her] [npc3.cock+]."
                                        : ""),
                                "Roughly grabbing hold of the sides of [npc2.namePos] head, [npc.name] violently [npc.verb(slam)] [npc2.namePos] head up and down, forcing [npc2.herHim] to [npc2.verb(continue)] sucking [npc3.namePos] [npc3.cock+].",
                                "Snarling a series of lewd, degrading remarks into [npc2.her] [npc2.ear], [npc.name] [npc.verb(slam)] [npc2.namePos] head into [npc3.namePos] crotch,"
                                        + " thereby forcing [npc2.herHim] to take [npc3.her] [npc3.cock+] fully into [npc2.her] mouth."));

            } else {
                return UtilText.parse(
                        Util.newArrayListOfValues(
                                Main.sex.getCharacterPerformingAction(),
                                Main.sex.getCharacterTargetedForSexAction(this),
                                getBlowjobReceiver()),
                        UtilText.returnStringAtRandom(
                                (Main.sex.getCharacterTargetedForSexAction(this).hasHair() && Main.sex.getCharacterTargetedForSexAction(this).getHairRawLengthValue() > HairLength.THREE_SHOULDER_LENGTH.getMaximumValue()
                                        ? "Reaching up and running [npc.her] [npc.fingers+] through [npc2.namePos] [npc2.hair+],"
                                        + " [npc.name] [npc.verb(gather)] it up and [npc.verb(hold)] " + (Main.sex.getCharacterTargetedForSexAction(this).getHairType().isDefaultPlural(Main.sex.getCharacterTargetedForSexAction(this)) ? "them" : "it")
                                        + " out of the way as [npc2.name] [npc2.verb(continue)] to give [npc3.name] a blowjob."
                                        : ""),
                                "Taking hold of the sides of [npc2.namePos] head, [npc.name] [npc.verb(help)] to lift and push [npc2.her] head up and down, helping [npc2.herHim] to continue sucking on [npc3.namePos] [npc3.cock+].",
                                "Giving [npc2.herHim] some lewd words of encouragement, [npc.name] [npc.verb(help)] to push [npc2.namePos] head into [npc3.namePos] crotch,"
                                        + " thereby ensuring that [npc2.she] [npc2.verb(take)] [npc3.her] [npc3.cock+] fully into [npc2.her] mouth."));
            }
        }
    };

    //TODO assist cunnilingus

}
