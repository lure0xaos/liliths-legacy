package com.lilithslegacy.game.sex.sexActions.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.npc.dominion.Daddy;
import com.lilithslegacy.game.character.npc.dominion.Lilaya;
import com.lilithslegacy.game.sex.ArousalIncrease;
import com.lilithslegacy.game.sex.OrgasmCumTarget;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.managers.dominion.SMDaddyDinnerOral;
import com.lilithslegacy.game.sex.positions.slots.SexSlotTag;
import com.lilithslegacy.game.sex.sexActions.SexAction;
import com.lilithslegacy.game.sex.sexActions.SexActionPriority;
import com.lilithslegacy.game.sex.sexActions.SexActionType;
import com.lilithslegacy.game.sex.sexActions.baseActionsMisc.GenericOrgasms;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.List;

/**
 * @author Innoxia
 * @version 0.3.3.10
 * @since 0.3.3.10
 */
public class DaddySexActions {

    public static final SexAction ORAL_STOP_SEX = new SexAction(
            SexActionType.ONGOING,
            ArousalIncrease.ONE_MINIMUM,
            ArousalIncrease.ONE_MINIMUM,
            CorruptionLevel.ZERO_PURE,
            null,
            SexParticipantType.NORMAL) {

        @Override
        public String getActionTitle() {
            return "Waitress approaching";
        }

        @Override
        public String getActionDescription() {
            return "";
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return (Main.sex.getCharacterPerformingAction() instanceof Daddy)
                    && Main.sex.getSexManager().isPartnerWantingToStopSex(Main.sex.getCharacterPerformingAction())
                    && (Main.sex.getSexManager() instanceof SMDaddyDinnerOral);
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public String getDescription() {
            boolean lilayaPresent = Main.game.getCharactersPresent().contains(Main.game.getNpc(Lilaya.class));
            return "Now that [daddy.nameHasFull] been satisfied, [daddy.she] suddenly seems a lot more aware of [npc.her] surroundings, and, leaning down a little so as to speak to you"
                    + (lilayaPresent ? " and Lilaya" : "")
                    + " under the table, [npc.she] hisses,"
                    + " [daddy.speechNoEffects(I think one of the waitresses knows what's going on! Sit back up!)]"
                    + "<br/><br/>"
                    + "Disappointed that [daddy.name] wants to end this so soon, " + (lilayaPresent ? "the two of" : "") + " you sulkily slide back up into your " + (lilayaPresent ? "seats" : "seat") + " from under the table,"
                    + " before making a little show of licking your lips and grinning mischievously at [daddy.herHim]."
                    + " Sure enough, just as you've finished making this slutty display, you see one of the waitresses walking over towards you...";
        }

        @Override
        public void applyEffects() {
            Main.sex.addRemoveEndSexAffection(Main.game.getNpc(Lilaya.class));
        }

        @Override
        public boolean endsSex() {
            return true;
        }
    };

    public static final SexAction GENERIC_ORGASM_BACK = new SexAction(GenericOrgasms.GENERIC_ORGASM_FLOOR) {
        @Override
        public SexParticipantType getParticipantType() {
            return SexParticipantType.NORMAL;
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return Main.sex.getOngoingCharactersUsingAreas(Main.sex.getCharacterPerformingAction(), SexAreaPenetration.PENIS, SexAreaOrifice.VAGINA).contains(Main.game.getNpc(Lilaya.class))
                    && Main.game.getNpc(Lilaya.class).getFetishDesire(Fetish.FETISH_PREGNANCY).isNegative()
                    && !Main.sex.getSexPositionSlot(Main.sex.getCharacterPerformingAction()).hasTag(SexSlotTag.LYING_DOWN);
        }

        @Override
        public String getActionTitle() {
            if (!Main.sex.getCharactersHavingOngoingActionWith(Main.sex.getCharacterPerformingAction(), SexAreaPenetration.PENIS).isEmpty()) {
                if (!Main.sex.getOngoingCharactersUsingAreas(Main.sex.getCharacterPerformingAction(), SexAreaPenetration.PENIS, SexAreaPenetration.FINGER).isEmpty()) {
                    return "Handjob onto back";
                }
                return "Pull out (back)";
            }
            return "Cum on back";
        }

        @Override
        public String getActionDescription() {
            return "You've reached your climax, and can't hold back your orgasm any longer. Direct your cum onto [npc2.namePos] back.";
        }

        @Override
        public String getDescription() {
            return Main.sex.getCharacterPerformingAction().getSexActionOrgasmOverride(this, OrgasmCumTarget.BACK, false).getDescription();
        }

        @Override
        public void applyEffects() {
            GenericOrgasms.applyGenericPullOutEffects(this, OrgasmCumTarget.BACK);
        }

        @Override
        public List<CoverableArea> getAreasCummedOn(GameCharacter cumProvider, GameCharacter cumTarget) {
            if (cumProvider.equals(Main.sex.getCharacterPerformingAction()) && cumTarget.equals(Main.sex.getTargetedPartner(Main.sex.getCharacterPerformingAction()))) {
                return Util.newArrayListOfValues(
                        CoverableArea.BACK);
            }
            return null;
        }

        @Override
        public boolean endsSex() {
            return Main.sex.getCharacterPerformingAction().getSexActionOrgasmOverride(this, OrgasmCumTarget.BACK, true).isEndsSex();
        }
    };


    public static final SexAction GENERIC_ORGASM_SELF_STOMACH = new SexAction(GenericOrgasms.GENERIC_ORGASM_FLOOR) {

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return Main.sex.getOngoingCharactersUsingAreas(Main.sex.getCharacterPerformingAction(), SexAreaPenetration.PENIS, SexAreaOrifice.VAGINA).contains(Main.game.getNpc(Lilaya.class))
                    && Main.game.getNpc(Lilaya.class).getFetishDesire(Fetish.FETISH_PREGNANCY).isNegative()
                    && Main.sex.getSexPositionSlot(Main.sex.getCharacterPerformingAction()).hasTag(SexSlotTag.LYING_DOWN);
        }

        @Override
        public String getActionTitle() {
            if (!Main.sex.getCharactersHavingOngoingActionWith(Main.sex.getCharacterPerformingAction(), SexAreaPenetration.PENIS).isEmpty()) {
                if (!Main.sex.getOngoingCharactersUsingAreas(Main.sex.getCharacterPerformingAction(), SexAreaPenetration.PENIS, SexAreaPenetration.FINGER).isEmpty()) {
                    return "Handjob onto own stomach";
                }
                return "Pull out (own stomach)";
            }
            return "Cum on your stomach";
        }

        @Override
        public String getActionDescription() {
            return "You've reached your climax, and can't hold back your orgasm any longer. Direct your cum onto your stomach.";
        }

        @Override
        public String getDescription() {
            return Main.sex.getCharacterPerformingAction().getSexActionOrgasmOverride(this, OrgasmCumTarget.SELF_STOMACH, false).getDescription();
        }

        @Override
        public void applyEffects() {
            GenericOrgasms.applyGenericPullOutEffects(this, OrgasmCumTarget.SELF_STOMACH);
        }

        @Override
        public List<CoverableArea> getAreasCummedOn(GameCharacter cumProvider, GameCharacter cumTarget) {
            if (cumProvider.equals(Main.sex.getCharacterPerformingAction()) && cumProvider.equals(cumTarget)) {
                return Util.newArrayListOfValues(
                        CoverableArea.STOMACH);
            }
            return null;
        }

        @Override
        public boolean endsSex() {
            return Main.sex.getCharacterPerformingAction().getSexActionOrgasmOverride(this, OrgasmCumTarget.SELF_STOMACH, true).isEndsSex();
        }
    };
}
