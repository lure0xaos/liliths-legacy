package com.lilithslegacy.game.dialogue.responses;

import java.util.List;

import com.lilithslegacy.game.character.attributes.CorruptionLevel;
import com.lilithslegacy.game.character.body.valueEnums.Femininity;
import com.lilithslegacy.game.character.effects.AbstractPerk;
import com.lilithslegacy.game.character.fetishes.AbstractFetish;
import com.lilithslegacy.game.character.race.AbstractSubspecies;

/**
 * A Response class that does not progress to a new DialogueNode.<br/><br/>
 * <p>
 * As a result of there being no defined DialogueNode, no time will be passed unless you override this Response's getSecondsPassed() method.
 *
 * @author Innoxia
 * @version 0.4
 * @since 0.1.69
 */
public class ResponseEffectsOnly extends Response {

    public ResponseEffectsOnly(String title, String tooltipText) {
        this(title, tooltipText, null, null, null, null, null);
    }

    public ResponseEffectsOnly(String title,
                               String tooltipText,
                               List<AbstractFetish> fetishesForUnlock,
                               CorruptionLevel corruptionBypass,
                               List<AbstractPerk> perksRequired,
                               Femininity femininityRequired,
                               List<AbstractSubspecies> subspeciesRequired) {
        super(title,
                tooltipText,
                null,
                fetishesForUnlock,
                corruptionBypass,
                perksRequired,
                femininityRequired,
                subspeciesRequired);
    }

    public ResponseEffectsOnly(String title,
                               String tooltipText,
                               String secondsPassedString,
                               boolean asMinutes,
                               String colourId,
                               String effectsString,
                               List<String> fetishesForUnlockId,
                               String corruptionBypassId,
                               List<String> perksRequiredId,
                               String femininityRequiredId,
                               List<String> subspeciesRequiredId) {
        super(title,
                tooltipText,
                null,
                secondsPassedString,
                asMinutes,
                colourId,
                effectsString,
                fetishesForUnlockId,
                corruptionBypassId,
                perksRequiredId,
                femininityRequiredId,
                subspeciesRequiredId);
    }

    @Override
    public boolean disabledOnNullDialogue() {
        return false;
    }
}
