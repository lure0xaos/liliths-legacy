package com.lilithslegacy.game.sex.managers.submission;

import com.lilithslegacy.game.character.GameCharacter;
import com.lilithslegacy.game.character.body.CoverableArea;
import com.lilithslegacy.game.character.npc.submission.Axel;
import com.lilithslegacy.game.sex.SexType;
import com.lilithslegacy.game.sex.managers.SexManagerDefault;
import com.lilithslegacy.game.sex.positions.AbstractSexPosition;
import com.lilithslegacy.game.sex.positions.slots.SexSlot;
import com.lilithslegacy.main.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Innoxia
 * @version 0.3.5.5
 * @since 0.3.5.5
 */
public class SMAxel extends SexManagerDefault {

    private SexType axelSexTypePreference;
    private List<CoverableArea> exposeAxelAreas;
    private List<CoverableArea> exposePlayerAreas;

    public SMAxel(AbstractSexPosition position,
                  SexType axelSexTypePreference,
                  List<CoverableArea> exposeAxelAreas,
                  List<CoverableArea> exposePlayerAreas,
                  Map<GameCharacter, SexSlot> dominants,
                  Map<GameCharacter, SexSlot> submissives) {
        super(position,
                dominants,
                submissives);
        this.axelSexTypePreference = axelSexTypePreference;
        this.exposeAxelAreas = exposeAxelAreas;
        this.exposePlayerAreas = exposePlayerAreas;
    }

    @Override
    public Map<GameCharacter, List<CoverableArea>> exposeAtStartOfSexMap() {
        Map<GameCharacter, List<CoverableArea>> map = new HashMap<>();
        map.put(Main.game.getNpc(Axel.class), exposeAxelAreas);
        map.put(Main.game.getPlayer(), exposePlayerAreas);
        return map;
    }

    @Override
    public boolean isSwapPositionAllowed(GameCharacter character, GameCharacter target) {
        return character.isPlayer() || !Main.sex.isInForeplay(character);
    }

    @Override
    public boolean isPositionChangingAllowed(GameCharacter character) {
        return character.isPlayer() || !Main.sex.isInForeplay(character);
    }

    @Override
    public SexType getForeplayPreference(GameCharacter character, GameCharacter targetedCharacter) {
        if (Main.sex.isDom(character)) {
            return axelSexTypePreference;
        }
        return character.getForeplayPreference(targetedCharacter);
    }

}
