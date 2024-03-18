package com.lilithslegacy.game.sex.sexActions.dominion;

import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.race.Race;
import com.lilithslegacy.game.dialogue.npcDialogue.dominion.EnforcerAlleywayDialogue;
import com.lilithslegacy.game.sex.ArousalIncrease;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.sexActions.SexAction;
import com.lilithslegacy.game.sex.sexActions.SexActionPriority;
import com.lilithslegacy.game.sex.sexActions.SexActionType;
import com.lilithslegacy.main.Main;

/**
 * @author Innoxia
 * @version 0.3.8.3
 * @since 0.3.8.3
 */
public class EnforcerPatrolSA {

    public static final SexAction DEMON_TF_REACTION = new SexAction(
            SexActionType.ONGOING,
            ArousalIncrease.ONE_MINIMUM,
            ArousalIncrease.ONE_MINIMUM,
            CorruptionLevel.ZERO_PURE,
            null,
            SexParticipantType.NORMAL) {
        @Override
        public boolean isBaseRequirementsMet() {
            return !Main.sex.getCharacterPerformingAction().isPlayer()
                    && !Main.sex.isSpectator(Main.sex.getCharacterPerformingAction())
                    && !EnforcerAlleywayDialogue.isDemonRevealed()
                    && Main.game.getPlayer().getRace() == Race.DEMON;
        }

        @Override
        public String getActionTitle() {
            return "React to Demon";
        }

        @Override
        public String getActionDescription() {
            return "";
        }

        @Override
        public SexActionPriority getPriority() {
            return SexActionPriority.UNIQUE_MAX;
        }

        @Override
        public String getDescription() {
            return "Upon seeing you self-transform into your demonic form, [npc.name] lets out a shocked gasp and exclaims,"
                    + " [npc.speechNoExtraEffects(You're a demon?! I'm sorry, [pc.maam], I-I didn't know!)]"
                    + "<br/><br/>"
                    + "Pushing back against the [npc.race], you moan, [pc.speech(Come on, don't stop now! Carry on treating me like I'm a dirty little human!)]"
                    + "<br/><br/>"
                    + "Doing as [npc.sheIs] told, the Enforcer continues with your 'cavity search', [npc.moaning], [npc.speech(If that's what you want, then that's fine with me...)]";
        }

        @Override
        public void applyEffects() {
            EnforcerAlleywayDialogue.setDemonRevealed(true);
        }
    };
}
