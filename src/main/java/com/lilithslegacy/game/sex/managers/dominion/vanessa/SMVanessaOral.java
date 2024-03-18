package com.lilithslegacy.game.sex.managers.dominion.vanessa;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.dominion.Vanessa;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;

import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.4
 * @since 0.3.2
 */
public class SMVanessaOral extends SexManagerDefault {

    public SMVanessaOral(AbstractSexPosition position, Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(position,
                dominants,
                submissives);
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
        return false;
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        if (Main.sex.isDom(Main.game.getPlayer())) {
            return Util.newHashMapOfValues(new Value<>(Main.game.getPlayer(), Util.newArrayListOfValues(CoverableArea.VAGINA)));
        }
        return Util.newHashMapOfValues(new Value<>(Main.game.getNpc(Vanessa.class), Util.newArrayListOfValues(CoverableArea.VAGINA)));
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        if (character.isPlayer() && !Main.sex.isDom(character)) {
            return SexControl.ONGOING_ONLY;
        }
        return super.getSexControl(character);
    }

    @Override
    public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing) {
        return false;
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
        // When player is performing cunnilingus, she stops after orgasming once:
        if (Main.sex.isDom(partner)) {
            return Main.sex.getNumberOfOrgasms(partner) >= 1;
        }

        // When player is receiving cunnilingus, or fucking Vanessa, she stops after both of you are satisfied:
        return Main.sex.isSatisfiedFromOrgasms(Main.game.getPlayer(), true) && Main.sex.isSatisfiedFromOrgasms(Main.game.getNpc(Vanessa.class), true);
    }

}
