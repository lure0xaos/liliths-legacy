package com.lilithslegacy.game.sex.sexActions.submission;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.fetishes.AbstractFetish;
import com.lilithslegacy.game.character.fetishes.Fetish;
import com.lilithslegacy.game.character.npc.submission.Takahashi;
import com.lilithslegacy.game.sex.ArousalIncrease;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.positions.slots.SexSlotGeneric;
import com.lilithslegacy.game.sex.sexActions.SexAction;
import com.lilithslegacy.game.sex.sexActions.SexActionPriority;
import com.lilithslegacy.game.sex.sexActions.SexActionType;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.List;

/**
 * @author Innoxia
 * @version 0.2.12
 * @since 0.2.12
 */
public class CitadelYoukoSA {

    public static final SexAction ORGASM_DENIED = new SexAction(
            SexActionType.ORGASM_DENIAL,
            ArousalIncrease.NEGATIVE_MAJOR,
            ArousalIncrease.ZERO_NONE,
            CorruptionLevel.ZERO_PURE,
            null,
            SexParticipantType.NORMAL) {

        @Override
        public String getActionTitle() {
            return "Denied";
        }

        @Override
        public String getActionDescription() {
            return "As you prepare to reach your climax, [citadelArcanist.name] suddenly holds you in place, and forces you to calm down!";
        }

        @Override
        public boolean isBaseRequirementsMet() {
            return !Main.sex.getCharacterPerformingAction().equals(Main.game.getNpc(Takahashi.class))
                    && Main.sex.getSexPositionSlot(Main.sex.getCharacterPerformingAction()) != SexSlotGeneric.MISC_WATCHING;
        }

        @Override
        public String getDescription() {
            return "[npc.Name] [npc.verb(feel)] a desperate heat rising in [npc.her] groin, and, as [npc.she] [npc.verb(prepare)] to orgasm, [npc.she] [npc.verb(cry)] out, [npc.speech(Yes! I'm going to cum!)]"
                    + "<br/><br/>"
                    + "Upon hearing this, [citadelArcanist.name] lets out an amused giggle, [citadelArcanist.speechNoEffects(You silly thing! Didn't I tell you that you're not allowed to orgasm?!)]"
                    + "<br/><br/>"
                    + "Before [npc.name] can say anything, [citadelArcanist.name] uses her arcane power to telekinetically force [npc.herHim] to remain still."
                    + " [npc.Name] [npc.verb(try)] to desperately rub [npc.her] crotch up against [citadelArcanist.namePos] leg, but the tricky youko simply steps back, and patiently waits until [npc.namePos] climax has ebbed away."
                    + " Once it's clear that [npc.sheHas] calmed down, [citadelArcanist.name] releases [npc.herHim], and, pushing her pussy back up against [npc.her] face, she teases,"
                    + " [citadelArcanist.speechNoEffects(Silly! This is all about my pleasure, not yours!)]";
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public List<AbstractFetish> getFetishes(GameCharacter character) {
            if (character.isPlayer()) {
                return Util.newArrayListOfValues(Fetish.FETISH_DENIAL_SELF);
            } else {
                return Util.newArrayListOfValues(Fetish.FETISH_DENIAL);
            }
        }
    };

}
