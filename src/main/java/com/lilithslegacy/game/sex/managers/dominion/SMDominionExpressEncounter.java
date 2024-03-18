package com.lilithslegacy.game.sex.managers.dominion;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.dialogue.utils.UtilText;
import com.lilithslegacy.game.inventory.clothing.AbstractClothing;
import com.lilithslegacy.game.sex.SexControl;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;
import com.lilithslegacy.utils.colours.PresetColour;
import com.lilithslegacy.world.WorldType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.4.2.2
 * @since 0.3.7.7
 */
public class SMDominionExpressEncounter extends SexManagerDefault {

    private final Map<GameCharacter, SexType> preferences;
    private final Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap;

    public SMDominionExpressEncounter(AbstractSexPosition position,
                                      Map<GameCharacter, SexSlot> dominants,
                                      Map<GameCharacter, SexSlot> submissives,
                                      Map<GameCharacter, SexType> preferences,
                                      Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap) {
        super(position,
                dominants,
                submissives);
        this.preferences = preferences;
        this.exposeAtStartOfSexMap = exposeAtStartOfSexMap;
    }

    @Override
    public boolean isPublicSex() {
        return true;
    }

    @Override
    public SexControl getSexControl(GameCharacter character) {
        if (!Main.sex.isDom(character)) {
            return SexControl.ONGOING_ONLY; // So the player can't start anything else.
        }
        return super.getSexControl(character);
    }

    @Override
    public boolean isAbleToEquipSexClothing(GameCharacter equippingCharacter, GameCharacter targetedCharacter, AbstractClothing clothingToEquip) {
        return clothingToEquip.isCondom();
    }

    @Override
    public boolean isAbleToRemoveSelfClothing(GameCharacter character) {
        return true;
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
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        return exposeAtStartOfSexMap;
    }

    @Override
    public List<CoverableArea> getAdditionalAreasToExposeDuringSex(GameCharacter performer, GameCharacter target) {
        return new ArrayList<>();
    }

    @Override
    public SexType getForeplayPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (preferences.containsKey(character)) {
            return preferences.get(character);
        }
        return super.getForeplayPreference(character, targetedCharacter);
    }

    @Override
    public SexType getMainSexPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (!character.isPlayer()) {
            return getForeplayPreference(character, targetedCharacter);
        }
        return character.getMainSexPreference(targetedCharacter);
    }

    @Override
    public String getPublicSexStartingDescription() {
        if (Main.game.getPlayer().getWorldLocation() == WorldType.DOMINION_EXPRESS) {
            return "<p style='color:" + PresetColour.BASE_ORANGE.toWebHexString() + "; font-style:italic; text-align:center;'>"
                    + "A passing centaur slave looks over and smirks as he sees you submitting to [npc.name]..."
                    + "</p>";
        }

        return super.getPublicSexStartingDescription();
    }

    @Override
    public String getRandomPublicSexDescription() {
        if (Main.game.getPlayer().getWorldLocation() == WorldType.DOMINION_EXPRESS) {
            return "<p style='color:" + PresetColour.BASE_ORANGE.toWebHexString() + "; font-style:italic; text-align:center;'>"
                    + UtilText.parse(Main.sex.getTargetedPartner(Main.game.getPlayer()),
                    UtilText.returnStringAtRandom(
                            "A couple of centauresses look over at you and giggle as they pass by.",
                            "A centaur slave hastily trots past, too busy to pay you any attention.",
                            "A centauress slave hastily trots past, too busy to pay you any attention.",
                            "A group of centaur slaves take a moment to stop and comment on your performance, before continuing on their way to the stables.",
                            "You hear a centauress letting out an amused laugh from somewhere behind you.",
                            "You hear a centaur letting out an amused grunt from somewhere behind you.",
                            "You glance across to see a bronze-ranked filly curiously watching to see how you service centaurs.",
                            "A muscular centaur slave pauses to comment on your lewd display, before trotting off on his way to work.",
                            "A busty centauress slave pauses to comment on your lewd display, before trotting off on her way to work."))
                    + "</p>";

        }
        return super.getRandomPublicSexDescription();
    }
}
