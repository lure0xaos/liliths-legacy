package com.lilithslegacy.game.sex.managers.dominion.cultist;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.attributes.ArousalLevel;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.ImmobilisationType;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPositionUnique;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.game.sex.sexActions.SexActionInterface;
import com.lilithslegacy.game.sex.sexActions.baseActionsMisc.GenericActions;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util.Value;

import java.util.Map;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.1.88
 */
public class SMAltarMissionary extends SexManagerDefault {

    public SMAltarMissionary(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPositionUnique.MISSIONARY_ALTAR_CULTIST,
                dominants,
                submissives);
    }

    @Override
    public SexActionInterface getPartnerSexAction(NPC partner, SexActionInterface sexActionPlayer) {
        // If orgasming, use an orgasm action:
        if (ArousalLevel.getArousalLevelFromValue(Main.sex.getCharacterPerformingAction().getArousal()) == ArousalLevel.FIVE_ORGASM_IMMINENT) {
            return super.getPartnerSexAction(partner, sexActionPlayer);
        }

        Value<ImmobilisationType, GameCharacter> value = Main.sex.getImmobilisationType(Main.sex.getCharacterPerformingAction());
        if (value != null && value.getKey() == ImmobilisationType.WITCH_SEAL) {
            return GenericActions.WITCH_SEALED;

        } else {
            return super.getPartnerSexAction(partner, sexActionPlayer);
        }
    }

    @Override
    public boolean isAbleToRemoveSelfClothing(GameCharacter character) {
        return !Main.sex.isCharacterImmobilised(character);
    }

    @Override
    public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing) {
        return !Main.sex.isCharacterImmobilised(character);
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        if (!Main.sex.isDom(character) && character.isPlayer()) {
            return SexControl.ONGOING_ONLY;
        }
        return super.getSexControl(character);
    }
}
