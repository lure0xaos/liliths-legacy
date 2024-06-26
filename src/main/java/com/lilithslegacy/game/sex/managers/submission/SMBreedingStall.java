package com.lilithslegacy.game.sex.managers.submission;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.*;
import com.lilithslegacy.game.sex.managers.OrgasmBehaviour;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.SexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.9.9
 * @since 0.2.7
 */
public class SMBreedingStall extends SexManagerDefault {

    public SMBreedingStall(Map<GameCharacter, SexSlot> dominants, Map<GameCharacter, SexSlot> submissives) {
        super(SexPosition.BREEDING_STALL,
                dominants,
                submissives);

        for (GameCharacter character : submissives.keySet()) {
            getAreasBannedMap().put(character, new ArrayList<>());
        }

        for (GameCharacter character : submissives.keySet()) {
            getAreasBannedMap().get(character).add(SexAreaOrifice.ANUS);
            getAreasBannedMap().get(character).add(SexAreaOrifice.MOUTH);
        }
    }

    @Override
    public boolean isPublicSex() {
        return false;
    }

    @Override
    public boolean isEndSexAffectionChangeEnabled(GameCharacter character) {
        return false;
    }

    @Override
    public SexType getForeplayPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (Main.sex.isDom(character)) {
            return new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, SexAreaOrifice.VAGINA);
        } else {
            return new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, SexAreaPenetration.PENIS);
        }
    }

    @Override
    public SexType getMainSexPreference(GameCharacter character, GameCharacter targetedCharacter) {
        return getForeplayPreference(character, targetedCharacter);
    }

    @Override
    public List<CoverableArea> getAdditionalAreasToExposeDuringSex(GameCharacter performer, GameCharacter target) {
        return Util.newArrayListOfValues();
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        Map<GameCharacter, List<CoverableArea>> map = new HashMap<>();

        for (GameCharacter dom : this.getDominants().keySet()) {
            map.put(dom, Util.newArrayListOfValues(CoverableArea.PENIS));
        }

        for (GameCharacter sub : this.getSubmissives().keySet()) {
            map.put(sub, Util.newArrayListOfValues(CoverableArea.VAGINA));
        }

        return map;
    }

    @Override
    public boolean isPartnerWantingToStopSex(GameCharacter partner) {
//        if(Main.sex.isDom(partner)) {
//            return Main.sex.getNumberOfOrgasms(partner)>=1;
//        }
        return Main.sex.getNumberOfOrgasms(Main.sex.getDominantParticipants(false).keySet().iterator().next()) >= 1;
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return false;
    }

    @Override
    public boolean isAbleToRemoveSelfClothing(GameCharacter character) {
        return getDominants().containsKey(character);
    }

    @Override
    public boolean isAbleToRemoveOthersClothing(GameCharacter character, AbstractClothing clothing) {
        return getDominants().containsKey(character);
    }

    @Override
    public boolean isAbleToEquipSexClothing(GameCharacter equippingCharacter, GameCharacter targetedCharacter, AbstractClothing clothingToEquip) {
        return false;
    }

    @Override
    public boolean isItemUseAvailable() {
        return false;
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return false;
    }

    @Override
    public OrgasmBehaviour getCharacterOrgasmBehaviour(GameCharacter character) {
        return OrgasmBehaviour.CREAMPIE;
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        if (!Main.sex.isDom(character)) {
            return SexControl.ONGOING_ONLY;
        }
        return super.getSexControl(character);
    }
}
