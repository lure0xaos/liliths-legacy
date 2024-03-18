package com.lilithslegacy.game.sex.managers.dominion.zaranix;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.dominion.Zaranix;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;
import com.lilithslegacy.utils.Util.Value;

import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.1.95
 */
public class SMZaranixCockSucking extends SexManagerDefault {

    public SMZaranixCockSucking(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.SITTING,
                dominants,
                submissives);
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        return Util.newHashMapOfValues(
                new Value<>(Main.game.getPlayer(),
                        Util.newArrayListOfValues(
                                CoverableArea.MOUTH)),
                new Value<>(Main.game.getNpc(Zaranix.class),
                        Util.newArrayListOfValues(
                                CoverableArea.PENIS,
                                Main.game.getNpc(Zaranix.class).isFeminine()
                                        ? CoverableArea.NIPPLES
                                        : null)));
    }

    @Override
    public SexType getForeplayPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (character.equals(Main.game.getNpc(Zaranix.class))) {
            return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH);
        }
        return character.getForeplayPreference(targetedCharacter);
    }

    @Override
    public SexType getMainSexPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (character.equals(Main.game.getNpc(Zaranix.class))) {
            return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.MOUTH);
        }
        return character.getMainSexPreference(targetedCharacter);
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
    public SexControl getSexControl(GameCharacter character) {
        if (character.isPlayer()) {
            return SexControl.ONGOING_ONLY;
        }
        return super.getSexControl(character);
    }

    @Override
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
        return partner.equals(Main.game.getNpc(Zaranix.class))
                && Main.sex.getNumberOfOrgasms(Main.game.getNpc(Zaranix.class)) >= Main.game.getNpc(Zaranix.class).getOrgasmsBeforeSatisfied();
    }

    @Override
    public boolean isAbleToEquipSexClothing(GameCharacter equippingCharacter, GameCharacter targetedCharacter, AbstractClothing clothingToEquip) {
        return clothingToEquip.isCondom();
    }

    @Override
    public boolean isAbleToRemoveSelfClothing(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing) {
        return false;
    }
}
