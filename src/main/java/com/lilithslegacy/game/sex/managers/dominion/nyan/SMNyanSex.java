package com.lilithslegacy.game.sex.managers.dominion.nyan;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.NPC;
import com.lilithslegacy.game.character.npc.dominion.Nyan;
import com.lilithslegacy.game.character.npc.dominion.NyanMum;
import com.lilithslegacy.game.dialogue.DialogueFlagValue;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.SexAreaInterface;
import com.lilithslegacy.game.sex.SexAreaOrifice;
import com.lilithslegacy.game.sex.SexAreaPenetration;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.SexPace;
import com.lilithslegacy.game.sex.SexParticipantType;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.game.sex.managers.OrgasmEncourageBehaviour;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.game.sex.sexActions.SexActionInterface;
import com.lilithslegacy.game.sex.sexActions.baseActions.PenisBreasts;
import com.lilithslegacy.game.sex.sexActions.baseActions.PenisMouth;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.4
 * @since 0.4
 */
public class SMNyanSex extends SexManagerDefault {

    final AbstractSexPosition allowedPosition;

    public SMNyanSex(AbstractSexPosition position, Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(position,
                dominants,
                submissives);
        this.allowedPosition = position;
    }

    public SMNyanSex(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        this(SexPosition.LYING_DOWN, dominants, submissives);
    }

    public SexActionInterface getPartnerSexAction(NPC partner, SexActionInterface sexActionPlayer) {
        // To restart penetration actions if player pulled out during orgasm:
        if (partner instanceof Nyan && getSexControl(Main.game.getPlayer()) != SexControl.FULL) {
            if (getForeplayPreference(partner, Main.game.getPlayer()).equals(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.MOUTH, SexAreaPenetration.PENIS))
                    && !Main.sex.isOrificeNonSelfOngoingAction(partner, SexAreaOrifice.MOUTH)) {
                return PenisMouth.GIVING_BLOWJOB_START;
            }
            if (getForeplayPreference(partner, Main.game.getPlayer()).equals(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.BREAST, SexAreaPenetration.PENIS))
                    && !Main.sex.getOngoingSexAreas(partner, SexAreaOrifice.BREAST, Main.game.getPlayer()).contains(SexAreaPenetration.PENIS)) {
                return PenisBreasts.USING_COCK_START;
            }
        }
        return super.getPartnerSexAction(partner, sexActionPlayer);
    }

    @Override
    public SexPace getForcedSexPace(GameCharacter character) {
        if (!character.isPlayer()) {
            if (Main.sex.getSexPace(character).isDom()) {
                return SexPace.DOM_NORMAL;
            }
            return SexPace.SUB_EAGER;
        }
        return null;
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        if (character.isPlayer()) {
            return SexControl.ONGOING_PLUS_LIMITED_PENETRATIONS;
        }
        return SexControl.ONGOING_ONLY;
    }

    @Override
    public SexType getForeplayPreference(GameCharacter character, GameCharacter targetedCharacter) {
        return this.getMainSexPreference(character, targetedCharacter);
    }

    @Override
    public boolean isPlayerAbleToStopSex() {
        return false;
    }

    @Override
    public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing) {
        return false;
    }

    @Override
    public boolean isAbleToRemoveSelfClothing(GameCharacter character) {
        return character.isPlayer(); // Nyan does not remove her own clothing
    }

    @Override
    public boolean isEndSexAffectionChangeEnabled(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isForceCreampieAllowed(GameCharacter characterOrgasming, GameCharacter characterRecevingCreampie) {
        return false; // Nyan nor Leotie force creampies
    }

    @Override
    public OrgasmEncourageBehaviour getCharacterOrgasmEncourageBehaviour(GameCharacter character, GameCharacter characterOrgasming, GameCharacter characterPenetrated) {
        if (character instanceof Nyan) {
            return OrgasmEncourageBehaviour.NO_ENCOURAGE;
        }
        if (character instanceof NyanMum) {
            return OrgasmEncourageBehaviour.CREAMPIE;
        }
        return OrgasmEncourageBehaviour.DEFAULT;
    }


    @Override
    public Map<GameCharacter, List<SexAreaInterface>> getAreasBannedMap() {
        Map<GameCharacter, List<SexAreaInterface>> bannedMap = new HashMap<>();

        for (GameCharacter participant : Main.sex.getAllParticipants()) {
            if ((participant instanceof Nyan && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.nyanAnalTalk))
                    || (participant instanceof NyanMum && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.nyanmumAnalTalk))
                    || participant.isPlayer()) {
                bannedMap.put(participant, Util.newArrayListOfValues(SexAreaOrifice.ANUS));
            }
        }

        return bannedMap;
    }

    @Override
    public List<AbstractSexPosition> getAllowedSexPositions() {
        //return Util.newArrayListOfValues(allowedPosition);

        return Util.newArrayListOfValues(
                SexPosition.AGAINST_WALL,
                SexPosition.ALL_FOURS,
                SexPosition.LYING_DOWN,
                SexPosition.STANDING,
                SexPosition.SITTING);
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return getSexControl(character) == SexControl.FULL;
    }

    @Override
    public boolean isItemUseAvailable() {
        return false;
    }

    @Override
    public boolean isAbleToEquipSexClothing(GameCharacter equippingCharacter, GameCharacter targetedCharacter, AbstractClothing clothingToEquip) {
        return clothingToEquip.isCondom();
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        Map<GameCharacter, List<CoverableArea>> map = new HashMap<>();
        for (GameCharacter participant : Main.sex.getAllParticipants()) {
            if (!participant.isPlayer()) {
                map.put(participant, Util.newArrayListOfValues(CoverableArea.VAGINA));
            }
        }
        return map;
    }

    /**
     * @return true if this character should prefer to remove their clothes in the clothing removal setup to this sex scene. (Returns false by default.)
     */
    @Override
    public boolean isExposeAtStartOfSexMapRemoval(GameCharacter character) {
        return true;
    }

    @Override
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
        for (GameCharacter participant : Main.sex.getAllParticipants()) {
            if (!Main.sex.isSatisfiedFromOrgasms(participant, true)) {
                return false;
            }
        }
        return true;
    }
}
