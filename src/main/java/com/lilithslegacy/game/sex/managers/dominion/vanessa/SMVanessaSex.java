package com.lilithslegacy.game.sex.managers.dominion.vanessa;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.dominion.Vanessa;
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
public class SMVanessaSex extends SexManagerDefault {

    public SMVanessaSex(AbstractSexPosition position, Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(position,
                dominants,
                submissives);
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        return Util.newHashMapOfValues(
                new Value<>(Main.game.getPlayer(), Util.newArrayListOfValues(CoverableArea.PENIS)),
                new Value<>(Main.game.getNpc(Vanessa.class), Util.newArrayListOfValues(CoverableArea.VAGINA)));
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
        // She stops after both of you are satisfied:
        return Main.sex.isSatisfiedFromOrgasms(Main.game.getPlayer(), true) && Main.sex.isSatisfiedFromOrgasms(Main.game.getNpc(Vanessa.class), true);
    }

}
